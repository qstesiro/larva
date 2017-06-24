package com.runbox.debug.command.breakpoint;

import java.util.List;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.Location;

import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.manager.BreakpointManager;
import com.runbox.debug.manager.RequestManager;

public class BreakpointLineCommand extends BreakpointSetCommand {

    public BreakpointLineCommand(String command) throws Exception {
        super(command);        
    }

    @Override
    public boolean execute() throws Exception {
        BreakpointManager.LineBreakpoint breakpoint = new BreakpointManager.LineBreakpoint(clazz(), line(), routine()); 
        if (!BreakpointManager.instance().contain(breakpoint)) {
			search(breakpoint);
            BreakpointManager.instance().append(breakpoint);            
        }
        return super.execute();
    }

    private void search(BreakpointManager.LineBreakpoint breakpoint) {
        if (null != breakpoint) {
            List<ReferenceType> types = MachineManager.instance().allClasses();
            for (ReferenceType type : types) {
                Location location = BreakpointManager.instance().find(breakpoint, type);
                if (null != location) RequestManager.instance().createBreakpointRequest(location, breakpoint);
            }
        }
    }     
}
