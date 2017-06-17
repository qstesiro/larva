package com.runbox.debug.script.expression;

import java.util.List;
import java.util.LinkedList;
import java.util.Stack;

import com.sun.jdi.StackFrame;

import com.runbox.debug.manager.ContextManager;
import com.runbox.script.Engine;
import com.runbox.script.statement.Script;
import com.runbox.script.statement.node.BlockNode;
import com.runbox.debug.script.expression.token.operand.*;
import com.runbox.debug.script.expression.token.Token;
import com.runbox.debug.script.expression.token.operator.Operator;

public class Executer {

    public Executer(Stack<Operator> operators, Stack<Operand> operands) {
        this.operators = operators;
        this.operands = operands;
    }

    private Stack<Operator> operators = null;
    private Stack<Operand> operands = null;

	private BlockNode block = null;
	
    public void execute(Operator current, Operator next) throws Exception {		
        if (current.name().equals("(")) {
            executeI1(current, next);
        } else if (current.name().equals("[")) {
            executeI2(current, next);
        } else if (current.name().equals("->")) {
			executeH(current, next);
		} else if (current.name().equals(".")) {
            executeG(current, next);
        } else if (current.name().equals("<-")) {
            executeF(current, next);
        } else if (current.name().equals("!")) {
            executeE1(current, next);
        } else if (current.name().equals("~")) {
            executeE2(current, next);
        } else if (current.name().equals("+++")) {
            executeE3(current, next);
        } else if (current.name().equals("++")) {
            executeE4(current, next);
        } else if (current.name().equals("---")) {
            executeE5(current, next);
        } else if (current.name().equals("--")) {
            executeE6(current, next);
        } else if (current.name().equals("0+")) {
            executeE7(current, next);
        } else if (current.name().equals("0-")) {
            executeE8(current, next);
        } else if (current.name().equals("*")) {
            executeD1(current, next);
        } else if (current.name().equals("/")) {
            executeD2(current, next);
        } else if (current.name().equals("%")) {
            executeD3(current, next);
        } else if (current.name().equals("+")) {
            executeC1(current, next);
        } else if (current.name().equals("-")) {
            executeC2(current, next);
        } else if (current.name().equals("<<")) {
            executeB1(current, next);
        } else if (current.name().equals(">>")) {
            executeB2(current, next);
        } else if (current.name().equals(">>>")) {
            executeB3(current, next);
        } else if (current.name().equals("<")) {
            executeA1(current, next);
        } else if (current.name().equals("<=")) {
            executeA2(current, next);
        } else if (current.name().equals(">")) {
            executeA3(current, next);
        } else if (current.name().equals(">=")) {
            executeA4(current, next);
        } else if (current.name().equals("instanceof")) {
            executeA5(current, next);
        } else if (current.name().equals("==")) {
            execute91(current, next);
        } else if (current.name().equals("!=")) {
            execute92(current, next);
        } else if (current.name().equals("&")) {
            execute8(current, next);
        } else if (current.name().equals("^")) {
            execute7(current, next);
        } else if (current.name().equals("|")) {
            execute6(current, next);
        } else if (current.name().equals("&&")) {
            execute5(current, next);
        } else if (current.name().equals("||")) {
            execute4(current, next);
        } else if (current.name().equals("?")) {
            execute31(current, next);
        } else if (current.name().equals(":")) {
            execute32(current, next);
        } else if (current.name().equals("=")) {
            execute21(current, next);
        } else if (current.name().equals("+=")) {
            execute22(current, next);
        } else if (current.name().equals("-=")) {
            execute23(current, next);
        } else if (current.name().equals("*=")) {
            execute24(current, next);
        } else if (current.name().equals("/=")) {
            execute25(current, next);
        } else if (current.name().equals("%=")) {
            execute26(current, next);
        } else if (current.name().equals("&=")) {
            execute27(current, next);
        } else if (current.name().equals("^=")) {
            execute28(current, next);
        } else if (current.name().equals("|=")) {
            execute29(current, next);
        } else if (current.name().equals("<<=")) {
            execute2A(current, next);
        } else if (current.name().equals(">>=")) {
            execute2B(current, next);
        } else if (current.name().equals(">>>=")) {
            execute2C(current, next);
        } else if (current.name().equals(")")) {
            execute11(current, next);
        } else if (current.name().equals("]")) {
            execute12(current, next);
        } else if (current.name().equals(",")) {
			execute13(current, next);
		} else if (current.name().equals("#")) {
            execute0(current, next);
        } else {
            throw new Exception(current.name().toString());
        }
    }

    public void executeI1(Operator current, Operator next) throws Exception {   // (
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

    public void executeI2(Operator current, Operator next) throws Exception {   // [
        if (next.name().equals("]")) {
            operators.pop();
            Operand operand2 = operands.pop();
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeI2(operand1, operand2);
            operands.push(result);
        }
    }

	public void executeH(Operator current, Operator next) throws Exception {    // ->
		if (next.name().equals(")")) {
			operators.pop();
			List<Operand> arguments = new LinkedList<Operand>();
			Operand operand = operands.peek();
			while (!Token.routine(operand.name())) {				
				arguments.add(0, convert(operands.pop()));
				operand = operands.peek();
			} 
			RoutineOperand routine = (RoutineOperand)operands.pop();
			if (routine.count() == arguments.size()) {
				Operand result = Computer.computeH(routine, arguments);
				operands.push(result);
			} else {
				throw new Exception("invalie argument count -> " + arguments.size());
			}
		}
	}

    public void executeG(Operator current, Operator next) throws Exception {    // .
        if (current.level() >= next.level()) {
            operators.pop();
            if (!operators.peek().name().equals("instanceof")) {
                Operand operand2 = operands.pop();
                Operand operand1 = convert(operands.pop());
                Operand result = Computer.computeG1(operand1, operand2);
                operands.push(result);
            } else {
                Operand operand2 = operands.pop();
                Operand operand1 = operands.pop();
                Operand result = Computer.computeG2(operand1, operand2);
                operands.push(result);
            }
            execute(operators.peek(), next);
        }
    }

    public void executeF(Operator current, Operator next) throws Exception {     // <-
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = operands.pop();
            Operand result = Computer.computeF(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeE1(Operator current, Operator next) throws Exception {    // !
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand = convert(operands.pop());
            Operand result = Computer.computeE1(operand);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeE2(Operator current, Operator next) throws Exception {    // ~
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand = convert(operands.pop());
            Operand result = Computer.computeE2(operand);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeE3(Operator current, Operator next) throws Exception {   // +++
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand = convert(operands.pop());
            Operand result = Computer.computeE3(operand);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeE4(Operator current, Operator next) throws Exception {   // ++
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand = convert(operands.pop());
            Operand result = Computer.computeE4(operand);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeE5(Operator current, Operator next) throws Exception {   // ---
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand = convert(operands.pop());
            Operand result = Computer.computeE5(operand);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeE6(Operator current, Operator next) throws Exception {   // --
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand = convert(operands.pop());
            Operand result = Computer.computeE6(operand);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeE7(Operator current, Operator next) throws Exception {   // 0+
        if (current.level() >= next.level()) {
            operators.pop();
            execute(operators.peek(), next);
        }
    }

    public void executeE8(Operator current, Operator next) throws Exception {   // 0-
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand = convert(operands.pop());
            Operand result = Computer.computeE8(operand);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeD1(Operator current, Operator next) throws Exception {    // *
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeD1(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeD2(Operator current, Operator next) throws Exception {    // /
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeD2(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeD3(Operator current, Operator next) throws Exception {    // %
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeD3(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeC1(Operator current, Operator next) throws Exception {    // +
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeC1(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeC2(Operator current, Operator next) throws Exception {    // -
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeC2(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeB1(Operator current, Operator next) throws Exception {    // <<
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeB1(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeB2(Operator current, Operator next) throws Exception {    // >>
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeB2(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeB3(Operator current, Operator next) throws Exception {    // >>>
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeB3(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeA1(Operator current, Operator next) throws Exception {    // <
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeA1(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeA2(Operator current, Operator next) throws Exception {    // <=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeA2(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeA3(Operator current, Operator next) throws Exception {    // >
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeA3(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeA4(Operator current, Operator next) throws Exception {    // >=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeA4(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void executeA5(Operator current, Operator next) throws Exception {    // instanceof
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = operands.pop();
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.computeA5(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute91(Operator current, Operator next) throws Exception {    // ==
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute91(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute92(Operator current, Operator next) throws Exception {    // !=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute92(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute8(Operator current, Operator next) throws Exception {     // &
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute8(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute7(Operator current, Operator next) throws Exception {     // ^
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute7(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute6(Operator current, Operator next) throws Exception {     // |
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute6(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute5(Operator current, Operator next) throws Exception {     // &&
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute5(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute4(Operator current, Operator next) throws Exception {     // ||
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute4(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute31(Operator current, Operator next) throws Exception {    // ?
        Computer.compute31(convert(operands.peek()));
    }

    public void execute32(Operator current, Operator next) throws Exception {    // :
        if (current.level() >= next.level()) {
            operators.pop();
            Operator operator = operators.pop();
            if (operator.name().equals("?")) {
                Operand operand1 = convert(operands.pop());
                Operand operand2 = convert(operands.pop());
                Operand operand3 = convert(operands.pop());
                Operand result = Computer.compute32(operand1, operand2, operand3);
                operands.push(result);
                execute(operators.peek(), next);
            }
        }
    }

    public void execute21(Operator current, Operator next) throws Exception {    // =
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());			
			if (Token.auto(operand1.name()) && !(operand1 instanceof AutoOperand)) {
				operand1 = new AutoOperand(operand1.name());
				Engine.instance().append((AutoOperand)operand1);
			}			
            Operand result = Computer.compute21(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute22(Operator current, Operator next) throws Exception {    // +=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute22(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute23(Operator current, Operator next) throws Exception {    // -=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute23(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute24(Operator current, Operator next) throws Exception {    // *=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute24(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute25(Operator current, Operator next) throws Exception {    // /=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute25(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute26(Operator current, Operator next) throws Exception {    // %=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute26(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute27(Operator current, Operator next) throws Exception {    // &=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute27(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute28(Operator current, Operator next) throws Exception {    // ^=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute28(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute29(Operator current, Operator next) throws Exception {    // |=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute29(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute2A(Operator current, Operator next) throws Exception {    // <<=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute2A(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute2B(Operator current, Operator next) throws Exception {    // >>=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute2B(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute2C(Operator current, Operator next) throws Exception {    // >>>=
        if (current.level() >= next.level()) {
            operators.pop();
            Operand operand2 = convert(operands.pop());
            Operand operand1 = convert(operands.pop());
            Operand result = Computer.compute2C(operand1, operand2);
            operands.push(result);
            execute(operators.peek(), next);
        }
    }

    public void execute11(Operator current, Operator next) throws Exception {    // )
		
    }

    public void execute12(Operator current, Operator next) throws Exception {    // ]

    }

	public void execute13(Operator current, Operator next) throws Exception {    // ,
		
	}
	
    public void execute0(Operator current, Operator next) throws Exception {    // #
        if (current.level() == next.level()) {
            operators.pop();
			for (int i = 0; i < operands.size(); ++i) {
				operands.set(i, convert(operands.get(i)));
			}            
        }
    }

    private Operand convert(Operand operand) throws Exception {
        if (null != operand) {
            if (!Operand.subClass(operand)) {
				if (Token.field(operand.name())) {
					return new FieldOperand(operand.name());
				} else if (Token.local(operand.name())) {
					return new LocalOperand(operand.name());
				} else if (Token.auto(operand.name())) {
					AutoOperand auto = (AutoOperand)Engine.instance().findAuto(operand.name());
					if (null != auto) return auto;
				}
			}
		}
        return operand;
    }
}
