package com.runbox.script.statement.token;

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

    public static boolean bound(char letter) {
        boolean condition = '{' == letter;
        condition = condition || '}' == letter;
        condition = condition || ';' == letter;
        condition = condition || '#' == letter;
        return condition;
    }

    public static boolean key(String string) {
        boolean condition = string.equals("def");
		condition = condition || string.equals("if");
        condition = condition || string.equals("else");
        condition = condition || string.equals("while");
        condition = condition || string.equals("continue");
        condition = condition || string.equals("break");
		condition = condition || string.equals("return");
        return condition;
    }
}
