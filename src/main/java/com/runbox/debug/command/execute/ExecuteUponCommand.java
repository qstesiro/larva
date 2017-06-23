package com.runbox.debug.command.execute;

import java.util.List;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.StackFrame;
import com.sun.jdi.Location;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.ExecuteManager;

public class ExecuteUponCommand extends ExecuteWalkCommand {

    public ExecuteUponCommand(String command) throws Exception {
        super(command);
    }

    @Override 
    public boolean execute() throws Exception {
		ThreadReference thread = ContextManager.instance().current();
        if (null != thread) {
			List<StackFrame> frames = thread.frames();
			if (null != frames && 1 < frames.size()) {
				Location location = frames.get(0).location();
				ExecuteManager.ReturnEntry entry = new ExecuteManager.ReturnEntry(thread,
																				  location.declaringType(),
																				  location.declaringType().name(),
																				  // null,
																				  frames.size(),
																				  routine());
				ExecuteManager.instance().create(thread, entry);
				return !super.execute();
			}                        
        }        
        throw new Exception("thread context is null, don`t execute.");
    }
}
