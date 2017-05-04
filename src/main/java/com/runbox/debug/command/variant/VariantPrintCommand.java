package com.runbox.debug.command.variant;

import com.sun.jdi.*;

import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.Token;
import com.runbox.debug.script.expression.token.operand.*;
import com.runbox.debug.manager.ContextManager;

public class VariantPrintCommand extends VariantCommand {

    public VariantPrintCommand(String command) throws Exception {
        super(command);
		if (null != argument()) {
			values = new Expression(argument()).execute();
			operand = operand(); flags = flags();
			return;
		}
		throw new Exception("invalid operand");				
    }

	private Expression.Values<Operand> values = null;		

	@Override
    public boolean execute() throws Exception {
        if (null != operand) {            
            print();
        }
		return super.execute();        
    }
	
	private static final int OPERAND = 0;
	private static final int FLAGS = 1;
	private static final int MAX = 2;
	
	private Operand operand = null;

	private Operand operand() throws Exception {
		if (null != values) {
			if (OPERAND < values.size() && MAX >= values.size()) {
				return values.get(OPERAND);
			}
		}
		throw new Exception("invalid operand");
	}
	
	private static final int FLAG_TYPE = 0x01;
	private static final int FLAG_VALUE_TYPE = 0x02;

	private int flags = 0;

	private int flags() throws Exception {
		if (null != values) {
			if (FLAGS < values.size() && MAX >= values.size()) {
				return values.getInteger(FLAGS);
			}
		}
		return 0;
	}	

	private void print() throws Exception {		
		if (null != operand.name()) {
			System.out.printf("%s", operand.name());		
			if (FLAG_TYPE == (FLAG_TYPE & flags)) {
				Type type = operand.type();
				System.out.print(" :" + (null != type ? type.name() : "none"));
			}
			System.out.printf(" = ");
		}		
		if (null != operand.value()) {							
			System.out.print(operand.value());			
			if (FLAG_VALUE_TYPE == (FLAG_VALUE_TYPE & flags)) {
				System.out.print(" :" + operand.value().type().name());
			}
		} else {
			System.out.print("null");
		}
		System.out.println();
	}
}
