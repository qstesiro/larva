package com.runbox.debug.script.expression;

import java.util.Vector;
import java.util.Stack;

import com.sun.jdi.ByteValue;
import com.sun.jdi.ShortValue;
import com.sun.jdi.IntegerValue;
import com.sun.jdi.LongValue;
import com.sun.jdi.FloatValue;
import com.sun.jdi.DoubleValue;
import com.sun.jdi.BooleanValue;
import com.sun.jdi.StringReference;

import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operator.Operator;
import com.runbox.debug.script.expression.token.Token;
import com.runbox.debug.script.expression.token.Lexer;

public class Expression implements com.runbox.script.expression.Expression {

    private Stack<Operator> operators = new Stack<Operator>();
    private Values<Operand> operands = new Values<Operand>();

    public Expression(String expression) throws Exception {
        if (null != expression) {
            lexer = new Lexer(expression);
            executer = new Executer(operators, operands);
            operators.push(new Operator("#"));
        } else {
            throw new Exception("invalid express");
        }
    }

    private Lexer lexer = null;

    public void lexer(Lexer lexer) {
        this.lexer = lexer;
    }

    private Executer executer = null;

    public void executer(Executer executer) {
        this.executer = executer;
    }

    @Override
    public Values<Operand> execute() throws Exception {
        if (null != lexer) {
            while (true) {
                Token token = lexer.token();
                if (token instanceof Operator) {
                    executer.execute(operators.peek(), (Operator)token);
                    if (operators.empty()) {
                        return operands;
                    }
                    push(token);
                } else {					
                    operands.push((Operand)token);
                }                
            }
        }
        throw new Exception("invalid exception");
    }	       

	private void push(Token token) {
		if (!token.name().equals(")") &&
			!token.name().equals("]") &&
			!token.name().equals(",") &&
			!token.name().equals("#")) {
			operators.push((Operator)token);
		}
	}

    public class Values<T extends Token> extends com.runbox.script.expression.Expression.Values<T> {

        @Override
        public byte getByte(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand.value()) {
                    if (operand.value() instanceof ByteValue) {
                        return ((ByteValue)operand.value()).value();
                    }
                }
            }
            throw new Exception("invalid operand");
        }

        @Override
        public short getShort(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand.value()) {
                    if (operand.value() instanceof ByteValue) {
                        return (short)((ByteValue)operand.value()).value();
                    } else if (operand.value() instanceof ShortValue) {
                        return ((ShortValue)operand.value()).value();
                    }
                }
            }
            throw new Exception("invalid operand");
        }

        @Override
        public int getInteger(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand.value()) {
                    if (operand.value() instanceof ByteValue) {
                        return (int)((ByteValue)operand.value()).value();
                    } else if (operand.value() instanceof ShortValue) {
                        return (int)((ShortValue)operand.value()).value();
                    } else if (operand.value() instanceof IntegerValue) {
                        return ((IntegerValue)operand.value()).value();
                    }
                }
            }
            throw new Exception("invalid operand");
        }

        @Override
        public long getLong(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand.value()) {
                    if (operand.value() instanceof ByteValue) {
                        return (long)((ByteValue)operand.value()).value();
                    } else if (operand.value() instanceof ShortValue) {
                        return (long)((ShortValue)operand.value()).value();
                    } else if (operand.value() instanceof IntegerValue) {
                        return (long)((IntegerValue)operand.value()).value();
                    } else if (operand.value() instanceof LongValue) {
                        return ((LongValue)operand.value()).value();
                    }
                }
            }
            throw new Exception("invalid operand");
        }

        @Override
        public float getFloat(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand.value()) {
                    if (operand.value() instanceof FloatValue) {
                        return ((FloatValue)operand.value()).value();
                    }
                }
            }
            throw new Exception("invalid operand");
        }

        @Override
        public double getDouble(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand.value()) {
                    if (operand.value() instanceof FloatValue) {
                        return (double)((FloatValue)operand.value()).value();
                    } else if (operand.value() instanceof DoubleValue) {
                        return ((DoubleValue)operand.value()).value();
                    }
                }
            }
            throw new Exception("invalid operand");
        }

        @Override
        public boolean getBoolean(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand.value()) {
                    if (operand.value() instanceof BooleanValue) {                    
                        return ((BooleanValue)operand.value()).value();
                    }
                }
            }
            throw new Exception("invalid operand");
        }

        @Override
        public String getString(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand.value()) {
                    if (operand.value() instanceof StringReference) {
                        return ((StringReference)operand.value()).value();
                    }
                }
            }
            throw new Exception("invalid operand");
        }
    } 
}
