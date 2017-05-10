package com.runbox.debug.command.clazz;

import java.util.List;
import java.util.LinkedList;
import java.util.Vector;
import java.util.regex.Pattern;

import com.sun.jdi.Method;
import com.sun.jdi.ByteValue;
import com.sun.jdi.CharValue;
import com.sun.jdi.ShortValue;
import com.sun.jdi.IntegerValue;
import com.sun.jdi.StringReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.AbsentInformationException;

import com.runbox.debug.command.clazz.ClassCommand;
import com.runbox.debug.manager.MachineManager;

import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.ArrayOperand;

public class ClassMethodCommand extends ClassCommand {

    public ClassMethodCommand(String command) throws Exception {
        super(command);
		if (null != argument()) {
			values = new Expression(argument()).execute();
			clazz = clazz(); method = method(); flags = flags();
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
                List<Method> methods = type.allMethods();
                for (Method item : methods) {
                    if (Pattern.compile(method).matcher(item.name()).matches()) {
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

	private String method = null;

	private String method() throws Exception {
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

	private static final int FLAG_PACKAGE      = 0x00001;
	private static final int FLAG_ACCESS       = 0x00002;
	private static final int FLAG_MODIFIER     = 0x00004;
	private static final int FLAG_ABSTRACT     = 0x00008;
	private static final int FLAG_FINAL        = 0x00010;
	private static final int FLAG_STATIC       = 0x00020;
	private static final int FLAG_SYNTHETIC    = 0x00040;	
	private static final int FLAG_BRIDGE       = 0x00080;
	private static final int FLAG_NATIVE       = 0x00100;		
	private static final int FLAG_SYNCHRONIZED = 0x00200;
	private static final int FLAG_CONSTRUCTOR  = 0x00400;
	private static final int FLAG_INITIALIZER  = 0x00800;
	private static final int FLAG_VARIABLE     = 0x01000;	
	private static final int FLAG_LINE         = 0x02000;
	private static final int FLAG_DECLARE      = 0x04000;
	private static final int FLAG_RETURN       = 0x08000;
	
	private int flags = 0;
	
	private int flags() throws Exception {
		if (null != values && FLAGS < values.size()) {
			flags = values.getInteger(FLAGS);
			if (FLAG_DECLARE == (FLAG_DECLARE & flags) &&
				FLAG_RETURN == (FLAG_RETURN & flags)) {
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
			format += "%-12s";
		}
		if (FLAG_ABSTRACT == (FLAG_ABSTRACT & flags)) {
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
		if (FLAG_BRIDGE == (FLAG_BRIDGE & flags)) {
			format += "%-8s";
		}
		if (FLAG_NATIVE == (FLAG_NATIVE & flags)) {
			format += "%-8s";
		}
		if (FLAG_SYNCHRONIZED == (FLAG_SYNCHRONIZED & flags)) {
			format += "%-16s";
		}
		if (FLAG_CONSTRUCTOR == (FLAG_CONSTRUCTOR & flags)) {
			format += "%-16s";
		}
		if (FLAG_INITIALIZER == (FLAG_INITIALIZER & flags)) {
			format += "%-16s";
		}
		if (FLAG_VARIABLE == (FLAG_VARIABLE & flags)) {
			format += "%-10s";
		}
		if (FLAG_LINE == (FLAG_LINE & flags)) {
			format += "%-8s";
		}		
		if (FLAG_DECLARE == (FLAG_DECLARE & flags)) {
			format += "%s";
		}
		if (FLAG_RETURN == (FLAG_RETURN & flags)) {
			format += "%s";
		}
		return format + "\n";
	}

	private Object[] arguments() {
		List<Object> objects = new LinkedList<Object>();
		objects.add("#"); objects.add("method");
		if (FLAG_PACKAGE == (FLAG_PACKAGE & flags)) {
			objects.add("package");
		}
		if (FLAG_ACCESS == (FLAG_ACCESS & flags)) {
			objects.add("access");
		}
		if (FLAG_MODIFIER == (FLAG_MODIFIER & flags)) {
			objects.add("modifier");
		}
		if (FLAG_ABSTRACT == (FLAG_ABSTRACT & flags)) {
			objects.add("abstract");
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
		if (FLAG_BRIDGE == (FLAG_BRIDGE & flags)) {
			objects.add("bridge");
		}
		if (FLAG_NATIVE == (FLAG_NATIVE & flags)) {
			objects.add("native");
		}
		if (FLAG_SYNCHRONIZED == (FLAG_SYNCHRONIZED & flags)) {
			objects.add("synchronized");
		}
		if (FLAG_CONSTRUCTOR == (FLAG_CONSTRUCTOR & flags)) {
			objects.add("constructor");
		}
		if (FLAG_INITIALIZER == (FLAG_INITIALIZER & flags)) {
			objects.add("initializer");
		}
		if (FLAG_VARIABLE == (FLAG_VARIABLE & flags)) {
			objects.add("varargs");
		}
		if (FLAG_LINE == (FLAG_LINE & flags)) {
			objects.add("line");
		}
		if (FLAG_DECLARE == (FLAG_DECLARE & flags)) {
			objects.add("declare");
		}
		if (FLAG_RETURN == (FLAG_RETURN & flags)) {
			objects.add("return");
		}		
		return objects.toArray();
	}
	
	private Object[] arguments(int index, Method method) {
		List<Object> objects = new LinkedList<Object>();
		objects.add(String.valueOf(index));
		objects.add(method.name());
		if (FLAG_PACKAGE == (FLAG_PACKAGE & flags)) {
			objects.add(String.valueOf(method.isPackagePrivate()));
		}
		if (FLAG_ACCESS == (FLAG_ACCESS & flags)) {
			if (method.isPrivate()) {
				objects.add("private");
			} else if (method.isProtected()) {
				objects.add("protected");
			} else if (method.isPublic()) {
				objects.add("public");
			} else {
				objects.add("none");
			}
		}
		if (FLAG_MODIFIER == (FLAG_MODIFIER & flags)) {
			objects.add(String.valueOf(method.modifiers()));
		}
		if (FLAG_ABSTRACT == (FLAG_ABSTRACT & flags)) {
			objects.add(String.valueOf(method.isAbstract()));
		}
		if (FLAG_FINAL == (FLAG_FINAL & flags)) {
			objects.add(String.valueOf(method.isFinal()));
		}
		if (FLAG_STATIC == (FLAG_STATIC & flags)) {
			objects.add(String.valueOf(method.isStatic()));
		}
		if (FLAG_SYNTHETIC == (FLAG_SYNTHETIC & flags)) {
			objects.add(String.valueOf(method.isSynthetic()));
		}
		if (FLAG_BRIDGE == (FLAG_BRIDGE & flags)) {
			objects.add(String.valueOf(method.isBridge()));
		}
		if (FLAG_NATIVE == (FLAG_NATIVE & flags)) {
			objects.add(String.valueOf(method.isNative()));
		}
		if (FLAG_SYNCHRONIZED == (FLAG_SYNCHRONIZED & flags)) {
			objects.add(String.valueOf(method.isSynchronized()));
		}
		if (FLAG_CONSTRUCTOR == (FLAG_CONSTRUCTOR & flags)) {
			objects.add(String.valueOf(method.isConstructor()));
		}
		if (FLAG_INITIALIZER == (FLAG_INITIALIZER & flags)) {
			objects.add(String.valueOf(method.isStaticInitializer()));
		}
		if (FLAG_VARIABLE == (FLAG_VARIABLE & flags)) {
			objects.add(String.valueOf(method.isVarArgs()));
		}
		if (FLAG_LINE == (FLAG_LINE & flags)) {
			if (method.isAbstract()) {
				objects.add("none");
			} else {
				int line = method.location().lineNumber();
				objects.add(String.valueOf((-1 == line) ? "none" : line));
			}			
		}
		if (FLAG_DECLARE == (FLAG_DECLARE & flags)) {
			objects.add(method.declaringType().name());
		}			
		if (FLAG_RETURN == (FLAG_RETURN & flags)) {
			objects.add(method.returnTypeName());
		}				
		return objects.toArray();
	}
}
