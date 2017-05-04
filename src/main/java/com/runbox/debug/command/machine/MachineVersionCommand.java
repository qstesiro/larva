package com.runbox.debug.command.machine;

import com.runbox.debug.manager.MachineManager;

public class MachineVersionCommand extends MachineCommand {

    public MachineVersionCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        System.out.println(MachineManager.instance().version());
        return super.execute();
    }
}
