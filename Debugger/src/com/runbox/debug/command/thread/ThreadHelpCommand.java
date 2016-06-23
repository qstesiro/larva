package com.runbox.debug.command.thread;

import com.runbox.debug.command.Command;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/6/23.
 */
public class ThreadHelpCommand extends Command {

    public ThreadHelpCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        help(list);
        return super.execute();
    }

    private List<String> list = new LinkedList<String>() {{
        add(THREAD_QUERY);
        add(THREAD_SWITCH);
        add(THREAD_SUSPEND);
        add(THREAD_RESUME);
    }};
}
