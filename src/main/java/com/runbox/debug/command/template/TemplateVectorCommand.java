package com.runbox.debug.command.template;

import java.util.List;
import java.util.LinkedList;

import com.sun.jdi.*;

import com.runbox.debug.script.expression.token.operand.ArrayOperand;
import com.runbox.debug.script.expression.token.operand.Operand;

public class TemplateVectorCommand extends TemplateCommand {

    public TemplateVectorCommand(String command) throws Exception {
        super(command);
    }    

	@Override
    public boolean execute() throws Exception {       
		if (vector()) {
			return super.execute();
		}
        throw new Exception("invalid operand");
    }
	
	@Override
    protected List<Operand> elements() throws Exception {
        List<Operand> operands = new LinkedList<Operand>();
        Operand elements = field("elementData");
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

    private boolean vector() throws Exception {
        if (superClass("java.util.Vector")) {
            boolean condition = exist("elementData");
            condition = condition && exist("elementCount");
            condition = condition && exist("capacityIncrement");
            return condition;
        }
        return false;
    }
}
