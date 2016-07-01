package com.runbox.debug.command.template;

import com.runbox.debug.command.Command;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/6/23.
 */
public class TemplateHelpCommand extends Command {

    public TemplateHelpCommand(String command) throws Exception {
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
            System.out.println(TEMPLATE_LIST);
            System.out.println(TEMPLATE_MAP);
            System.out.println(TEMPLATE_VECTOR);
            System.out.println(TEMPLATE_QUEUE);
            System.out.println(TEMPLATE_STACK);
        }
        return super.execute();
    }

    private static List<String> list = new LinkedList<String>() {{
        add(TEMPLATE_LIST_REFERENCE);
        add(TEMPLATE_MAP_REFERENCE);
        add(TEMPLATE_VECTOR_REFERENCE);
        add(TEMPLATE_QUEUE_REFERENCE);
        add(TEMPLATE_STACK_REFERENCE);
    }};

    private static final String TEMPLATE_LIST_REFERENCE = "";

    private static final String TEMPLATE_MAP_REFERENCE = "";

    private static final String TEMPLATE_VECTOR_REFERENCE = "";

    private static final String TEMPLATE_QUEUE_REFERENCE = "";

    private static final String TEMPLATE_STACK_REFERENCE = "";
}
