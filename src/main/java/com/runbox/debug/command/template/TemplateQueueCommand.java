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

	private static final String QUEUE = "java.util.Queue";
	private static final String ARRAY_DEQUE = "java.util.ArrayDeque";
	private static final String LINKED_LIST = "java.util.LinkedList";
	private static final String PRIORITY_QUEUE = "java.util.PriorityQueue";
	private static final String DELAY_QUEUE = "java.util.concurrent.DelayQueue";
	private static final String ARRAY_BLOCK_QUEUE = "java.util.concurrent.ArrayBlockingQueue";	
	private static final String CONCURRENT_LINKED_DEQUE = "java.util.concurrent.ConcurrentLinkedDeque";
	private static final String CONCURRENT_LINKED_QUEUE = "java.util.concurrent.ConcurrentLinkedQueue";	
	private static final String LINKED_BLOCKING_DEQUE = "java.util.concurrent.LinkedBlockingDeque";
	private static final String LINKED_BLOCKING_QUEUE = "java.util.concurrent.LinkedBlockingQueue";
	private static final String PRIORITY_BLOCKING_QUEUE = "java.util.concurrent.PriorityBlockingQueue";
	
	@Override
	protected boolean type() throws Exception {
		return superInterface(QUEUE);
    }	
	
	@Override
    protected List<Operand> elements() throws Exception {
		if (superClass(ARRAY_DEQUE)) {
			return arrayElements();
		} else if (superClass(LINKED_LIST)) {
			return new LinkedList<Operand>();
		} else if (superClass(PRIORITY_QUEUE)) {
			return priorityElements();
		} else if (superClass(DELAY_QUEUE)) {
			return new LinkedList<Operand>();
		} else if (superClass(ARRAY_BLOCK_QUEUE)) {
			return new LinkedList<Operand>();
		} else if (superClass(CONCURRENT_LINKED_DEQUE)) {
			return new LinkedList<Operand>();
		} else if (superClass(CONCURRENT_LINKED_QUEUE)) {
			return new LinkedList<Operand>();
		} else if (superClass(LINKED_BLOCKING_DEQUE)) {
			return new LinkedList<Operand>();
		} else if (superClass(LINKED_BLOCKING_QUEUE)) {
			return new LinkedList<Operand>();
		} else if (superClass(PRIORITY_BLOCKING_QUEUE)) {
			return new LinkedList<Operand>();
		}		
        throw new Exception("can not recognize template");
    }
		
	private List<Operand> arrayElements() throws Exception {
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
	
    private List<Operand> priorityElements() throws Exception {
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
}
