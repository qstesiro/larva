package com.runbox.debug.command.execute;

import java.util.List;

import com.sun.jdi.request.StepRequest;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.ExecuteManager;
import com.runbox.debug.script.expression.Expression;

import com.runbox.script.statement.node.RoutineNode;

public class ExecuteWalkCommand extends ExecuteCommand {

    public ExecuteWalkCommand(String command) throws Exception {
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
		if (null != ContextManager.instance().current()) {
			if (this instanceof ExecuteNextOverCommand) {
				return ExecuteManager.instance().create(ContextManager.instance().current(),
														StepRequest.STEP_LINE,
														StepRequest.STEP_OVER,
														routine());
			} else if (this instanceof ExecuteNextIntoCommand) {
				return ExecuteManager.instance().create(ContextManager.instance().current(),
														StepRequest.STEP_LINE,
														StepRequest.STEP_INTO,
														routine());
			} else if (this instanceof ExecuteStepOverCommand) {
				return ExecuteManager.instance().create(ContextManager.instance().current(),
														StepRequest.STEP_MIN,
														StepRequest.STEP_OVER,
														routine());
			} else if (this instanceof ExecuteStepIntoCommand) {
				return ExecuteManager.instance().create(ContextManager.instance().current(),
														StepRequest.STEP_MIN,
														StepRequest.STEP_INTO,
														routine());
			}			
		}
		throw new Exception("thread context is null, don`t execute.");	   
	}
	
	protected void config(StepRequest request) {
		if (null != request) {
			request.disable();			
			request.addCountFilter(1);
			request.putProperty(COUNT, count);
			request.enable();
		}
	}
	
	private RoutineNode routine = null;
	
	@Override
	public RoutineNode routine(RoutineNode routine) throws Exception {	
        RoutineNode prev = this.routine;
        this.routine = routine; arguments();
        return prev;
    }

	@Override
    public RoutineNode routine() throws Exception {
		return routine; 
    }

	private void arguments() {
		if (null != routine) {
			List<String> list = routine.arguments(); list.clear();
			list.add("@id");
			list.add("@thread");			
		}
	}
}
