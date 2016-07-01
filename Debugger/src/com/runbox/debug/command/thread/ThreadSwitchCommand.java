package com.runbox.debug.command.thread;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.command.Command;
import com.runbox.debug.manager.MachineManager;
import com.sun.jdi.ThreadReference;

/**
 * Created by qstesiro
 */
public class ThreadSwitchCommand extends Command {

    public ThreadSwitchCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid argument");
        }
    }

    @Override
    public boolean execute() throws Exception {
        for (ThreadReference thread : MachineManager.instance().allThreads()) {
            if (thread.uniqueID() == Long.valueOf(argument)) {
                ContextManager.instance().thread(thread);
                break;
            }
        }
        return super.execute();
    }

    @Override
    public void help() {
        String help = "\r\n";
        help += "description\r\n";
        help += "";
        help += "note";
        help += "";
        help += "example";
        help += "";
        System.out.println(help);
    }
}
