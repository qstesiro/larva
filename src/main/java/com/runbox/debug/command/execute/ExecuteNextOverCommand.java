package com.runbox.debug.command.execute;

import com.sun.jdi.request.StepRequest;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.ExecuteManager;

public class ExecuteNextOverCommand extends ExecuteNextCommand {

    public ExecuteNextOverCommand(String command) throws Exception {
        super(command);
    }	

    @Override
    public boolean execute() throws Exception {
        if (null != ContextManager.instance().current()) {
            StepRequest request = ExecuteManager.instance().get(ContextManager.instance().current());
            if (null != request) {										
                if (StepRequest.STEP_LINE != request.size() || StepRequest.STEP_OVER != request.depth()) {
                    ExecuteManager.instance().delete(ContextManager.instance().current());
                    request = create();
                }				
            } 			
            config((null != request) ? request : create());
            return !super.execute();
        }
        throw new Exception("thread context is null, don`t execute.");
    }
}
