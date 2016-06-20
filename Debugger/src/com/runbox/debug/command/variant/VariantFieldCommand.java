package com.runbox.debug.command.variant;

import com.runbox.debug.command.Command;

/**
 * Created by qstesiro on 2016/5/29.
 */
public class VariantFieldCommand extends VariantCommand {

    public VariantFieldCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        print(fields());
        return super.execute();
    }
}
