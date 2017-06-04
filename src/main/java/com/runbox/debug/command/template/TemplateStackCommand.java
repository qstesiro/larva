package com.runbox.debug.command.template;

import java.util.List;
import java.util.LinkedList;

import com.sun.jdi.*;

import com.runbox.debug.script.expression.token.operand.ArrayOperand;
import com.runbox.debug.script.expression.token.operand.Operand;

public class TemplateStackCommand extends TemplateCommand {

    public TemplateStackCommand(String command) throws Exception {
        super(command);
    }

	@Override
    protected boolean type() throws Exception {
        if (superClass("java.util.Stack")) {
			return true;
        }
        return false;
    }
	
	@Override
	protected void printElements() throws Exception {
		if (null != operand()) {
			List<Operand> operands = elements();
			for (int i = operands.size() - 1; i >= 0; --i) {
				Operand operand = operands.get(i);
				System.out.printf("#%-7d", i);
				if (null != operand.value()) {
					if (operand.isString()) {
						String string = "instance of ";
						string += operand.type().name();
						string += "(" + "id=" + ((ObjectReference)operand.value()).uniqueID() + ")";
						System.out.print(string);
					} else {
						System.out.print(operand.value());
					}
					if (FLAG_ELEMENT_TYPE == (FLAG_ELEMENT_TYPE & flags())) {
						System.out.print(" :" + operand.value().type().name());
					}
				} else {
					System.out.print("null");
				}
				System.out.println();
			}
		}
	}
	
	@Override
    protected List<Operand> elements() throws Exception {
        List<Operand> operands = new LinkedList<Operand>();
        Operand elements = field("elementData");
		if (null != elements) {
			for (int i = 0; i < ((ArrayReference)elements.value()).length(); ++i) {
				ArrayOperand operand = new ArrayOperand((ArrayReference)elements.value(), i);
				if (null != operand.value()) {
					operands.add(0, operand);
				}
			}
		}
        return operands;
    }
}
