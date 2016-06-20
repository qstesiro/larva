package com.runbox.debug.command.breakpoint;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.BreakManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/4/26.
 */
public class BreakDisableCommand extends Command {

    public BreakDisableCommand(String command) throws Exception {
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
}
