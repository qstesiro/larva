package com.runbox.debug.command.stack;

import com.runbox.debug.command.Command;

/**
 * Created by qstesiro
 */
public class StackSwitchCommand extends Command {

    public StackSwitchCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
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
