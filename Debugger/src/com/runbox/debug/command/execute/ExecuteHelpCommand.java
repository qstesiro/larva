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
        if (null != argument) {
            String reference = list.get(Integer.valueOf(String.valueOf(argument.trim())) - 1);
            if (null != reference) {
                System.out.println(reference);
            }
        } else {
            System.out.println(EXECUTE_RUN);
            System.out.println(EXECUTE_NEXT);
            System.out.println(EXECUTE_STEP);
            System.out.println(EXECUTE_DETACH);
            System.out.println(EXECUTE_FILE);
            System.out.println(EXECUTE_QUIT);
        }
        return super.execute();
    }

    private static List<String> list = new LinkedList<String>() {{
        add(EXECUTE_RUN_REFERENCE);
        add(EXECUTE_NEXT_REFERENCE);
        add(EXECUTE_STEP_REFERENCE);
        add(EXECUTE_DETACH_REFERENCE);
        add(EXECUTE_FILE_REFERENCE);
        add(EXECUTE_QUIT_REFERENCE);
    }};

    private static final String EXECUTE_RUN_REFERENCE = "";

    private static final String EXECUTE_NEXT_REFERENCE = "";

    private static final String EXECUTE_STEP_REFERENCE = "";

    private static final String EXECUTE_DETACH_REFERENCE = "";

    private static final String EXECUTE_FILE_REFERENCE = "";

    private static final String EXECUTE_QUIT_REFERENCE = "";
}
