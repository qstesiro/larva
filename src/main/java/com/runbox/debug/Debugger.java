package com.runbox.debug;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.sun.jdi.Bootstrap;
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

import com.sun.tools.javac.util.Pair;
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
        // launch();
        attach();
        monitor(true);
        if (execute(ConfigManager.instance().get(ConfigManager.SCRIPT))) {
			execute();
		} loop();
        monitor(false);
        exit();
    }    

    // adb shell am start -D -n "com.example.administrator.datareport/com.example.administrator.datareport.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
    /*java -classpath ".\out\production\Debugger;D:\Program Files\Java\jdk1.8.0_45\lib\tools.jar" com.runbox.debug.Debugger*/
    /*"D:\Program Files\Java\jdk1.8.0_45\bin\java"
            -agentlib:jdwp=transport=dt_socket,address=localhost:5005,server=y,suspend=y
           -classpath ".\out\production\Debugger;D:\Program Files\Java\jdk1.8.0_45\lib\tools.jar" com.runbox.debug.Debugger*/
    private void launch() {
        LaunchingConnector connector = Bootstrap.virtualMachineManager().defaultConnector();
        Map<String, com.sun.jdi.connect.Connector.Argument> map = connector.defaultArguments();
        map.get("main").setValue("Demo");
        map.get("options").setValue("-classpath D:\\JetBrains\\IdeaProjects\\Demo\\out\\production\\Demo");
        try {
            MachineManager.set(connector.launch(map));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalConnectorArgumentsException e) {
            e.printStackTrace();
        } catch (VMStartException e) {
            e.printStackTrace();
        }
    }

    /*"D:\Program Files\Java\jdk1.8.0_45\bin\java" -agentlib:jdwp=transport=dt_socket,address=localhost:1025,server=y,suspend=y -classpath ".\out\production\Demo" Demo*/
    /*"D:\Program Files\Java\jdk1.8.0_45\bin\java" -agentlib:jdwp=transport=dt_socket,address=localhost:5005,server=y,suspend=y -classpath ".\target\classes\;D:\Program Files\Java\jdk1.8.0_45\lib\tools.jar" com.runbox.debug.Debugger -address localhost:1025 -script D:\program\maven\debug\debug.jdb*/
    // C:\Program Files\Java\jdk1.8.0_45\bin\java.exe\java.exe -agentlib:jdwp=transport=dt_socket,address=localhost:1025,server=y,suspend=y -classpath .\out\production\Demo Demo
    // java -classpath ".\target\classes\;D:\Program Files\Java\jdk1.8.0_45\lib\tools.jar" com.runbox.debug.Debugger -address localhost:1025 -script D:\program\maven\debug\debug.jdb
    private void attach() {
        List<AttachingConnector> list = Bootstrap.virtualMachineManager().attachingConnectors();
        for (AttachingConnector connector : list) {
            if (connector instanceof SocketAttachingConnector) {
                try {
                    Pair<String, String> pair = ConfigManager.instance().getAddress();
                    Map<String, com.sun.jdi.connect.Connector.Argument> map = connector.defaultArguments();
                    map.get("hostname").setValue(pair.fst);
                    map.get("port").setValue(pair.snd);
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
                        handle(iterator); if (CONTINUE != flag) break;
                    }
                }
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
			String command = new Scanner(System.in).nextLine();
            try {                
                if (!Engine.instance().execute(command + ";")) return;
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

    private void monitor(boolean flag) throws Exception {
        if (DISCONNECT != this.flag) {
            if (!flag) {
				ClassManager.instance().clean();
                BreakpointManager.instance().clean();
                ExecuteManager.instance().clean();
                ExceptionManager.instance().clean();
            }
			ClassManager.instance().monitor(flag);
            ExecuteManager.instance().monitor(flag);
            ExceptionManager.instance().monitor(flag);
        }
    }    

    private void exit() {
        if (QUIT == flag) {
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
