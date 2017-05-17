package com.runbox.debug.command.breakpoint;

import java.util.LinkedList;
import java.util.List;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.Location;

import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.manager.BreakpointManager;
import com.runbox.debug.manager.RequestManager;

public class BreakpointMethodCommand extends BreakpointCommand {

    public BreakpointMethodCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        BreakpointManager.MethodBreakpoint breakpoint = new BreakpointManager.MethodBreakpoint(clazz(), method(), arguments(), routine());
        if (!BreakpointManager.instance().contain(breakpoint)) {
            BreakpointManager.instance().append(breakpoint);
            search(breakpoint);
        }
        return super.execute();
    }

    private void search(BreakpointManager.MethodBreakpoint breakpoint) {
        if (null != breakpoint) {
            List<ReferenceType> types = MachineManager.instance().allClasses();
            for (ReferenceType type : types) {
                Location location = BreakpointManager.instance().find(breakpoint, type);
                if (null != location) {
                    RequestManager.instance().createBreakpointRequest(location, breakpoint);
                }
            }
        }
    }  
}
