package com.runbox.debug.parser.statement.token;

import com.runbox.debug.parser.Lexer;

/**
 * Created by qstesiro
 */
public class StringParser implements Lexer {

    private String statement = null;

    public StringParser(String statement) {
        this.statement = statement;
    }

    public void clean() throws Exception {

    }

    @Override
    public Token token() throws Exception {
        if (null != statement) {
            if (null == peek) {
                skip();
                StringBuffer buffer = new StringBuffer();
                while (true) {
                    if (Token.bound(lookup())) {
                        if (0 == buffer.length()) {
                            return new Token(buffer.append(next()).toString());
                        } else {
                            return new Token(buffer.toString());
                        }
                    } else {
                        buffer.append(next());
                        if (key(buffer)) {
                            return new Token(buffer.toString());
                        }
                    }
                }
            } else {
                Token token = peek;
                peek = null;
                return token;
            }
        }
        throw new Exception("invalid statement");
    }

    private Token peek = null;

    @Override
    public Token peek() throws Exception {
        if (null == peek) {
            peek = token();
        }
        return peek;
    }

    private int index = 0;

    private char lookup() {
        if (index < statement.length()) {
            return statement.charAt(index);
        }
        return '#';
    }

    private char next() {
        if (index < statement.length()) {
            return statement.charAt(index++);
        }
        return '#';
    }

    private void skip() {
        while ((index < statement.length())) {
            if (!space(statement.charAt(index))) {
                return;
            }
            index++;
        }
    }

    private boolean space(char letter) {
        boolean condition = ' ' == letter;
        condition = condition || '\t' == letter;
        condition = condition || '\r' == letter;
        condition = condition || '\n' == letter;
        return condition;
    }

    private boolean key(StringBuffer buffer) throws Exception {
        if (Token.key(buffer.toString()) && space(lookup())) {
            return true;
        }
        return false;
    }
}
