package com.runbox.debug.command.template;

import java.util.List;
import java.util.LinkedList;

import com.sun.jdi.*;

import com.runbox.debug.script.expression.token.operand.ArrayOperand;
import com.runbox.debug.script.expression.token.operand.Operand;

public class TemplateQueueCommand extends TemplateCommand {

    public TemplateQueueCommand(String command) throws Exception {
        super(command);
    }    

	@Override
    public boolean execute() throws Exception {       
		if (queue()) {
			return super.execute();
		}
        throw new Exception("invalid operand");
    }
	
	@Override
    protected List<Operand> elements() throws Exception {
        if (priority()) {
            return priorityEntries();
        } else if (array()) {
            return arrayEntries();
        }
        throw new Exception("can not recognize template");
    }

    private List<Operand> priorityEntries() throws Exception {
        List<Operand> operands = new LinkedList<Operand>();
        Operand queue = field("queue");
		if (null != queue) {
			for (int i = 0; i < ((ArrayReference)queue.value()).length(); ++i) {
				ArrayOperand operand = new ArrayOperand((ArrayReference)queue.value(), i);
				if (null != operand.value()) {
					operands.add(operand);
				}
			}
		}
        return operands;
    }

    private List<Operand> arrayEntries() throws Exception {
        List<Operand> operands = new LinkedList<Operand>();
        Operand elements = field("elements");
		if (null != elements) {
			for (int i = 0; i < ((ArrayReference)elements.value()).length(); ++i) {
				ArrayOperand operand = new ArrayOperand((ArrayReference)elements.value(), i);
				if (null != operand.value()) {
					operands.add(operand);
				}
			}
		}
        return operands;
    }

    private boolean queue() throws Exception {
        boolean condition = superClass("java.util.ArrayQueue");
        condition = condition || superClass("java.util.PriorityQueue");
        condition = condition || superClass("java.util.ArrayDeque");
        return condition;
    }

    private boolean priority() throws Exception {
        if (superClass("java.util.PriorityQueue")) {
            boolean condition = exist("queue");
            condition = condition && exist("size");
            condition = condition && exist("comparator");
            condition = condition && exist("modCount");
            return condition;
        }
        return false;
    }

    private boolean array() throws Exception {
        if (superClass("java.util.ArrayDeque")) {
            boolean condition = exist("elements");
            condition = condition && exist("head");
            condition = condition && exist("tail");
            return condition;
        }
        return false;
    }
}
