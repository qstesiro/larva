package com.runbox.debug.parser.expression.token.operand;

import com.sun.jdi.*;

/**
 * Created by huangmengmeng01 on 2016/5/16.
 */
public class FieldOperand extends Operand {

    private ObjectReference object = null;
    private com.sun.jdi.Field field = null;

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

    public Type type() throws Exception {
        if (null != field) {
            return field.type();
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
