package com.runbox.debug.parser.expression.token.operand;

import com.sun.jdi.*;

/**
 * Created by qstesiro
 */
public class ArrayOperand extends Operand {

    private ArrayReference array = null;
    private int index = 0;

    public ArrayOperand(ArrayReference array, int index) throws Exception {
        super(null);
        if (null != array) {
            if (0 <= index && index < array.length()) {
                this.array = array;
                this.index = index;
                return;
            }
        }
        throw new Exception("invalid array");
    }

    public Type type() throws Exception {
        if (null != array) {
            if (0 <= index && index < array.length()) {
                return value().type();
            }
        }
        throw new Exception("invalid type");
    }

    public Value value(Value value) throws Exception {
        if (null != array) {
            if (0 <= index && index < array.length()) {
                Value previous = value();
                array.setValue(index, value);
                return previous;
            }
        }
        throw new Exception("invalid array");
    }

    public Value value() throws Exception {
        if (null != array) {
            if (0 <= index && index < array.length()) {
                return array.getValue(index);
            }
        }
        throw new Exception("invalid array");
    }
}
