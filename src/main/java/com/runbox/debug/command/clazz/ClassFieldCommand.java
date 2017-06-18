package com.runbox.debug.command.clazz;

import java.util.List;
import java.util.LinkedList;
import java.util.Vector;
import java.util.regex.Pattern;

import com.sun.jdi.Field;
import com.sun.jdi.ByteValue;
import com.sun.jdi.CharValue;
import com.sun.jdi.ShortValue;
import com.sun.jdi.IntegerValue;
import com.sun.jdi.StringReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ArrayType;

import com.runbox.debug.command.clazz.ClassCommand;
import com.runbox.debug.manager.MachineManager;

import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.ArrayOperand;

public class ClassFieldCommand extends ClassCommand {

    public ClassFieldCommand(String command) throws Exception {
        super(command);
		if (null != argument()) {
			values = new Expression(argument()).execute();
			clazz = clazz(); field = field(); flags = flags();
			return;
		}
		throw new Exception("invalid operand");
    }

	private Expression.Values<Operand> values = null;	
	
    @Override
    public boolean execute() throws Exception {
        List<ReferenceType> types = MachineManager.instance().allClasses();
		System.out.printf("%-8s%s\n", "#", "field");
        int i = 0; for (ReferenceType type : types) {
			if (!(type instanceof ArrayType)) {
				if (type.name().equals(clazz)) {
					List<Field> fields = type.allFields();
					for (Field element : fields) {
						if (Pattern.compile(field).matcher(element.name()).matches()) {
							print(i++, element);
						}
					}
				}
			}
        }
        return super.execute();
    }			

	private static final int REGEX = 0;
	private static final int FLAGS = 1;
	private static final int MAX = 2;
	
	private String clazz = null;

	@Override
	protected String clazz() throws Exception {		
		if (null != values && REGEX < values.size()) {
			String value = values.getString(REGEX);			
			int index = value.lastIndexOf(".*");
			if (-1 != index) {
				index = value.lastIndexOf(".", index - 1); 
				if (-1 != index) {					
					return clazz(value.substring(0, index).trim());
				}
			} else {
				index = value.lastIndexOf("."); 
				if (-1 != index) {
					return clazz(value.substring(0, index).trim());
				}
			}
		}
		throw new Exception("invalid operand -> " + argument());
	}

	private String field = null;

	private String field() throws Exception {
		if (null != values && REGEX < values.size()) {
			String value = values.getString(REGEX);
			int index = value.lastIndexOf(".*");
			if (-1 != index) {
				index = value.lastIndexOf(".", index - 1);
				if (-1 != index)  {                
					return value.substring(index + 1).trim();
				}
			} else {
				index = value.lastIndexOf(".");
				if (-1 != index) {                
					return value.substring(index + 1).trim();
				}
			}
		}
		throw new Exception("invalid operand -> " + argument());
	}

	private static final int FLAG_PACKAGE   = 0x001;
	private static final int FLAG_ACCESS    = 0x002;
	private static final int FLAG_MODIFIER  = 0x004;
	private static final int FLAG_FINAL     = 0x008;
	private static final int FLAG_STATIC    = 0x010;
	private static final int FLAG_SYNTHETIC = 0x020;	
	private static final int FLAG_ENUM      = 0x040;
	private static final int FLAG_TRANSIENT = 0x080;
	private static final int FLAG_VOLATILE  = 0x100;
	private static final int FLAG_DECLARE   = 0x200;
	private static final int FLAG_TYPE      = 0x400;	
	
	private int flags = 0;
	
	private int flags() throws Exception {
		if (null != values && FLAGS < values.size()) {
			flags = values.getInteger(FLAGS);			
		}
		return flags;
	}

	private void print(int index, Field field) {
		System.out.printf("%-8s%s\n", String.valueOf(index), field.name());		
		final String FORMAT = "%-8s%-16s%s\n";
		if (FLAG_PACKAGE == (FLAG_PACKAGE & flags)) {
			System.out.printf(FORMAT, "", "package", String.valueOf(field.isPackagePrivate()));
		}
		if (FLAG_ACCESS == (FLAG_ACCESS & flags)) {
			System.out.printf(FORMAT, "", "access", access(field));
		}
		if (FLAG_MODIFIER == (FLAG_MODIFIER & flags)) {
		    System.out.printf(FORMAT, "", "modifier", String.valueOf(field.modifiers()));
		}
		if (FLAG_FINAL == (FLAG_FINAL & flags)) {
		    System.out.printf(FORMAT, "", "final", String.valueOf(field.isFinal()));
		}
		if (FLAG_STATIC == (FLAG_STATIC & flags)) {
		    System.out.printf(FORMAT, "", "static", String.valueOf(field.isStatic()));
		}
		if (FLAG_SYNTHETIC == (FLAG_SYNTHETIC & flags)) {
		    System.out.printf(FORMAT, "", "synthetic", String.valueOf(field.isSynthetic()));
		}
		if (FLAG_ENUM == (FLAG_ENUM & flags)) {
		    System.out.printf(FORMAT, "", "enum", String.valueOf(field.isEnumConstant()));
		}
		if (FLAG_TRANSIENT == (FLAG_TRANSIENT & flags)) {
		    System.out.printf(FORMAT, "", "transient", String.valueOf(field.isTransient()));
		}
		if (FLAG_VOLATILE == (FLAG_VOLATILE & flags)) {
		    System.out.printf(FORMAT, "", "volatile", String.valueOf(field.isVolatile()));
		}
		if (FLAG_DECLARE == (FLAG_DECLARE & flags)) {
		    System.out.printf(FORMAT, "", "declare", field.declaringType().name());
		}
		if (FLAG_TYPE == (FLAG_TYPE & flags)) {
		    System.out.printf(FORMAT, "", "type", field.typeName());
		}
	}

	private String access(Field field) {
		if (field.isPrivate()) {
			return "private";
		} else if (field.isProtected()) {
			return "protected";
		} else if (field.isPublic()) {
			return "public";
		}
		return "n/a";
	}		
}
