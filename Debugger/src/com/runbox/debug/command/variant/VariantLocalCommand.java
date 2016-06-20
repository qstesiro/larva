package com.runbox.debug.command.variant;

/**
 * Created by qstesiro on 2016/5/29.
 */
public class VariantLocalCommand extends VariantCommand {

    public VariantLocalCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        print(locals());
        return super.execute();
    }
}
