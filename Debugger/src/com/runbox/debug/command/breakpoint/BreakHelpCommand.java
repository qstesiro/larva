package com.runbox.debug.command.breakpoint;

import com.runbox.debug.command.Command;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/6/23.
 */
public class BreakHelpCommand extends Command {

    public BreakHelpCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        help(list);
        return super.execute();
    }

    private List<String> list = new LinkedList<String>() {{
        add(BREAK_METHOD);
        add(BREAK_LINE);
        add(BREAK_ACCESS);
        add(BREAK_MODIFY);
        add(BREAK_QUERY);
        add(BREAK_DELETE);
        add(BREAK_ENABLE);
        add(BREAK_DISABLE);
    }};
}
