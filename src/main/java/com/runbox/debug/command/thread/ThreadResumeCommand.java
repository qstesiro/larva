package com.runbox.debug.command.thread;

import java.util.List;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.request.EventRequest;

import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.manager.ContextManager;

public class ThreadResumeCommand extends ThreadCommand {

    public ThreadResumeCommand(String command) throws Exception {
        super(command);        
    }

    @Override
    public boolean execute() throws Exception {
		List<Long> ids = ids();
		if (null != ids) {
			for (ThreadReference thread : MachineManager.instance().allThreads()) {
				for (long id : ids) {
					if (thread.uniqueID() == id) {
						if (count(id) < thread.suspendCount()) {
							thread.resume(); break;
						}
					}
				}
			}			
		} else {
			for (ThreadReference thread : MachineManager.instance().allThreads()) {
				if (count(thread.uniqueID()) < thread.suspendCount()) {
					thread.resume();
				}
			}
		}        
        return super.execute();        
    }

	private int count(long id) {
		int count = MachineManager.instance().count();
		ThreadReference thread = ContextManager.instance().thread();
		if (null != thread && thread.uniqueID() == id) {
			EventRequest request = ContextManager.instance().request();
			if (null != request && request.suspendPolicy() == EventRequest.SUSPEND_EVENT_THREAD) {
				++count;
			}
		}
		return count;
	}			
}
