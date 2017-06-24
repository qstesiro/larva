package com.runbox.debug.command.execute;

import java.util.List;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.StackFrame;
import com.sun.jdi.Location;
import com.sun.jdi.ReferenceType;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.ExecuteManager;
import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.manager.RequestManager;

public class ExecuteUponCommand extends ExecuteWalkCommand {

    public ExecuteUponCommand(String command) throws Exception {
        super(command);
    }

    @Override 
    public boolean execute() throws Exception {
		ThreadReference thread = ContextManager.instance().current();
		List<StackFrame> frames = thread.frames();
		if (null != frames && 1 < frames.size()) {
			Location location = location(frames.get(1).location());
			ExecuteManager.ReturnBreakpoint breakpoint = new ExecuteManager.ReturnBreakpoint(thread,
																							 location.declaringType().name(),
																							 location.lineNumber(),
																							 routine());
			search(breakpoint);
			ExecuteManager.instance().append(breakpoint);			
			return !super.execute();
		}
		throw new Exception("invalid context");
    }

	private Location location(Location location) throws Exception {				
 		List<Location> locations = location.method().allLineLocations();
		int i = 0; for (Location element : locations) {
			++i; if (element.lineNumber() == location.lineNumber()) break;			
		}
		if (i < locations.size()) {
			return locations.get(i);
		}
		throw new Exception("can`t find up frame location");
	}

	private void search(ExecuteManager.ReturnBreakpoint breakpoint) {
        if (null != breakpoint) {
            List<ReferenceType> types = MachineManager.instance().allClasses();
            for (ReferenceType type : types) {
                Location location = ExecuteManager.instance().find(breakpoint, type);
                if (null != location) {
					RequestManager.instance().createBreakpointRequest(location, breakpoint);
				}
            }
        }
    }
}
