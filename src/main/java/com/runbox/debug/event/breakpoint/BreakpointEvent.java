package com.runbox.debug.event.breakpoint;

import java.util.List;
import java.util.LinkedList;

import com.sun.jdi.request.EventRequest;

import com.runbox.debug.event.LocatableEvent;
import com.runbox.debug.manager.ExecuteManager;

import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.ConstOperand;

public class BreakpointEvent<T extends com.sun.jdi.event.LocatableEvent> extends LocatableEvent<T> {

	public BreakpointEvent(T event) {
		super(event); arguments();
	}	

	@Override
	public boolean handle() throws Exception {
		EventRequest request = event().request();
		if (null != request.getProperty(ExecuteManager.Breakpoint.OBJECT)) {
			System.out.println("hit location -> " +
							   ((ExecuteManager.Breakpoint)
								request.getProperty(ExecuteManager.Breakpoint.OBJECT))
							   .location());
			ExecuteManager.instance().clean(event().thread());
		}
		return super.handle();
	}
	
	private void arguments() {
		if (null != routine()) {
			List<Operand> autos = new LinkedList<Operand>();
			autos.add(new ConstOperand(event().thread().uniqueID()));
			autos.add(new ConstOperand(event().thread()));
			arguments(autos);
		}
	}
}
