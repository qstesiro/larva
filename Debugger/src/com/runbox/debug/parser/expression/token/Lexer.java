package com.runbox.debug.parser.expression.token;

import com.runbox.debug.parser.expression.token.operand.AutoOperand;
import com.runbox.debug.parser.expression.token.operand.ConstOperand;
import com.runbox.debug.parser.expression.token.operand.Operand;
import com.runbox.debug.parser.expression.token.operator.Operator;
import com.sun.jdi.Value;

/**
 * Created by qstesiro
 */
public class Lexer implements com.runbox.debug.parser.Lexer {

    private Token front = null;
    private String express = null;

    public Lexer(String express) throws Exception {
        this.express = express;
        front = new Operator("#");
    }

    public Token token() throws Exception {
        if (null != express) {
            if (null == peek) {
            skip();
            Token token = (operator(lookup()) ? operator() : operand());
            front = correct(token);
            return front;
            } else {
                Token token = peek;
                peek = null;
                return token;
            }
        }
        throw new Exception("invalid token");
    }

    private Token peek = null;

    public Token peek() throws Exception {
        if (null == peek) {
            peek = token();
        }
        return peek;
    }

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
        } else if (token instanceof Operand) {
            if (Token.isInstanceof(token.name())) {
                token = new Operator(token.name());
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
            return true;
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
        } else if ('#' == letter) {
            return true;
        }
        return false;
    }

    private Operator operator() throws Exception {
        StringBuffer buffer = new StringBuffer();
        if ('(' == lookup()) {
            buffer.append(next());
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
        } else if ('#' == lookup()) {
            buffer.append(next());
        } else {
            return null;
        }
        return new Operator(buffer.toString());
    }

    protected boolean variant(char letter) {
        if ('_' == letter || alphabet(letter) || '@' == letter) {
            return true;
        }
        return false;
    }

    protected Operand operand() throws Exception {
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
                    if (Token.isNull(buffer.toString())) {
                        return new ConstOperand((Value)null);
                    } else if (Token.isBoolean(buffer.toString())) {
                        return new ConstOperand(Boolean.valueOf(buffer.toString()));
                    } else if (Token.isInstanceof(buffer.toString())) {
                        return new Operand(buffer.toString());
                    } else if (Operand.auto(buffer.toString())) {
                        return new AutoOperand(buffer.toString());
                    } else {
                        return new Operand(buffer.toString());
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
            if (alphabet(lookup())) {
                buffer.append(next());
                if ('\'' == lookup()) {
                    next();
                    return new ConstOperand(buffer.charAt(0));
                }
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
                    next();
                    return new ConstOperand(buffer.toString());
                } else if ('#' == lookup()) {
                    throw new Exception("invalid express");
                } else {
                    buffer.append(next());
                }
            }
        }
        throw new Exception("invalid string");
    }

    private boolean number(char letter) {
        if ('0' <= letter && '9' >= letter) {
            return true;
        }
        return false;
    }

    private Operand number() throws Exception {
        StringBuffer buffer = new StringBuffer();
        if ('0' == lookup()) {
            buffer.append(next());
            if ('x' == lookup() || 'X' == lookup()) {
                buffer.append(next());
                return radix16(buffer);
            } else if ('0' <= lookup() && '7' >= lookup()) {
                return radix8(buffer);
            } else if ('.' == lookup()) {
                buffer.append(next());
                return radix10(buffer);
            } else {
                return radix10(buffer);
            }
        } else {
            return radix10(buffer);
        }
    }

    private Operand radix10(StringBuffer buffer) throws Exception {
        while (true) {
            if (number(lookup(), 10) || '.' == lookup()) {
                buffer.append(next());
            } else if ('l' == lookup() || 'L' == lookup()) {
                buffer.append(next());
                return integer(buffer);
            } else if ('f' == lookup() || 'F' == lookup()) {
                buffer.append(next());
                return point(buffer);
            } else {
                if (!point(buffer.toString())) {
                    return integer(buffer);
                } else {
                    return point(buffer);
                }
            }
        }
    }

    private Operand integer(StringBuffer buffer) throws Exception {
        if ('l' == buffer.charAt(buffer.length() - 1) || 'L' == buffer.charAt(buffer.length() - 1)) {
            String number = buffer.subSequence(0, buffer.length() - 1).toString();
            return new ConstOperand(Long.valueOf(number));
        }
        return new ConstOperand(Integer.valueOf(buffer.toString()));
    }

    private Operand point(StringBuffer buffer) throws Exception {
        if ('f' == buffer.charAt(buffer.length() - 1) || 'F' == buffer.charAt(buffer.length() - 1)) {
            String number = buffer.subSequence(0, buffer.length() - 1).toString();
            return new ConstOperand(Float.valueOf(number));
        }
        return new ConstOperand(Double.valueOf(buffer.toString()));
    }

    private Operand radix8(StringBuffer buffer) throws Exception {
        while (true) {
            if (number(lookup(), 8)) {
                buffer.append(next());
            } else {
                return new ConstOperand(Integer.valueOf(buffer.substring(1).toString(), 8));
            }
        }
    }

    private Operand radix16(StringBuffer buffer) throws Exception {
        while (true) {
            if (number(lookup(), 16)) {
                buffer.append(next());
            } else {
                return new ConstOperand(Integer.valueOf(buffer.substring(2).toString(), 16));
            }
        }
    }

    private int index = 0;

    private char lookup() {
        if (index < express.length()) {
            return express.charAt(index);
        }
        return '#';
    }

    private char next() {
        if (index < express.length()) {
            return express.charAt(index++);
        }
        return '#';
    }

    private void skip() {
        while ((index < express.length())) {
            if (space()) {
                return;
            }
            index++;
        }
    }

    private boolean space() {
        if (' ' == express.charAt(index)
                || '\t' == express.charAt(index)
                || '\r' == express.charAt(index)
                || '\n' == express.charAt(index)) {
            return false;
        }
        return true;
    }

    protected boolean alphabet(char lookup) {
        if (('A' <= lookup && 'Z' >= lookup) || ('a' <= lookup && 'z' >= lookup)) {
            return true;
        }
        return false;
    }

    private boolean number(char lookup, int radix) {
        if (10 == radix) {
            if ('0' <= lookup && '9' >= lookup) {
                return true;
            }
        } else if (8 == radix) {
            if ('0' <= lookup && '7' >= lookup) {
                return true;
            }
        } else if (16 == radix) {
            if (('0' <= lookup && '9' >= lookup) || ('A' <= lookup && 'F' >= lookup) || ('a' <= lookup && 'f' >= lookup)) {
                return true;
            }
        }
        return false;
    }

    private boolean point(String string) {
        String[] parts = string.split("\\.");
        if (2 == parts.length) {
            return true;
        }
        return false;
    }
}
