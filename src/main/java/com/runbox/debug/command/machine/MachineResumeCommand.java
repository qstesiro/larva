package com.runbox.debug.command.machine;

import com.runbox.debug.manager.MachineManager;

public class MachineResumeCommand extends MachineCommand {

    public MachineResumeCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        MachineManager.instance().resume();
        return super.execute();
    }
}
