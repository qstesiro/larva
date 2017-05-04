package com.runbox.debug.command.machine;

import com.runbox.debug.manager.MachineManager;

public class MachineStatusCommand extends MachineCommand {

    public MachineStatusCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        int count = MachineManager.instance().status();
		System.out.printf("status: %s\n", 0 == count ? "running" : "suspended");
		System.out.printf("count:  %d\n", count);
        return super.execute();
    }
}
