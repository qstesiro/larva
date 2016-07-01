package com.runbox.debug.command.breakpoint;

import com.runbox.debug.manager.breakpoint.BreakPoint;
import com.runbox.debug.command.Command;
import com.runbox.debug.manager.BreakManager;

import java.util.Map;

/**
 * Created by qstesiro
 */
public class BreakQueryCommand extends Command {

    public BreakQueryCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        printPoints(BreakManager.instance().points());
        return super.execute();
    }

    private void printPoints(Map<Integer, BreakPoint> points) {
        int index = 0;
        System.out.println("index\tnumber\ttype\tstatus\tlocation");
        for (Integer number : points.keySet()) {
            String log = index++ + "\t";
            log += number + "\t";
            log += type(points.get(number).type()) + "\t";
            log += status(points.get(number).status()) + "\t";
            log += points.get(number).location();
            System.out.println(log);
        }
    }

    private String type(int type) {
        switch (type) {
            case BreakPoint.TYPE_METHOD: {
                return "method";
            }
            case BreakPoint.TYPE_LINE: {
                return "line";
            }
            case BreakPoint.TYPE_ACCESS: {
                return "access";
            }
            case BreakPoint.TYPE_MODIFY: {
                return "modify";
            }
        }
        return "unknown";
    }

    private String status(int status) {
        switch (status) {
            case BreakPoint.STATUS_PENDING: {
                return "pending";
            }
            case BreakPoint.STATUS_ENABLE: {
                return "enable";
            }
            case BreakPoint.STATUS_DISABLE: {
                return "disable";
            }
        }
        return "unknown";
    }

    @Override
    public void help() {
        String help = "break.query\r\n";
        help += "description\r\n";
        help += "print all breakpoints which have been set. each breakpoint entry has five attributes:" +
                "index, number (it`s a unique number which identify a breakpoint), type, status, location";
        help += "example";
        help += "";
        System.out.println(help);
    }
}
