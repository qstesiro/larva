package com.runbox.debug.script.expression;

import java.util.List;

import com.sun.jdi.*;

import com.runbox.manager.ImportManager;
import com.runbox.debug.manager.MachineManager;

import com.runbox.debug.script.expression.token.Token;
import com.runbox.debug.script.expression.token.operand.*;

public class Computer {

    public static Operand computeI2(Operand operand1, Operand operand2) throws Exception { // [
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.valueType() instanceof ArrayType) {
                    if (operand2.isInteger()) {
                        int index = operand2.intValue();
                        return new ArrayOperand(operand1.arrayValue(), index);
                    }
                }
            }
        }
        throw new Exception("invalid array");
    }

	public static Operand computeH(RoutineOperand routine, List<Operand> arguments) throws Exception { // ->
		if (null != routine && null != arguments) {
			routine.arguments(arguments);
			return routine.invoke();			
		}
		throw new Exception("invalue routine invoke");
	}
	
    public static Operand computeG1(Operand operand1, Operand operand2) throws Exception { // .
        if (null != operand1 && null != operand2) {
            if (Operand.subClass(operand1)) {
                if (operand1.isObject() && !(operand2 instanceof ConstOperand)) {
                    return new FieldOperand(operand1.objectValue(), operand2.name());
                }
            }
        }
        throw new Exception("invalid variant");
    }

    public static Operand computeG2(Operand operand1, Operand operand2) throws Exception { // .
        if (null != operand1 && null != operand2) {
            if (!Operand.subClass(operand1) && !Operand.subClass(operand2)) {
                return new Operand(operand1.name() + "." + operand2.name());
            }
        }
        throw new Exception("invalid variant");
    }

    public static Operand computeF(Operand operand1, Operand operand2) throws Exception { // <-
        if (null != operand1 && null != operand2) {
            if (Token.primitive(operand1.name()) && null != operand2.value()) {
                if (operand1.name().equals("byte")) {
                    if (operand2.isByte()) {
                        return operand2;
                    } else if (operand2.isChar()) {
                        return new ConstOperand((byte)operand2.charValue());
                    } else if (operand2.isShort()) {
                        return new ConstOperand((byte)operand2.shortValue());
                    } else if (operand2.isInteger()) {
                        return new ConstOperand((byte)operand2.intValue());
                    } else if (operand2.isLong()) {
                        return new ConstOperand((byte)operand2.longValue());
                    } else if (operand2.isFloat()) {
                        return new ConstOperand((byte)operand2.floatValue());
                    } else if (operand2.isDouble()) {
                        return new ConstOperand((byte)operand2.doubleValue());
                    }
                } else if (operand1.name().equals("char")) {
                    if (operand2.isByte()) {
                        return new ConstOperand((char)operand2.byteValue());
                    } else if (operand2.isChar()) {
                        return operand2;
                    } else if (operand2.isShort()) {
                        return new ConstOperand((char)operand2.shortValue());
                    } else if (operand2.isInteger()) {
                        return new ConstOperand((char)operand2.intValue());
                    } else if (operand2.isLong()) {
                        return new ConstOperand((char)operand2.longValue());
                    } else if (operand2.isFloat()) {
                        return new ConstOperand((char)operand2.floatValue());
                    } else if (operand2.isDouble()) {
                        return new ConstOperand((char)operand2.doubleValue());
                    }
                } else if (operand1.name().equals("short")) {
                    if (operand2.isByte()) {
                        return new ConstOperand((short)operand2.byteValue());
                    } else if (operand2.isChar()) {
                        return new ConstOperand((short)operand2.charValue());
                    } else if (operand2.isShort()) {
                        return operand2;
                    } else if (operand2.isInteger()) {
                        return new ConstOperand((short)operand2.intValue());
                    } else if (operand2.isLong()) {
                        return new ConstOperand((short)operand2.longValue());
                    } else if (operand2.isFloat()) {
                        return new ConstOperand((short)operand2.floatValue());
                    } else if (operand2.isDouble()) {
                        return new ConstOperand((short)operand2.doubleValue());
                    }
                } else if (operand1.name().equals("int")) {
                    if (operand2.isByte()) {
                        return new ConstOperand((int)operand2.byteValue());
                    } else if (operand2.isChar()) {
                        return new ConstOperand((int)operand2.charValue());
                    } else if (operand2.isShort()) {
                        return new ConstOperand((int)operand2.shortValue());
                    } else if (operand2.isInteger()) {
                        return operand2;
                    } else if (operand2.isLong()) {
                        return new ConstOperand((int)operand2.longValue());
                    } else if (operand2.isFloat()) {
                        return new ConstOperand((int)operand2.floatValue());
                    } else if (operand2.isDouble()) {
                        return new ConstOperand((int)operand2.doubleValue());
                    }
                } else if (operand1.name().equals("long")) {
                    if (operand2.isByte()) {
                        return new ConstOperand((long)operand2.byteValue());
                    } else if (operand2.isChar()) {
                        return new ConstOperand((long)operand2.charValue());
                    } else if (operand2.isShort()) {
                        return new ConstOperand((long)operand2.shortValue());
                    } else if (operand2.isInteger()) {
                        return new ConstOperand((long)operand2.intValue());
                    } else if (operand2.isLong()) {
                        return operand2;
                    } else if (operand2.isFloat()) {
                        return new ConstOperand((long)operand2.floatValue());
                    } else if (operand2.isDouble()) {
                        return new ConstOperand((long)operand2.doubleValue());
                    }
                } else if (operand1.name().equals("float")) {
                    if (operand2.isByte()) {
                        return new ConstOperand((float)operand2.byteValue());
                    } else if (operand2.isChar()) {
                        return new ConstOperand((float)operand2.charValue());
                    } else if (operand2.isShort()) {
                        return new ConstOperand((float)operand2.shortValue());
                    } else if (operand2.isInteger()) {
                        return new ConstOperand((float)operand2.intValue());
                    } else if (operand2.isLong()) {
                        return new ConstOperand((float)operand2.longValue());
                    } else if (operand2.isFloat()) {
                        return operand2;
                    } else if (operand2.isDouble()) {
                        return new ConstOperand((float)operand2.doubleValue());
                    }
                } else if (operand1.name().equals("double")) {
                    if (operand2.isByte()) {
                        return new ConstOperand((double)operand2.byteValue());
                    } else if (operand2.isChar()) {
                        return new ConstOperand((double)operand2.charValue());
                    } else if (operand2.isShort()) {
                        return new ConstOperand((double)operand2.shortValue());
                    } else if (operand2.isInteger()) {
                        return new ConstOperand((double)operand2.intValue());
                    } else if (operand2.isLong()) {
                        return new ConstOperand((double)operand2.longValue());
                    } else if (operand2.isFloat()) {
                        return new ConstOperand((double)operand2.floatValue());
                    } else if (operand2.isDouble()) {
                        return operand2;
                    }
                } else if (operand1.name().equals("boolean")) {
                    return operand2;
                }
            }
        }
        throw new Exception("invalid type covert");
    }

    public static Operand computeE1(Operand operand) throws Exception { // !
        if (null != operand) {
            if (null != operand.value()) {
                if (operand.isBoolean()) {
                    return new ConstOperand(!operand.boolValue());
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeE2(Operand operand) throws Exception { // ~
        if (null != operand) {
            if (null != operand.value()) {
                if (operand.isByte()) {
                    return new ConstOperand(~operand.byteValue());
                } else if (operand.isChar()) {
                    return new ConstOperand(~operand.charValue());
                } else if (operand.isShort()) {
                    return new ConstOperand(~operand.shortValue());
                } else if (operand.isInteger()) {
                    return new ConstOperand(~operand.intValue());
                } else if (operand.isLong()) {
                    return new ConstOperand(~operand.longValue());
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeE3(Operand operand) throws Exception { // +++
        if (null != operand) {
            if (null != operand.value()) {
				VirtualMachine machine = MachineManager.instance().get();
                if (operand.isByte()) {
                    byte value = operand.byteValue();
                    operand.value(machine.mirrorOf(++value));
                    return new ConstOperand(value);
                } else if (operand.isChar()) {
                    char value = operand.charValue();
                    operand.value(machine.mirrorOf(++value));
                    return new ConstOperand(value);
                } else if (operand.isShort()) {
                    short value = operand.shortValue();
                    operand.value(machine.mirrorOf(++value));
                    return new ConstOperand(value);
                } else if (operand.isInteger()) {
                    int value = operand.intValue();
                    operand.value(machine.mirrorOf(++value));
                    return new ConstOperand(value);
                } else if (operand.isLong()) {
                    long value = operand.longValue();
                    operand.value(machine.mirrorOf(++value));
                    return new ConstOperand(value);
                } else if (operand.isFloat()) {
                    float value = operand.floatValue();
                    operand.value(machine.mirrorOf(++value));
                    return new ConstOperand(value);
                } else if (operand.isDouble()) {
                    double value = operand.doubleValue();
                    operand.value(machine.mirrorOf(++value));
                    return new ConstOperand(value);
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeE4(Operand operand) throws Exception { // ++
        if (null != operand) {
            if (null != operand.value()) {
				VirtualMachine machine = MachineManager.instance().get();
                if (operand.isByte()) {
                    byte value = operand.byteValue();
                    ConstOperand constant = new ConstOperand(value++);
                    operand.value(machine.mirrorOf(value));
                    return constant;
                } else if (operand.isChar()) {
                    char value = operand.charValue();
                    ConstOperand constant = new ConstOperand(value++);
                    operand.value(machine.mirrorOf(value));
                    return constant;
                } else if (operand.isShort()) {
                    short value = operand.shortValue();
                    ConstOperand constant = new ConstOperand(value++);
                    operand.value(machine.mirrorOf(value));
                    return constant;
                } else if (operand.isInteger()) {
                    int value = operand.intValue();
                    ConstOperand constant = new ConstOperand(value++);
                    operand.value(machine.mirrorOf(value));
                    return constant;
                } else if (operand.isLong()) {
                    long value = operand.longValue();
                    ConstOperand constant = new ConstOperand(value++);
                    operand.value(machine.mirrorOf(value));
                    return constant;
                } else if (operand.isFloat()) {
                    float value = operand.floatValue();
                    ConstOperand constant = new ConstOperand(value++);
                    operand.value(machine.mirrorOf(value));
                    return constant;
                } else if (operand.isDouble()) {
                    double value = operand.doubleValue();
                    ConstOperand constant = new ConstOperand(value++);
                    operand.value(machine.mirrorOf(value));
                    return constant;
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeE5(Operand operand) throws Exception { // ---
        if (null != operand) {
            if (null != operand.value()) {
				VirtualMachine machine = MachineManager.instance().get();
                if (operand.isByte()) {
                    byte value = operand.byteValue();
                    operand.value(machine.mirrorOf(--value));
                    return new ConstOperand(value);
                } else if (operand.isChar()) {
                    char value = operand.charValue();
                    operand.value(machine.mirrorOf(--value));
                    return new ConstOperand(value);
                } else if (operand.isShort()) {
                    short value = operand.shortValue();
                    operand.value(machine.mirrorOf(--value));
                    return new ConstOperand(value);
                } else if (operand.isInteger()) {
                    int value = operand.intValue();
                    operand.value(machine.mirrorOf(--value));
                    return new ConstOperand(value);
                } else if (operand.isLong()) {
                    long value = operand.longValue();
                    operand.value(machine.mirrorOf(--value));
                    return new ConstOperand(value);
                } else if (operand.isFloat()) {
                    float value = operand.floatValue();
                    operand.value(machine.mirrorOf(--value));
                    return new ConstOperand(value);
                } else if (operand.isDouble()) {
                    double value = operand.doubleValue();
                    operand.value(machine.mirrorOf(--value));
                    return new ConstOperand(value);
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeE6(Operand operand) throws Exception { // --
        if (null != operand) {
            if (null != operand.value()) {
				VirtualMachine machine = MachineManager.instance().get();
                if (operand.isByte()) {
                    byte value = operand.byteValue();
                    ConstOperand constant = new ConstOperand(value--);
                    operand.value(machine.mirrorOf(value));
                    return constant;
                } else if (operand.isChar()) {
                    char value = operand.charValue();
                    ConstOperand constant = new ConstOperand(value--);
                    operand.value(machine.mirrorOf(value));
                    return constant;
                } else if (operand.isShort()) {
                    short value = operand.shortValue();
                    ConstOperand constant = new ConstOperand(value--);
                    operand.value(machine.mirrorOf(value));
                    return constant;
                } else if (operand.isInteger()) {
                    int value = operand.intValue();
                    ConstOperand constant = new ConstOperand(value--);
                    operand.value(machine.mirrorOf(value));
                    return constant;
                } else if (operand.isLong()) {
                    long value = operand.longValue();
                    ConstOperand constant = new ConstOperand(value--);
                    operand.value(machine.mirrorOf(value));
                    return constant;
                } else if (operand.isFloat()) {
                    float value = operand.floatValue();
                    ConstOperand constant = new ConstOperand(value--);
                    operand.value(machine.mirrorOf(value));
                    return constant;
                } else if (operand.isDouble()) {
                    double value = operand.doubleValue();
                    ConstOperand constant = new ConstOperand(value--);
                    operand.value(machine.mirrorOf(value));
                    return constant;
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeE8(Operand operand) throws Exception { // 0-
        if (null != operand) {
            if (null != operand.value()) {
                if (operand.isByte()) {
                    byte value = operand.byteValue();
                    return new ConstOperand(0 - value);
                } else if (operand.isChar()) {
                    char value = operand.charValue();
                    return new ConstOperand(0 - value);
                } else if (operand.isShort()) {
                    short value = operand.shortValue();
                    return new ConstOperand(0 - value);
                } else if (operand.isInteger()) {
                    int value = operand.intValue();
                    return new ConstOperand(0 - value);
                } else if (operand.isLong()) {
                    long value = operand.longValue();
                    return new ConstOperand(0 - value);
                } else if (operand.isFloat()) {
                    float value = operand.floatValue();
                    return new ConstOperand(0 - value);
                } else if (operand.isDouble()) {
                    double value = operand.doubleValue();
                    return new ConstOperand(0 - value);
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeD1(Operand operand1, Operand operand2) throws Exception { // *
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 * value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 * value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 * value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 * value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 * value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = operand1.floatValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 * value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = operand1.doubleValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 * value2);
                    }
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeD2(Operand operand1, Operand operand2) throws Exception { // /
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 / value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 / value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 / value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 / value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 / value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = operand1.floatValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 / value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = operand1.doubleValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 / value2);
                    }
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeD3(Operand operand1, Operand operand2) throws Exception { // %
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 % value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 % value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 % value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 % value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 % value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = operand1.floatValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 % value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = operand1.doubleValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 % value2);
                    }
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeC1(Operand operand1, Operand operand2) throws Exception { // +
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 + value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 + value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 + value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 + value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 + value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = operand1.floatValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 + value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = operand1.doubleValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 + value2);
                    }
                } else if (operand1.isString()) {
					String value1 = operand1.strValue();
					if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.isBoolean()) {
						boolean value2 = operand2.boolValue();
						return new ConstOperand(value1 + value2);
					} else if (operand2.isString()) {
						String value2 = operand2.strValue();
						return new ConstOperand(value1 + value2);
					}
				}
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeC2(Operand operand1, Operand operand2) throws Exception { // -
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 - value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 - value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 - value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 - value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 - value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = operand1.floatValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 - value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = operand1.doubleValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 - value2);
                    }
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeB1(Operand operand1, Operand operand2) throws Exception { // <<
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 << value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 << value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 << value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 << value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 << value2);
                    }
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeB2(Operand operand1, Operand operand2) throws Exception { // >>
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >> value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >> value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >> value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >> value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >> value2);
                    }
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeB3(Operand operand1, Operand operand2) throws Exception { // >>>
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >>> value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >>> value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >>> value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >>> value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >>> value2);
                    }
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeA1(Operand operand1, Operand operand2) throws Exception { // <
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 < value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 < value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 < value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 < value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 < value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = operand1.floatValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 < value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = operand1.doubleValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 < value2);
                    }
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeA2(Operand operand1, Operand operand2) throws Exception { // <=
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 <= value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 <= value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 <= value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 <= value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 <= value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = operand1.floatValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 <= value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = operand1.doubleValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 <= value2);
                    }
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeA3(Operand operand1, Operand operand2) throws Exception { // >
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 > value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 > value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 > value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 > value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 > value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = operand1.floatValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 > value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = operand1.doubleValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 > value2);
                    }
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeA4(Operand operand1, Operand operand2) throws Exception { // >=
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 >= value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 >= value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 >= value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 >= value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 >= value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = operand1.floatValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 >= value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = operand1.doubleValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 >= value2);
                    }
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeA5(Operand operand1, Operand operand2) throws Exception { // instanceof
        if (null != operand1 && null != operand2) {
            if (!(operand1 instanceof ConstOperand) && !Operand.subClass(operand2)) {
                if (null != operand1.value() && null != operand2.name()) {
					String clazz = clazz(operand2.name());
                    if (operand1.valueType() instanceof ClassType) {
                        ClassType type = (ClassType)operand1.valueType();						
                        if (type.name().equals(clazz)) {
                            return new ConstOperand(true);
                        }
                        List<InterfaceType> interfaces = type.allInterfaces();
                        for (InterfaceType element : interfaces) {							
                            if (element.name().equals(clazz)) {
                                return new ConstOperand(true);
                            }
                        }
                        type = type.superclass();
                        while (null != type) {
                            if (type.name().equals(clazz)) {
                                return new ConstOperand(true);
                            }
                            type = type.superclass();
                        }
                    }
                    return new ConstOperand(false);
                }
            }
        }
        throw new Exception("invalid instanceof");
    }

    public static Operand compute91(Operand operand1, Operand operand2) throws Exception { // ==
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 == value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 == value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 == value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 == value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 == value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = operand1.floatValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 == value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = operand1.doubleValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 == value2);
                    }
                } else if (operand1.type() instanceof ReferenceType && operand2.type() instanceof ReferenceType) {
					if ((operand1.valueType() instanceof ClassType || operand1.valueType() instanceof InterfaceType) &&
						(operand2.valueType() instanceof ClassType || operand2.valueType() instanceof InterfaceType)) {
						return new ConstOperand(operand1.value() == operand2.value());
					} else if (operand1.valueType() instanceof ArrayType && operand2.valueType() instanceof ArrayType) {
						return new ConstOperand(operand1.value() == operand2.value());
					}
                }
            } else if (null == operand1.value() && null != operand2.value()) {
				if (operand2.valueType() instanceof ReferenceType) return new ConstOperand(false);
            } else if (null != operand1.value() && null == operand2.value()) {
				if (operand1.valueType() instanceof ReferenceType) return new ConstOperand(false);
            } else if (null == operand1.value() && null == operand2.value()) {
                return new ConstOperand(true);
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand compute92(Operand operand1, Operand operand2) throws Exception { // !=
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 != value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 != value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 != value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 != value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 != value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = operand1.floatValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 != value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = operand1.doubleValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isFloat()) {
                        float value2 = operand2.floatValue();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.isDouble()) {
                        double value2 = operand2.doubleValue();
                        return new ConstOperand(value1 != value2);
                    }
                } else if (operand1.type() instanceof ReferenceType && operand2.type() instanceof ReferenceType) {                    
					if ((operand1.valueType() instanceof ClassType || operand1.valueType() instanceof InterfaceType) &&
						(operand2.valueType() instanceof ClassType || operand2.valueType() instanceof InterfaceType)) {
						return new ConstOperand(operand1.value() != operand2.value());
					} else if (operand1.valueType() instanceof ArrayType && operand2.valueType() instanceof ArrayType) {
						return new ConstOperand(operand1.value() != operand2.value());
					}
                }
            } else if (null == operand1.value() && null != operand2.value()) {
				if (operand2.valueType() instanceof ReferenceType) return new ConstOperand(true);				
            } else if (null != operand1.value() && null == operand2.value()) {
				if (operand1.valueType() instanceof ReferenceType) return new ConstOperand(true);				
            } else if (null == operand1.value() && null == operand2.value()) {
                return new ConstOperand(false);
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand compute8(Operand operand1, Operand operand2) throws Exception { // &
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 & value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 & value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 & value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 & value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 & value2);
                    }
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand compute7(Operand operand1, Operand operand2) throws Exception { // ^
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 ^ value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 ^ value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 ^ value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 ^ value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 ^ value2);
                    }
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand compute6(Operand operand1, Operand operand2) throws Exception { // |
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isByte()) {
                    byte value1 = operand1.byteValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 | value2);
                    }
                } else if (operand1.isChar()) {
                    char value1 = operand1.charValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 | value2);
                    }
                } else if (operand1.isShort()) {
                    short value1 = operand1.shortValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 | value2);
                    }
                } else if (operand1.isInteger()) {
                    int value1 = operand1.intValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 | value2);
                    }
                } else if (operand1.isLong()) {
                    long value1 = operand1.longValue();
                    if (operand2.isByte()) {
                        byte value2 = operand2.byteValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isChar()) {
                        char value2 = operand2.charValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isShort()) {
                        short value2 = operand2.shortValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isInteger()) {
                        int value2 = operand2.intValue();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.isLong()) {
                        long value2 = operand2.longValue();
                        return new ConstOperand(value1 | value2);
                    }
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand compute5(Operand operand1, Operand operand2) throws Exception { // &&
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isBoolean() && operand2.isBoolean()) {
					boolean value1 = operand1.boolValue();
					boolean value2 = operand2.boolValue();
                    return new ConstOperand(value1 && value2);
                }
            }
        }
        throw new Exception("invalid &&");
    }

    public static Operand compute4(Operand operand1, Operand operand2) throws Exception { // ||
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.isBoolean() && operand2.isBoolean()) {
					boolean value1 = operand1.boolValue();
					boolean value2 = operand2.boolValue();
                    return new ConstOperand(value1 || value2);
                }
            }
        }
        throw new Exception("invalid ||");
    }

    public static void compute31(Operand operand) throws Exception { // ?
        if (null != operand) {
            if (null != operand.value()) {
                if (operand.isBoolean()) {
                    return;
                }
            }
        }
        throw new Exception("invalid ?");
    }

    public static Operand compute32(Operand operand1, Operand operand2, Operand operand3) throws Exception { // :
        if (null != operand1 && null != operand2 && null != operand3) {
            if (null != operand1.value() &&
				null != operand2.value() &&
				null != operand3.value()) {
                if (operand1.isBoolean()) {
                    return (operand1.boolValue() ? operand2 : operand3);
                }
            }
        }
        throw new Exception("invalie :");
    }

    public static Operand compute21(Operand operand1, Operand operand2) throws Exception { // =
        if (null != operand1 && null != operand2) {
            if (!(operand1 instanceof ConstOperand)) {
                operand1.value(operand2.value());
                if (operand1 instanceof AutoOperand) {
                    operand1.type(operand2.type());
                }
                return operand1;
            }
        }
        throw new Exception("invalid =");
    }

    public static Operand compute22(Operand operand1, Operand operand2) throws Exception { // +=
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (!(operand1 instanceof ConstOperand)) {
                    operand1.value(computeC1(operand1, operand2).value());
                    return operand1;
                }
            }
        }
        throw new Exception("invalid +=");
    }

    public static Operand compute23(Operand operand1, Operand operand2) throws Exception { // -=
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (!(operand1 instanceof ConstOperand)) {
                    operand1.value(computeC2(operand1, operand2).value());
                    return operand1;
                }
            }
        }
        throw new Exception("invalid +=");
    }

    public static Operand compute24(Operand operand1, Operand operand2) throws Exception { // *=
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (!(operand1 instanceof ConstOperand)) {
                    operand1.value(computeD1(operand1, operand2).value());
                    return operand1;
                }
            }
        }
        throw new Exception("invalid *=");
    }

    public static Operand compute25(Operand operand1, Operand operand2) throws Exception { // /=
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (!(operand1 instanceof ConstOperand)) {
                    operand1.value(computeD2(operand1, operand2).value());
                    return operand1;
                }
            }
        }
        throw new Exception("invalid /=");
    }

    public static Operand compute26(Operand operand1, Operand operand2) throws Exception { // %=
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (!(operand1 instanceof ConstOperand)) {
                    operand1.value(computeD3(operand1, operand2).value());
                    return operand1;
                }
            }
        }
        throw new Exception("invalid %=");
    }

    public static Operand compute27(Operand operand1, Operand operand2) throws Exception { // &=
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (!(operand1 instanceof ConstOperand)) {
                    operand1.value(compute8(operand1, operand2).value());
                    return operand1;
                }
            }
        }
        throw new Exception("invalid &=");
    }

    public static Operand compute28(Operand operand1, Operand operand2) throws Exception { // ^=
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (!(operand1 instanceof ConstOperand)) {
                    operand1.value(compute7(operand1, operand2).value());
                    return operand1;
                }
            }
        }
        throw new Exception("invalid ^=");
    }

    public static Operand compute29(Operand operand1, Operand operand2) throws Exception { // |=
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (!(operand1 instanceof ConstOperand)) {
                    operand1.value(compute6(operand1, operand2).value());
                    return operand1;
                }
            }
        }
        throw new Exception("invalid ~=");
    }

    public static Operand compute2A(Operand operand1, Operand operand2) throws Exception { // <<=
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (!(operand1 instanceof ConstOperand)) {
                    operand1.value(computeB1(operand1, operand2).value());
                    return operand1;
                }
            }
        }
        throw new Exception("invalid <<=");
    }

    public static Operand compute2B(Operand operand1, Operand operand2) throws Exception { // >>=
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (!(operand1 instanceof ConstOperand)) {
                    operand1.value(computeB2(operand1, operand2).value());
                    return operand1;
                }
            }
        }
        throw new Exception("invalid >>=");
    }

    public static Operand compute2C(Operand operand1, Operand operand2) throws Exception { // >>>=
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (!(operand1 instanceof ConstOperand)) {
                    operand1.value(computeB3(operand1, operand2).value());
                    return operand1;
                }
            }
        }
        throw new Exception("invalid >>>=");
    }

	private static String clazz(String clazz) {
		String path = ImportManager.instance().find(clazz);
		return (null != path ? path + "." + clazz : clazz);
	}
}
