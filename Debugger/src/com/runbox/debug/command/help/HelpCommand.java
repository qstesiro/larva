package com.runbox.debug.command.help;

import com.runbox.debug.command.Command;

/**
 * Created by qstesiro
 */
public class HelpCommand extends Command {

    public HelpCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        print();
        return super.execute();
    }

    private void print() {
        for (Integer key : types.keySet()) {
            System.out.println(key + ". " + types.get(key));
        }
    }
}
