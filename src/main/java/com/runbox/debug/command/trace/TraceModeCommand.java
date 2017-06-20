package com.runbox.debug.command.trace;

import com.sun.jdi.VirtualMachine;

import com.runbox.debug.manager.TraceManager;
import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;

public class TraceModeCommand extends TraceCommand {

	public TraceModeCommand(String command) throws Exception {
		super(command);
		if (null != argument()) {
			mode = new Expression(argument()).execute().getInteger(0);
			return;
		}
		throw new Exception("invalid argument");
	}

	public int mode = VirtualMachine.TRACE_NONE;
	
	@Override
	public boolean execute() throws Exception {		
		TraceManager.instance().mode(mode);
		return super.execute();
	}	
}
