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

    public Type type(Type type) throws Exception {
		Type prev = this.type;
		this.type = type;
		return prev;
    }

    public Type type() throws Exception {
		return type;
    }
	
	private Value value = null;

    public Value value(Value value) throws Exception {
		Value prev = this.value;
		this.value = value;
		return prev;	
    }

    public Value value() throws Exception {
		return value;
    }

	// public static class Pair<T, V> {
		
	// 	public Pair(T type, V value) {
	// 		this.type = type;
	// 		this.value = value;
	// 	}

	// 	private T type = null;		
	// 	private V value = null;

	// 	public T type(T type) {
	// 		T prev = this.type;
	// 		this.type = type;
	// 		return prev;
	// 	}

	// 	public T type() {
	// 		return type;
	// 	}

	// 	public V value(V value) {
	// 		V prev = this.value;
	// 		this.value = value;
	// 		return prev;
	// 	}

	// 	public V value() {
	// 		return value;
	// 	}
	// }
}
