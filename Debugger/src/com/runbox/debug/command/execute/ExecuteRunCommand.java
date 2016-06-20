package com.runbox.debug.command.execute;

/**
 * Created by huangmengmeng01 on 2016/4/26.
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
