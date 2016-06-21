package com.runbox.debug.parser.expression;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.parser.expression.token.operand.*;
import com.runbox.debug.parser.expression.token.operator.Operator;
import com.runbox.debug.parser.expression.token.Token;
import com.sun.jdi.StackFrame;

import java.util.Stack;

/**
 * Created by qstesiro
 */
public class Handler {

    public Handler(Stack<Operator> operators, Stack<Operand> operands) {
        this.operators = operators;
        this.operands = operands;
    }

    protected Stack<Operator> operators = null;
    protected Stack<Operand> operands = null;

    public void handle(Operator current, Operator next) throws Exception {
        if (current.name().equals("(")) {
            handleG1(current, next);
        } else if (current.name().equals("[")) {
            handleG2(current, next);
        } else if (current.name().equals(".")) {
            handleG3(current, next);
        } else if (current.name().equals("<-")) {
            handleF(current, next);
        } else if (current.name().equals("!")) {
            handleE1(current, next);
        } else if (current.name().equals("~")) {
            handleE2(current, next);
        } else if (current.name().equals("+++")) {
            handleE3(current, next);
        } else if (current.name().equals("++")) {
            handleE4(current, next);
        } else if (current.name().equals("---")) {
            handleE5(current, next);
        } else if (current.name().equals("--")) {
            handleE6(current, next);
        } else if (current.name().equals("0+")) {
            handleE7(current, next);
        } else if (current.name().equals("0-")) {
            handleE8(current, next);
        } else if (current.name().equals("*")) {
            handleD1(current, next);
        } else if (current.name().equals("/")) {
            handleD2(current, next);
        } else if (current.name().equals("%")) {
            handleD3(current, next);
        } else if (current.name().equals("+")) {
            handleC1(current, next);
        } else if (current.name().equals("-")) {
            handleC2(current, next);
        } else if (current.name().equals("<<")) {
            handleB1(current, next);
        } else if (current.name().equals(">>")) {
            handleB2(current, next);
        } else if (current.name().equals(">>>")) {
            handleB3(current, next);
        } else if (current.name().equals("<")) {
            handleA1(current, next);
        } else if (current.name().equals("<=")) {
            handleA2(current, next);
        } else if (current.name().equals(">")) {
            handleA3(current, next);
        } else if (current.name().equals(">=")) {
            handleA4(current, next);
        } else if (current.name().equals("instanceof")) {
            handleA5(current, next);
        } else if (current.name().equals("==")) {
            handle91(current, next);
        } else if (current.name().equals("!=")) {
            handle92(current, next);
        } else if (current.name().equals("&")) {
            handle8(current, next);
        } else if (current.name().equals("^")) {
            handle7(current, next);
        } else if (current.name().equals("|")) {
            handle6(current, next);
        } else if (current.name().equals("&&")) {
            handle5(current, next);
        } else if (current.name().equals("||")) {
            handle4(current, next);
        } else if (current.name().equals("?")) {
            handle31(current, next);
        } else if (current.name().equals(":")) {
            handle32(current, next);
        } else if (current.name().equals("=")) {
            handle21(current, next);
        } else if (current.name().equals("+=")) {
            handle22(current, next);
        } else if (current.name().equals("-=")) {
            handle23(current, next);
        } else if (current.name().equals("*=")) {
            handle24(current, next);
        } else if (current.name().equals("/=")) {
            handle25(current, next);
        } else if (current.name().equals("%=")) {
            handle26(current, next);
        } else if (current.name().equals("&=")) {
            handle27(current, next);
        } else if (current.name().equals("^=")) {
            handle28(current, next);
        } else if (current.name().equals("|=")) {
            handle29(current, next);
        } else if (current.name().equals("<<=")) {
            handle2A(current, next);
        } else if (current.name().equals(">>=")) {
            handle2B(current, next);
        } else if (current.name().equals(">>>=")) {
            handle2C(current, next);
        } else if (current.name().equals(")")) {
            handle11(current, next);
        } else if (current.name().equals("]")) {
            handle12(current, next);
        } else if (current.name().equals("#")) {
            handle0(current, next);
        } else {
            throw new Exception(current.name().toString());
        }
    }

    public void handleG1(Operator current, Operator next) throws Exception {   // (
        if (next.name().equals(")")) {
            operators.pop();
            Operand operand = operands.peek();
            if (null != operand.name()) {
                if (Token.primitive(operand.name().toString())) {
                    operators.push(new Operator("<-"));
                }
            }
        } else if (next.name().equals("#")) {
            throw new Exception("missing right )");
        }
    }

    public void handleG2(Operator current, Operator next) throws Exception {   // [
        if (next.name().equals("]")) {
            operators.pop();
            Operand operand2 = operands.pop();
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeG2(operand1, operand2);
            operands.push(result);
        }
    }

    public void handleG3(Operator current, Operator next) throws Exception {    // .
        if (current.level() >= next.level()) {
            operators.pop();
            if (!operators.peek().name().equals("instanceof")) {
                Operand operand2 = operands.pop();
                Operand operand1 = convert(operands.pop());
                Operand result = Computer.computeG31(operand1, operand2);
                operands.push(result);
            } else {
                Operand operand2 = operands.pop();
                Operand operand1 = operands.pop();
                Operand result = Computer.computeG32(operand1, operand2);
                operands.push(result);
            }
            handle(operators.peek(), next);
        }
    }

    public void handleF(Operator current, Operator next) throws Exception {     // <-
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = operands.pop();
            Operand result = Computer.computeF(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleE1(Operator current, Operator next) throws Exception {    // !
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand = convert(operands.pop());
            Operand result = Computer.computeE1(operand);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleE2(Operator current, Operator next) throws Exception {    // ~
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand = convert(operands.pop());
            Operand result = Computer.computeE2(operand);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleE3(Operator current, Operator next) throws Exception {   // +++
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand = convert(operands.pop());
            Operand result = Computer.computeE3(operand);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleE4(Operator current, Operator next) throws Exception {   // ++
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand = convert(operands.pop());
            Operand result = Computer.computeE4(operand);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleE5(Operator current, Operator next) throws Exception {   // ---
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand = convert(operands.pop());
            Operand result = Computer.computeE5(operand);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleE6(Operator current, Operator next) throws Exception {   // --
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand = convert(operands.pop());
            Operand result = Computer.computeE6(operand);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleE7(Operator current, Operator next) throws Exception {   // 0+
        if (current.level() >= next.level()) {
            operators.pop();
            handle(operators.peek(), next);
        }
    }

    public void handleE8(Operator current, Operator next) throws Exception {   // 0-
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand = convert(operands.pop());
            Operand result = Computer.computeE8(operand);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleD1(Operator current, Operator next) throws Exception {    // *
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeD1(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleD2(Operator current, Operator next) throws Exception {    // /
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeD2(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleD3(Operator current, Operator next) throws Exception {    // %
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeD3(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleC1(Operator current, Operator next) throws Exception {    // +
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeC1(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleC2(Operator current, Operator next) throws Exception {    // -
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeC2(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleB1(Operator current, Operator next) throws Exception {    // <<
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeB1(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleB2(Operator current, Operator next) throws Exception {    // >>
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeB2(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleB3(Operator current, Operator next) throws Exception {    // >>>
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeB3(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleA1(Operator current, Operator next) throws Exception {    // <
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeA1(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleA2(Operator current, Operator next) throws Exception {    // <=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeA2(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleA3(Operator current, Operator next) throws Exception {    // >
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeA3(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleA4(Operator current, Operator next) throws Exception {    // >=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeA4(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handleA5(Operator current, Operator next) throws Exception {    // instanceof
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = operands.pop();
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeA5(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle91(Operator current, Operator next) throws Exception {    // ==
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute91(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle92(Operator current, Operator next) throws Exception {    // !=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute92(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle8(Operator current, Operator next) throws Exception {     // &
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute8(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle7(Operator current, Operator next) throws Exception {     // ^
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute7(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle6(Operator current, Operator next) throws Exception {     // |
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute6(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle5(Operator current, Operator next) throws Exception {     // &&
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute5(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle4(Operator current, Operator next) throws Exception {     // ||
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute4(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle31(Operator current, Operator next) throws Exception {    // ?
        Computer.compute31(convert(operands.peek()));
    }

    public void handle32(Operator current, Operator next) throws Exception {    // :
        if (current.level() >= next.level()) {
            operators.pop();
            Operator operator = operators.pop();
            if (operator.name().equals("?")) {
                Operand operand1 = convert(operands.pop());
                Operand operand2 = convert(operands.pop());
                Operand operand3 = convert(operands.pop());
                Operand result = Computer.compute32(operand1, operand2, operand3);
                operands.push(result);
                handle(operators.peek(), next);
            }
        }
    }

    public void handle21(Operator current, Operator next) throws Exception {    // =
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute21(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle22(Operator current, Operator next) throws Exception {    // +=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute22(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle23(Operator current, Operator next) throws Exception {    // -=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute23(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle24(Operator current, Operator next) throws Exception {    // *=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute24(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle25(Operator current, Operator next) throws Exception {    // /=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute25(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle26(Operator current, Operator next) throws Exception {    // %=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute26(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle27(Operator current, Operator next) throws Exception {    // &=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute27(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle28(Operator current, Operator next) throws Exception {    // ^=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute28(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle29(Operator current, Operator next) throws Exception {    // |=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute29(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle2A(Operator current, Operator next) throws Exception {    // <<=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute2A(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle2B(Operator current, Operator next) throws Exception {    // >>=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute2B(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle2C(Operator current, Operator next) throws Exception {    // >>>=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute2C(operand1, operand2);
            operands.push(result);
            handle(operators.peek(), next);
        }
    }

    public void handle11(Operator current, Operator next) throws Exception {    // )

    }

    public void handle12(Operator current, Operator next) throws Exception {    // ]

    }

    public void handle0(Operator current, Operator next) throws Exception { // #
        if (current.level() == next.level()) {
            operators.pop();
            Operand operand = operands.pop();
            Operand result = convert(operand);
            operands.push(result);
        }
    }

    protected Operand convert(Operand operand) throws Exception {
        if (null != operand) {
            if (!Operand.subClass(operand)) {
                if (Token.isThis(operand.name())) {
                    StackFrame frame = ContextManager.instance().frame();
                    if (null != frame && null != frame.thisObject()) {
                        return new ConstOperand("this", frame.thisObject());
                    }
                } else if (!(Token.local(operand.name()) && Token.field(operand.name()))) {
                    if (Token.local(operand.name())) {
                        return new LocalOperand(operand.name());
                    } else if (Token.field(operand.name())) {
                        StackFrame frame = ContextManager.instance().frame();
                        if (null != frame) {
                            return new FieldOperand(frame.thisObject(), operand.name());
                        }
                    }
                }
            }
        }
        return operand;
    }
}
