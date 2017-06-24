package com.runbox.debug.event.method;

import java.util.List;
import java.util.LinkedList;

import com.runbox.debug.event.LocatableEvent;

import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.ConstOperand;

public class MethodEvent<T extends com.sun.jdi.event.LocatableEvent> extends LocatableEvent<T> {

    public MethodEvent(T event) {
        super(event); arguments();
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
