package com.runbox.debug.command.block;

import com.runbox.debug.command.Command;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/6/23.
 */
public class BlockHelpCommand extends Command {

    public BlockHelpCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        help(list);
        return super.execute();
    }

    private List<String> list = new LinkedList<String>() {{
        add(BLOCK_QUERY);
        add(BLOCK_FORMAT);
    }};
}
