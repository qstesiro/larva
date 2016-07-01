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
        if (null != argument) {
            String reference = list.get(Integer.valueOf(String.valueOf(argument.trim())) - 1);
            if (null != reference) {
                System.out.println(reference);
            }
        } else {
            System.out.println(VARIANT_PRINT);
            System.out.println(VARIANT_FIELD);
            System.out.println(VARIANT_LOCAL);
            System.out.println(VARIANT_AUTO);
            System.out.println(VARIANT_ARRAY);
            System.out.println(VARIANT_STRING);
        }
        return super.execute();
    }

    private static List<String> list = new LinkedList<String>() {{
        add(VARIANT_PRINT_REFERENCE);
        add(VARIANT_FIELD_REFERENCE);
        add(VARIANT_LOCAL_REFERENCE);
        add(VARIANT_AUTO_REFERENCE);
        add(VARIANT_ARRAY_REFERENCE);
        add(VARIANT_STRING_REFERENCE);
    }};

    private static final String VARIANT_PRINT_REFERENCE = "";

    private static final String VARIANT_FIELD_REFERENCE = "";

    private static final String VARIANT_LOCAL_REFERENCE = "";

    private static final String VARIANT_AUTO_REFERENCE = "";

    private static final String VARIANT_ARRAY_REFERENCE = "";

    private static final String VARIANT_STRING_REFERENCE = "";
}
