package com.runbox.debug.command.thread;

import com.sun.jdi.ThreadReference;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.MachineManager;

public class ThreadSwitchCommand extends ThreadCommand {

    public ThreadSwitchCommand(String command) throws Exception {
        super(command);        
    }

    @Override
    public boolean execute() throws Exception {
        for (ThreadReference thread : MachineManager.instance().allThreads()) {
            if (thread.uniqueID() == id() && thread.isSuspended()) {				
				ContextManager.instance().current(thread); break;
            }
        }
        return super.execute();
    }
}
