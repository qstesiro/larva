package com.runbox.debug.command.template;

import com.runbox.debug.command.Command;
import com.runbox.debug.parser.expression.Expression;
import com.runbox.debug.parser.expression.token.operand.ConstOperand;
import com.runbox.debug.parser.expression.token.operand.FieldOperand;
import com.runbox.debug.parser.expression.token.operand.Operand;
import com.sun.jdi.ClassType;
import com.sun.jdi.Field;
import com.sun.jdi.ObjectReference;


/**
 * Created by qstesiro
 */
public class TemplateCommand extends Command {

    public TemplateCommand(String command) throws Exception {
        super(command);
        if (null != argument) {
            operand = new Expression(argument.trim()).handle();
            if (Operand.subClass(operand) && !(operand instanceof ConstOperand)) {
                return;
            }
        }
        throw new Exception("invalid template");
    }

    protected Operand operand = null;

    protected boolean superClass(String name) throws Exception {
        if (null != operand.value() && operand.value().type() instanceof ClassType) {
            if (((ClassType)operand.value().type()).superclass().name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    protected boolean exist(String name) throws Exception {
        return (null != field(name));
    }

    protected Operand field(String name) throws Exception {
        if (null != operand) {
            ObjectReference object = (ObjectReference)operand.value();
            Field field = object.referenceType().fieldByName(name);
            if (null != field) {
                return new FieldOperand(object, field.name());
            }
        }
        return null;
    }

    protected String format(Operand operand) throws Exception {
        String print = "";
        if (null != operand.value()) {
            print += operand.value();
            if (operand.value() instanceof ObjectReference) {
                print += " :" + operand.value().type().name();
            }
        }
        else {
            print += "null";
        }
        return print;
    }
}
