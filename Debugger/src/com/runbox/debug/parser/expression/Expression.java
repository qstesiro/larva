package com.runbox.debug.parser.expression;

import com.runbox.debug.parser.expression.token.operand.Operand;
import com.runbox.debug.parser.expression.token.operator.Operator;
import com.runbox.debug.parser.expression.token.*;

import java.util.*;

/**
 * Created by huangmengmeng01 on 2016/5/9.
 */
public class Expression {

    private Stack<Operator> operators = new Stack<>();
    private Stack<Operand> operands = new Stack<>();

    public Expression(String express) throws Exception {
        if (null != express) {
            parser = new Lexer(express);
            handler = new Handler(operators, operands);
            operators.push(new Operator("#"));
        } else {
            throw new Exception("invalid express");
        }
    }

    private Lexer parser = null;

    public void parser(Lexer parser) {
        this.parser = parser;
    }

    private Handler handler = null;

    public void handler(Handler handler) {
        this.handler = handler;
    }

    public Operand handle() throws Exception {
        if (null != parser) {
            while (true) {
                Token token = parser.token();
                if (token instanceof Operator) {
                    handler.handle(operators.peek(), (Operator)token);
                    if (operators.empty()) {
                        return operands.pop();
                    }
                    if (!token.name().equals(")") && !token.name().equals("]") && !token.name().equals("#")) {
                        operators.push((Operator)token);
                    }
                } else {
                    operands.push((Operand)token);
                }
                /*if (token instanceof Operator) {
                    System.out.print(token.name() + " ");
                    if (token.name().equals("#")) {
                        System.out.println();
                        return null;
                    }
                } else if (token instanceof Operand) {
                    Operand operand = (Operand)token;
                    if (null != operand.name()) {
                        System.out.print(operand.name() + " ");
                    } else if (null != operand.value()){
                        System.out.print(operand.value().toString() + " ");
                    }
                }*/
            }
        }
        throw new Exception("invalid exception");
    }
}
