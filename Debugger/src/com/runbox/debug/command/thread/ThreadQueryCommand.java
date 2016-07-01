package com.runbox.debug.command.thread;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.command.Command;
import com.runbox.debug.manager.MachineManager;
import com.sun.jdi.ThreadReference;

/**
 * Created by qstesiro
 */
public class ThreadQueryCommand extends Command {

    public ThreadQueryCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        int index = 0;
        System.out.println(title());
        for (ThreadReference thread : MachineManager.instance().allThreads()) {
            System.out.println(entry(index++, thread));
        }
        return super.execute();
    }

    private String title() {
        String title = "index" + "\t";
        title += "id" + "\t";
        title += "group" + "\t";
        title += "status" + "\t";
        title += "suspend" + "\t";
        title += "break" + "\t";
        title += "name" + "\t";
        return title;
    }

    private String entry(int index, ThreadReference thread) {
        String entry = index + "\t";
        String tag = (thread == ContextManager.instance().thread()) ? "#" : " ";
        entry += thread.uniqueID() + tag + "\t";
        entry += thread.threadGroup().uniqueID() + "\t";
        entry += status(thread.status()) + "\t";
        entry += thread.isSuspended() + "\t";
        entry += thread.isAtBreakpoint() + "\t";
        entry += thread.name() + "\t";
        return entry;
    }

    private String status(int status) {
        switch (status) {
            case ThreadReference.THREAD_STATUS_UNKNOWN: {
                return "unknown";
            }
            case ThreadReference.THREAD_STATUS_ZOMBIE: {
                return "zombie";
            }
            case ThreadReference.THREAD_STATUS_RUNNING: {
                return "running";
            }
            case ThreadReference.THREAD_STATUS_SLEEPING: {
                return "sleeping";
            }
            case ThreadReference.THREAD_STATUS_MONITOR: {
                return "monitor";
            }
            case ThreadReference.THREAD_STATUS_WAIT: {
                return "wait";
            }
            case ThreadReference.THREAD_STATUS_NOT_STARTED: {
                return "started";
            }
        }
        return "";
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
