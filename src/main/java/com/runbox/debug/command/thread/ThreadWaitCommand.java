package com.runbox.debug.command.thread;

import java.util.List;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.IncompatibleThreadStateException;

import com.runbox.debug.manager.MachineManager;

public class ThreadWaitCommand extends ThreadCommand {

	public ThreadWaitCommand(String command) throws Exception {
		super(command);
	}

	@Override
	public boolean execute() throws Exception {
		List<Long> ids = ids();
		if (null != ids) {
			int index = 0; for (long id : ids) {
				for (ThreadReference thread : MachineManager.instance().allThreads()) {
					if (thread.uniqueID() == id) {
						print(index++, thread);
					}
				}			
			}
		} else {
			int index = 0;
			for (ThreadReference thread : MachineManager.instance().allThreads()) {
				print(index++, thread);
			}
		}
		return super.execute();
	}

	private void print(int index, ThreadReference thread) throws Exception {
		System.out.printf("#%-4d%-10d%s\n", index, thread.uniqueID(), status(thread.status()));
		if (ThreadReference.THREAD_STATUS_UNKNOWN != thread.status() &&
			ThreadReference.THREAD_STATUS_ZOMBIE != thread.status() &&
			thread.isSuspended()) {
			print(thread);
		} else {
			print();
		}
	}

	private void print(ThreadReference thread) throws Exception {
		if (null != thread) {			
			ObjectReference object = thread.currentContendedMonitor();
			print(object); if (null != object) {
				print(object.owningThread());
			}
		}		
	}   	

	private void print(ObjectReference object) throws Exception {
		if (null != object) {
			ThreadReference thread = object.owningThread();
			System.out.printf("%5s%-10s%s\n", "", "waiting", object.toString());
			System.out.printf("%5s%-10s%s\n", "", "holding", (null != thread ? thread.toString() : "n/a"));
		} else {
			print();
		}
	}

	private void print() {
		System.out.printf("%5s%-10s%s\n", "", "waiting", "n/a");
		System.out.printf("%5s%-10s%s\n", "", "holding", "n/a");
	}
}
