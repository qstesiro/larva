package com.runbox.debug.command.clazz;

import com.runbox.debug.command.Command;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/6/23.
 */
public class ClassHelpCommand extends Command {

    public ClassHelpCommand(String command) throws Exception {
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
            System.out.println(CLASS_QUERY);
            System.out.println(CLASS_FIELD);
            System.out.println(CLASS_METHOD);
            System.out.println(CLASS_LOAD);
            System.out.println(CLASS_UNLOAD);
        }
        return super.execute();
    }

    private static List<String> list = new LinkedList<String>() {{
        add(CLASS_QUERY_REFERENCE);
        add(CLASS_FIELD_REFERENCE);
        add(CLASS_METHOD_REFERENCE);
        add(CLASS_LOAD_REFERENCE);
        add(CLASS_UNLOAD_REFERENCE);
    }};

    private static final String CLASS_QUERY_REFERENCE = "";

    private static final String CLASS_FIELD_REFERENCE = "";

    private static final String CLASS_METHOD_REFERENCE = "";

    private static final String CLASS_LOAD_REFERENCE = "";

    private static final String CLASS_UNLOAD_REFERENCE = "";
}
