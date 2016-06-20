package com.runbox.debug.parser.expression.token.operand;

import com.runbox.debug.Debugger;
import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.parser.expression.token.Token;
import com.sun.jdi.*;

/**
 * Created by huangmengmeng01 on 2016/5/10.
 */
public class Operand extends Token {

    public Operand(String name) {
        super(name);
    }

    private VirtualMachine machine = MachineManager.instance();

    protected VirtualMachine machine() {
        return machine;
    }

    public Type type(Type type) throws Exception {
        throw new Exception("invalid invoke");
    }

    public Type type() throws Exception {
        throw new Exception("invalid invoke");
    }

    public byte value(byte value) throws Exception {
        return ((ByteValue)value(machine().mirrorOf(value))).value();
    }

    public char value(char value) throws Exception {
        return ((CharValue)value(machine().mirrorOf(value))).value();
    }

    public short value(short value) throws Exception {
        return ((ShortValue)value(machine().mirrorOf(value))).value();
    }

    public int value(int value) throws Exception {
        return ((IntegerValue)value(machine().mirrorOf(value))).value();
    }

    public long value(long value) throws Exception {
        return ((LongValue)value(machine().mirrorOf(value))).value();
    }

    public float value(float value) throws Exception {
        return ((FloatValue)value(machine().mirrorOf(value))).value();
    }

    public double value(double value) throws Exception {
        return ((DoubleValue)value(machine().mirrorOf(value))).value();
    }

    public boolean value(boolean value) throws Exception {
        return ((BooleanValue)value(machine().mirrorOf(value))).value();
    }

    public Value value(Value value) throws Exception {
        throw new Exception("invalid invoke");
    }

    public Value value() throws Exception {
        throw new Exception("invalid invoke");
    }

    public static boolean subClass(Operand operand) {
        boolean condition = (operand instanceof ConstOperand);
        condition = condition || (operand instanceof FieldOperand);
        condition = condition || (operand instanceof LocalOperand);
        condition = condition || (operand instanceof AutoOperand);
        condition = condition || (operand instanceof ArrayOperand);
        if (condition) {
            return true;
        }
        return false;
    }

    public static boolean auto(String name) {
        if (null != name) {
            if ('@' == name.charAt(0)) {
                return true;
            }
        }
        return false;
    }
}
