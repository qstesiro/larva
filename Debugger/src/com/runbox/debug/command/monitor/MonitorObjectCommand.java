package com.runbox.debug.command.monitor;

import com.runbox.debug.Debugger;
import com.runbox.debug.command.Command;
import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.parser.expression.Expression;
import com.runbox.debug.parser.expression.token.Token;
import com.runbox.debug.parser.expression.token.operand.ConstOperand;
import com.runbox.debug.parser.expression.token.operand.Operand;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ThreadReference;

import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/6/15.
 */
public class MonitorObjectCommand extends Command {

    public MonitorObjectCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid argument");
        }
    }

    @Override
    public boolean execute() throws Exception {
        Operand operand = new Expression(argument.trim()).handle();
        if (operand instanceof ConstOperand && !Token.isThis(operand.name())) {
            long id = Long.valueOf(String.valueOf(argument.trim()));
            ObjectReference object = object(id);
            if (null != object) {
                System.out.println(entry(object));
            } else {
                String entry = "id\t\t" + id + "\n";
                entry += "owned:\t\t" + "none" + "\n";
                entry += "waiting:\t" + "none" + "\n";
                System.out.println(entry);
            }
        } else {
            if (null != operand.value() && operand.value() instanceof ObjectReference) {
                ObjectReference object = (ObjectReference)operand.value();
                if (null != object) {
                    System.out.println(entry(object));
                }
            }
        }
        return super.execute();
    }

    private String entry(ObjectReference object) throws IncompatibleThreadStateException {
        String entry = "id\t\t" + object.uniqueID() + "\n";
        ThreadReference thread = object.owningThread();
        entry += "owned:\t\t" + ((null != thread) ? thread : "none") + "\n";
        entry += "waiting:\t";
        List<ThreadReference> threads = object.waitingThreads();
        if (0 < threads.size()) {
            for (int i = 0; i < threads.size(); ++i) {
                entry += ((0 < i) ? "\n\t" : "");
                entry += threads.get(i);
            }
        } else {
            entry += "none";
        }
        return entry + "\n";
    }

    private ObjectReference object(long id) throws IncompatibleThreadStateException {
        for (ThreadReference thread : MachineManager.instance().allThreads()) {
            if (null != thread.currentContendedMonitor() && id == thread.currentContendedMonitor().uniqueID()) {
                return thread.currentContendedMonitor();
            }
            for (ObjectReference object : thread.ownedMonitors()) {
                if (null != object && id == object.uniqueID()) {
                    return object;
                }
            }
        }
        return null;
    }
}
