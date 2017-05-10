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
		int index = 0;
        List<ReferenceType> types = MachineManager.instance().allClasses();
		System.out.printf(format(), arguments());
        for (ReferenceType type : types) {
            if (Pattern.compile(clazz).matcher(type.name()).matches()) {
                List<Field> fields = type.allFields();
                for (Field item : fields) {
                    if (Pattern.compile(field).matcher(item.name()).matches()) {
                        System.out.printf(format(), arguments(index++, item));
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

	protected String field() throws Exception {
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
			if (FLAG_DECLARE == (FLAG_DECLARE & flags) &&
				FLAG_TYPE == (FLAG_TYPE & flags)) {
				throw new Exception("invalid flags combine");
			}			
		}
		return flags;
	}

	private String format() {
		String format = "%-5s" + "%-24s";
		if (FLAG_PACKAGE == (FLAG_PACKAGE & flags)) {
			format += "%-10s";
		}
		if (FLAG_ACCESS == (FLAG_ACCESS & flags)) {
			format += "%-12s";
		}
		if (FLAG_MODIFIER == (FLAG_MODIFIER & flags)) {
			format += "%-10s";
		}
		if (FLAG_FINAL == (FLAG_FINAL & flags)) {
			format += "%-8s";
		}
		if (FLAG_STATIC == (FLAG_STATIC & flags)) {
			format += "%-8s";
		}
		if (FLAG_SYNTHETIC == (FLAG_SYNTHETIC & flags)) {
			format += "%-12s";
		}
		if (FLAG_ENUM == (FLAG_ENUM & flags)) {
			format += "%-8s";
		}
		if (FLAG_TRANSIENT == (FLAG_TRANSIENT & flags)) {
			format += "%-12s";
		}
		if (FLAG_VOLATILE == (FLAG_VOLATILE & flags)) {
			format += "%-10s";
		}		
		if (FLAG_DECLARE == (FLAG_DECLARE & flags)) {
			format += "%s";
		}
		if (FLAG_TYPE == (FLAG_TYPE & flags)) {
			format += "%s";
		}
		return format + "\n";
	}

	private Object[] arguments() {
		List<Object> objects = new LinkedList<Object>();
		objects.add("#"); objects.add("field");
		if (FLAG_PACKAGE == (FLAG_PACKAGE & flags)) {
			objects.add("package");
		}
		if (FLAG_ACCESS == (FLAG_ACCESS & flags)) {
			objects.add("access");
		}
		if (FLAG_MODIFIER == (FLAG_MODIFIER & flags)) {
			objects.add("modifier");
		}
		if (FLAG_FINAL == (FLAG_FINAL & flags)) {
			objects.add("final");
		}
		if (FLAG_STATIC == (FLAG_STATIC & flags)) {
			objects.add("static");
		}
		if (FLAG_SYNTHETIC == (FLAG_SYNTHETIC & flags)) {
			objects.add("synthetic");
		}
		if (FLAG_ENUM == (FLAG_ENUM & flags)) {
			objects.add("enum");
		}
		if (FLAG_TRANSIENT == (FLAG_TRANSIENT & flags)) {
			objects.add("transient");
		}
		if (FLAG_VOLATILE == (FLAG_VOLATILE & flags)) {
			objects.add("volatile");
		}
		if (FLAG_DECLARE == (FLAG_DECLARE & flags)) {
			objects.add("declare");
		}				
		if (FLAG_TYPE == (FLAG_TYPE & flags)) {
			objects.add("type");
		}				
		return objects.toArray();
	}
	
	private Object[] arguments(int index, Field field) {
		List<Object> objects = new LinkedList<Object>();
		objects.add(String.valueOf(index));
		objects.add(field.name());
		if (FLAG_PACKAGE == (FLAG_PACKAGE & flags)) {
			objects.add(String.valueOf(field.isPackagePrivate()));
		}
		if (FLAG_ACCESS == (FLAG_ACCESS & flags)) {
			if (field.isPrivate()) {
				objects.add("private");
			} else if (field.isProtected()) {
				objects.add("protected");
			} else if (field.isPublic()) {
				objects.add("public");
			} else {
				objects.add("none");
			}
		}
		if (FLAG_MODIFIER == (FLAG_MODIFIER & flags)) {
			objects.add(String.valueOf(field.modifiers()));
		}
		if (FLAG_FINAL == (FLAG_FINAL & flags)) {
			objects.add(String.valueOf(field.isFinal()));
		}
		if (FLAG_STATIC == (FLAG_STATIC & flags)) {
			objects.add(String.valueOf(field.isStatic()));
		}
		if (FLAG_SYNTHETIC == (FLAG_SYNTHETIC & flags)) {
			objects.add(String.valueOf(field.isSynthetic()));
		}
		if (FLAG_ENUM == (FLAG_ENUM & flags)) {
			objects.add(String.valueOf(field.isEnumConstant()));
		}
		if (FLAG_TRANSIENT == (FLAG_TRANSIENT & flags)) {
			objects.add(String.valueOf(field.isTransient()));
		}
		if (FLAG_VOLATILE == (FLAG_VOLATILE & flags)) {
			objects.add(String.valueOf(field.isVolatile()));
		}
		if (FLAG_DECLARE == (FLAG_DECLARE & flags)) {
			objects.add(field.declaringType().name());
		}
		if (FLAG_TYPE == (FLAG_TYPE & flags)) {
			objects.add(field.typeName());
		}		
		return objects.toArray();
	}
}
