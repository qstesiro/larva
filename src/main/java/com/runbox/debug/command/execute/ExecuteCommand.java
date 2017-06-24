package com.runbox.debug.command.execute;

import com.sun.jdi.ThreadReference;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.ExecuteManager;

public class ExecuteCommand extends Command {
	
    public ExecuteCommand(String command) throws Exception {
        super(command);		
		ThreadReference thread = ContextManager.instance().current();
		if (null != thread) {
            ExecuteManager.instance().clean(thread);
			return;
        } else if (this instanceof ExecuteRunCommand) {
			return;
		}
		throw new Exception("thread context is null, don`t execute.");
    }		
}
