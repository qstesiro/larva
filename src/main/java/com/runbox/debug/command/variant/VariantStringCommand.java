package com.runbox.debug.command.variant;

import com.sun.jdi.Field;
import com.sun.jdi.ClassType;
import com.sun.jdi.IntegerValue;
import com.sun.jdi.ArrayReference;
import com.sun.jdi.ObjectReference;

import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.FieldOperand;
import com.runbox.debug.script.expression.token.operand.Operand;

public class VariantStringCommand extends VariantCommand {

    public VariantStringCommand(String command) throws Exception {
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
				printValues();
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
				if (string(values.get(OPERAND))) {
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
				if (standard(operand)) {
					String string = "instance of ";
					string += operand.type().name();
					string += "(" + "id=" + ((ObjectReference)operand.value()).uniqueID() + ")";
					System.out.printf("%-8s%s\n", "object:", string);
				} else {
					System.out.printf("%-8s%s\n", "object:", operand.value().toString());
				}	   
				System.out.printf("%-8s%d\n", "length:", length(operand));
				System.out.printf("%-8s%d\n", "size:", size(operand));
			} else {
				System.out.printf("%-8s%s\n", "value:", "null");
			}
		}
	}
	
    private void printValues() throws Exception {
		if (null != operand && null != operand.value()) {
			if (0 <= index && 0 <= count) {
				if (0 == count) count = size(operand) - index;
				if (index + count <= size(operand)) {
					ArrayReference array = array(operand); 			
					for (int i = index; i < (index + count); ++i) {
						System.out.print(array.getValue(i));
						printLine(i);
					}
					printLine();
				}
			}
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

    private ArrayReference array(Operand operand) throws Exception {
        if (standard(operand)) {
            return (ArrayReference)field(operand, "value").value();
        } else if (builder(operand)) {
            return (ArrayReference)field(operand, "value").value();
        } else if (buffer(operand)) {
            return (ArrayReference)field(operand, "value").value();
        }
        throw new Exception("invalid argument");
    }

	private int length(Operand operand) throws Exception {
		if (standard(operand)) {
            return array(operand).length();
        } else if (builder(operand)) {
            return array(operand).length();
        } else if (buffer(operand)) {
            return array(operand).length();
        }
        throw new Exception("invalid argument");
	}
	
	private int size(Operand operand) throws Exception {
		if (standard(operand)) {
			return ((ArrayReference)field(operand, "value").value()).length();
        } else if (builder(operand)) {
			return ((IntegerValue)field(operand, "count").value()).value();
        } else if (buffer(operand)) {
			return ((IntegerValue)field(operand, "count").value()).value();
		}
		throw new Exception("invalid operand");
	}   	

    private boolean string(Operand operand) throws Exception {
        boolean flag = clazz(operand, "java.lang.String");
        flag = flag || clazz(operand, "java.lang.StringBuilder");
        flag = flag || clazz(operand, "java.lang.StringBuffer");
        return flag;
    }

    private boolean standard(Operand operand) throws Exception {
        if (clazz(operand, "java.lang.String") && null != field(operand, "value")) {
			return true;
        }
        return false;
    }

    private boolean builder(Operand operand) throws Exception {
        if (clazz(operand, "java.lang.StringBuilder") && null != field(operand, "value")) {
			return true;
        }
        return false;
    }

    private boolean buffer(Operand operand) throws Exception {
        if (clazz(operand, "java.lang.StringBuffer") && null != field(operand, "value")) {            
			return true;
        }
        return false;
    }

    private boolean clazz(Operand operand, String name) throws Exception {
		if (null != operand) {
			if (null != operand.value()) {
				if (operand.value().type() instanceof ClassType) {
					if (operand.value().type().name().equals(name)) {
						return true;
					}
				}
			} else if (null != operand.type()) {				
				if (operand.type() instanceof ClassType) {
					if (operand.type().name().equals(name)) {
						return true;
					}
				}				
			}
		}        
        return false;
    }

    private FieldOperand field(Operand operand, String name) throws Exception {
        if (null != operand) {
			if (null != operand.value()) {
				ObjectReference object = (ObjectReference)operand.value();
				Field field = object.referenceType().fieldByName(name);
				return new FieldOperand(object, field.name());
			}
        }
        return null;
    }
}
