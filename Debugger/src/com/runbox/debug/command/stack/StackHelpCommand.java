package com.runbox.debug.command.stack;

import com.runbox.debug.command.Command;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/6/23.
 */
public class StackHelpCommand extends Command {

    public StackHelpCommand(String command) throws Exception {
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
            System.out.println(STACK_FRAME);
            System.out.println(STACK_SWITCH);
        }
        return super.execute();
    }

    private static List<String> list = new LinkedList<String>() {{
        add(STACK_FRAME_REFERENCE);
        add(STACK_SWITCH_REFERENCE);
    }};

    private static final String STACK_FRAME_REFERENCE = "";

    private static final String STACK_SWITCH_REFERENCE = "";
}
