package com.runbox.debug.command.variant;

import com.runbox.debug.parser.expression.Expression;
import com.runbox.debug.parser.expression.token.operand.Operand;
import com.sun.jdi.ArrayReference;

/**
 * Created by huangmengmeng01 on 2016/6/23.
 */
public class VariantArrayCommand extends VariantCommand {

    public VariantArrayCommand(String command) throws Exception {
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
                if (array(operand)) {
                    print(operand, count);
                }
            }
        } else {
            Operand operand = new Expression(argument.trim()).handle();
            if (array(operand)) {
                print(operand, 1);
            }
        }
        return super.execute();
    }

    private void print(Operand operand, int count) throws Exception {
        if (null != operand && 0 < count) {
            ArrayReference array = (ArrayReference)operand.value();
            for (int i = 0; i < array.length(); ++i) {
                if (0 == i % count && 0 != i) {
                    System.out.println();
                }
                if (0 == i % count) {
                    System.out.print("[" + i + "]" + "\t");
                }
                System.out.print(array.getValue(i));
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private boolean array(Operand operand) throws Exception {
        if (null != operand.value()) {
            return (operand.value() instanceof ArrayReference);
        }
        return false;
    }
}
