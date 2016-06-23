package com.runbox.debug.command.variant;

import com.runbox.debug.command.Command;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/6/23.
 */
public class VariantHelpCommand extends Command {

    public VariantHelpCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        help(list);
        return super.execute();
    }

    private List<String> list = new LinkedList<String>() {{
        add(VARIANT_PRINT);
        add(VARIANT_FIELD);
        add(VARIANT_LOCAL);
        add(VARIANT_AUTO);
        add(VARIANT_ARRAY);
        add(VARIANT_STRING);
    }};
}
