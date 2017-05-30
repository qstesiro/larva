package com.runbox.debug.command.execute;

import com.sun.jdi.request.StepRequest;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.ExecuteManager;

public class ExecuteStepIntoCommand extends ExecuteStepCommand {

    public ExecuteStepIntoCommand(String command) throws Exception {
        super(command);
    }	

    @Override
    public boolean execute() throws Exception {
        if (null != ContextManager.instance().thread()) {
            StepRequest request = ExecuteManager.instance().get(ContextManager.instance().thread());
            if (null != request) {										
                if (StepRequest.STEP_MIN != request.size() || StepRequest.STEP_INTO != request.depth()) {
                    ExecuteManager.instance().delete(ContextManager.instance().thread());
                    request = create();
                }				
            } 			
            config((null != request) ? request : create());
            return !super.execute();
        }
        throw new Exception("thread context is null, don`t execute.");
    }
}
