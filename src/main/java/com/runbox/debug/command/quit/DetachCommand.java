package com.runbox.debug.command.quit;

import com.runbox.debug.Debugger;
import com.runbox.debug.command.Command;
import com.runbox.debug.manager.MachineManager;

public class DetachCommand extends Command {

    public DetachCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        Debugger.instance().flag(Debugger.DETACH);		
        return !super.execute();
    }
}
