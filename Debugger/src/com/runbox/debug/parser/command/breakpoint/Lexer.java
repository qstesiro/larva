package com.runbox.debug.parser.command.breakpoint;

import java.lang.*;

/**
 * Created by qstesiro
 */
public class Lexer implements com.runbox.debug.parser.Lexer {

    public Lexer(String buffer) {
        if (null != buffer) {
            this.buffer = buffer;
        }
    }

    private String buffer = null;

    public Token token() throws Exception {
        if (null != this.buffer) {
            if (null == peek) {
                skip();
                StringBuffer buffer = new StringBuffer();
                while (true) {
                    if (bound(lookup())) {
                        if (0 == buffer.length()) {
                            return new Token(buffer.append(next()).toString());
                        } else {
                            return new Token(buffer.toString());
                        }
                    } else if (number(lookup())) {
                        return number();
                    } else if (alphabet(lookup()) || '.' == lookup()) {
                        buffer.append(next());
                    } else {
                        break;
                    }
                }
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

    private boolean number(char letter) {
        if ('0' <= letter && '9' >= letter) {
            return true;
        }
        return false;
    }

    private com.runbox.debug.parser.command.breakpoint.Number number() throws Exception {
        StringBuffer buffer = new StringBuffer();
        if ('0' == lookup()) {
            buffer.append(next());
            if ('x' == lookup() || 'X' == lookup()) {
                buffer.append(next());
                return radix16(buffer);
            } else if ('0' <= lookup() && '7' >= lookup()) {
                return radix8(buffer);
            } else {
                return radix10(buffer);
            }
        } else {
            return radix10(buffer);
        }
    }

    private com.runbox.debug.parser.command.breakpoint.Number radix10(StringBuffer buffer) throws Exception {
        while (true) {
            if (number(lookup(), 10)) {
                buffer.append(next());
            } else {
                return new com.runbox.debug.parser.command.breakpoint.Number(Integer.valueOf(buffer.toString(), 10));
            }
        }
    }

    private com.runbox.debug.parser.command.breakpoint.Number radix8(StringBuffer buffer) throws Exception {
        while (true) {
            if (number(lookup(), 8)) {
                buffer.append(next());
            } else {
                return new com.runbox.debug.parser.command.breakpoint.Number(Integer.valueOf(buffer.substring(1).toString(), 8));
            }
        }
    }

    private com.runbox.debug.parser.command.breakpoint.Number radix16(StringBuffer buffer) throws Exception {
        while (true) {
            if (number(lookup(), 16)) {
                buffer.append(next());
            } else {
                return new com.runbox.debug.parser.command.breakpoint.Number(Integer.valueOf(buffer.substring(2).toString(), 16));
            }
        }
    }

    private int index = 0;

    public boolean bound(Token token) {
        if (1 == token.name().length()) {
            return bound(token.name().charAt(0));
        }
        return false;
    }

    private char next() {
        if (index < buffer.length()) {
            return buffer.charAt(index++);
        }
        return '#';
    }

    private char lookup() {
        if (index < buffer.length()) {
            return buffer.charAt(index);
        }
        return '#';
    }

    private void skip() {
        while ((index < buffer.length())) {
            if (space()) {
                return;
            }
            index++;
        }
    }

    private boolean space() {
        if (' ' == buffer.charAt(index)
                || '\t' == buffer.charAt(index)
                || '\r' == buffer.charAt(index)
                || '\n' == buffer.charAt(index)) {
            return false;
        }
        return true;
    }

    private boolean bound(char lookup) {
        if ('#' == lookup || '(' == lookup || ')' == lookup || ',' == lookup || ':' == lookup) {
            return true;
        }
        return false;
    }

    private boolean alphabet(char lookup) {
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
}
