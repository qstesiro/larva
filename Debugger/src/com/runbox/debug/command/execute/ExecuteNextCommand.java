package com.runbox.debug.command.execute;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.ExecuteManager;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.StepRequest;

/**
 * Created by qstesiro
 */
public class ExecuteNextCommand extends ExecuteCommand {

    public ExecuteNextCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        int count = ((null != argument) ? Integer.valueOf(argument.trim()) : 1);
        if (null != ContextManager.instance().thread()) {
            StepRequest request = ExecuteManager.instance().next(ContextManager.instance().thread());
            request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
            request.addCountFilter(count); /*此方法是限定某请求触发事件的次数*/
            request.setEnabled(true);
        } else {
            System.out.println("thread context is null, don`t execute.");
        }
        return false;
    }
}
