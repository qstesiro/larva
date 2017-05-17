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
		if (null != machine) {
			machine.resume(); --count;
		}
	}

	public void status(EventRequest request, boolean flag) {
		if (null != request) {
			if (EventRequest.SUSPEND_ALL == request.suspendPolicy()) {
				count = (flag ? ++count : --count);
			}
		}
	}

	public int status() {
		return count;
	}

	public String version() {
		if (null != machine) {
			return machine.version();
		}
		return null;
	}

	public String name() {
		if (null != machine) {
			return machine.name();
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

	public boolean canAddMethod() {
		if (null != machine) {
			return machine.canAddMethod();
		}
		return false;
	}

	public boolean canBeModified() {
		if (null != machine) {
			return machine.canBeModified();
		}
		return false;
	}

	public boolean canGetBytecodes() {
		if (null != machine) {
			return machine.canGetBytecodes();
		}
		return false;
	}

	public boolean canGetCurrentContendedMonitor() {
		if (null != machine) {
			return machine.canGetCurrentContendedMonitor();
		}
		return false;
	}

	public boolean canGetMonitorFrameInfo() {
		if (null != machine) {
			return machine.canGetMonitorFrameInfo();
		}
		return false;
	}

	public boolean canGetOwnedMonitorInfo() {
		if (null != machine) {
			return machine.canGetOwnedMonitorInfo();
		}
		return false;
	}

	public boolean canGetSourceDebugExtension() {
		if (null != machine) {
			return machine.canGetSourceDebugExtension();
		}
		return false;
	}

	public boolean canGetSyntheticAttribute() {
		if (null != machine) {
			return machine.canGetSyntheticAttribute();
		}
		return false;
	}

	public boolean canPopFrames() {
		if (null != machine) {
			return machine.canPopFrames();
		}
		return false;
	}

	public boolean canRedefineClasses() {
		if (null != machine) {
			return machine.canRedefineClasses();
		}
		return false;
	}

	public boolean canRequestVMDeathEvent() {
		if (null != machine) {
			return machine.canRequestVMDeathEvent();
		}
		return false;
	}

	public boolean canUnrestrictedlyRedefineClasses() {
		if (null != machine) {
			return machine.canUnrestrictedlyRedefineClasses();
		}
		return false;
	}

	public boolean canUseInstanceFilters() {
		if (null != machine) {
			return machine.canUseInstanceFilters();
		}
		return false;
	}

	public boolean canWatchFieldAccess() {
		if (null != machine) {
			return machine.canWatchFieldAccess();
		}
		return false;
	}

	public boolean canWatchFieldModification() {
		if (null != machine) {
			return machine.canWatchFieldModification();
		}
		return false;
	}
}
