package com.runbox.debug.command.variant;

import com.runbox.debug.parser.expression.Expression;
import com.runbox.debug.parser.expression.token.operand.FieldOperand;
import com.runbox.debug.parser.expression.token.operand.Operand;
import com.sun.jdi.ArrayReference;
import com.sun.jdi.ClassType;
import com.sun.jdi.Field;
import com.sun.jdi.ObjectReference;

/**
 * Created by huangmengmeng01 on 2016/6/23.
 */
public class VariantStringCommand extends VariantCommand {

    public VariantStringCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid argument");
        }
    }

    @Override
    public boolean execute() throws Exception {
        int index = argument.lastIndexOf(" ");
        if (-1 != index) {
            String expression = argument.substring(0, index).trim();
            int count = Integer.valueOf(argument.substring(index + 1, argument.length()).trim());
            if (0 < count) {
                Operand operand = new Expression(expression).handle();
                if (string(operand)) {
                    print(array(operand), count);
                }
            }
        } else {
            Operand operand = new Expression(argument.trim()).handle();
            if (string(operand)) {
                print(array(operand), 0);
            }
        }
        return super.execute();
    }

    private void print(Operand operand, int count) throws Exception {
        if (null != operand && 0 < count) {
            ArrayReference array = (ArrayReference)operand.value();
            for (int i = 0; i < array.length(); ++i) {
                System.out.print(array.getValue(i));
                if (0 == (i + 1) % count && 0 != i && array.length() != (i + 1)) {
                    System.out.println();
                }
            }
            System.out.println();
        } else if (0 == count) {
            ArrayReference array = (ArrayReference)operand.value();
            for (int i = 0; i < array.length(); ++i) {
                System.out.print(array.getValue(i));
            }
            System.out.println();
        }
    }

    private FieldOperand array(Operand operand) throws Exception {
        if (standard(operand)) {
            return field(operand, "value");
        } else if (builder(operand)) {
            return field(operand, "value");
        } else if (buffer(operand)) {
            return field(operand, "value");
        }
        throw new Exception("invalid argument");
    }

    private boolean string(Operand operand) throws Exception {
        boolean flag = clazz(operand, "java.lang.String");
        flag = flag || clazz(operand, "java.lang.StringBuilder");
        flag = flag || clazz(operand, "java.lang.StringBuffer");
        return flag;
    }

    private boolean standard(Operand operand) throws Exception {
        if (clazz(operand, "java.lang.String")) {
            if (null != field(operand, "value")) {
                return true;
            }
        }
        return false;
    }

    private boolean builder(Operand operand) throws Exception {
        if (clazz(operand, "java.lang.StringBuilder")) {
            if (null != field(operand, "value")) {
                return true;
            }
        }
        return false;
    }

    private boolean buffer(Operand operand) throws Exception {
        if (clazz(operand, "java.lang.StringBuffer")) {
            if (null != field(operand, "value")) {
                return true;
            }
        }
        return false;
    }

    private boolean clazz(Operand operand, String name) throws Exception {
        if (null != operand.value() && operand.value().type() instanceof ClassType) {
            if (operand.value().type().name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    protected FieldOperand field(Operand operand, String name) throws Exception {
        if (null != operand) {
            ObjectReference object = (ObjectReference)operand.value();
            Field field = object.referenceType().fieldByName(name);
            return new FieldOperand(object, field.name());
        }
        return null;
    }
}
