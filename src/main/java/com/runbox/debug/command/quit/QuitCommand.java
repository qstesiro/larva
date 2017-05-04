package com.runbox.debug.command.quit;

import com.runbox.debug.Debugger;
import com.runbox.debug.command.Command;

public class QuitCommand extends Command {

    public QuitCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        Debugger.instance().flag(Debugger.QUIT);
        return !super.execute();
    }
}
