package com.runbox.debug.command.print;

import com.sun.jdi.Field;
import com.sun.jdi.ClassType;
import com.sun.jdi.IntegerValue;
import com.sun.jdi.ArrayReference;
import com.sun.jdi.ObjectReference;

import com.runbox.debug.manager.MachineManager;

import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.FieldOperand;
import com.runbox.debug.script.expression.token.operand.Operand;

public class PrintStringCommand extends PrintCommand {

    public PrintStringCommand(String command) throws Exception {
        super(command);
		if (null != argument()) {
			values = new Expression(argument()).execute();			
			operand = operand();
			flags = flags();
			index = index();
			count = count();
			line = line();
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
				printValue();
			}			
		}        
        return super.execute();
    }
	
	private static final int OPERAND = 0;
	private static final int FLAGS = 1;
	private static final int INDEX = 2;
	private static final int COUNT = 3;
	private static final int LINE = 4;
	private static final int MAX = 5;
	
	private Operand operand = null;

	private Operand operand() throws Exception {
		if (null != values) {
			if (OPERAND < values.size() && MAX >= values.size()) {				
				if (type(values.get(OPERAND))) {
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

	private int line = 0;

	private int line() throws Exception {
		if (null != values) {
			if (LINE < values.size() && MAX >= values.size()) {
				return values.getInteger(LINE);									
			}			
		}
		return 0;
	}

	private void printDefault() throws Exception {
		if (null != operand) {
			System.out.printf("%-8s%s\n", "type:", operand.type().name());
			if (null != operand.value()) {
				System.out.printf("%-8s%s\n", "vtype:", operand.value().type().name());
				System.out.printf("%-8s%s\n", "object:", object(operand));
				System.out.printf("%-8s%d\n", "length:", length(operand));
				System.out.printf("%-8s%d\n", "size:", size(operand));
			} else {
				System.out.printf("%-8s%s\n", "value:", "null");
			}
		}
	}
			
    private void printValue() throws Exception {
		if (null != operand && null != operand.value()) {
			if (0 <= index && 0 <= count) {
				if (0 == count) count = size(operand) - index;
				if (index + count <= size(operand)) {					
					if (MachineManager.instance().dalvik() &&
						clazz(operand, DEFAULT_STRING)) {
						printValue(stringValue(operand)); return;
					}
					printValue(arrayValue(operand));
				}
			}
		}
    }

	private void printValue(String string) throws Exception {
		if (null != string) {
			for (int i = 0; i < count; ++i) {
				System.out.print(string.charAt(i + index));
				printLine(i);
			}
			printLine();
		}
	}

	private void printValue(ArrayReference array) throws Exception {
		if (null != array) {
			for (int i = 0; i < count; ++i) {
				System.out.print(array.getValue(i + index));
				printLine(i);
			}
			printLine();
		}
	}

	private void printLine(int i) {
		if (0 != line) {
			if (0 == (i + 1) % line && index != i) {
				System.out.println();
			}
		}
	}

	private void printLine() {
		if (0 != line) {
			if (0 != count % line) {
				System.out.println();
			}
		} else {
			System.out.println();
		}			
	}	

	private static final String DEFAULT_STRING = "java.lang.String";
	private static final String BUILDER_STRING = "java.lang.StringBuilder";
	private static final String BUFFER_STRING = "java.lang.StringBuffer";

	private String stringValue(Operand operand) throws Exception {
		if (clazz(operand, DEFAULT_STRING)) {
			if (MachineManager.instance().dalvik()) {
				return operand.value().toString();
			}
		}
		throw new Exception("invalid type");
	}

	private ArrayReference arrayValue(Operand operand) throws Exception {
		if (clazz(operand, DEFAULT_STRING)) {
			if (!MachineManager.instance().dalvik()) {
				return field(operand, "value").arrayValue();
			}
		} else if (clazz(operand, BUILDER_STRING)) {
            return field(operand, "value").arrayValue();
        } else if (clazz(operand, BUFFER_STRING)) {
            return field(operand, "value").arrayValue();
        }
		throw new Exception("invalid argument");
	}       

	private int length(Operand operand) throws Exception {
		if (clazz(operand, DEFAULT_STRING)) {		
			if (MachineManager.instance().dalvik()) {
				return stringValue(operand).length();
			}
			return arrayValue(operand).length();
		} else if (clazz(operand, BUILDER_STRING)) {
			return arrayValue(operand).length();
        } else if (clazz(operand, BUFFER_STRING)) {
            return arrayValue(operand).length();
        }
        throw new Exception("invalid argument");
	}
	
	private int size(Operand operand) throws Exception {
		if (clazz(operand, DEFAULT_STRING)) {
			if (MachineManager.instance().dalvik()) {
				return stringValue(operand).length();
			}
			return arrayValue(operand).length();
        } else if (clazz(operand, BUILDER_STRING)) {
			return field(operand, "count").intValue();
        } else if (clazz(operand, BUFFER_STRING)) {
			return field(operand, "count").intValue();
		}
		throw new Exception("invalid operand");
	}   	

    private boolean type(Operand operand) throws Exception {
        if (clazz(operand, DEFAULT_STRING) ||
			clazz(operand, BUILDER_STRING) ||
			clazz(operand, BUFFER_STRING)) {
			return true;
		}
        return false;
    }            
}
