package com.runbox.script.expression;

public class ExpressionFactory {

    public static Expression build(String expression) throws Exception {
        return new com.runbox.debug.script.expression.Expression(expression);
    }
}