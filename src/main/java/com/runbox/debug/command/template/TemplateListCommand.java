package com.runbox.debug.command.template;

import java.util.List;
import java.util.LinkedList;

import com.sun.jdi.*;

import com.runbox.debug.script.expression.token.operand.ArrayOperand;
import com.runbox.debug.script.expression.token.operand.ConstOperand;
import com.runbox.debug.script.expression.token.operand.FieldOperand;
import com.runbox.debug.script.expression.token.operand.Operand;

public class TemplateListCommand extends TemplateCommand {

    public TemplateListCommand(String command) throws Exception {
        super(command);
    }

	private static final String LIST = "java.util.List";
	private static final String ARRAY_LIST = "java.util.ArrayList";	
	private static final String LINKED_LIST = "java.util.LinkedList";
	private static final String STACK = "java.util.Stack";
	private static final String VECTOR = "java.util.Vector";
	private static final String COPY_ON_WRITE_ARRAY_LIST = "java.util.concurrent.CopyOnWriteArrayList";
	
	@Override
	protected boolean type() throws Exception {
        return superInterface(LIST);
    }       
	
	@Override
    protected List<Operand> elements() throws Exception {
        if (superClass(ARRAY_LIST)) {
            return arrayElements();
        } else if (superClass(LINKED_LIST)) {
            return linkedElements();
        } else if (superClass(STACK)) {
			return stackElements();
		} else if (superClass(VECTOR)) {
			return vectorElements();
		} else if (superClass(COPY_ON_WRITE_ARRAY_LIST)) {
			return copyOnWriteArrayElements();
		}
        throw new Exception("can not recognize template");
    }	

    private List<Operand> arrayElements() throws Exception {
        List<Operand> operands = new LinkedList<Operand>();
        Operand array = field("elementData");
        Operand size = field("size");
		if (null != array && null != size) {
			for (int i = 0; i < ((IntegerValue)size.value()).value(); ++i) {
				operands.add(new ConstOperand(new ArrayOperand((ArrayReference)array.value(), i).value()));
			}
		}
        return operands;
    } 	
	
    private List<Operand> linkedElements() throws Exception {
        List<Operand> operands = new LinkedList<Operand>();
        Operand element = field("first");
		if (null != element) {
			while (null != element.value()) {
				operands.add(new FieldOperand((ObjectReference)element.value(), "item"));
				element = new FieldOperand((ObjectReference)element.value(), "next");
			}
		}
        return operands;
    }

	private List<Operand> stackElements() throws Exception {
        List<Operand> operands = new LinkedList<Operand>();
        Operand array = field("elementData");
		if (null != array) {
			for (int i = 0; i < ((ArrayReference)array.value()).length(); ++i) {
				ArrayOperand operand = new ArrayOperand((ArrayReference)array.value(), i);
				if (null != operand.value()) operands.add(0, operand);				
			}
		}
        return operands;
    }
	
	private List<Operand> vectorElements() throws Exception {
        List<Operand> operands = new LinkedList<Operand>();
        Operand array = field("elementData");
		if (null != array) {
			for (int i = 0; i < ((ArrayReference)array.value()).length(); ++i) {
				ArrayOperand operand = new ArrayOperand((ArrayReference)array.value(), i);
				if (null != operand.value()) operands.add(operand);				
			}
		}
        return operands;
    }

	private List<Operand> copyOnWriteArrayElements() throws Exception {
		List<Operand> operands = new LinkedList<Operand>();
		return operands;
	}
}
