package com.runbox.debug.command.variant;

/**
 * Created by qstesiro
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
