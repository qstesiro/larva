package com.runbox.debug.command.thread;

import com.sun.jdi.*;

import com.runbox.debug.manager.ContextManager;

public class ThreadStackCommand extends ThreadCommand {

    public ThreadStackCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        ThreadReference thread = ContextManager.instance().thread();
        if (null != thread) {
            int index = 0;
            System.out.printf("%-5s%s\n", "#", "location");
            for (StackFrame frame : thread.frames()) {
				Location location = frame.location();
                System.out.printf("%-5d%s.%s\n", index++, location.declaringType().name(), location.method().name());
            }
        }
        return super.execute();
    }    
}
