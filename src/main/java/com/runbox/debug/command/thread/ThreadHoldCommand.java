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
		System.out.printf("#%-4d%-8d\n", index, thread.uniqueID());				
		print(thread.ownedMonitors());	
	}

	private void print(List<ObjectReference> objects) throws Exception {
		if (0 < objects.size()) {
			int index = 0; for (ObjectReference object : objects) {				
				System.out.printf("%5s%-10s", "", "object");
				System.out.printf("%-8d%s\n", object.uniqueID(), object.referenceType().name().replace("$", "."));
				System.out.printf("%5s%-10s", "", "waiting");
				System.out.println(object.waitingThreads());
			}
		} else {
			System.out.printf("%5s%-10s%-8s\n", "", "objects", "none");
			System.out.printf("%5s%-10s%-8s\n", "", "waiting", "[]");
		}
	}	
}
