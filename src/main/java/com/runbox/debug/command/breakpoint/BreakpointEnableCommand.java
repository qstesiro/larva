package com.runbox.debug.command.breakpoint;

import java.util.Collection;

import com.runbox.debug.command.breakpoint.BreakpointCommand;
import com.runbox.debug.manager.BreakpointManager;

public class BreakpointEnableCommand extends BreakpointCommand {

    public BreakpointEnableCommand(String command) throws Exception {
        super(command);        
    }

    @Override
    public boolean execute() throws Exception {
        Collection<Integer> ids = ids();
        if (null != ids) {
            for (int id : ids) {
                BreakpointManager.instance().enable(id);
            }            
        } else {
            BreakpointManager.instance().enable();
        }        
        return super.execute();        
    }    
}
