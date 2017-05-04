package com.runbox.debug.command.breakpoint;

import java.util.List;

import com.sun.jdi.Field;
import com.sun.jdi.ReferenceType;

import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.manager.BreakpointManager;
import com.runbox.debug.manager.RequestManager;

public class BreakpointAccessCommand extends BreakpointCommand {

    public BreakpointAccessCommand(String command) throws Exception {
        super(command);        
    }

    @Override
    public boolean execute() throws Exception {        
        BreakpointManager.AccessBreakpoint breakpoint = new BreakpointManager.AccessBreakpoint(clazz(), field(), routine());
        if (!BreakpointManager.instance().contain(breakpoint)) {
            BreakpointManager.instance().append(breakpoint);
            search(breakpoint);
        }
        return super.execute();
    }

    private void search(BreakpointManager.AccessBreakpoint breakpoint) {
        List<ReferenceType> types = MachineManager.instance().allClasses();
        for (ReferenceType type : types) {
            Field field = BreakpointManager.instance().find(breakpoint, type);
            if (null != field) RequestManager.instance().createBreakpointRequest(field, breakpoint);
        }
    }
}
