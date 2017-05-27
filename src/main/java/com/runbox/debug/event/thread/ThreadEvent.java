package com.runbox.debug.event.thread;

import java.util.List;
import java.util.LinkedList;

import com.runbox.debug.event.Event;
import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.ConstOperand;

public class ThreadEvent<T extends com.sun.jdi.event.Event> extends Event<T> {

	public ThreadEvent(T event) {
		super(event); arguments();
	}

	private void arguments() {
		if (null != routine()) {
			List<Operand> autos = new LinkedList<Operand>();
			if (this instanceof ThreadStartEvent) {
				com.sun.jdi.event.ThreadStartEvent event = (com.sun.jdi.event.ThreadStartEvent)event();
				autos.add(new ConstOperand(event.thread().uniqueID()));
				autos.add(new ConstOperand(event.thread()));
			} else if (this instanceof ThreadDeathEvent) {
				com.sun.jdi.event.ThreadDeathEvent event = (com.sun.jdi.event.ThreadDeathEvent)event();
				autos.add(new ConstOperand(event.thread().uniqueID()));
				autos.add(new ConstOperand(event.thread()));
			}
			arguments(autos);
		}
	}
}
