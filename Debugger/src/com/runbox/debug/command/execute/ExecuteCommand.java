package com.runbox.debug.command.execute;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.ExecuteManager;

/**
 * Created by qstesiro
 */
public class ExecuteCommand extends Command {

    public ExecuteCommand(String command) throws Exception {
        super(command);
        if (null != ContextManager.instance().thread()) {
            ExecuteManager.instance().delete(ContextManager.instance().thread());
        }
    }
}
