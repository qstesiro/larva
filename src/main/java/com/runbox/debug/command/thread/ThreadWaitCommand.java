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
		System.out.printf("#%-4d%-8d\n", index, thread.uniqueID());
		print(thread);
	}

	private void print(ObjectReference object) throws Exception {
		if (null != object) {
			System.out.printf("%5s%-10s", "", "object");
			System.out.printf("%-8d%s\n", object.uniqueID(), object.referenceType().name());
			System.out.printf("%5s%-10s", "", "holding");
			ThreadReference thread = object.owningThread();
			System.out.printf("%-8s\n", ((null != thread) ? String.valueOf(thread.uniqueID()) : "n/a"));
		}
	}

	private void print(ThreadReference thread) throws Exception {
		if (null != thread) {
			ObjectReference object = thread.currentContendedMonitor(); print(object);
			if (null != object) {
				print(thread.owningThread());
			}
		}
	}
}
