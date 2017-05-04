package com.runbox.debug.command.machine;

import com.runbox.debug.manager.MachineManager;

public class MachineNameCommand extends MachineCommand {

    public MachineNameCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        System.out.println(MachineManager.instance().name());
        return super.execute();
    }
}
