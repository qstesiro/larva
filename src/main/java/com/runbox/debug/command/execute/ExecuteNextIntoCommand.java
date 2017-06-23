package com.runbox.debug.command.execute;

import com.sun.jdi.request.StepRequest;

public class ExecuteNextIntoCommand extends ExecuteNextCommand {

    public ExecuteNextIntoCommand(String command) throws Exception {
        super(command);
    }	

    // @Override
    // public boolean execute() throws Exception {
    //     if (null != ContextManager.instance().current()) {
    //         StepRequest request = ExecuteManager.instance().get(ContextManager.instance().current());
    //         if (null != request) {										
    //             if (StepRequest.STEP_LINE != request.size() || StepRequest.STEP_INTO != request.depth()) {
    //                 ExecuteManager.instance().delete(ContextManager.instance().current());
    //                 request = create();
    //             }				
    //         } 			
    //         config((null != request) ? request : create());
    //         return !super.execute();
    //     }
    //     throw new Exception("thread context is null, don`t execute.");
    // }
}
