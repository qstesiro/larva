package com.runbox.debug.command.execute;

/**
 * Created by qstesiro
 */
public class ExecuteRunCommand extends ExecuteCommand {

    public ExecuteRunCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        return false;
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
