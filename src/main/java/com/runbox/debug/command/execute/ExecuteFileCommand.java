package com.runbox.debug.command.execute;

public class ExecuteFileCommand extends ExecuteCommand {

    public ExecuteFileCommand(String command) throws Exception {
        super(command);
        if (null == argument()) {
            throw new Exception("invalid argument");
        }
    }

    @Override
    public boolean execute() throws Exception {
        return super.execute();
    }
}
