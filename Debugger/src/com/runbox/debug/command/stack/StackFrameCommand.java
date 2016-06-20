package com.runbox.debug.command.stack;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.command.Command;
import com.sun.jdi.*;

/**
 * Created by qstesiro
 */
public class StackFrameCommand extends Command {

    public StackFrameCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        ThreadReference thread = ContextManager.instance().thread();
        if (null != thread) {
            int index = 0;
            System.out.println(title());
            for (StackFrame frame : thread.frames()) {
                System.out.println(entry(index++, frame));
            }
        }
        return super.execute();
    }

    public String title() {
        String title = "index" + "\t";
        title += "location";
        return title;
    }

    public String entry(int index, StackFrame frame) {
        String entry = index + "\t";
        Location location = frame.location();
        String clazz = location.declaringType().name();
        String method = location.method().name();
        entry += clazz + "." + method;
        return entry;
    }
}
