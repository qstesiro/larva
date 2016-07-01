package com.runbox.debug.command.variant;

/**
 * Created by qstesiro
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
