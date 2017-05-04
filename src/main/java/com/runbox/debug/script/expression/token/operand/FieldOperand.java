package com.runbox.debug.script.expression.token.operand;

import com.sun.jdi.Field;
import com.sun.jdi.Type;
import com.sun.jdi.Value;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ClassNotLoadedException;

import com.runbox.debug.manager.ContextManager;

public class FieldOperand extends Operand {

	public FieldOperand(String name) throws Exception {
		super(name);
		ObjectReference object = ContextManager.instance().frame().thisObject();
		if (null != object && null != name) {
            field = object.referenceType().fieldByName(name);
            if (null != field) {
                this.object = object;
                return;
            }
        }
        throw new Exception("invalid filed name");
	}       

    public FieldOperand(ObjectReference object, String name) throws Exception {
        super(name);
        if (null != object && null != name) {
            field = object.referenceType().fieldByName(name);
            if (null != field) {
                this.object = object;
                return;
            }
        }
        throw new Exception("invalid filed name");
    }

	private ObjectReference object = null;
	private Field field = null;
	
    public Type type() throws Exception {
        if (null != field) {
			try {
				return field.type();
			} catch (ClassNotLoadedException e) {
				return null;
			}
        }
        throw new Exception("invalid field");
    }

    public Value value(Value value) throws Exception {
        if (null != object && null != field) {
            Value previous = value();
            object.setValue(field, value);
            return previous;
        }
        throw new Exception("invalid variant");
    }

    public Value value() {
        if (null != object && null != field) {
            return object.getValue(field);
        }
        return null;
    }
}
