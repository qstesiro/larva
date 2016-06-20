package com.runbox.debug.command.variant;

/**
 * Created by qstesiro
 */
public class VariantAutoCommand extends VariantCommand {

    public VariantAutoCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        print(autos());
        return super.execute();
    }
}
