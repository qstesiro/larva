package com.runbox.debug.command.thread;

import java.util.List;

import com.sun.jdi.ThreadReference;

import com.runbox.debug.manager.MachineManager;

public class ThreadResumeCommand extends ThreadCommand {

    public ThreadResumeCommand(String command) throws Exception {
        super(command);        
    }

    @Override
    public boolean execute() throws Exception {
		List<Long> ids = ids();
		if (null != ids) {
			for (ThreadReference thread : MachineManager.instance().allThreads()) {
				for (long id : ids()) {
					if (thread.uniqueID() == id && 1 < thread.suspendCount()) {
						thread.resume();
					}
				}
			}			
		} else {
			for (ThreadReference thread : MachineManager.instance().allThreads()) {
				if (1 < thread.suspendCount()) {
					thread.resume();
				}
			}
		}        
        return super.execute();        
    }    
}
