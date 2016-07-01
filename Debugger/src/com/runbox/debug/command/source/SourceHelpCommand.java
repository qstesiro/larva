package com.runbox.debug.command.source;

import com.runbox.debug.command.Command;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/6/23.
 */
public class SourceHelpCommand extends Command {

    public SourceHelpCommand(String command) throws Exception {
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
            System.out.println(SOURCE_APPEND);
            System.out.println(SOURCE_DELETE);
            System.out.println(SOURCE_QUERY);
        }
        return super.execute();
    }

    private static List<String> list = new LinkedList<String>() {{
        add(SOURCE_APPEND_REFERENCE);
        add(SOURCE_DELETE_REFERENCE);
        add(SOURCE_QUERY_REFERENCE);
    }};

    private static final String SOURCE_APPEND_REFERENCE = "";

    private static final String SOURCE_DELETE_REFERENCE = "";

    private static final String SOURCE_QUERY_REFERENCE = "";
}
