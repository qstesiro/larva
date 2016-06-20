package com.runbox.debug.command.execute;

/**
 * Created by qstesiro on 2016/5/22.
 */
public class ExecuteFileCommand extends ExecuteCommand {

    public ExecuteFileCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid argument");
        }
    }

    @Override
    public boolean execute() throws Exception {
        return super.execute();
    }
}
