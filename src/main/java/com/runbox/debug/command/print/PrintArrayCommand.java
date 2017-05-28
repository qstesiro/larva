package com.runbox.debug.command.print;

import com.sun.jdi.Value;
import com.sun.jdi.ArrayType;
import com.sun.jdi.StringReference;
import com.sun.jdi.ArrayReference;

import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;

public class PrintArrayCommand extends PrintCommand {

    public PrintArrayCommand(String command) throws Exception {
        super(command);
		if (null != argument()) {
			values = new Expression(argument()).execute();
			operand = operand();
			flags = flags();
			index = index();
			count = count();
			return;
		}
		throw new Exception("invalid operand");
    }

	private Expression.Values<Operand> values = null;
	
    @Override
    public boolean execute() throws Exception {
		if (null != operand) {
			if (FLAG_DEFAULT == (FLAG_DEFAULT & flags)) {
				printDefault();				
			}
			if (FLAG_VALUE == (FLAG_VALUE & flags)) {
				printValues();
			}
		}		        
        return super.execute();        
    }

	private static final int OPERAND = 0;
	private static final int FLAGS = 1;
	private static final int INDEX = 2;
	private static final int COUNT = 3;	
	private static final int MAX = 4;

	private Operand operand = null;

	private Operand operand() throws Exception {
		if (null != values) {
			if (OPERAND < values.size() && MAX >= values.size()) {
				if (values.get(OPERAND).type() instanceof ArrayType) {
					return values.get(OPERAND);
				}
			}
		}
		throw new Exception("invalid operand");
	}

	private static final int FLAG_DEFAULT = 0x01;
	private static final int FLAG_VALUE = 0x02;
   
	private int flags = FLAG_DEFAULT | FLAG_VALUE;

	private int flags() throws Exception {
		if (null != values) {
			if (FLAGS < values.size() && MAX >= values.size()) {
				return values.getInteger(FLAGS);
			}
		}
		return FLAG_DEFAULT | FLAG_VALUE;
	}
	
	private int index = 0;

	private int index() throws Exception {
		if (null != values) {
			if (INDEX < values.size() && MAX >= values.size()) {
				return values.getInteger(INDEX);
			}
		}
		return 0;
	}
	
	private int count = 0;
	
	private int count() throws Exception {
		if (null != values) {
			if (COUNT < values.size() && MAX >= values.size()) {
				return values.getInteger(COUNT);									
			}			
		}
		return 0;
	}

	private void printDefault() throws Exception {		
		System.out.printf("%-8s%s\n", "type:", ((ArrayType)operand.type()).componentType().name());
		if (null != operand.value()) {
			System.out.printf("%-8s%s\n", "vtype:", operand.value().type().name());
			System.out.printf("%-8s%s\n", "object:", operand.value());
			System.out.printf("%-8s%d\n", "length:", ((ArrayReference)operand.value()).length());
		} else {
			System.out.printf("%-8s%s\n", "value:", "null");
		}
	}
	
    private void printValues() throws Exception {
		if (null != operand && null != operand.value()) {		   
			if (0 <= index && 0 <= count) {
				ArrayReference array = (ArrayReference)operand.value();
				if (0 == count) count = array.length() - index;
				if (index + count <= array.length()) {
					for (int i = index; i < (index + count); ++i) {
						System.out.printf("#%-5d", i);
						Value value = array.getValue(i);
						if (null != value) {
							if (value instanceof StringReference) {
								String string = "instance of ";
								string += value.type().name();
								string += "(" + "id=" + ((StringReference)value).uniqueID() + ")";
								System.out.print(string);
							} else {
								System.out.print(value);
							}
						} else {
							System.out.print("null");
						}
						System.out.println();
					}
				}
			}
		}
    }
}
