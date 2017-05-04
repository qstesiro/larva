package com.runbox.debug.script.expression.token.operand;

import com.sun.jdi.Type;
import com.sun.jdi.Value;

public class ConstOperand extends Operand {

    private Value value = null;

    public ConstOperand(byte value) {
        super(null); this.value = machine().mirrorOf(value);
    }

    public ConstOperand(char value) {
        super(null); this.value = machine().mirrorOf(value);
    }

    public ConstOperand(short value) {
        super(null); this.value = machine().mirrorOf(value);
    }

    public ConstOperand(int value) {
        super(null); this.value = machine().mirrorOf(value);
    }

    public ConstOperand(long value) {
        super(null); this.value = machine().mirrorOf(value);
    }

    public ConstOperand(float value) {
        super(null); this.value = machine().mirrorOf(value);
    }

    public ConstOperand(double value) {
        super(null); this.value = machine().mirrorOf(value);
    }

    public ConstOperand(boolean value) {
        super(null); this.value = machine().mirrorOf(value);
    }

    public ConstOperand(String value) {
        super(null); this.value = machine().mirrorOf(value);
    }

    public ConstOperand(String name, Value value) {
        super(name); this.value = value;
    }

    public ConstOperand(Value value) {
        super(null); this.value = value;
    }

    public Type type() throws Exception {
        if (null != value) {
            return value.type();
        }
        return null;
    }

    public Value value() throws Exception {
        return value;
    }
}
