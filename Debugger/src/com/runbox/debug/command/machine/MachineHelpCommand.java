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
        if (null != argument) {
            String reference = list.get(Integer.valueOf(String.valueOf(argument.trim())) - 1);
            if (null != reference) {
                System.out.println(reference);
            }
        } else {
            System.out.println(MACHINE_NAME);
            System.out.println(MACHINE_VERSION);
            System.out.println(MACHINE_ABILITY);
        }
        return super.execute();
    }

    private static List<String> list = new LinkedList<String>() {{
        add(MACHINE_NAME_REFERENCE);
        add(MACHINE_VERSION_REFERENCE);
        add(MACHINE_ABILITY_REFERENCE);
    }};

    private static final String MACHINE_NAME_REFERENCE = "";

    private static final String MACHINE_VERSION_REFERENCE = "";

    private static final String MACHINE_ABILITY_REFERENCE = "";
}
