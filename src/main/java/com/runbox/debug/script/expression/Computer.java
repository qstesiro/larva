package com.runbox.debug.script.expression;

import java.util.List;

import com.sun.jdi.*;

import com.runbox.debug.script.expression.token.Token;
import com.runbox.debug.script.expression.token.operand.*;

public class Computer {

    public static Operand computeI2(Operand operand1, Operand operand2) throws Exception { // [
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.value().type() instanceof ArrayType) {
                    if (operand2.value() instanceof IntegerValue) {
                        int index = ((IntegerValue)operand2.value()).value();
                        return new ArrayOperand((ArrayReference)operand1.value(), index);
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
                if ((operand1.value() instanceof ObjectReference) && !(operand2 instanceof ConstOperand)) {
                    return new FieldOperand((ObjectReference)operand1.value(), operand2.name());
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
                    if (operand2.value() instanceof ByteValue) {
                        return operand2;
                    } else if (operand2.value() instanceof CharValue) {
                        return new ConstOperand((byte)((CharValue)operand2.value()).value());
                    } else if (operand2.value() instanceof ShortValue) {
                        return new ConstOperand((byte)((ShortValue)operand2.value()).value());
                    } else if (operand2.value() instanceof IntegerValue) {
                        return new ConstOperand((byte)((IntegerValue)operand2.value()).value());
                    } else if (operand2.value() instanceof LongValue) {
                        return new ConstOperand((byte)((LongValue)operand2.value()).value());
                    } else if (operand2.value() instanceof FloatValue) {
                        return new ConstOperand((byte)((FloatValue)operand2.value()).value());
                    } else if (operand2.value() instanceof DoubleValue) {
                        return new ConstOperand((byte)((DoubleValue)operand2.value()).value());
                    }
                } else if (operand1.name().equals("char")) {
                    if (operand2.value() instanceof ByteValue) {
                        return new ConstOperand((char)((ByteValue)operand2).value());
                    } else if (operand2.value() instanceof CharValue) {
                        return operand2;
                    } else if (operand2.value() instanceof ShortValue) {
                        return new ConstOperand((char)((ShortValue)operand2.value()).value());
                    } else if (operand2.value() instanceof IntegerValue) {
                        return new ConstOperand((char)((IntegerValue)operand2.value()).value());
                    } else if (operand2.value() instanceof LongValue) {
                        return new ConstOperand((char)((LongValue)operand2.value()).value());
                    } else if (operand2.value() instanceof FloatValue) {
                        return new ConstOperand((char)((FloatValue)operand2.value()).value());
                    } else if (operand2.value() instanceof DoubleValue) {
                        return new ConstOperand((char)((DoubleValue)operand2.value()).value());
                    }
                } else if (operand1.name().equals("short")) {
                    if (operand2.value() instanceof ByteValue) {
                        return new ConstOperand((short)((ByteValue)operand2).value());
                    } else if (operand2.value() instanceof CharValue) {
                        return new ConstOperand((short)((CharValue)operand2.value()).value());
                    } else if (operand2.value() instanceof ShortValue) {
                        return operand2;
                    } else if (operand2.value() instanceof IntegerValue) {
                        return new ConstOperand((short)((IntegerValue)operand2.value()).value());
                    } else if (operand2.value() instanceof LongValue) {
                        return new ConstOperand((short)((LongValue)operand2.value()).value());
                    } else if (operand2.value() instanceof FloatValue) {
                        return new ConstOperand((short)((FloatValue)operand2.value()).value());
                    } else if (operand2.value() instanceof DoubleValue) {
                        return new ConstOperand((short)((DoubleValue)operand2.value()).value());
                    }
                } else if (operand1.name().equals("int")) {
                    if (operand2.value() instanceof ByteValue) {
                        return new ConstOperand((int)((ByteValue)operand2).value());
                    } else if (operand2.value() instanceof CharValue) {
                        return new ConstOperand((int)((CharValue)operand2.value()).value());
                    } else if (operand2.value() instanceof ShortValue) {
                        return new ConstOperand((int)((ShortValue)operand2.value()).value());
                    } else if (operand2.value() instanceof IntegerValue) {
                        return operand2;
                    } else if (operand2.value() instanceof LongValue) {
                        return new ConstOperand((int)((LongValue)operand2.value()).value());
                    } else if (operand2.value() instanceof FloatValue) {
                        return new ConstOperand((int)((FloatValue)operand2.value()).value());
                    } else if (operand2.value() instanceof DoubleValue) {
                        return new ConstOperand((int)((DoubleValue)operand2.value()).value());
                    }
                } else if (operand1.name().equals("long")) {
                    if (operand2.value() instanceof ByteValue) {
                        return new ConstOperand((long)((ByteValue)operand2).value());
                    } else if (operand2.value() instanceof CharValue) {
                        return new ConstOperand((long)((CharValue)operand2.value()).value());
                    } else if (operand2.value() instanceof ShortValue) {
                        return new ConstOperand((long)((ShortValue)operand2.value()).value());
                    } else if (operand2.value() instanceof IntegerValue) {
                        return new ConstOperand((long)((IntegerValue)operand2.value()).value());
                    } else if (operand2.value() instanceof LongValue) {
                        return operand2;
                    } else if (operand2.value() instanceof FloatValue) {
                        return new ConstOperand((long)((FloatValue)operand2.value()).value());
                    } else if (operand2.value() instanceof DoubleValue) {
                        return new ConstOperand((long)((DoubleValue)operand2.value()).value());
                    }
                } else if (operand1.name().equals("float")) {
                    if (operand2.value() instanceof ByteValue) {
                        return new ConstOperand((float)((ByteValue)operand2).value());
                    } else if (operand2.value() instanceof CharValue) {
                        return new ConstOperand((float)((CharValue)operand2.value()).value());
                    } else if (operand2.value() instanceof ShortValue) {
                        return new ConstOperand((float)((ShortValue)operand2.value()).value());
                    } else if (operand2.value() instanceof IntegerValue) {
                        return new ConstOperand((float)((IntegerValue)operand2.value()).value());
                    } else if (operand2.value() instanceof LongValue) {
                        return new ConstOperand((float)((LongValue)operand2.value()).value());
                    } else if (operand2.value() instanceof FloatValue) {
                        return operand2;
                    } else if (operand2.value() instanceof DoubleValue) {
                        return new ConstOperand((float)((DoubleValue)operand2.value()).value());
                    }
                } else if (operand1.name().equals("double")) {
                    if (operand2.value() instanceof ByteValue) {
                        return new ConstOperand((double)((ByteValue)operand2).value());
                    } else if (operand2.value() instanceof CharValue) {
                        return new ConstOperand((double)((CharValue)operand2.value()).value());
                    } else if (operand2.value() instanceof ShortValue) {
                        return new ConstOperand((double)((ShortValue)operand2.value()).value());
                    } else if (operand2.value() instanceof IntegerValue) {
                        return new ConstOperand((double)((IntegerValue)operand2.value()).value());
                    } else if (operand2.value() instanceof LongValue) {
                        return new ConstOperand((double)((LongValue)operand2.value()).value());
                    } else if (operand2.value() instanceof FloatValue) {
                        return new ConstOperand((double)((FloatValue)operand2.value()).value());
                    } else if (operand2.value() instanceof DoubleValue) {
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
                if (operand.value() instanceof BooleanValue) {
                    return new ConstOperand(!((BooleanValue)operand.value()).value());
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeE2(Operand operand) throws Exception { // ~
        if (null != operand) {
            if (null != operand.value()) {
                if (operand.value() instanceof ByteValue) {
                    return new ConstOperand(~((ByteValue)operand.value()).value());
                } else if (operand.value() instanceof CharValue) {
                    return new ConstOperand(~((CharValue)operand.value()).value());
                } else if (operand.value() instanceof ShortValue) {
                    return new ConstOperand(~((ShortValue)operand.value()).value());
                } else if (operand.value() instanceof IntegerValue) {
                    return new ConstOperand(~((IntegerValue)operand.value()).value());
                } else if (operand.value() instanceof LongValue) {
                    return new ConstOperand(~((LongValue)operand.value()).value());
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeE3(Operand operand) throws Exception { // +++
        if (null != operand) {
            if (null != operand.value()) {
                if (operand.value() instanceof ByteValue) {
                    byte value = ((ByteValue)operand.value()).value();
                    operand.value(++value);
                    return new ConstOperand(value);
                } else if (operand.value() instanceof CharValue) {
                    char value = ((CharValue)operand.value()).value();
                    operand.value(++value);
                    return new ConstOperand(value);
                } else if (operand.value() instanceof ShortValue) {
                    short value = ((ShortValue)operand.value()).value();
                    operand.value(++value);
                    return new ConstOperand(value);
                } else if (operand.value() instanceof IntegerValue) {
                    int value = ((IntegerValue)operand.value()).value();
                    operand.value(++value);
                    return new ConstOperand(value);
                } else if (operand.value() instanceof LongValue) {
                    long value = ((LongValue)operand.value()).value();
                    operand.value(++value);
                    return new ConstOperand(value);
                } else if (operand.value() instanceof FloatValue) {
                    float value = ((FloatValue)operand.value()).value();
                    operand.value(++value);
                    return new ConstOperand(value);
                } else if (operand.value() instanceof DoubleValue) {
                    double value = ((DoubleValue)operand.value()).value();
                    operand.value(++value);
                    return new ConstOperand(value);
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeE4(Operand operand) throws Exception { // ++
        if (null != operand) {
            if (null != operand.value()) {
                if (operand.value() instanceof ByteValue) {
                    byte value = ((ByteValue)operand.value()).value();
                    ConstOperand constant = new ConstOperand(value++);
                    operand.value(value);
                    return constant;
                } else if (operand.value() instanceof CharValue) {
                    char value = ((CharValue)operand.value()).value();
                    ConstOperand constant = new ConstOperand(value++);
                    operand.value(value);
                    return constant;
                } else if (operand.value() instanceof ShortValue) {
                    short value = ((ShortValue)operand.value()).value();
                    ConstOperand constant = new ConstOperand(value++);
                    operand.value(value);
                    return constant;
                } else if (operand.value() instanceof IntegerValue) {
                    int value = ((IntegerValue)operand.value()).value();
                    ConstOperand constant = new ConstOperand(value++);
                    operand.value(value);
                    return constant;
                } else if (operand.value() instanceof LongValue) {
                    long value = ((LongValue)operand.value()).value();
                    ConstOperand constant = new ConstOperand(value++);
                    operand.value(value);
                    return constant;
                } else if (operand.value() instanceof FloatValue) {
                    float value = ((FloatValue)operand.value()).value();
                    ConstOperand constant = new ConstOperand(value++);
                    operand.value(value);
                    return constant;
                } else if (operand.value() instanceof DoubleValue) {
                    double value = ((DoubleValue)operand.value()).value();
                    ConstOperand constant = new ConstOperand(value++);
                    operand.value(value);
                    return constant;
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeE5(Operand operand) throws Exception { // ---
        if (null != operand) {
            if (null != operand.value()) {
                if (operand.value() instanceof ByteValue) {
                    byte value = ((ByteValue)operand.value()).value();
                    operand.value(--value);
                    return new ConstOperand(value);
                } else if (operand.value() instanceof CharValue) {
                    char value = ((CharValue)operand.value()).value();
                    operand.value(--value);
                    return new ConstOperand(value);
                } else if (operand.value() instanceof ShortValue) {
                    short value = ((ShortValue)operand.value()).value();
                    operand.value(--value);
                    return new ConstOperand(value);
                } else if (operand.value() instanceof IntegerValue) {
                    int value = ((IntegerValue)operand.value()).value();
                    operand.value(--value);
                    return new ConstOperand(value);
                } else if (operand.value() instanceof LongValue) {
                    long value = ((LongValue)operand.value()).value();
                    operand.value(--value);
                    return new ConstOperand(value);
                } else if (operand.value() instanceof FloatValue) {
                    float value = ((FloatValue)operand.value()).value();
                    operand.value(--value);
                    return new ConstOperand(value);
                } else if (operand.value() instanceof DoubleValue) {
                    double value = ((DoubleValue)operand.value()).value();
                    operand.value(--value);
                    return new ConstOperand(value);
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeE6(Operand operand) throws Exception { // --
        if (null != operand) {
            if (null != operand.value()) {
                if (operand.value() instanceof ByteValue) {
                    byte value = ((ByteValue)operand.value()).value();
                    ConstOperand constant = new ConstOperand(value--);
                    operand.value(value);
                    return constant;
                } else if (operand.value() instanceof CharValue) {
                    char value = ((CharValue)operand.value()).value();
                    ConstOperand constant = new ConstOperand(value--);
                    operand.value(value);
                    return constant;
                } else if (operand.value() instanceof ShortValue) {
                    short value = ((ShortValue)operand.value()).value();
                    ConstOperand constant = new ConstOperand(value--);
                    operand.value(value);
                    return constant;
                } else if (operand.value() instanceof IntegerValue) {
                    int value = ((IntegerValue)operand.value()).value();
                    ConstOperand constant = new ConstOperand(value--);
                    operand.value(value);
                    return constant;
                } else if (operand.value() instanceof LongValue) {
                    long value = ((LongValue)operand.value()).value();
                    ConstOperand constant = new ConstOperand(value--);
                    operand.value(value);
                    return constant;
                } else if (operand.value() instanceof FloatValue) {
                    float value = ((FloatValue)operand.value()).value();
                    ConstOperand constant = new ConstOperand(value--);
                    operand.value(value);
                    return constant;
                } else if (operand.value() instanceof DoubleValue) {
                    double value = ((DoubleValue)operand.value()).value();
                    ConstOperand constant = new ConstOperand(value--);
                    operand.value(value);
                    return constant;
                }
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand computeE8(Operand operand) throws Exception { // 0-
        if (null != operand) {
            if (null != operand.value()) {
                if (operand.value() instanceof ByteValue) {
                    byte value = ((ByteValue)operand.value()).value();
                    return new ConstOperand(0 - value);
                } else if (operand.value() instanceof CharValue) {
                    char value = ((CharValue)operand.value()).value();
                    return new ConstOperand(0 - value);
                } else if (operand.value() instanceof ShortValue) {
                    short value = ((ShortValue)operand.value()).value();
                    return new ConstOperand(0 - value);
                } else if (operand.value() instanceof IntegerValue) {
                    int value = ((IntegerValue)operand.value()).value();
                    return new ConstOperand(0 - value);
                } else if (operand.value() instanceof LongValue) {
                    long value = ((LongValue)operand.value()).value();
                    return new ConstOperand(0 - value);
                } else if (operand.value() instanceof FloatValue) {
                    float value = ((FloatValue)operand.value()).value();
                    return new ConstOperand(0 - value);
                } else if (operand.value() instanceof DoubleValue) {
                    double value = ((DoubleValue)operand.value()).value();
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
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = ((FloatValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = ((DoubleValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 * value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
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
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = ((FloatValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = ((DoubleValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 / value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
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
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = ((FloatValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = ((DoubleValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 % value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
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
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = ((FloatValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = ((DoubleValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 + value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
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
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = ((FloatValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = ((DoubleValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 - value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
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
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 << value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
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
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >> value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
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
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >>> value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
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
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = ((FloatValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = ((DoubleValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 < value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
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
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = ((FloatValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = ((DoubleValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 <= value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
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
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = ((FloatValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = ((DoubleValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 > value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
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
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = ((FloatValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = ((DoubleValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 >= value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
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
                    if (operand1.value().type() instanceof ClassType) {
                        ClassType type = (ClassType)operand1.value().type();
                        if (type.name().equals(operand2.name())) {
                            return new ConstOperand(true);
                        }
                        List<InterfaceType> interfaces = type.allInterfaces();
                        for (InterfaceType entry : interfaces) {
                            if (entry.name().equals(operand2.name())) {
                                return new ConstOperand(true);
                            }
                        }
                        ClassType entry = type.superclass();
                        while (null != entry) {
                            if (entry.name().equals(operand2.name())) {
                                return new ConstOperand(true);
                            }
                            entry = entry.superclass();
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
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = ((FloatValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = ((DoubleValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 == value2);
                    }
                } else if (operand1.type() instanceof ReferenceType && operand2.type() instanceof ReferenceType) {
                    // if ((operand1.type() instanceof ClassType) && (operand2.type() instanceof ClassType)) {
                    //     ClassType type1 = (ClassType)operand1.type();
                    //     ClassType type2 = (ClassType)operand2.type();
                    //     if (type1.name().equals(type2.name())) {
                    //         return new ConstOperand(operand1.value() == operand2.value());
                    //     }
                    //     ClassType entry1 = type1.superclass();
                    //     while (null != entry1) {
                    //         ClassType entry2 = type2.superclass();
                    //         while (null != entry2) {
                    //             if (entry2.name().equals(entry1.name()) && !entry2.name().equals("java.lang.Object")) {
                    //                 return new ConstOperand(operand1.value() == operand2.value());
                    //             }
                    //             entry2 = entry2.superclass();
                    //         }
                    //         entry1 = entry1.superclass();
                    //     }
                    // } else {
                    //     ClassType type1 = (ClassType)operand1.value().type();
                    //     ClassType type2 = (ClassType)operand2.value().type();
                    //     List<InterfaceType> interfaces1 = type1.allInterfaces();
                    //     List<InterfaceType> interfaces2 = type2.allInterfaces();
                    //     for (InterfaceType entry1 : interfaces1) {
                    //         for (InterfaceType entry2 : interfaces2) {
                    //             if (entry2.name().equals(entry1.name())) {
                    //                 return new ConstOperand(operand1.value() == operand2.value());
                    //             }
                    //         }
                    //     }
                    // }
					if ((operand1.type() instanceof ClassType || operand1.type() instanceof InterfaceType) &&
						(operand2.type() instanceof ClassType || operand2.type() instanceof InterfaceType)) {
						return new ConstOperand(operand1.value() == operand2.value());
					} else if (operand1.type() instanceof ArrayType && operand2.type() instanceof ArrayType) {
						return new ConstOperand(operand1.value() == operand2.value());
					}
                }
            } else if (null == operand1.value() && null != operand2.value()) {
				if (operand2.value().type() instanceof ReferenceType) {					
					return new ConstOperand(false);
				}
            } else if (null != operand1.value() && null == operand2.value()) {
				if (operand1.value().type() instanceof ReferenceType) {
					return new ConstOperand(false);
				}
            } else if (null == operand1.value() && null == operand2.value()) {
                return new ConstOperand(true);
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand compute92(Operand operand1, Operand operand2) throws Exception { // !=
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    }
                } else if (operand1.value() instanceof FloatValue) {
                    float value1 = ((FloatValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    }
                } else if (operand1.value() instanceof DoubleValue) {
                    double value1 = ((DoubleValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof FloatValue) {
                        float value2 = ((FloatValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    } else if (operand2.value() instanceof DoubleValue) {
                        double value2 = ((DoubleValue)operand2.value()).value();
                        return new ConstOperand(value1 != value2);
                    }
                } else if (operand1.type() instanceof ReferenceType && operand2.type() instanceof ReferenceType) {
                    // if ((operand1.type() instanceof ClassType) && (operand2.type() instanceof ClassType)) {
                    //     ClassType type1 = (ClassType)operand1.type();
                    //     ClassType type2 = (ClassType)operand2.type();
                    //     if (type1.name().equals(type2.name())) {
                    //         return new ConstOperand(operand1.value() != operand2.value());
                    //     }
                    //     ClassType entry1 = type1.superclass();
                    //     while (null != entry1) {
                    //         ClassType entry2 = type2.superclass();
                    //         while (null != entry2) {
                    //             if (entry2.name().equals(entry1.name()) && !entry2.name().equals("java.lang.Object")) {
                    //                 return new ConstOperand(operand1.value() != operand2.value());
                    //             }
                    //             entry2 = entry2.superclass();
                    //         }
                    //         entry1 = entry1.superclass();
                    //     }
                    // } else {
                    //     ClassType type1 = (ClassType)operand1.value().type();
                    //     ClassType type2 = (ClassType)operand2.value().type();
                    //     List<InterfaceType> interfaces1 = type1.allInterfaces();
                    //     List<InterfaceType> interfaces2 = type2.allInterfaces();
                    //     for (InterfaceType entry1 : interfaces1) {
                    //         for (InterfaceType entry2 : interfaces2) {
                    //             if (entry2.name().equals(entry1.name())) {
                    //                 return new ConstOperand(operand1.value() != operand2.value());
                    //             }
                    //         }
                    //     }
                    // }
					if ((operand1.type() instanceof ClassType || operand1.type() instanceof InterfaceType) &&
						(operand2.type() instanceof ClassType || operand2.type() instanceof InterfaceType)) {
						return new ConstOperand(operand1.value() != operand2.value());
					} else if (operand1.type() instanceof ArrayType && operand2.type() instanceof ArrayType) {
						return new ConstOperand(operand1.value() != operand2.value());
					}
                }
            } else if (null == operand1.value() && null != operand2.value()) {
				if (operand2.value().type() instanceof ReferenceType) {
					return new ConstOperand(true);
				}
            } else if (null != operand1.value() && null == operand2.value()) {
				if (operand1.value().type() instanceof ReferenceType) {
					return new ConstOperand(true);
				}
            } else if (null == operand1.value() && null == operand2.value()) {
                return new ConstOperand(false);
            }
        }
        throw new Exception("invalid operand");
    }

    public static Operand compute8(Operand operand1, Operand operand2) throws Exception { // &
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 & value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
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
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 ^ value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
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
                if (operand1.value() instanceof ByteValue) {
                    byte value1 = ((ByteValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    }
                } else if (operand1.value() instanceof CharValue) {
                    char value1 = ((CharValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    }
                } else if (operand1.value() instanceof ShortValue) {
                    short value1 = ((ShortValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    }
                } else if (operand1.value() instanceof IntegerValue) {
                    int value1 = ((IntegerValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    }
                } else if (operand1.value() instanceof LongValue) {
                    long value1 = ((LongValue)operand1.value()).value();
                    if (operand2.value() instanceof ByteValue) {
                        byte value2 = ((ByteValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof CharValue) {
                        char value2 = ((CharValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof ShortValue) {
                        short value2 = ((ShortValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof IntegerValue) {
                        int value2 = ((IntegerValue)operand2.value()).value();
                        return new ConstOperand(value1 | value2);
                    } else if (operand2.value() instanceof LongValue) {
                        long value2 = ((LongValue)operand2.value()).value();
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
                if (operand1.value() instanceof BooleanValue && operand2.value() instanceof BooleanValue) {
                    boolean value1 = ((BooleanValue)operand1).value();
                    boolean value2 = ((BooleanValue)operand2).value();
                    return new ConstOperand(value1 && value2);
                }
            }
        }
        throw new Exception("invalid &&");
    }

    public static Operand compute4(Operand operand1, Operand operand2) throws Exception { // ||
        if (null != operand1 && null != operand2) {
            if (null != operand1.value() && null != operand2.value()) {
                if (operand1.value() instanceof BooleanValue && operand2.value() instanceof BooleanValue) {
                    boolean value1 = ((BooleanValue)operand1).value();
                    boolean value2 = ((BooleanValue)operand2).value();
                    return new ConstOperand(value1 || value2);
                }
            }
        }
        throw new Exception("invalid ||");
    }

    public static void compute31(Operand operand) throws Exception { // ?
        if (null != operand) {
            if (null != operand.value()) {
                if (operand.value() instanceof BooleanValue) {
                    return;
                }
            }
        }
        throw new Exception("invalid ?");
    }

    public static Operand compute32(Operand operand1, Operand operand2, Operand operand3) throws Exception { // :
        if (null != operand1 && null != operand2 && null != operand3) {
            if (null != operand1.value() && null != operand2.value() && null != operand3.value()) {
                if (operand1.value() instanceof BooleanValue) {
                    return (((BooleanValue)operand1).value() ? operand2 : operand3);
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
}
