package com.runbox.debug.command.execute;

import com.runbox.debug.manager.ContextManager;

public class ExecuteStepCommand extends ExecuteWalkCommand {

    public ExecuteStepCommand(String command) throws Exception {
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
