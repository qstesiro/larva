package com.runbox.debug.command.variant;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.command.Command;
import com.runbox.debug.parser.expression.token.operand.*;
import com.runbox.debug.manager.AutoManager;
import com.sun.jdi.*;
import com.sun.tools.javac.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by qstesiro on 2016/5/29.
 */
public class VariantCommand extends Command {

    public VariantCommand(String command) throws Exception {
        super(command);
    }

    protected List<Operand> fields() throws Exception {
        List<Operand> operands = new LinkedList<>();
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
        List<Operand> operands = new LinkedList<>();
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
        List<Operand> operands = new LinkedList<>();
        StackFrame frame = ContextManager.instance().frame();
        if (null != frame) {
            List<LocalVariable> locals = frame.visibleVariables();
            for (LocalVariable local : locals) {
                operands.add(new LocalOperand(local.name()));
            }
        }
        return operands;
    }

    protected List<Operand> autos() {
        List<Operand> operands = new LinkedList<>();
        Map<String, Pair<Type, Value>> autos = AutoManager.instance().autos();
        for (String key : autos.keySet()) {
            operands.add(new AutoOperand(key));
        }
        return operands;
    }

    protected void print(List<Operand> operands) throws Exception {
        for (Operand operand : operands) {
            print(operand);
        }
    }

    protected void print(Operand operand) throws Exception {
        if (null != operand) {
            if (null != operand.value()) {
                if (operand.value() instanceof ByteValue) {
                    System.out.println(format(operand));
                    return;
                } else if (operand.value() instanceof CharValue) {
                    System.out.println(format(operand));
                    return;
                } else if (operand.value() instanceof ShortValue) {
                    System.out.println(format(operand));
                    return;
                } else if (operand.value() instanceof IntegerValue) {
                    System.out.println(format(operand));
                    return;
                } else if (operand.value() instanceof LongValue) {
                    System.out.println(format(operand));
                    return;
                } else if (operand.value() instanceof FloatValue) {
                    System.out.println(format(operand));
                    return;
                } else if (operand.value() instanceof DoubleValue) {
                    System.out.println(format(operand));
                    return;
                } else if (operand.value() instanceof BooleanValue) {
                    System.out.println(format(operand));
                    return;
                } else if (operand.value() instanceof StringReference) {
                    System.out.println(format(operand));
                    return;
                } else if (operand.value() instanceof ObjectReference) {
                    System.out.println(format(operand));
                    return;
                }
            } else {
                System.out.println(format(operand));
                return;
            }
        }
        throw new Exception("invalid operand");
    }

    private String format(Operand operand) throws Exception {
        String print = "";
        if (null != operand.value()) {
            if (null != operand.name()) {
                print += operand.name();
                print += " :" + operand.type().name();
                print += " = ";
            }
            print += operand.value();
            if (operand.value() instanceof ObjectReference) {
                print += " :" + operand.value().type().name();
            }
        } else {
            if (null != operand.name()) {
                print = operand.name();
            }
            print += " = " + "null";
        }
        return print;
    }
}
