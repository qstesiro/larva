package com.runbox.debug.command.thread;

import java.util.List;
import java.util.LinkedList;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.ThreadGroupReference;
import com.sun.jdi.IncompatibleThreadStateException;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.MachineManager;

import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.ArrayOperand;

public class ThreadQueryCommand extends ThreadCommand {

    public ThreadQueryCommand(String command) throws Exception {
        super(command);
		if (null != argument()) {
			flags = new Expression(argument()).execute().getInteger(0);
		}
    }

    @Override
    public boolean execute() throws Exception {
		int index = 0; 
		System.out.printf(format(), arguments());
		for (ThreadReference thread : MachineManager.instance().allThreads()) {			
			System.out.printf(format(), arguments(index++, thread));
		}      
        return super.execute();
    }		
	
	private static final int FLAG_GROUP      = 0x01;
	private static final int FLAG_FRAME      = 0x02;	
	private static final int FLAG_SUSPEND    = 0x04;
	private static final int FLAG_STATUS     = 0x08;   
	private static final int FLAG_BREAKPOINT = 0x10;
	private static final int FLAG_NAME       = 0x20;
	
	private int flags = FLAG_STATUS | FLAG_NAME;

	private String format() {
		String format = "%-5s" + "%-8s";
		if (FLAG_GROUP == (FLAG_GROUP & flags)) {
			format += "%-8s";
		}
		if (FLAG_FRAME == (FLAG_FRAME & flags)) {
			format += "%-8s";
		}
		if (FLAG_SUSPEND == (FLAG_SUSPEND & flags)) {
			format += "%-10s";
		}
		if (FLAG_STATUS == (FLAG_STATUS & flags)) {
			format += "%-16s";
		}
		if (FLAG_BREAKPOINT == (FLAG_BREAKPOINT & flags)) {
			format += "%-16s";
		}
		if (FLAG_NAME == (FLAG_NAME & flags)) {
			format += "%s";
		}		
		return format += "\n";
	}

	private Object[] arguments() {
		List<Object> objects = new LinkedList<Object>();
		objects.add("#"); objects.add("id");		
		if (FLAG_GROUP == (FLAG_GROUP & flags)) {
			objects.add("group");
		}
		if (FLAG_FRAME == (FLAG_FRAME & flags)) {
			objects.add("frame");
		}
		if (FLAG_SUSPEND == (FLAG_SUSPEND & flags)) {
			objects.add("suspend");
		}
		if (FLAG_STATUS == (FLAG_STATUS & flags)) {
			objects.add("status");
		}
		if (FLAG_BREAKPOINT == (FLAG_BREAKPOINT & flags)) {
			objects.add("breakpoint");
		}
		if (FLAG_NAME == (FLAG_NAME & flags)) {
			objects.add("name");
		}		
		return objects.toArray();
	}
	
	private Object[] arguments(int index, ThreadReference thread) throws Exception {
		List<Object> objects = new LinkedList<Object>();
		objects.add(String.valueOf(index));
		objects.add(String.valueOf(thread.uniqueID()));
		if (FLAG_GROUP == (FLAG_GROUP & flags)) {
			ThreadGroupReference group = thread.threadGroup();
			objects.add(null != group ? String.valueOf(group.uniqueID()) : "n/a");
		}
		if (FLAG_FRAME == (FLAG_FRAME & flags)) {			
			objects.add(thread.isSuspended() ? String.valueOf(thread.frameCount()) : "n/a");
		}
		if (FLAG_SUSPEND == (FLAG_SUSPEND & flags)) {
			objects.add(thread.isSuspended() ? String.valueOf(thread.suspendCount()) : String.valueOf(0));
		}
		if (FLAG_STATUS == (FLAG_STATUS & flags)) {
			objects.add(status(thread.status()));
		}
		if (FLAG_BREAKPOINT == (FLAG_BREAKPOINT & flags)) {
			objects.add(thread.isSuspended() ? String.valueOf(thread.isAtBreakpoint()) : "false");
		}
		if (FLAG_NAME == (FLAG_NAME & flags)) {
			objects.add(thread.name());
		}		
		return objects.toArray();
	}              

    private String status(int status) {
        switch (status) {
		case ThreadReference.THREAD_STATUS_UNKNOWN:     return "n/a";            
		case ThreadReference.THREAD_STATUS_ZOMBIE:      return "zombie";            
		case ThreadReference.THREAD_STATUS_RUNNING:     return "running";            
		case ThreadReference.THREAD_STATUS_SLEEPING:    return "sleeping";            
		case ThreadReference.THREAD_STATUS_MONITOR:     return "monitor";           
		case ThreadReference.THREAD_STATUS_WAIT:        return "wait";            
		case ThreadReference.THREAD_STATUS_NOT_STARTED: return "started";            
        }
		return null;
    }
}
