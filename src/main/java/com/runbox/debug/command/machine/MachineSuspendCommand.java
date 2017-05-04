package com.runbox.debug.command.machine;

import com.runbox.debug.manager.MachineManager;

public class MachineSuspendCommand extends MachineCommand {

    public MachineSuspendCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        MachineManager.instance().suspend();
        return super.execute();
    }
}
