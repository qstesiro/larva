package com.runbox.debug.command.breakpoint;

import java.util.Collection;

import com.runbox.debug.command.breakpoint.BreakpointCommand;
import com.runbox.debug.manager.BreakpointManager;

public class BreakpointDisableCommand extends BreakpointCommand {

    public BreakpointDisableCommand(String command) throws Exception {
        super(command);        
    }

    @Override
    public boolean execute() throws Exception {
        Collection<Integer> ids = ids();
        if (null != ids) {
            for (int id : ids) {
                BreakpointManager.instance().disable(id);
            }            
        } else {
            BreakpointManager.instance().disable();
        }
        return super.execute();
    }    
}
