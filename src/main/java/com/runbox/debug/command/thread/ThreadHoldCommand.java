package com.runbox.debug.command.thread;

import java.util.List;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.ObjectReference;

import com.runbox.debug.manager.MachineManager;

public class ThreadHoldCommand extends ThreadCommand {

	public ThreadHoldCommand(String command) throws Exception {
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
			int index = 0; for (ThreadReference thread : MachineManager.instance().allThreads()) {
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
			if (0 < thread.ownedMonitors().size()) {
				boolean flag = false; for (ObjectReference object : thread.ownedMonitors()) {
					// perhaps this is a bug in davlik, I`m not sure
					// because a thread has invoked wait it should release monitor
					// in this case this thread doesn`t own this monitor
					// but owningThread method will return a thread that has invoke wait				   
					if (object.owningThread() == thread) { 
						System.out.printf("%5s%-10s", "", "holding");
						System.out.printf("%s\n", object.toString());
						System.out.printf("%5s%-10s", "", "waiting");
						print(object.waitingThreads());
						flag = true;
					}
				}
				if (!flag) print();
			} else {
				print();
			}
		}
	}

	private void print(List<ThreadReference> threads) throws Exception {
		if (null != threads) {
			int i = 0; for (ThreadReference thread : threads) {
				if (0 < i++) System.out.printf("%5s%-10s", "", "");
				System.out.printf("%s\n", thread.toString());
			}
		} else {
			System.out.printf("%s\n", "n/a");
		}		
	}

	private void print() {
		System.out.printf("%5s%-10s%-8s\n", "", "holding", "n/a");
		System.out.printf("%5s%-10s%-8s\n", "", "waiting", "n/a");
	}
}
