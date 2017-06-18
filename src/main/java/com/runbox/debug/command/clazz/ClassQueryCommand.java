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
			System.out.printf("%-8s%s\n", "#", "class");
            int i = 0; for (ReferenceType type : classes) {
				if (!(type instanceof ArrayType)) {
					if (Pattern.compile(clazz).matcher(type.name()).matches()) {						
						print(i++, type);
					}
				}
            }
        } else {
			List<ReferenceType> classes = MachineManager.instance().allClasses();
			System.out.printf("%-8s%s\n", "#", "class");
            int i = 0; for (ReferenceType type : classes) {
				if (!(type instanceof ArrayType)) {					
					print(i++, type);
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
				return clazz;
			}
		}
		return null;
	}

	private static final int FLAG_PACKAGE     = 0x0001;
	private static final int FLAG_ACCESS      = 0x0002;
	private static final int FLAG_MODIFIER    = 0x0004;
	private static final int FLAG_ABSTRACT    = 0x0008;
	private static final int FLAG_FINAL       = 0x0010;
	private static final int FLAG_INITIALIZED = 0x0020;
	private static final int FLAG_PREPARED    = 0x0040;
	private static final int FLAG_STATIC      = 0x0080;
	private static final int FLAG_VERIFIED    = 0x0100;
	private static final int FLAG_VERSION     = 0x0200;
	private static final int FLAG_SOURCE      = 0x0400;
	private static final int FLAG_INSTANCE    = 0x0800;
	private static final int FLAG_LOADER      = 0x1000;
	private static final int FLAG_CLASS       = 0x2000;
	
	private int flags = 0;
	
	private int flags() throws Exception {
		if (null != values && FLAGS < values.size()) {
			flags = values.getInteger(FLAGS);
		}
		return flags;
	}

	private void print(int index, ReferenceType type) throws Exception {
		System.out.printf("%-8s%s\n", String.valueOf(index), type.name());		
		final String FORMAT = "%-8s%-16s%s\n";
		if (FLAG_PACKAGE == (FLAG_PACKAGE & flags)) {
			System.out.printf(FORMAT, "", "package", String.valueOf(type.isPackagePrivate()));
		}
		if (FLAG_ACCESS == (FLAG_ACCESS & flags)) {
			System.out.printf(FORMAT, "", "access", access(type));
		}
		if (FLAG_MODIFIER == (FLAG_MODIFIER & flags)) {
			System.out.printf(FORMAT, "", "modifier", String.valueOf(type.modifiers()));
		}
		if (FLAG_ABSTRACT == (FLAG_ABSTRACT & flags)) {
			System.out.printf(FORMAT, "", "abstract", String.valueOf(type.isAbstract()));
		}
		if (FLAG_FINAL == (FLAG_FINAL & flags)) {
			System.out.printf(FORMAT, "", "final", String.valueOf(type.isFinal()));
		}
		if (FLAG_INITIALIZED == (FLAG_INITIALIZED & flags)) {
		    System.out.printf(FORMAT, "", "initialized", String.valueOf(type.isInitialized()));
		}
		if (FLAG_PREPARED == (FLAG_PREPARED & flags)) {
		    System.out.printf(FORMAT, "", "prepared", String.valueOf(type.isPrepared()));
		}
		if (FLAG_STATIC == (FLAG_STATIC & flags)) {			
		    System.out.printf(FORMAT, "", "static", String.valueOf(type.isStatic())); // ClassNotLoadedException
		}
		if (FLAG_VERIFIED == (FLAG_VERIFIED & flags)) {
		    System.out.printf(FORMAT, "", "verified", String.valueOf(type.isVerified()));
		}
		if (FLAG_VERSION == (FLAG_VERSION & flags)) {
		    System.out.printf(FORMAT, "", "version", type.majorVersion() + "." + type.minorVersion());
		}
		if (FLAG_SOURCE == (FLAG_SOURCE & flags)) {
			System.out.printf(FORMAT, "", "source", source(type));
		}
	}

	private String access(ReferenceType type) {
		if (type.isPrivate()) {
			return "private";
		} else if (type.isProtected()) {
			return "protected";
		} else if (type.isPublic()) {
			return "public";
		}
		return "n/a";
	}

	private String source(ReferenceType type) {
		try {
			return type.sourceName();
		} catch (AbsentInformationException e) {
			return "n/a";
		}
	}
}
