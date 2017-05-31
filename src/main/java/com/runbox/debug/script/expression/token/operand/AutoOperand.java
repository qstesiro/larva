package com.runbox.debug.script.expression.token.operand;

import com.sun.jdi.Type;
import com.sun.jdi.Value;

public class AutoOperand extends Operand {

    public AutoOperand(String name) {
        super(name);
    }

	public AutoOperand(String name, Type type, Value value) {
		super(name);
		this.type = type;
		this.value = value;
	}
	
	private Type type = null;

	@Override
    public Type type(Type type) throws Exception {
		Type prev = this.type;
		this.type = type;
		return prev;
    }

	@Override
    public Type type() throws Exception {
		return type;
    }
	
	private Value value = null;

	@Override
    public Value value(Value value) throws Exception {
		Value prev = this.value;
		this.value = value;
		return prev;
    }

	@Override
    public Value value() throws Exception {
		return value;
    }	
}
