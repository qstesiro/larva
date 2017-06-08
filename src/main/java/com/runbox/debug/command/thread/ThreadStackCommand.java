package com.runbox.debug.command.thread;

import com.sun.jdi.*;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.script.expression.Expression;

public class ThreadStackCommand extends ThreadCommand {

    public ThreadStackCommand(String command) throws Exception {
        super(command);
		if (null != argument()) {
			flags = new Expression(argument()).execute().getInteger(0);
		}
    }

	
	private int flags = FLAG_PACKAGE | FLAG_LINE;
	
    @Override
    public boolean execute() throws Exception {
        ThreadReference thread = ContextManager.instance().current();
        if (null != thread && thread.isSuspended()) {
            System.out.printf("%-5s%s\n", "#", "location");
			if (0 < thread.frameCount()) {
				int i = 0; for (StackFrame frame : thread.frames()) {
					print(i++, frame.location());					
				}
			}
        }
        return super.execute();
    }

	private static final int FLAG_PACKAGE = 0x1;	
	private static final int FLAG_LINE = 0x2;
	private static final int FLAG_MAX = 0x2;
	
	private void print(int index, Location location) {
		System.out.printf("%-5d", index);
		if (FLAG_PACKAGE == (FLAG_PACKAGE & flags)) {
			System.out.printf("%s.", location.declaringType().name());
		}
		System.out.printf("%s", location.method().name());
		if (FLAG_LINE == (FLAG_LINE & flags)) {
			if (-1 != location.lineNumber()) {
				System.out.printf(":%d", location.lineNumber());
			} else  {
				System.out.printf(":%s", "n/a");
			}
		}
		System.out.println();
	}	
}
