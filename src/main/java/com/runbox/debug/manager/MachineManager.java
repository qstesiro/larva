package com.runbox.debug.manager;

import java.util.List;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.event.EventQueue;

public class MachineManager extends Manager {

    private MachineManager() {

    }

    private static MachineManager instance = new MachineManager();

    public static MachineManager instance() {
        return instance;
    }

    private VirtualMachine machine = null;

    public static void set(VirtualMachine machine) {
        instance.machine = machine;
    }

	public static VirtualMachine get() {
		return instance.machine;
	}

	private final static String HOTSPOT = "hotspot";
	private final static String DALVIK = "dalvik";
	
	public boolean hotspot() {
		return name().toLowerCase().equals(HOTSPOT);
	}

	public boolean dalvik() {
		return name().toLowerCase().equals(DALVIK);
	}
	
	public EventRequestManager eventRequestManager() {
		if (null != machine) {
			return machine.eventRequestManager();
		}
		return null;
	}

	public EventQueue eventQueue() {
		if (null != machine) {
			return machine.eventQueue();
		}
		return null;
	}
	
	public List<ReferenceType> allClasses() {
		if (null != machine) {
			return machine.allClasses();
		}
		return null;
	}

	public List<ThreadReference> allThreads() {
		if (null != machine) {
			return machine.allThreads();
		}
		return null;
	}		
	
	private int count = 0;

	public void suspend() {
		if (null != machine) {
			machine.suspend(); ++count;
		}
	}

	public void resume() {
		if (null != machine && 0 < count) {
			machine.resume(); --count;
		}
	}

	public void count(EventRequest request, boolean flag) {
		if (null != request) {
			if (EventRequest.SUSPEND_ALL == request.suspendPolicy()) {
				count = (flag ? ++count : --count);
			}
		}
	}

	public int count() {
		return count;
	}

	public String version() {
		if (null != machine) {
			return machine.version();
		}
		return null;
	}

	private String name = null;
	
	public String name() {
		if (null != machine) {
			if (null == name) {
				name = machine.name();
				return name;				
			}
			return name;
		}
		return null;
	}

	public void exit(int code) {
		if (null != machine) {			
			machine.exit(code);
		}
	}

	public void dispose() {
		if (null != machine) {
			machine.dispose();
		}
	}

	public Process process() {
		if (null != machine) {
			return machine.process();
		}
		return null;
	}	
}
