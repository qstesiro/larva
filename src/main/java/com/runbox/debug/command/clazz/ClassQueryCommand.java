package com.runbox.debug.command.clazz;

import java.util.List;
import java.util.LinkedList;
import java.util.Vector;
import java.util.regex.Pattern;

import com.sun.jdi.ByteValue;
import com.sun.jdi.CharValue;
import com.sun.jdi.ShortValue;
import com.sun.jdi.IntegerValue;
import com.sun.jdi.StringReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ArrayType;
import com.sun.jdi.AbsentInformationException;

import com.runbox.debug.manager.MachineManager;

import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.ArrayOperand;

public class ClassQueryCommand extends ClassCommand {
	
    public ClassQueryCommand(String command) throws Exception {
        super(command);
		if (null != argument()) {
			values = new Expression(argument()).execute();
			clazz = clazz(); flags = flags();
		}
    }

	private Expression.Values<Operand> values = null;
	
    @Override
    public boolean execute() throws Exception {
        if (null != clazz) {			
            List<ReferenceType> classes = MachineManager.instance().allClasses();
			System.out.printf(format(), arguments());
            int i = 0; for (ReferenceType type : classes) {
				if (!(type instanceof ArrayType)) {
					if (Pattern.compile(clazz).matcher(type.name()).matches()) {
						System.out.printf(format(), arguments(i++, type));
					}
				}
            }
        } else {
			List<ReferenceType> classes = MachineManager.instance().allClasses();
			int i = 0; System.out.printf(format(), arguments());
            for (ReferenceType type : classes) {
				if (!(type instanceof ArrayType)) {
					System.out.printf(format(), arguments(i++, type));
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
			String clazz = values.getString(REGEX);
			if (!clazz.equals("")) {
				return clazz(clazz);
			}
		}
		return null;
	}

	private static final int FLAG_PACKAGE     = 0x001;
	private static final int FLAG_ACCESS      = 0x002;
	private static final int FLAG_MODIFIER    = 0x004;
	private static final int FLAG_ABSTRACT    = 0x008;
	private static final int FLAG_FINAL       = 0x010;
	private static final int FLAG_INITIALIZED = 0x020;
	private static final int FLAG_PREPARED    = 0x040;
	private static final int FLAG_STATIC      = 0x080;
	private static final int FLAG_VERIFIED    = 0x100;
	private static final int FLAG_VERSION     = 0x200;
	private static final int FLAG_SOURCE      = 0x400;
	
	private int flags = 0;
	
	private int flags() throws Exception {
		if (null != values && FLAGS < values.size()) {
			flags = values.getInteger(FLAGS);
		}
		return flags;
	}

	private String format() {
		String format = "%-8s";
		if (FLAG_PACKAGE == (FLAG_PACKAGE & flags)) {
			format += "%-10s";
		}
		if (FLAG_ACCESS == (FLAG_ACCESS & flags)) {
			format += "%-12s";
		}
		if (FLAG_MODIFIER == (FLAG_MODIFIER & flags)) {
			format += "%-10s";
		}
		if (FLAG_ABSTRACT == (FLAG_ABSTRACT & flags)) {
			format += "%-10s";
		}
		if (FLAG_FINAL == (FLAG_FINAL & flags)) {
			format += "%-8s";
		}
		if (FLAG_INITIALIZED == (FLAG_INITIALIZED & flags)) {
			format += "%-12s";
		}
		if (FLAG_PREPARED == (FLAG_PREPARED & flags)) {
			format += "%-10s";
		}
		if (FLAG_STATIC == (FLAG_STATIC & flags)) {
			format += "%-8s";
		}
		if (FLAG_VERIFIED == (FLAG_VERIFIED & flags)) {
			format += "%-10s";
		}
		if (FLAG_VERSION == (FLAG_VERSION & flags)) {
			format += "%-10s";
		}
		if (FLAG_SOURCE == (FLAG_SOURCE & flags)) {
			format += "%-16s";
		}
		return format + "%s\n";
	}

	private Object[] arguments() {
		List<Object> objects = new LinkedList<Object>();
		objects.add("#");
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
		if (FLAG_INITIALIZED == (FLAG_INITIALIZED & flags)) {
			objects.add("initialized");
		}
		if (FLAG_PREPARED == (FLAG_PREPARED & flags)) {
			objects.add("prepare");
		}
		if (FLAG_STATIC == (FLAG_STATIC & flags)) {
			objects.add("static");
		}
		if (FLAG_VERIFIED == (FLAG_VERIFIED & flags)) {
			objects.add("verified");
		}
		if (FLAG_VERSION == (FLAG_VERSION & flags)) {
			objects.add("version");
		}
		if (FLAG_SOURCE == (FLAG_SOURCE & flags)) {
			objects.add("source");
		}
		objects.add("class");
		return objects.toArray();
	}
	
	private Object[] arguments(int index, ReferenceType type) {
		List<Object> objects = new LinkedList<Object>();
		objects.add(String.valueOf(index));
		if (FLAG_PACKAGE == (FLAG_PACKAGE & flags)) {
			objects.add(String.valueOf(type.isPackagePrivate()));
		}
		if (FLAG_ACCESS == (FLAG_ACCESS & flags)) {
			if (type.isPrivate()) {
				objects.add("private");
			} else if (type.isProtected()) {
				objects.add("protected");
			} else if (type.isPublic()) {
				objects.add("public");
			} else {
				objects.add("n/a");
			}
		}
		if (FLAG_MODIFIER == (FLAG_MODIFIER & flags)) {
			objects.add(String.valueOf(type.modifiers()));
		}		
		if (FLAG_ABSTRACT == (FLAG_ABSTRACT & flags)) {
			objects.add(String.valueOf(type.isAbstract()));
		}
		if (FLAG_FINAL == (FLAG_FINAL & flags)) {
			objects.add(String.valueOf(type.isFinal()));
		}
		if (FLAG_INITIALIZED == (FLAG_INITIALIZED & flags)) {
			objects.add(String.valueOf(type.isInitialized()));
		}
		if (FLAG_PREPARED == (FLAG_PREPARED & flags)) {
			objects.add(String.valueOf(type.isPrepared()));
		}
		if (FLAG_STATIC == (FLAG_STATIC & flags)) {			
			objects.add(String.valueOf(type.isStatic())); // ClassNotLoadedException
		}
		if (FLAG_VERIFIED == (FLAG_VERIFIED & flags)) {
			objects.add(String.valueOf(type.isVerified()));
		}
		if (FLAG_VERSION == (FLAG_VERSION & flags)) {
			objects.add(type.majorVersion() + "." + type.minorVersion());
		}
		if (FLAG_SOURCE == (FLAG_SOURCE & flags)) {
			try { 
				objects.add(type.sourceName());
			} catch (AbsentInformationException e) {
				objects.add("n/a");
			}
		}
		objects.add(type.name());
		return objects.toArray();
	}
}
