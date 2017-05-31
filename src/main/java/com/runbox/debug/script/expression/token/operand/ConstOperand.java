package com.runbox.debug.script.expression.token.operand;

import com.sun.jdi.Type;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;

import com.runbox.debug.manager.MachineManager;

public class ConstOperand extends Operand {    
	
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

	private VirtualMachine machine() {
		return MachineManager.instance().get();
	}
	
	@Override
    public Type type() throws Exception {
        if (null != value) {
            return value.type();
        }
        return null;
    }

	private Value value = null;	
	
	@Override
    public Value value() throws Exception {
        return value;
    }	
}
