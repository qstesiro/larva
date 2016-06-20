package com.runbox.debug.parser.expression.token.operand;

import com.runbox.debug.manager.ContextManager;
import com.sun.jdi.*;

/**
 * Created by qstesiro
 */
public class LocalOperand extends Operand {

    StackFrame frame = ContextManager.instance().frame();
    private LocalVariable local = null;

    public LocalOperand(String name) throws Exception {
        super(name);
        if (null != name) {
            if (null != frame) {
                local = frame.visibleVariableByName(name());
                if (null != local) {
                    return;
                }
            }
        }
        throw new Exception("invalid local");
    }

    public Type type() throws Exception {
        if (null != local) {
            return local.type();
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
