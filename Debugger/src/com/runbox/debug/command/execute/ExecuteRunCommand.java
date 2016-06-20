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
}
