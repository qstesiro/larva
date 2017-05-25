package com.runbox.debug.command.execute;

import com.sun.jdi.request.StepRequest;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.ExecuteManager;
import com.runbox.debug.script.expression.Expression;

public class ExecuteCommand extends Command {	
	
    public ExecuteCommand(String command) throws Exception {
        super(command);
		if (this instanceof ExecuteNextOverCommand ||
			this instanceof ExecuteNextIntoCommand ||
			this instanceof ExecuteStepOverCommand ||
			this instanceof ExecuteStepIntoCommand) {
			if (null != argument()) {
				count = new Expression(argument()).execute().getInteger(0);
			}
		}
    }

	public static final String COUNT = "count";
	
	private int count = 1;

	protected StepRequest create() throws Exception {
		if (null != ContextManager.instance().thread()) {
			if (this instanceof ExecuteNextOverCommand) {
				return ExecuteManager.instance().create(ContextManager.instance().thread(), StepRequest.STEP_LINE, StepRequest.STEP_OVER, routine());
			} else if (this instanceof ExecuteNextIntoCommand) {
				return ExecuteManager.instance().create(ContextManager.instance().thread(), StepRequest.STEP_LINE, StepRequest.STEP_INTO, routine());
			} else if (this instanceof ExecuteStepOverCommand) {
				return ExecuteManager.instance().create(ContextManager.instance().thread(), StepRequest.STEP_MIN, StepRequest.STEP_OVER, routine());
			} else if (this instanceof ExecuteStepIntoCommand) {
				return ExecuteManager.instance().create(ContextManager.instance().thread(), StepRequest.STEP_MIN, StepRequest.STEP_INTO, routine());
			}			
		}
		throw new Exception("thread context is null, don`t execute.");	   
	}
	
	protected void config(StepRequest request) {
		if (null != request) {
			request.disable();
			request.addCountFilter(count);
			request.putProperty(COUNT, count);
			request.enable();
		}
	}
}
