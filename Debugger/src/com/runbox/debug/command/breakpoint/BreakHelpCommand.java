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
        if (null != argument) {
            String reference = list.get(Integer.valueOf(String.valueOf(argument.trim())) - 1);
            if (null != reference) {
                System.out.println(reference);
            }
        } else {
            System.out.println(BREAK_METHOD);
            System.out.println(BREAK_LINE);
            System.out.println(BREAK_ACCESS);
            System.out.println(BREAK_MODIFY);
            System.out.println(BREAK_QUERY);
            System.out.println(BREAK_DELETE);
            System.out.println(BREAK_ENABLE);
            System.out.println(BREAK_DISABLE);
        }
        return super.execute();
    }

    private static List<String> list = new LinkedList<String>() {{
        add(BREAK_METHOD_REFERENCE);
        add(BREAK_LINE_REFERENCE);
        add(BREAK_ACCESS_REFERENCE);
        add(BREAK_MODIFY_REFERENCE);
        add(BREAK_QUERY_REFERENCE);
        add(BREAK_DELETE_REFERENCE);
        add(BREAK_ENABLE_REFERENCE);
        add(BREAK_DISABLE_REFERENCE);
    }};

    private static final String BREAK_METHOD_REFERENCE = "break.method method(arguments)\n" +
            "description\n" +
            "set a method breakpoint, if the class which owns the field variable which is watched has been " +
            "loaded by virtual machine, the access breakpoint will be enabled, otherwise, the breakpoint will was" +
            "pending until class is loaded." +
            "arguments" +
            "method(arguments) is a full method signature, arguments of method need full class name" +
            "note" +
            "Dalvik machine could not support method breakpoint." +
            "example";

    private static final String BREAK_LINE_REFERENCE = "break.method class:line\n" +
            "description\n" +
            "set a line breakpoint, if the class which owns the field variable which is watched has been\n" +
            "loaded by virtual machine, the access breakpoint will be enabled, otherwise, the breakpoint will was\n" +
            "pending until class is loaded.\n" +
            "arguments\n" +
            "class:line is a full method signature, class is name of the class which must be a short class name;\n" +
            "line is line number of the class file.\n" +
            "example";

    private static final String BREAK_ACCESS_REFERENCE = "break.access variable \n" +
            "description\n" +
            "set a access breakpoint, if the class which owns the field variable which is watched has been\n" +
            "loaded by virtual machine, the access breakpoint will be enabled, otherwise, the breakpoint will was\n" +
            "pending until class is loaded." +
            "note\n" +
            "breakpoint is triggered after the variable is accessed.\n" +
            "example";

    private static final String BREAK_MODIFY_REFERENCE = "break.modify variable \n" +
            "description \n" +
            "set a modified breakpoint, if the class which owns the field variable which is watched has been\n" +
            "loaded by virtual machine, the access breakpoint will be enabled, otherwise, the breakpoint will was\n" +
            "pending until class is loaded.\n" +
            "note\n" +
            "breakpoint is triggered after the variable is modified.\n" +
            "example";

    private static final String BREAK_QUERY_REFERENCE = "break.query\n" +
            "description\n" +
            "print all breakpoints which have been set. each breakpoint entry has five attributes:\n" +
            "index, number (it`s a unique number which identify a breakpoint), type, status, location\n" +
            "example";

    private static final String BREAK_DELETE_REFERENCE = "break.delete number[, number]* \n" +
            "description\n" +
            "delete a breakpoint or several breakpoints\n" +
            "arguments\n" +
            "number is at least one, if there are several numbers, they must be separated by semicolon\n" +
            "example";

    private static final String BREAK_ENABLE_REFERENCE = "break.enable number[, number]* \n" +
            "description\n" +
            "enable a breakpoint or several breakpoints" +
            "arguments" +
            "number is at least one, if there are several numbers, they must be separated by semicolon" +
            "example";

    private static final String BREAK_DISABLE_REFERENCE = "break.disable number[, number]* \n" +
            "description\n" +
            "disable a breakpoint or several breakpoints\n" +
            "arguments\n" +
            "number is at least one, if there are several numbers, they must be separated by semicolon\n" +
            "example\n";
}
