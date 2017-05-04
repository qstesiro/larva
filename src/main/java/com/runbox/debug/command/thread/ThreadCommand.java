package com.runbox.debug.command.thread;

import java.util.List;
import java.util.LinkedList;
import java.util.Vector;

import com.sun.jdi.ByteValue;
import com.sun.jdi.CharValue;
import com.sun.jdi.ShortValue;
import com.sun.jdi.IntegerValue;
import com.sun.jdi.LongValue;
import com.sun.jdi.StringReference;

import com.runbox.debug.command.Command;

import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.ArrayOperand;

public class ThreadCommand extends Command {

    public ThreadCommand(String command) throws Exception {
        super(command);
		if (this instanceof ThreadSuspendCommand ||
			this instanceof ThreadResumeCommand ||
			this instanceof ThreadInterruptCommand ||
			this instanceof ThreadHoldCommand ||
			this instanceof ThreadWaitCommand ||
			this instanceof ThreadMonitorStartupCommand ||
			this instanceof ThreadMonitorDeathCommand ||
			this instanceof ThreadSwitchCommand) {
			if (null != argument()) {
				values = new Expression(argument()).execute();
			}
		}
    }

	private Expression.Values<Operand> values = null;
	
	protected List<Long> ids() throws Exception {	
        if (null != values) {
            List<Long> ids = new LinkedList<Long>();
			for (int i = 0; i < values.size(); ++i) {
				ids.add(values.getLong(i));
			}            
			return ids;
		}
		return null;
	}

	protected String status() throws Exception {
		if (null != values) {
			return values.getString(0);
		}
		return null;
	}

	protected long id() throws Exception {
		if (null != values) {
			return values.getLong(0);
		}
		throw new Exception("invalid operand");
	}   	   	
}
