package com.runbox.debug.event.clazz;

import java.util.List;
import java.util.LinkedList;

import com.sun.jdi.request.EventRequest;

import com.runbox.debug.event.Event;
import com.runbox.debug.command.clazz.ClassCommand;

import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.ConstOperand;

public class ClassPrepareEvent extends Event<com.sun.jdi.event.ClassPrepareEvent> {

    public ClassPrepareEvent(com.sun.jdi.event.ClassPrepareEvent event) {
        super(event); arguments();	   
    }

    @Override
    public boolean handle() throws Exception {		
        com.sun.jdi.event.ClassPrepareEvent event = ((com.sun.jdi.event.ClassPrepareEvent)event());
        System.out.println("prepare -> " + event.referenceType().name());
        return super.handle();
    }

	private void arguments() {
		if (null != routine()) {
			List<Operand> autos = new LinkedList<Operand>();
			autos.add(new ConstOperand(event().thread().uniqueID()));
			autos.add(new ConstOperand(event().thread()));
			autos.add(new ConstOperand(event().referenceType().name()));
			arguments(autos);
		}
	}
}
