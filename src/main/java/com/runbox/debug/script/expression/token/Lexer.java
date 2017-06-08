package com.runbox.debug.script.expression.token;

import com.sun.jdi.Value;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.script.expression.token.operand.AutoOperand;
import com.runbox.debug.script.expression.token.operand.ConstOperand;
import com.runbox.debug.script.expression.token.operand.RoutineOperand;
import com.runbox.debug.script.expression.token.Token;
import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operator.Operator;

public class Lexer extends com.runbox.script.statement.Lexer {    

    public Lexer(String express) throws Exception {
        this.express = express;
        front = new Operator("#");
    }

    private String express = null;
    private Token front = null;    
    private Token peek = null;

    @Override
    public Token token() throws Exception {
        if (null != express) {
            if (null == peek) {
                skip();
                Token token = (operator(lookup()) ? operator() : operand());				
                front = correct(token);				
                return front;
            } else {
                Token token = peek; peek = null;				
				return token;
            }
        }
        throw new Exception("invalid token");
    }    

    @Override
    public Token peek(boolean flag) throws Exception {
        if (null == peek && flag) {
            peek = token();
        }
        return peek;
    }

    @Override
    public void clean() throws Exception {

    }

    private Token correct(Token token) throws Exception {
        if (token instanceof Operator) {
            Operator operator = (Operator)token;
            if (operator.name().equals("+") || operator.name().equals("-")) {
                if (front instanceof Operator) {
                    if (!front.name().equals("++") && !front.name().equals("--")) {
                        if (operator.name().equals("+")) {
                            operator.name("0+");
                        } else {
                            operator.name("0-");
                        }
                        operator.level(Operator.LEVEL_E);
                    }
                }
            } else if (operator.name().equals("++") || operator.name().equals("--")) {
                if (front instanceof Operator) {
                    if (operator.name().equals("++")) {
                        operator.name("+++");
                    } else {
                        operator.name("---");
                    }
                }
            }
        }
        return token;
    }    
   	
    private boolean operator(char letter) {
        if ('(' == letter) {
            return true;
        } else if (')' == letter) {
            return true;
        } else if ('[' == letter) {
            return true;
        } else if (']' == letter) {
            return true;
        } else if ('.' == letter) {
            if ((front instanceof Operand) ||
				(front instanceof Operator &&
				 (front.name().equals(")") ||
				  front.name().equals("]")))) {
                return true;
            }
        } else if ('!' == letter) {
            return true;
        } else if ('+' == letter) {
            return true;
        } else if ('-' == letter) {
            return true;
        } else if ('~' == letter) {
            return true;
        } else if ('*' == letter) {
            return true;
        } else if ('/' == letter) {
            return true;
        } else if ('%' == letter) {
            return true;
        } else if ('<' == letter) {
            return true;
        } else if ('>' == letter) {
            return true;
        } else if ('=' == letter) {
            return true;
        } else if ('&' == letter) {
            return true;
        } else if ('|' == letter) {
            return true;
        } else if ('^' == letter) {
            return true;
        } else if ('?' == letter) {
            return true;
        } else if (':' == letter) {
            return true;
        } else if (',' == letter) {
			return true;
		} else if ('#' == letter) {
            return true;
        }
        return false;
    }

    private Token operator() throws Exception {
        StringBuffer buffer = new StringBuffer();
        if ('(' == lookup()) {
			if (front instanceof RoutineOperand) {
				buffer.append("->"); next();
			} else {
				buffer.append(next());
			}
        } else if (')' == lookup()) {
            buffer.append(next());
        } else if ('[' == lookup()) {
            buffer.append(next());
        } else if (']' == lookup()) {
            buffer.append(next());
        } else if ('.' == lookup()) {
            buffer.append(next());
        } else if ('!' == lookup()) {
            buffer.append(next());
            if ('=' == lookup()) {
                buffer.append(next());
            }
        } else if ('+' == lookup()) {
            buffer.append(next());
            if ('+' == lookup()) {
                buffer.append(next());
            } else if ('=' == lookup()) {
                buffer.append(next());
            }
        } else if ('-' == lookup()) {
            buffer.append(next());
            if ('-' == lookup()) {
                buffer.append(next());
            } else if ('=' == lookup()) {
                buffer.append(next());
            }
        } else if ('~' == lookup()) {
            buffer.append(next());
        } else if ('*' == lookup()) {
            buffer.append(next());
            if ('=' == lookup()) {
                buffer.append(next());
            }
        } else if ('/' == lookup()) {
            buffer.append(next());
            if ('=' == lookup()) {
                buffer.append(next());
            }
        } else if ('%' == lookup()) {
            buffer.append(next());
            if ('=' == lookup()) {
                buffer.append(next());
            }
        } else if ('<' == lookup()) {
            buffer.append(next());
            if ('<' == lookup()) {
                buffer.append(next());
                if ('=' == lookup()) {
                    buffer.append(next());
                }
            } else if ('=' == lookup()) {
                buffer.append(next());
            } else if ('-' == lookup()) { // never get it
                buffer.append(next());
            }
        } else if ('>' == lookup()) {
            buffer.append(next());
            if ('>' == lookup()) {
                buffer.append(next());
                if ('>' == lookup()) {
                    buffer.append(next());
                    if ('=' == lookup()) {
                        buffer.append(next());
                    }
                } else if ('=' == lookup()) {
                    buffer.append(next());
                }
            } else if ('=' == lookup()) {
                buffer.append(next());
            }
        } else if ('=' == lookup()) {
            buffer.append(next());
            if ('=' == lookup()) {
                buffer.append(next());
            }
        } else if ('&' == lookup()) {
            buffer.append(next());
            if ('&' == lookup()) {
                buffer.append(next());
            } else if ('=' == lookup()) {
                buffer.append(next());
            }
        } else if ('|' == lookup()) {
            buffer.append(next());
            if ('|' == lookup()) {
                buffer.append(next());
            } else if ('=' == lookup()) {
                buffer.append(next());
            }
        } else if ('^' == lookup()) {
            buffer.append(next());
            if ('=' == lookup()) {
                buffer.append(next());
            }
        } else if ('?' == lookup()) {
            buffer.append(next());
        } else if (':' == lookup()) {
            buffer.append(next());
        } else if (',' == lookup()) {
			buffer.append(next());
		} else if ('#' == lookup()) {
            buffer.append(next());
        } else {
            return null;
        }
        return new Operator(buffer.toString());
    }

    private boolean variant(char letter) {
        if (alphabet(letter) || '_' == letter || '@' == letter || '$' == letter) {
            return true;
        }
        return false;
    }    

    private Token operand() throws Exception {
        if (letter(lookup())) {
            return letter();
        } else if (string(lookup())) {
            return string();
        } else if (number(lookup())) {
            return number();
        } else if (variant(lookup())) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(next());
            while (true) {
                if (alphabet(lookup()) || number(lookup(), 10) || '_' == lookup() || '$' == lookup()) {
                    buffer.append(next());
                } else {
					String string = buffer.toString();
                    if (Token.isNull(string)) {
                        return new ConstOperand((Value)null);
                    } else if (Token.isBoolean(string)) {
                        return new ConstOperand(Boolean.valueOf(string));
                    } else if (Token.isThis(string)) {
						return new ConstOperand(string, ContextManager.instance().frame().thisObject());
					} else if (Token.isInstanceof(string)) {
                        return new Operator(string);
                    } else if (Token.routine(string) && '(' == lookup()) {
                        return new RoutineOperand(string);
                    } else {
                        return new Operand(string);
                    }
                }
            }
        }
        throw new Exception("invalid name operand");
    }                

    private boolean letter(char letter) {
        if ('\'' == letter) {
            return true;
        }
        return false;
    }
    
    private Operand letter() throws Exception {
        StringBuffer buffer = new StringBuffer();
        if ('\'' == lookup()) {
            next();                            
            if (escape(lookup())) {
                buffer.append(escape());                
            } else {
                buffer.append(next());                
            }
            if ('\'' == lookup()) {
                next(); return new ConstOperand(buffer.charAt(0));
            }
        }
        throw new Exception("invalid letter operand");
    }

    private boolean string(char letter) {
        if ('\"' == letter) {
            return true;
        }
        return false;
    }

    private Operand string() throws Exception {
        StringBuffer buffer = new StringBuffer();
        if ('\"' == lookup()) {
            next();
            while (true) {
                if ('\"' == lookup()) {
                    next(); return new ConstOperand(buffer.toString());
                } else if ('#' == lookup()) {
                    throw new Exception("invalid string operand");
                } else if (escape(lookup())) {
                    buffer.append(escape());
                } else {
                    buffer.append(next());
                }
            }
        }
        throw new Exception("invalid string");
    }

    private boolean escape(char letter) {
        if ('\\' == letter) {
            return true;
        }
        return false;
    }

    private char escape() throws Exception {
        next();
        switch (lookup()) {
        case 'b':
            next(); return '\b';
        case 'f':
            next(); return '\f';
        case 'n':
            next(); return '\n';
        case 'r':
            next(); return '\r';
        case 't':
            next(); return '\t';         
        case '\\':
            next(); return '\\';
        case '\'':
            next(); return '\'';
        case '\"':
            next(); return '\"';
        case '0':
            next(); return '\0';
        default:
            throw new Exception("invalue escape char");
        }
    }

     private boolean number(char letter) {
        if ('0' <= letter && '9' >= letter) {
            return true;
        } else if ('.' == letter) {
            return true;
        }
        return false;
    }

    private Operand number() throws Exception {
        StringBuffer buffer = new StringBuffer();
        if ('0' == lookup()) {
            buffer.append(next());
            if ('x' == lookup() || 'X' == lookup()) {
                buffer.append(next()); return radix16(buffer);
            } else if ('0' <= lookup() && '7' >= lookup()) {
                buffer.append(next()); return radix8(buffer);
            } else if ('b' == lookup() || 'B' == lookup()) {
                buffer.append(next()); return radix2(buffer);
            } else if ('.' == lookup()) {
                buffer.append(next()); return point(buffer);
            } 
        } else if ('.' == lookup()) {
            buffer.append(next()); return point(buffer);
        } 
		while (true) {
			if ('.' == lookup() || 'e' == lookup() || 'E' == lookup()) {
				buffer.append(next()); return point(buffer);
			}
			if (number(lookup(), 10)) {
				buffer.append(next()); 
			} else if ('l' == lookup() || 'L' == lookup()) {
				next(); return new ConstOperand(Long.valueOf(buffer.toString()));
			} else {
				return new ConstOperand(Integer.valueOf(buffer.toString()));
			}
		}                           
    }           

    private Operand radix16(StringBuffer buffer) throws Exception {
        while (true) {
            if (number(lookup(), 16)) {
                buffer.append(next());
            } else if ('l' == lookup() || 'L' == lookup()) {
                buffer = new StringBuffer(buffer.substring(2, buffer.length()));
                next(); return new ConstOperand(Long.valueOf(buffer.toString(), 16));
            } else {
                buffer = new StringBuffer(buffer.substring(2, buffer.length()));
                return new ConstOperand(Integer.valueOf(buffer.toString(), 16));
            }
        }        
    }

    private Operand radix8(StringBuffer buffer) throws Exception {
        while (true) {
            if (number(lookup(), 8)) {
                buffer.append(next());
            } else if ('l' == lookup() || 'L' == lookup()) {
                buffer = new StringBuffer(buffer.substring(1, buffer.length()));
                next(); return new ConstOperand(Long.valueOf(buffer.toString(), 8));
            } else {
                buffer = new StringBuffer(buffer.substring(1, buffer.length()));
                return new ConstOperand(Integer.valueOf(buffer.toString(), 8));
            }
        }        
    }

    private Operand radix2(StringBuffer buffer) throws Exception {
        while (true) {
            if (number(lookup(), 2)) {
                buffer.append(next());
            } else if ('l' == lookup() || 'L' == lookup()) {
                buffer = new StringBuffer(buffer.substring(2, buffer.length()));
                next(); return new ConstOperand(Long.valueOf(buffer.toString(), 2));
            } else {
                buffer = new StringBuffer(buffer.substring(2, buffer.length()));
                return new ConstOperand(Integer.valueOf(buffer.toString(), 2));
            }
        }
    }

    private Operand point(StringBuffer buffer) throws Exception {
        while (true) {
            if (number(lookup(), 10)) {
                buffer.append(next());
            } else if (('e' == lookup() || 'E' == lookup()) && (-1 == buffer.indexOf("e") || -1 == buffer.indexOf("E"))) {
                buffer.append(next());
            } else if ('f' == lookup() || 'F' == lookup()) {
                next(); return new ConstOperand(Float.valueOf(buffer.toString()));
            } else if ('d' == lookup() || 'D' == lookup()) {
                next(); return new ConstOperand(Double.valueOf(buffer.toString()));
            } else {
                // next(); 
				return new ConstOperand(Float.valueOf(buffer.toString()));
            }
        }
    }

	@Override
	protected void preprocess() {
		
	}
	
    private int index = 0;

    @Override
    protected char lookup() {
        if (index < express.length()) {
            return express.charAt(index);
        }
        return '#';
    }

    @Override
    protected char next() {
        if (index < express.length()) {
            return express.charAt(index++);
        }
        return '#';
    }	
	
    @Override
    protected void skip() {
        while ((index < express.length())) {
            if (!space(express.charAt(index))) {
                return;
            }
            index++;
        }
    }    

    private boolean point(String string) {
        String[] parts = string.split("\\.");
        if (2 == parts.length) {
            return true;
        }
        return false;
    }
}
