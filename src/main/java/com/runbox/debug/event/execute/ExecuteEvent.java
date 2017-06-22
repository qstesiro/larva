package com.runbox.debug.event.execute;

import java.util.List;
import java.util.LinkedList;

import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.StepRequest;
import com.sun.jdi.event.StepEvent;

import com.runbox.debug.command.execute.ExecuteCommand;
import com.runbox.debug.event.LocatableEvent;
import com.runbox.debug.manager.ContextManager;

import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.ConstOperand;

public class ExecuteEvent<T extends StepEvent> extends LocatableEvent<T> {

	public ExecuteEvent(T event) {
		super(event); arguments();		
	}

	@Override
	public boolean handle() throws Exception {		
		if (0 < count()) {
			EventRequest request = event().request();
			if (null != request) {
				request.disable();
				request.addCountFilter(1);
				request.enable();
			}			
			return !super.handle();
		}
		// print();		
        return super.handle();
	}

	private int count() {
		EventRequest request = event().request();
		int count = (Integer)request.getProperty(ExecuteCommand.COUNT);		
		request.putProperty(ExecuteCommand.COUNT, --count);		
		return count;
	}

	private void arguments() {
		if (null != routine()) {
			List<Operand> autos = new LinkedList<Operand>();
			autos.add(new ConstOperand(event().thread().uniqueID()));
			autos.add(new ConstOperand(event().thread()));
			arguments(autos);
		}
	}
	
	private void print() {
		StepRequest request = (StepRequest)event().request();
		String string = (StepRequest.STEP_LINE == request.size() ? "next" : "step");
		string += " " + (StepRequest.STEP_OVER == request.depth() ? "over" : "into");
		System.out.println(string);
	}	
}
