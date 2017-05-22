package com.runbox.debug.event.execute;

import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.StepRequest;
import com.sun.jdi.event.StepEvent;

import com.runbox.debug.command.execute.ExecuteCommand;
import com.runbox.debug.event.LocatableEvent;
import com.runbox.debug.manager.ContextManager;

public class ExecuteEvent<T extends StepEvent> extends LocatableEvent<T> {

	public ExecuteEvent(T event) {
		super(event);
		ContextManager.instance().thread(event.thread());
	}

	@Override
	public boolean handle() throws Exception {		
		if (0 == count()) {
			// print();
			return !super.handle();
		}
        return super.handle();
	}

	private int count() {
		EventRequest request = event.request();
		int count = (int)request.getProperty(ExecuteCommand.COUNT);
		request.putProperty(ExecuteCommand.COUNT, --count);
		return count;
	}

	private void print() {
		StepRequest request = (StepRequest)event.request();
		String string = (StepRequest.STEP_LINE == request.size() ? "next" : "step");
		string += " " + (StepRequest.STEP_OVER == request.depth() ? "over" : "into");		
		System.out.println(string);
	}	
}
