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
import com.sun.jdi.ArrayType;
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
        List<ReferenceType> types = MachineManager.instance().allClasses();
		System.out.printf("%-8s%s\n", "#", "method");
        int i = 0; for (ReferenceType type : types) {
			if (!(type instanceof ArrayType)) {
				if (type.name().equals(clazz)) {
					List<Method> methods = type.allMethods();
					for (Method element : methods) {
						if (Pattern.compile(method).matcher(element.name()).matches()) {
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
		}
		return flags;
	}

	private void print(int index, Method method) {
		System.out.printf("%-8s%s\n", String.valueOf(index), method.name());
		final String FORMAT = "%-8s%-16s%s\n";
		if (FLAG_PACKAGE == (FLAG_PACKAGE & flags)) {
			System.out.printf(FORMAT, "", "package", String.valueOf(method.isPackagePrivate()));
		}
		if (FLAG_ACCESS == (FLAG_ACCESS & flags)) {
			System.out.printf(FORMAT, "", "access", access(method));
		}
		if (FLAG_MODIFIER == (FLAG_MODIFIER & flags)) {
			System.out.printf(FORMAT, "", "modifier", String.valueOf(method.modifiers()));
		}
		if (FLAG_ABSTRACT == (FLAG_ABSTRACT & flags)) {
		    System.out.printf(FORMAT, "", "abstract", String.valueOf(method.isAbstract()));
		}
		if (FLAG_FINAL == (FLAG_FINAL & flags)) {
		    System.out.printf(FORMAT, "", "final", String.valueOf(method.isFinal()));
		}
		if (FLAG_STATIC == (FLAG_STATIC & flags)) {
		    System.out.printf(FORMAT, "", "static", String.valueOf(method.isStatic()));
		}
		if (FLAG_SYNTHETIC == (FLAG_SYNTHETIC & flags)) {
		    System.out.printf(FORMAT, "", "synthetic", String.valueOf(method.isSynthetic()));
		}
		if (FLAG_BRIDGE == (FLAG_BRIDGE & flags)) {
		    System.out.printf(FORMAT, "", "bridge", String.valueOf(method.isBridge()));
		}
		if (FLAG_NATIVE == (FLAG_NATIVE & flags)) {
		    System.out.printf(FORMAT, "", "native", String.valueOf(method.isNative()));
		}
		if (FLAG_SYNCHRONIZED == (FLAG_SYNCHRONIZED & flags)) {
		    System.out.printf(FORMAT, "", "synchronized", String.valueOf(method.isSynchronized()));
		}
		if (FLAG_CONSTRUCTOR == (FLAG_CONSTRUCTOR & flags)) {
		    System.out.printf(FORMAT, "", "constructor", String.valueOf(method.isConstructor()));
		}
		if (FLAG_INITIALIZER == (FLAG_INITIALIZER & flags)) {
		    System.out.printf(FORMAT, "", "initializer", String.valueOf(method.isStaticInitializer()));
		}
		if (FLAG_VARIABLE == (FLAG_VARIABLE & flags)) {
		    System.out.printf(FORMAT, "", "variable", String.valueOf(method.isVarArgs()));
		}
		if (FLAG_LINE == (FLAG_LINE & flags)) {
		    System.out.printf(FORMAT, "", "line", line(method));
		}
		if (FLAG_DECLARE == (FLAG_DECLARE & flags)) {
		    System.out.printf(FORMAT, "", "declare", method.declaringType().name());
		}			
		if (FLAG_RETURN == (FLAG_RETURN & flags)) {
		    System.out.printf(FORMAT, "", "return", method.returnTypeName());
		}
	}

	private String access(Method method) {
		if (method.isPrivate()) {
			return "private";
		} else if (method.isProtected()) {
			return "protected";
		} else if (method.isPublic()) {
			return "public";
		}
		return "n/a";
	}

	private String line(Method method) {
		if (!method.isAbstract()) {
			int line = method.location().lineNumber();
			return String.valueOf((-1 == line) ? "n/a" : line);			
		}
		return "n/a";
	}   
}
