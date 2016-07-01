package com.runbox.debug.command.block;

import com.runbox.debug.command.Command;

import java.util.*;

/**
 * Created by huangmengmeng01 on 2016/6/23.
 */
public class BlockHelpCommand extends Command {

    public BlockHelpCommand(String command) throws Exception {
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
            System.out.println(BLOCK_QUERY);
            System.out.println(BLOCK_FORMAT);
        }
        return super.execute();
    }

    private static List<String> list = new LinkedList<String>() {{
        add(BLOCK_QUERY_REFERENCE);
        add(BLOCK_FORMAT_REFERENCE);
    }};

    private static final String BLOCK_QUERY_REFERENCE = "block.format [name]\n" +
            "description\n" +
            "print block statement formatted. the format style is as same as standard Java code. " +
            "arguments" +
            "name is optional, if it`s omitted debugger will print all blocks defined. Otherwise, debugger will \n" +
            "print single block if it`s existence" +
            "example";

    private static final String BLOCK_FORMAT_REFERENCE = "block.query [name]\n" +
            "description\n" +
            "print block statement in single line." +
            "arguments" +
            "name is optional, if it`s omitted debugger will print all blocks defined. Otherwise, debugger will \n" +
            "print the block which name equals name." +
            "example";
}
