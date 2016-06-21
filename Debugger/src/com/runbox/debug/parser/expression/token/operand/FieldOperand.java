package com.runbox.debug.parser.expression.token.operand;

import com.sun.jdi.*;

/**
 * Created by qstesiro
 */
public class FieldOperand extends Operand {

    private ObjectReference object = null;

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

    private ClassType clazz = null;

    public FieldOperand(ClassType clazz, String name) throws Exception {
        super(name);
        if (null != clazz && null != name) {
            field = clazz.fieldByName(name);
            if (null != field) {
                this.clazz = clazz;
                return;
            }
        }
        throw new Exception("invalid field name");
    }

    private com.sun.jdi.Field field = null;

    public Type type() throws Exception {
        if (null != field) {
            return field.type();
        }
        throw new Exception("invalid field");
    }

    public Value value(Value value) throws Exception {
        if (null != field) {
            if (null != object) {
                Value previous = value();
                object.setValue(field, value);
                return previous;
            } else if (null != clazz) {
                Value previous = value();
                clazz.setValue(field, value);
                return previous;
            }
        }
        throw new Exception("invalid variant");
    }

    public Value value() {
        if (null != field) {
            if (null != object) {
                return object.getValue(field);
            } else if (null != clazz) {
                return clazz.getValue(field);
            }
        }
        return null;
    }
}
