package com.runbox.debug.command.machine;

import com.runbox.debug.Debugger;
import com.runbox.debug.command.Command;
import com.runbox.debug.manager.MachineManager;

/**
 * Created by huangmengmeng01 on 2016/5/25.
 */
public class MachineVersionCommand extends Command {

    public MachineVersionCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        System.out.println(MachineManager.instance().version());
        return super.execute();
    }
}
