package com.runbox.debug.event.exception;

import java.util.List;
import java.util.LinkedList;

import com.sun.jdi.ClassType;
import com.sun.jdi.Field;
import com.sun.jdi.Location;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.StringReference;
import com.sun.jdi.ArrayReference;

import com.runbox.debug.event.Event;
import com.runbox.debug.manager.SourceManager;
import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.ConstOperand;
import com.runbox.debug.script.expression.token.operand.FieldOperand;

public class ExceptionEvent extends Event<com.sun.jdi.event.ExceptionEvent> {

	public ExceptionEvent(com.sun.jdi.event.ExceptionEvent event) {
		super(event); arguments();
	}

	@Override
	public boolean handle() throws Exception {
		System.out.println("catch exception -> " + event().exception().type().name());
		return !super.handle();
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
