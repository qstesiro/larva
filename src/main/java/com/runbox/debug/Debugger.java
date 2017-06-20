package com.runbox.debug;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.*;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.EventIterator;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.event.VMStartEvent;
import com.sun.jdi.event.ThreadStartEvent;
import com.sun.jdi.event.ThreadDeathEvent;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.request.EventRequest;

import com.sun.tools.jdi.SocketAttachingConnector;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import com.runbox.manager.ConfigManager;

import com.runbox.debug.command.Command;
import com.runbox.debug.event.Event;
import com.runbox.debug.event.EventFactory;
import com.runbox.debug.manager.*;

import com.runbox.script.Engine;

public class Debugger implements SignalHandler {

    private Debugger() {

    }

    private static Debugger debugger = new Debugger();

    public static Debugger instance() {
        return debugger;
    }

    public void debug() throws Exception {
        Signal.handle(new Signal("INT"), this);        
        attach(); monitor(true);
        if (execute(ConfigManager.instance().script())) execute(); 
        loop(); monitor(false); exit();
    }
	
    private void attach() {
        List<AttachingConnector> list = Bootstrap.virtualMachineManager().attachingConnectors();
        for (AttachingConnector connector : list) {
            if (connector instanceof SocketAttachingConnector) {
                try {                    
                    Map<String, com.sun.jdi.connect.Connector.Argument> map = connector.defaultArguments();
                    map.get("hostname").setValue(ConfigManager.instance().ip());
                    map.get("port").setValue(String.valueOf(ConfigManager.instance().port()));
                    MachineManager.set(connector.attach(map));					
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalConnectorArgumentsException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void loop() throws Exception {
        EventQueue queue = MachineManager.instance().eventQueue();
        while (CONTINUE == flag) {
            EventSet set = null;
			try {
				final int TIMEOUT = 1000;
                set = queue.remove(TIMEOUT);
                if (null != set) {
                    EventIterator iterator = set.eventIterator();
                    while (iterator.hasNext()) {
						if (CONTINUE == flag) {
							handle(iterator);
							if (CONTINUE != flag) break;
						}
                    }
                }
            } catch (java.lang.NullPointerException e) {
				// this exception will be thrown in dalvik
				// when remove mehtod is invoked
				// if we set TRACE_EVENTS 
			} finally {
                if (null != set) {
                    set.resume();
                }
            }
        }
    }
    
    private void handle(EventIterator iterator) throws Exception {                
        com.sun.jdi.event.Event event = iterator.nextEvent();		
		if (null != event.request()) {
			MachineManager.instance().count(event.request(), true);
		}		
		ContextManager.instance().event(event); handle(event);
		if (null != event.request()) {
			MachineManager.instance().count(event.request(), false);
		}
    }	
	
	private void handle(com.sun.jdi.event.Event event) throws Exception {
		if (ClassManager.instance().need(event)) {
			if (ClassManager.instance().handle(event)) return;
		} else if (BreakpointManager.instance().need(event)) {			
			if (BreakpointManager.instance().handle(event)) return;
		} else if (ExecuteManager.instance().need(event)) {
			if (ExecuteManager.instance().handle(event)) return;
		} else if (ExceptionManager.instance().need(event)) {
			if (ExceptionManager.instance().handle(event)) return;
        }		
		if (execute(event)) execute();
	}	
		   
    private boolean execute(String file) {
		if (null != file) {
			try {
				return Engine.instance().execute(new File(file));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
    }

	private boolean execute(com.sun.jdi.event.Event object) throws Exception {
		Event event = EventFactory.build(object);
		if (event.handle()) {
			if (null != event.routine()) {
				return event.execute();
			}
			return true;
		}
		return false;
	}    

	
	
    private synchronized void execute() {
        while (true) {
            prompt();			
            try {
                if (!Engine.instance().execute(command() + ";")) return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }	

    private void prompt() {
        if (null == ContextManager.instance().current()) {
            System.out.print("0> ");
        } else {
            System.out.print(ContextManager.instance().current().uniqueID() + "> ");
        }
    }

	private String command = new String("");
	
	private String command() throws Exception {
		String command = new Scanner(System.in).nextLine();
		if (!command.equals("")) {
			this.command = command; return command;
		} else if (!this.command.equals("")) {
			return this.command;
		}
		throw new Exception("invalid command");
	}

    private void monitor(boolean flag) throws Exception {
        if (DISCONNECT != this.flag) {
            if (!flag) {
				ClassManager.instance().clean();
                BreakpointManager.instance().clean();
                ExecuteManager.instance().clean();
                ExceptionManager.instance().clean();
				ContextManager.instance().clean();
            }
			ClassManager.instance().monitor(flag);
            ExecuteManager.instance().monitor(flag);
            ExceptionManager.instance().monitor(flag);
        }
    }    

    private void exit() {
        if (QUIT == flag) {
			// app will not exit in davlik when VM is stopped by certain event
			// but app will exit successfuly when pressing Ctrl+C and execute quit
			// I wonder why, perhaps there is a bug in davlik
            MachineManager.instance().exit(0);			
        } else if (DETACH == flag) {
			MachineManager.instance().dispose();
		}
    }

    @Override
    public void handle(Signal signal) {
        MachineManager.instance().suspend();
        ContextManager.instance().store();
        ContextManager.instance().event(null); execute();
        ContextManager.instance().restore();
        MachineManager.instance().resume();		
    }

    public final static int CONTINUE = 0;
    public final static int QUIT = 1;
    public final static int DETACH = 2;
    public final static int DISCONNECT = 3;

    private int flag = CONTINUE;

    public void flag(int flag) {
        this.flag = flag;
    }

    public int flag() {
        return flag;
    }
}
