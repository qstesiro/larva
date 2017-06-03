package com.runbox.debug.command.thread;

import com.sun.jdi.*;

import com.runbox.debug.manager.ContextManager;

public class ThreadStackCommand extends ThreadCommand {

    public ThreadStackCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        ThreadReference thread = ContextManager.instance().current();
        if (null != thread && thread.isSuspended()) {
            System.out.printf("%-5s%s\n", "#", "location");
			if (0 < thread.frameCount()) {
				int i = 0; for (StackFrame frame : thread.frames()) {
					Location location = frame.location();
					System.out.printf("%-5d%s.%s\n", i++, location.declaringType().name(), location.method().name());
				}
			}
        }
        return super.execute();
    }    
}
