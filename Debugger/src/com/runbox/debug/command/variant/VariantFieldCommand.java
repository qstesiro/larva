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

    @Override
    public void help() {
        String help = "\r\n";
        help += "description\r\n";
        help += "";
        help += "note";
        help += "";
        help += "example";
        help += "";
        System.out.println(help);
    }
}
