package com.runbox.debug.command.execute;

import com.runbox.debug.command.Command;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/6/23.
 */
public class ExecuteHelpCommand extends Command {

    public ExecuteHelpCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        help(list);
        return super.execute();
    }

    private List<String> list = new LinkedList<String>() {{
        add(EXECUTE_RUN);
        add(EXECUTE_NEXT);
        add(EXECUTE_STEP);
        add(EXECUTE_DETACH);
        add(EXECUTE_FILE);
        add(EXECUTE_QUIT);
    }};
}
