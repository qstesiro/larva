package com.runbox.debug.script.expression.token;

import com.sun.jdi.Field;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.StackFrame;
import com.sun.jdi.AbsentInformationException;

import com.runbox.debug.manager.ContextManager;

public class Token extends com.runbox.script.expression.token.Token {

    public Token(String name) {
        super(name);
    }

    private final static String[] primitives = new String[] {
            "byte", "char", "short", "int", "long", "float", "double", "boolean"
    };

    public static boolean primitive(String primitive) {
        for (String entry : primitives) {
            if (entry.equals(primitive)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNull(String string) {
        if (string.toLowerCase().equals("null")) {
            return true;
        }
        return false;
    }

    private final static String[] bools = new String[] {"true", "false"};

    public static boolean isBoolean(String bool) {
        if (bools[0].equals(bool) || bools[1].equals(bool)) {
            return true;
        }
        return false;
    }

    public static boolean isThis(String string) {
        if (null != string) {
            if (string.toLowerCase().equals("this")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInstanceof(String string) {
        if (null != string) {
            if (string.toLowerCase().equals("instanceof")) {
                return true;
            }
        }
        return false;
    }

	public static boolean routine(String name) {
		if (null != name) {
            if ('$' == name.charAt(0)) {
                return true;
            }
        }
        return false;
	}
	
    public static boolean local(String name) {
        if (null != name) {
            StackFrame frame = ContextManager.instance().frame();
            if (null != frame) {
				try {
					if (null != frame.visibleVariableByName(name)) return true;
				} catch (AbsentInformationException e) {}
            }
        }
        return false;
    }

    public static boolean field(String name) {
        if (null != name) {
            StackFrame frame = ContextManager.instance().frame();
            if (null != frame && null != frame.thisObject()) {
                Field field = frame.thisObject().referenceType().fieldByName(name);
                if (null != field) {
                    return true;
                }
            }
        }
        return false;
    }

	public static boolean auto(String name) {
        if (null != name) {
            if ('@' == name.charAt(0)) {
                return true;
            }
        }
        return false;
    }
}
