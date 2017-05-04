package com.runbox.debug.command.breakpoint;

import java.util.List;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.Field;

import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.manager.BreakpointManager;
import com.runbox.debug.manager.RequestManager;

public class BreakpointModifyCommand extends BreakpointCommand {

    public BreakpointModifyCommand(String command) throws Exception {
        super(command);        
    }

    @Override
    public boolean execute() throws Exception { 
        BreakpointManager.ModifyBreakpoint breakpoint = new BreakpointManager.ModifyBreakpoint(clazz(), field(), routine());
        if (!BreakpointManager.instance().contain(breakpoint)) {
            BreakpointManager.instance().append(breakpoint);
            search(breakpoint);
        }
        return super.execute();
    }

    private void search(BreakpointManager.ModifyBreakpoint breakpoint) {
        List<ReferenceType> types = MachineManager.instance().allClasses();
        for (ReferenceType type : types) {
            Field field = BreakpointManager.instance().find(breakpoint, type);
            if (null != field) RequestManager.instance().createBreakpointRequest(field, breakpoint);
        }
    }    
}
