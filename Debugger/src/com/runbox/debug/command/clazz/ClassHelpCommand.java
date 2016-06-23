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
        help(list);
        return super.execute();
    }

    private List<String> list = new LinkedList<String>() {{
        add(CLASS_QUERY);
        add(CLASS_FIELD);
        add(CLASS_METHOD);
        add(CLASS_LOAD);
        add(CLASS_UNLOAD);
    }};
}
