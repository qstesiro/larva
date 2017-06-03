package com.runbox.debug.event.clazz;

import java.util.List;
import java.util.LinkedList;

import com.sun.jdi.request.EventRequest;

import com.runbox.debug.event.Event;
import com.runbox.debug.command.clazz.ClassCommand;

import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.ConstOperand;

public class ClassUnloadEvent extends Event<com.sun.jdi.event.ClassUnloadEvent> {

    public ClassUnloadEvent(com.sun.jdi.event.ClassUnloadEvent event) {
        super(event); arguments();
    }

    @Override
    public boolean handle() throws Exception {
        com.sun.jdi.event.ClassUnloadEvent event = (com.sun.jdi.event.ClassUnloadEvent)event();
        System.out.println("unload -> " + event.className());
        return super.handle();
    }

	private void arguments() {
		if (null != routine()) {
			List<Operand> autos = new LinkedList<Operand>();
			autos.add(new ConstOperand(event().className()));
			arguments(autos);
		}
	}
}
