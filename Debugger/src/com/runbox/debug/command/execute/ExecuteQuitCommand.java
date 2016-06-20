package com.runbox.debug.command.execute;

import com.runbox.debug.Debugger;
import com.runbox.debug.manager.MachineManager;

/**
 * Created by huangmengmeng01 on 2016/4/27.
 */
public class ExecuteQuitCommand extends ExecuteCommand {

    public ExecuteQuitCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        Debugger.instance().flag(Debugger.QUIT);
        return false;
    }
}
