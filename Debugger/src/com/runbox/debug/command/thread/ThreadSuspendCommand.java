package com.runbox.debug.command.thread;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.MachineManager;
import com.sun.jdi.ThreadReference;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by qstesiro
 */
public class ThreadSuspendCommand extends Command {

    public ThreadSuspendCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid argument");
        }
    }

    @Override
    public boolean execute() throws Exception {
        for (ThreadReference thread : MachineManager.instance().allThreads()) {
            for (long id : ids()) {
                if (thread.uniqueID() == id) {
                    thread.suspend();
                }
            }
        }
        return super.execute();
    }

    private List<Long> ids() {
        List<Long> ids = new LinkedList<>();
        for (String id : argument.split(",")) {
            ids.add(Long.valueOf(id.trim()));
        }
        return ids;
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
