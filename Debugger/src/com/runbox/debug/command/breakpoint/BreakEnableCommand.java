package com.runbox.debug.command.breakpoint;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.BreakManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by qstesiro
 */
public class BreakEnableCommand extends Command {

    public BreakEnableCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid argument");
        }
    }

    @Override
    public boolean execute() throws Exception {
        for (Integer number : numbers()) {
            BreakManager.instance().disable(number);
        }
        return super.execute();
    }

    private List<Integer> numbers() {
        List<Integer> numbers = new LinkedList<>();
        for (String number: argument.split(",")) {
            numbers.add(Integer.valueOf(number.trim()));
        }
        return numbers;
    }

    @Override
    public void help() {
        String help = "break.enable number[, number]* \r\n";
        help += "description\r\n";
        help += "enable a breakpoint or several breakpoints";
        help += "arguments";
        help += "number is at least one, if there are several numbers, they must be separated by semicolon";
        help += "example";
        help += "";
        System.out.println(help);
    }
}
