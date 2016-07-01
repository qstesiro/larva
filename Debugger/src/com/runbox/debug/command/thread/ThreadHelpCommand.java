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
        if (null != argument) {
            String reference = list.get(Integer.valueOf(String.valueOf(argument.trim())) - 1);
            if (null != reference) {
                System.out.println(reference);
            }
        } else {
            System.out.println(THREAD_QUERY);
            System.out.println(THREAD_SWITCH);
            System.out.println(THREAD_SUSPEND);
            System.out.println(THREAD_RESUME);
        }
        return super.execute();
    }

    private List<String> list = new LinkedList<String>() {{
        add(THREAD_QUERY_REFERENCE);
        add(THREAD_SWITCH_REFERENCE);
        add(THREAD_SUSPEND_REFERENCE);
        add(THREAD_RESUME_REFERENCE);
    }};

    private static final String THREAD_QUERY_REFERENCE = "";

    private static final String THREAD_SWITCH_REFERENCE = "";

    private static final String THREAD_SUSPEND_REFERENCE = "";

    private static final String THREAD_RESUME_REFERENCE = "";
}
