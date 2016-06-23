package com.runbox.debug.command.machine;

import com.runbox.debug.command.Command;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/6/23.
 */
public class MachineHelpCommand extends Command {

    public MachineHelpCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        help(list);
        return super.execute();
    }

    private List<String> list = new LinkedList<String>() {{
        add(MACHINE_NAME);
        add(MACHINE_VERSION);
        add(MACHINE_ABILITY);
    }};
}
