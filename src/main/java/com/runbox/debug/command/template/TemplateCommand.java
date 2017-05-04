package com.runbox.debug.command.template;

import java.util.List;
import java.util.Vector;

import com.sun.jdi.*;

import com.runbox.debug.command.Command;
import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.ConstOperand;
import com.runbox.debug.script.expression.token.operand.FieldOperand;
import com.runbox.debug.script.expression.token.operand.Operand;

public class TemplateCommand extends Command {

    public TemplateCommand(String command) throws Exception {
        super(command);
		if (null != argument()) {
			values = new Expression(argument()).execute();
			operand = operand();
			flags = flags();
			return;
		}
		throw new Exception("invalid operand");
    }

	private Expression.Values<Operand> values = null;

	@Override
    public boolean execute() throws Exception {       
		if (FLAG_DEFAULT == (FLAG_DEFAULT & flags())) {
			printDefault();
		}
		if (FLAG_ELEMENT == (FLAG_ELEMENT & flags())) {
			printElements();
		}
        return super.execute();
    }    

	protected void printDefault() throws Exception {		
		if (null != operand) {			
			System.out.printf("%-8s%s\n", "type:", operand.type().name());
			if (null != operand.value()) {
				System.out.printf("%-8s%s\n", "vtype:", operand.value().type().name());
				System.out.printf("%-8s%s\n", "object:", operand.value());
				System.out.printf("%-8s%d\n", "size:", elements().size());
			} else {
				System.out.printf("%-8s%s\n", "value:", "null");
			}
		}
	}
	
	protected void printElements() throws Exception {
		if (null != operand) {
			int i = 0; for (Operand operand : elements()) {
				System.out.printf("#%-7d", i++);
				if (null != operand.value()) {
					if (operand.value() instanceof StringReference) {
						String string = "instance of ";
						string += operand.type().name();
						string += "(" + "id=" + ((ObjectReference)operand.value()).uniqueID() + ")";
						System.out.print(string);
					} else {
						System.out.print(operand.value());
					}
					if (FLAG_ELEMENT_TYPE == (FLAG_ELEMENT_TYPE & flags)) {
						System.out.print(" :" + operand.value().type().name());
					}
				} else {
					System.out.print("null");
				}
				System.out.println();
			}
		}
	}
	
	private static final int OPERAND = 0;
	private static final int FLAGS = 1;
	private static final int MAX = 2;

	private Operand operand = null;

	protected Operand operand() throws Exception {
		if (null == operand) {
			if (null != values) {			
				if (OPERAND < values.size() && MAX >= values.size()) {
					if (values.get(OPERAND).type() instanceof ClassType ||
						values.get(OPERAND).type() instanceof InterfaceType) {
						return values.get(OPERAND);
					}
				}
			}
			throw new Exception("invalid operand");
		}
		return operand;
	}

	protected static final int FLAG_DEFAULT = 0x01;
	protected static final int FLAG_ELEMENT = 0x02;
	protected static final int FLAG_ELEMENT_TYPE = 0x04;
   
	private int flags = 0;

	protected int flags() throws Exception {
		if (0 == flags) {
			if (null != values) {
				if (FLAGS < values.size() && MAX >= values.size()) {
					return values.getInteger(FLAGS);
				}
			}
			return FLAG_DEFAULT | FLAG_ELEMENT;
		}
		return flags;
	}
	
    protected boolean superClass(String name) throws Exception {
		if (null != operand) {
			if (null != operand.value()) {
				if (operand.value().type() instanceof ClassType) {
					if (((ClassType)operand.value().type()).superclass().name().equals(name)) {
						return true;
					}
				}
			} else if (null != operand.type()) {
				if (operand.type() instanceof ClassType) {
					if (((ClassType)operand.type()).superclass().name().equals(name)) {
						return true;
					}
				}
			}
		}
        return false;
    }

	protected List<Operand> elements() throws Exception {
		return null;
	}
	
    protected boolean exist(String name) throws Exception {
        return (null != field(name));
    }

    protected Operand field(String name) throws Exception {
        if (null != operand && null != operand.value()) {
            ObjectReference object = (ObjectReference)operand.value();
            Field field = object.referenceType().fieldByName(name);
            if (null != field) {
                return new FieldOperand(object, field.name());
            }
        }
        return null;
    }
   
    protected String format(Operand operand) throws Exception {
        String print = "";
        if (null != operand.value()) {
            print += operand.value();
            if (operand.value() instanceof ObjectReference) {
                print += " :" + operand.value().type().name();
            }
        } else {
            print += "null";
        }
        return print;
    }	
}
