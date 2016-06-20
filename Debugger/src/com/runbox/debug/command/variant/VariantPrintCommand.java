package com.runbox.debug.command.variant;

import com.runbox.debug.parser.expression.Expression;
import com.runbox.debug.parser.expression.token.Token;
import com.runbox.debug.parser.expression.token.operand.*;
import com.runbox.debug.manager.ContextManager;
import com.sun.jdi.*;

/**
 * Created by qstesiro
 */
public class VariantPrintCommand extends VariantCommand {

    public VariantPrintCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid argument");
        }
    }

    @Override
    public boolean execute() throws Exception {
        if (null != argument) {
            Operand operand = new Expression(argument.trim()).handle();
            if (Token.isThis(operand.name())) {
                StackFrame frame = ContextManager.instance().frame();
                if (null != frame && null != frame.thisObject()) {
                    print(fields(new ConstOperand("this", frame.thisObject())));
                }
            } else if (Operand.subClass(operand) && !(operand instanceof ConstOperand)) {
                if (null == operand.value()) {
                    print(operand);
                } else if (!(operand.value().type() instanceof ClassType)) {
                    print(operand);
                } else {
                    print(fields(operand));
                }
            }
            return super.execute();
        }
        throw new Exception("invalid expression");
    }
}
