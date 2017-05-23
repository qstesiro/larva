package com.runbox.debug.event.exception;

import java.util.List;

import com.sun.jdi.ClassType;
import com.sun.jdi.Field;
import com.sun.jdi.Location;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;

import com.runbox.debug.event.Event;
import com.runbox.debug.manager.SourceManager;
import com.runbox.debug.script.expression.token.operand.FieldOperand;
import com.runbox.debug.script.expression.token.operand.Operand;

public class ExceptionEvent extends Event<com.sun.jdi.event.ExceptionEvent> {

	public ExceptionEvent(com.sun.jdi.event.ExceptionEvent event) {
		super(event);
	}

	@Override
	public boolean handle() throws Exception {
        Location location = event().location();
        String line = SourceManager.instance().line(event().catchLocation());
		if (null != line) System.out.println(line);
        print(event().exception());        
		return !super.handle();
	}

   private void print(ObjectReference object) throws Exception {
        ClassType type = (ClassType)object.referenceType();
        System.out.println(type.name());
        List<Field> fields = type.allFields();
        for (Field field : fields) {
            System.out.println(format(new FieldOperand(object, field.name())));
        }
    }

    private String format(Operand operand) throws Exception {
        String print = "";
        if (null != operand.value()) {
            if (null != operand.name()) {
                print += operand.name();
                print += " :" + operand.type().name();
                print += " = ";
            }
            print += operand.value();
            if (operand.value() instanceof ObjectReference) {
                print += " :" + operand.value().type().name();
            }
        } else {
            if (null != operand.name()) {
                print = operand.name();
            }
            print += " = " + "null";
        }
        return print;
    }
}
