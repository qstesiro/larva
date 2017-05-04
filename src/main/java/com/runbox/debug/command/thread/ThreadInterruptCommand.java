package com.runbox.debug.command.thread;

import java.util.List;

import com.sun.jdi.ThreadReference;

import com.runbox.debug.manager.MachineManager;

public class ThreadInterruptCommand extends ThreadCommand {

	public ThreadInterruptCommand(String command) throws Exception {
		super(command);
	}

	@Override
	public boolean execute() throws Exception {
		List<Long> ids = ids();
		if (null != ids) {
			for (ThreadReference thread : MachineManager.instance().allThreads()) {
				for (long id : ids()) {
					if (thread.uniqueID() == id) {
						thread.interrupt();
					}
				}
			}			
		} else {
			for (ThreadReference thread : MachineManager.instance().allThreads()) {
				thread.interrupt();
			}
		}                
		return super.execute();
	}
}
