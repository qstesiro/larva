package com.runbox.debug.parser.expression.token.operand;

import com.runbox.debug.manager.AutoManager;
import com.sun.jdi.Type;
import com.sun.jdi.Value;
import com.sun.tools.javac.util.Pair;

/**
 * Created by qstesiro on 2016/5/21.
 */
public class AutoOperand extends Operand {

    public AutoOperand(String name) {
        super(name);
    }

    public Type type(Type type) throws Exception {
        Pair<Type, Value> pair = AutoManager.instance().get(name());
        if (null != pair) {
            AutoManager.instance().set(name(), type, pair.snd);
            return pair.fst;
        }
        AutoManager.instance().set(name(), type, null);
        return null;
    }

    public Type type() throws Exception {
        Pair<Type, Value> pair = AutoManager.instance().get(name());
        if (null != pair) {
            return pair.fst;
        }
        throw new Exception("invalid auto");
    }

    public Value value(Value value) throws Exception {
        Pair<Type, Value> pair = AutoManager.instance().get(name());
        if (null != pair) {
            AutoManager.instance().set(name(), pair.fst, value);
            return pair.snd;
        }
        AutoManager.instance().set(name(), null, value);
        return null;
    }

    public Value value() throws Exception {
        Pair<Type, Value> pair = AutoManager.instance().get(name());
        if (null != pair.snd) {
            return pair.snd;
        }
        return null;
    }
}
