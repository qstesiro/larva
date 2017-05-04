package com.runbox.debug.script.expression.token.operand;

import com.sun.jdi.Type;
import com.sun.jdi.Value;
import com.sun.jdi.StackFrame;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.ClassNotLoadedException;

import com.runbox.debug.manager.ContextManager;

public class LocalOperand extends Operand {    

    public LocalOperand(String name) throws Exception {
        super(name);		
        if (null != name) {
			frame = ContextManager.instance().frame();
            if (null != frame) {
                local = frame.visibleVariableByName(name());
                if (null != local) {
                    return;
                }
            }
        }
        throw new Exception("invalid local");
    }

	private StackFrame frame = null;	
	private LocalVariable local = null;
	
    public Type type() throws Exception {
        if (null != local) {
			try {
				return local.type();
			} catch (ClassNotLoadedException e) {
				return null;
			}
        }
        throw new Exception("invalid type");
    }

    public Value value(Value value) throws Exception {
        if (null != frame && null != local) {
            Value previous = value();
            frame.setValue(local, value);
            return previous;
        }
        throw new Exception("invalid local");
    }

    public Value value() throws Exception {
        if (null != frame && null != local) {
            return frame.getValue(local);
        }
        throw new Exception("invalid variant");
    }
}
