package com.runbox.debug.command.machine;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.MachineManager;

/**
 * Created by qstesiro
 */
public class MachineNameCommand extends Command {

    public MachineNameCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        System.out.println(MachineManager.instance().name());
        return super.execute();
    }

    @Override
    public void help() {
        String help = "\r\n";
        help += "description\r\n";
        help += "";
        help += "note";
        help += "";
        help += "example";
        help += "";
        System.out.println(help);
    }
}
