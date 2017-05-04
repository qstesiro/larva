package com.runbox.debug.command.breakpoint;

import java.util.Collection;

import com.runbox.debug.command.breakpoint.BreakpointCommand;
import com.runbox.debug.manager.BreakpointManager;

public class BreakpointDeleteCommand extends BreakpointCommand {

    public BreakpointDeleteCommand(String command) throws Exception {
        super(command);        
    }

    @Override
    public boolean execute() throws Exception {
		Collection<Integer> ids = ids();
        if (null != ids) {
            for (int id : ids) {
                BreakpointManager.instance().delete(id);
            }            
        } else {
            BreakpointManager.instance().delete();
        }
        return super.execute();
    }    
}
