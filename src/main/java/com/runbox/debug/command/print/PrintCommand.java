package com.runbox.debug.command.print;

import java.util.List;
import java.util.LinkedList;
import java.util.Vector;

import com.sun.jdi.*;
import com.sun.tools.javac.util.Pair;

import com.runbox.script.Engine;
import com.runbox.script.statement.token.Token;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.*;

public class PrintCommand extends Command {

    public PrintCommand(String command) throws Exception {
        super(command);
    }    

	protected FieldOperand field(Operand operand, String name) throws Exception {
        if (null != operand) {
			if (null != operand.value()) {
				ObjectReference object = (ObjectReference)operand.value();
				Field field = object.referenceType().fieldByName(name);
				return new FieldOperand(object, field.name());
			}
        }
        return null;
    }
	
    protected List<Operand> fields(Operand operand) throws Exception {
        List<Operand> operands = new LinkedList<Operand>();
        if (null != operand && null != operand.value()) {
            ObjectReference object = (ObjectReference)operand.value();
            List<Field> fields = object.referenceType().allFields();
            for (Field field : fields) {
                operands.add(new FieldOperand(object, field.name()));
            }
        }
        return operands;
    }

    protected List<Operand> locals() throws Exception {
        List<Operand> operands = new LinkedList<Operand>();
        StackFrame frame = ContextManager.instance().frame();
        if (null != frame) {
			try {
				List<LocalVariable> locals = frame.visibleVariables();
				for (LocalVariable local : locals) {
					operands.add(new LocalOperand(local.name()));
				}
			} catch (AbsentInformationException e) {}
        }		
        return operands;
    }

	protected List<Operand> autos() throws Exception {
		List<Operand> autos = new LinkedList<Operand>();
		for (Token auto : Engine.instance().autos()) {
			if (auto instanceof Operand) {
				autos.add((Operand)auto);
			}
		}
		return autos;
	}

	protected String object(Operand operand) throws Exception {
		final String STRING = "java.lang.String";
		if (clazz(operand, STRING)) {
			String string = "instance of ";
			string += operand.type().name();
			string += "(" + "id=" + operand.objectValue().uniqueID() + ")";
			return string;
		}
		return operand.value().toString();		
	}

	protected boolean clazz(Operand operand, String name) throws Exception {
		if (null != operand) {
			if (null != operand.value()) {
				if (operand.valueType() instanceof ClassType) {
					if (operand.valueType().name().equals(name)) {
						return true;
					}
				}
			} else if (null != operand.type()) {				
				if (operand.type() instanceof ClassType) {
					if (operand.type().name().equals(name)) {
						return true;
					}
				}				
			}
		}        
        return false;
    }
}
