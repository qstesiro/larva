package com.runbox.debug.command.print;

import java.util.List;
import java.util.LinkedList;
import java.util.Vector;

import com.sun.jdi.*;
import com.sun.tools.javac.util.Pair;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.*;

public class PrintCommand extends Command {

    public PrintCommand(String command) throws Exception {
        super(command);
    }

    protected List<Operand> fields() throws Exception {
        List<Operand> operands = new LinkedList<Operand>();
        StackFrame frame = ContextManager.instance().frame();
        if (null != frame) {
            if (null != frame.thisObject()) {
                List<Field> fields = frame.thisObject().referenceType().allFields();
                for (Field field : fields) {
                    operands.add(new FieldOperand(frame.thisObject(), field.name()));
                }
            }
        }
        return operands;
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
            List<LocalVariable> locals = frame.visibleVariables();
            for (LocalVariable local : locals) {
                operands.add(new LocalOperand(local.name()));
            }
			System.out.println(frame.getArgumentValues().size());
        }		
        return operands;
    }        
}
