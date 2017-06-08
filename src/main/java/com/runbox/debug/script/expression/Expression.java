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

import com.runbox.debug.script.expression.token.Token;
import com.runbox.debug.script.expression.token.Lexer;
import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operator.Operator;

public class Expression implements com.runbox.script.expression.Expression {    

    public Expression(String expression) throws Exception {
        if (null != expression) {
            lexer = new Lexer(expression);
            executer = new Executer(operators, operands);
            operators.push(new Operator("#"));
        } else {
            throw new Exception("invalid express");
        }
    }

	private Stack<Operator> operators = new Stack<Operator>();

	public Stack<Operator> operators() {
		return operators;
	}
	
    private Values<Operand> operands = new Values<Operand>();

	public Values<Operand> operands() {
		return operands;
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
		public boolean isByte(int index) throws Exception {
			if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
                    return operand.isByte();
                }
            }
            return false;
		}

		@Override
		public boolean isChar(int index) throws Exception {
			if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
                    return operand.isChar();
                }
            }
            return false;
		}

		@Override
        public boolean isShort(int index) throws Exception {
			if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
                    return operand.isShort();
                }
            }
            return false;
		}

		@Override
        public boolean isInteger(int index) throws Exception {
			if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
                    return operand.isInteger();
                }
            }
            return false;
		}

		@Override
        public boolean isLong(int index) throws Exception {
			if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
					return operand.isLong();
                }
            }
            return false;
		}

		@Override
        public boolean isFloat(int index) throws Exception {
			if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
                    return operand.isFloat();
                }
            }
            return false;
		}

		@Override
        public boolean isDouble(int index) throws Exception {
			if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
                    return operand.isDouble();
                }
            }
            return false;
		}

		@Override
        public boolean isBoolean(int index) throws Exception {
			if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
                    return operand.isBoolean();
                }
            }
            return false;
		}

		@Override
        public boolean isString(int index) throws Exception {
			if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
                    return operand.isString();
                }
            }
            return false;
		}
				
        @Override
        public byte getByte(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
					return operand.byteValue();
				}
            }
            throw new Exception("invalid operand");
        }

		@Override
		public char getChar(int index) throws Exception {
			if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
					return operand.charValue();
				}
            }
            throw new Exception("invalid operand");
		}
		
        @Override
        public short getShort(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
					return operand.shortValue();
				}
            }
            throw new Exception("invalid operand");
        }

        @Override
        public int getInteger(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
					return operand.intValue();
				}
            }
            throw new Exception("invalid operand");
        }

        @Override
        public long getLong(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
					return operand.longValue();
				}
            }
            throw new Exception("invalid operand");
        }

        @Override
        public float getFloat(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
					return operand.floatValue();
				}
            }
            throw new Exception("invalid operand");
        }

        @Override
        public double getDouble(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
					return operand.doubleValue();
				}
            }
            throw new Exception("invalid operand");
        }

        @Override
        public boolean getBoolean(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
					return operand.boolValue();
				}
            }
            throw new Exception("invalid operand");
        }

        @Override
        public String getString(int index) throws Exception {
            if (operands.size() > index) {
                Operand operand = operands.get(index);
                if (null != operand) {
					return operand.strValue();
				}
            }
            throw new Exception("invalid operand");
        }
    } 
}
