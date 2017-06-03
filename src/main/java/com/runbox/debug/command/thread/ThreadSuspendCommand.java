package com.runbox.debug.command.thread;

import java.util.List;

import com.sun.jdi.ThreadReference;

import com.runbox.debug.manager.MachineManager;

public class ThreadSuspendCommand extends ThreadCommand {

    public ThreadSuspendCommand(String command) throws Exception {
        super(command);        
    }

    @Override
    public boolean execute() throws Exception {
		List<Long> ids = ids();
		if (null != ids) {
			for (ThreadReference thread : MachineManager.instance().allThreads()) {
				for (long id : ids) {
					if (thread.uniqueID() == id) {
						thread.suspend();
					}
				}
			}
		} else {
			for (ThreadReference thread : MachineManager.instance().allThreads()) {
				thread.suspend();
			}
		}        
        return super.execute();
    }    
}
