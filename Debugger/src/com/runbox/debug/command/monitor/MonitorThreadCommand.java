package com.runbox.debug.command.monitor;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.MachineManager;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ThreadReference;

import java.util.List;

/**
 * Created by qstesiro
 */
public class MonitorThreadCommand extends Command {

    public MonitorThreadCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid argument");
        }
    }

    @Override
    public boolean execute() throws Exception {
        long id = ((null != argument) ? Long.valueOf(String.valueOf(argument.trim())) : -1);
        if (-1 != id) {
            List<ThreadReference> threads = MachineManager.instance().allThreads();
            for (ThreadReference thread : threads) {
                if (thread.uniqueID() == id) {
                    System.out.println(entry(thread));
                }
            }
        } else {
            int index = 0;
            for (ThreadReference thread : MachineManager.instance().allThreads()) {
                System.out.println(entry(index++, thread));
            }
        }
        return super.execute();
    }

    private String entry(int index, ThreadReference thread) throws IncompatibleThreadStateException {
        return "index:\t\t" + index  + "\n" + entry(thread);
    }

    private String entry(ThreadReference thread) throws IncompatibleThreadStateException {
        String tag = (thread == ContextManager.instance().thread()) ? "#" : " ";
        String entry = "id\t\t" + thread.uniqueID() + tag + "\n";
        ObjectReference object = thread.currentContendedMonitor();
        entry += "waiting:\t" + ((null != object) ? object : "none") + "\n";
        entry += "owned:\t\t";
        List<ObjectReference> objects = thread.ownedMonitors();
        if (0 < objects.size()) {
            for (int i = 0; i < objects.size(); ++i) {
                entry += ((0 < i) ? "\n\t\t" : "");
                entry += objects.get(i);
            }
        } else {
            entry += "none";
        }
        return entry + "\n";
    }
}
