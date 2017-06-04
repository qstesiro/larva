package com.runbox.debug.command.print;

import java.util.List;

import com.sun.jdi.Type;
import com.sun.jdi.StringReference;
import com.sun.jdi.ObjectReference;

import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;

public class PrintAutoCommand extends PrintCommand {

	public PrintAutoCommand(String command) throws Exception {
		super(command);
		if (null != argument()) {
			flags = new Expression(argument()).execute().getInteger(0);
		}
	}

	@Override
	public boolean execute() throws Exception {
		print(autos());		
		return super.execute();
	}

	private static final int FLAG_TYPE = 0x01;
	private static final int FLAG_VALUE_TYPE = 0x02;

	private int flags = 0;
	
	private void print(List<Operand> operands) throws Exception {
		int i = 0; for (Operand operand : operands) {
			System.out.printf("#%-5d", i++);
			print(operand);
			System.out.println();
		}
	}

	private void print(Operand operand) throws Exception {
		System.out.printf("%s", operand.name());
		if (FLAG_TYPE == (FLAG_TYPE & flags)) {
			Type type = operand.type();
			System.out.print(" :" + (null != type.name() ? type.name() : "n/a"));			
		}		
		System.out.printf(" = ");
		if (null != operand.value()) {				
			if (operand.value() instanceof StringReference) {
				String string = "instance of ";
				string += operand.type().name();
				string += "(" + "id=" + ((ObjectReference)operand.value()).uniqueID() + ")";
				System.out.print(string);
			} else {
				System.out.print(operand.value());
			}
			if (FLAG_VALUE_TYPE == (FLAG_VALUE_TYPE & flags)) {
				System.out.print(" :" + operand.value().type().name());
			}
		} else {
			System.out.print("null");
		}
	}
}
