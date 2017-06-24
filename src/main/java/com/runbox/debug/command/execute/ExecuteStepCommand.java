package com.runbox.debug.command.execute;

import com.runbox.debug.manager.ContextManager;

public class ExecuteStepCommand extends ExecuteWalkCommand {

    public ExecuteStepCommand(String command) throws Exception {
        super(command);
    }	

    @Override
    public boolean execute() throws Exception {		        
		config(create());
		return !super.execute();        
    }
}
