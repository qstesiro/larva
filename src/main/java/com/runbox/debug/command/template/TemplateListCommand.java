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

	@Override
    public boolean execute() throws Exception {       
		if (list()) {
			return super.execute();
		}
        throw new Exception("invalid operand");
    }
	
	@Override
    protected List<Operand> elements() throws Exception {
        if (array()) {
            return arrayElements();
        } else if (linked()) {
            return linkedElements();
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

    private boolean list() throws Exception {
        boolean condition = superClass("java.util.ArrayList");
        condition = condition || superClass("java.util.LinkedList");
        return condition;
    }

    private boolean array() throws Exception {
        if (superClass("java.util.ArrayList")) {
            boolean condition = exist("elementData");
            condition = condition && exist("size");
            return condition;
        }
        return false;
    }

    private boolean linked() throws Exception {
        if (superClass("java.util.LinkedList")) {
            boolean condition = exist("first");
            condition = condition && exist("last");
            condition = condition && exist("size");
            return condition;
        }
        return false;
    }
}
