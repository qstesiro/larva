package com.runbox.debug.command.execute;

public class ExecuteRunCommand extends ExecuteCommand {

    public ExecuteRunCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        return !super.execute();
    }
}
