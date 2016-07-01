package com.runbox.debug.command.monitor;

import com.runbox.debug.command.Command;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/6/23.
 */
public class MonitorHelpCommand extends Command {

    public MonitorHelpCommand(String command) throws Exception {
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
            System.out.println(MONITOR_THREAD);
            System.out.println(MONITOR_OBJECT);
        }
        return super.execute();
    }

    private static List<String> list = new LinkedList<String>() {{
        add(MONITOR_THREAD_REFERENCE);
        add(MONITOR_OBJECT_REFERENCE);
    }};

    private static final String MONITOR_THREAD_REFERENCE = "";

    private static final String MONITOR_OBJECT_REFERENCE = "";
}
