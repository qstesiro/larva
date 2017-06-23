package com.runbox.debug.command.execute;

import com.runbox.debug.manager.ContextManager;

public class ExecuteNextCommand extends ExecuteWalkCommand {

    public ExecuteNextCommand(String command) throws Exception {
        super(command);
    }	

    @Override
    public boolean execute() throws Exception {		
        if (null != ContextManager.instance().current()) {            
            config(create());
            return !super.execute();
        }
        throw new Exception("thread context is null, don`t execute.");
    }
}
