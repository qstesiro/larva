package com.runbox.debug.parser.statement.token;

/**
 * Created by qstesiro
 */
public class Token extends com.runbox.debug.parser.Token {

    public Token(String name) {
        super(name.trim());
    }

    public static boolean key(String string) {
        boolean condition = string.equals("if");
        condition = condition || string.equals("else");
        condition = condition || string.equals("while");
        condition = condition || string.equals("continue");
        condition = condition || string.equals("break");
        return condition;
    }
}
