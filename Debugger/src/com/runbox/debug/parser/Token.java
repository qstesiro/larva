package com.runbox.debug.parser;

/**
 * Created by qstesiro
 */
public class Token {

    public Token(String name) {
        if (null != name) {
            this.name = name.trim();
        }
    }

    private String name = null;

    public void name(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public static boolean block(String string) {
        if ('$' == string.charAt(0)) {
            return true;
        }
        return false;
    }

    public static boolean bound(char letter) {
        boolean condition = '{' == letter;
        condition = condition || '}' == letter;
        condition = condition || ';' == letter;
        condition = condition || '#' == letter;
        return condition;
    }
}
