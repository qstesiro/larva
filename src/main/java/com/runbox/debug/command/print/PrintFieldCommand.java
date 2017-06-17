package com.runbox.debug.command.print;

import java.util.List;

import com.sun.jdi.Type;
import com.sun.jdi.StringReference;
import com.sun.jdi.ObjectReference;

import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;

public class PrintFieldCommand extends PrintCommand {

    public PrintFieldCommand(String command) throws Exception {
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
		if (FLAG_DEFAULT == (FLAG_DEFAULT & flags)) {
			print();
		}
		if (FLAG_FIELD == (FLAG_FIELD & flags)) {
			print(fields(operand));
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

	private static final int FLAG_DEFAULT = 0x01;
	private static final int FLAG_FIELD = 0x02;
	private static final int FLAG_TYPE = 0x04;
	private static final int FLAG_VALUE_TYPE = 0x08;

	private int flags = 0;

	private int flags() throws Exception {
		if (null != values) {
			if (FLAGS < values.size() && MAX >= values.size()) {
				return values.getInteger(FLAGS);
			}
		}
		return FLAG_DEFAULT | FLAG_FIELD;
	}

	private void print() throws Exception {
		if (null != operand) {
			System.out.printf("%-8s%s\n", "type:", (null != operand.type() ? operand.type() : "n/a"));
			if (null != operand().value()) {
				System.out.printf("%-8s%s\n", "vtype:", operand.value().type().name());				
				System.out.printf("%-8s%s\n", "object:", object(operand));
				System.out.printf("%-8s%s\n", "fields:", fields(operand).size());
			} else {
				System.out.printf("%-8s%s\n", "value:", "null");
			}
		}
	}
	
	private void print(List<Operand> operands) throws Exception {
		if (null != operands) {
			int i = 0; for (Operand operand : operands) {
				System.out.printf("#%-7d", i++);
				print(operand);
				System.out.println();
			}
		}
	}

	private void print(Operand operand) throws Exception {
		if (null != operand) {
			System.out.printf("%s", operand.name());
			if (FLAG_TYPE == (FLAG_TYPE & flags)) {
				Type type = operand.type();
				System.out.print(" :" + (null != type ? type.name() : "n/a"));			
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
}
