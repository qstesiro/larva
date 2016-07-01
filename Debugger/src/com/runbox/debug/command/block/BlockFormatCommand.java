package com.runbox.debug.command.block;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.BlockManager;
import com.runbox.debug.parser.statement.node.BlockNode;

import java.util.Map;

/**
 * Created by qstesiro
 */
public class BlockFormatCommand extends Command {

    public BlockFormatCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        String name = name();
        if (null != name) {
            System.out.println(BlockManager.instance().format(name));
        } else {
            Map<String, BlockNode> map = BlockManager.instance().get();
            for (String key : map.keySet()) {
                System.out.println(BlockManager.instance().format(map.get(key).name()));
            }
        }
        return super.execute();
    }

    private String name() throws Exception {
        if (null != argument) {
            String name = argument.trim();
            if (null == BlockManager.instance().get(name)) {
                throw new Exception("invalid block name");
            }
            return name;
        }
        return null;
    }
}
