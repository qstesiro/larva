package com.runbox.debug.command.execute;

import java.util.List;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.StackFrame;
import com.sun.jdi.Location;
import com.sun.jdi.ReferenceType;

import com.runbox.manager.ImportManager;
import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.ExecuteManager;
import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.manager.RequestManager;

import com.runbox.debug.script.expression.Expression;

public class ExecuteGotoCommand extends ExecuteWalkCommand {

    public ExecuteGotoCommand(String command) throws Exception {
        super(command);
		if (null != argument()) {
			value = new Expression(argument()).execute().getString(0);
			return;
		}
		throw new Exception("invalid operand");
    }

	private String value = null;
	
    @Override 
    public boolean execute() throws Exception {		
		ExecuteManager.GotoBreakpoint breakpoint = new ExecuteManager.GotoBreakpoint(ContextManager.instance().current(),
																					 clazz(), line(), routine());
		if (!ExecuteManager.instance().contain(breakpoint)) {
			search(breakpoint);
			ExecuteManager.instance().append(breakpoint);
		}
		return !super.execute();
    }

	private void search(ExecuteManager.GotoBreakpoint breakpoint) {
        if (null != breakpoint) {
            List<ReferenceType> types = MachineManager.instance().allClasses();
            for (ReferenceType type : types) {
                Location location = ExecuteManager.instance().find(breakpoint, type);
                if (null != location) {
					RequestManager.instance().createBreakpointRequest(location, breakpoint);
					breakpoint.solve(true);
				}
            }
        }
    }
	
	private String clazz() throws Exception {
		if (null != value) {			
			int index = value.lastIndexOf(":");
			if (-1 != index && 0 != index) {
				return clazz(value.substring(0, index).trim());
			} else if (0 == index)  {
				return current();
			}
		}
		throw new Exception("invalid operand");
    }

	private int line() throws Exception {		
		if (null != value) {			
			int index = value.lastIndexOf(":");
			if (-1 != index) {
				return Integer.valueOf(value.substring(index + 1).trim());
			}
		}
        throw new Exception("invalid operand");
    }

    private String clazz(String clazz) throws Exception {
        String path = ImportManager.instance().find(clazz);
		return (null != path ? path + "." + clazz : clazz);
    }

	private String current() throws Exception {
		List<StackFrame> frames = ContextManager.instance().current().frames();
		if (null != frames && 1 < frames.size()) {
			Location location = frames.get(0).location();
			return location.declaringType().name();		
		}
		throw new Exception("invalid frame");
	}
}
