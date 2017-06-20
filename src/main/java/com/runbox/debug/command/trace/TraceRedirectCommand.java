package com.runbox.debug.command.trace;

import com.runbox.debug.manager.TraceManager;
import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;

public class TraceRedirectCommand extends TraceCommand {

	public TraceRedirectCommand(String command) throws Exception {
		super(command);
		if (null != argument()) {
			file = new Expression(argument()).execute().getString(0);
			return;
		}
		throw new Exception("invalid argument");
	}

	private String file = null;
	
	@Override
	public boolean execute() throws Exception {
		if (null != file) {
			TraceManager.instance().redirect(file);
		}
		return super.execute();
	}
}
