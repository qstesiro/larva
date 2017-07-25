package com.runbox.clazz.entry.constant;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import com.runbox.clazz.reader.ConstantReader;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Constant {

    public static final short TYPE_UNUSED = 0;
    public static final short TYPE_CLASS = 7;
    public static final short TYPE_FIELD_REF = 9;
    public static final short TYPE_METHOD_REF = 10;
    public static final short TYPE_INTERFACE_METHOD_REF = 11;
    public static final short TYPE_STRING = 8;
    public static final short TYPE_INTEGER = 3;
    public static final short TYPE_FLOAT = 4;
    public static final short TYPE_LONG = 5;
    public static final short TYPE_DOUBLE = 6;
    public static final short TYPE_NAME_TYPE = 12;
    public static final short TYPE_UTF8 = 1;
    public static final short TYPE_METHOD_HANDLE = 15;
    public static final short TYPE_METHOD_TYPE = 16;
    public static final short TYPE_INVOKE_DYNAMIC = 18;

    public Constant(ConstantReader reader) {
        this.reader = reader;
    }

    public Constant(ConstantReader reader, short type) {
		this.reader = reader;
		this.type = type;
    }

	private ConstantReader reader = null;

	public Constant reader(ConstantReader reader) {
		this.reader = reader; return this;
	}

	public ConstantReader reader() {
		return reader;
	}

	private short type = 0;

	public Constant type(short type) {
		this.type = type; return this;
	}
	
    public short type() {
        return type;
    }
    
    private static Map<Short, String> names = new HashMap<Short, String>() {{
            put(TYPE_UNUSED, "Unused");
            put(TYPE_CLASS, "Class");
            put(TYPE_FIELD_REF, "FieldRef");
            put(TYPE_METHOD_REF, "MethodRef");
            put(TYPE_INTERFACE_METHOD_REF, "InterfaceMethodRef");
            put(TYPE_STRING, "String");
			put(TYPE_INTEGER, "Integer");
            put(TYPE_FLOAT, "Float");
            put(TYPE_LONG, "Long");
            put(TYPE_DOUBLE, "Double");
            put(TYPE_NAME_TYPE, "NameType");
            put(TYPE_UTF8, "UTF8");
            put(TYPE_METHOD_HANDLE, "MethodHandle");
            put(TYPE_METHOD_TYPE, "MethodType");
            put(TYPE_INVOKE_DYNAMIC, "InvokeDynamic"); 
        }};

    public String name() {
        if (names.containsKey(type)) {
            return names.get(type);
        }
        return null;
    }
    
    public static String name(short type) {
        if (names.containsKey(type)) {
            return names.get(type);
        }
        return null;
    }

	public static String convertType(String string) {
		if (string.equals("B")) return "byte";
		else if (string.equals("C")) return "char";
		else if (string.equals("D")) return "double";
		else if (string.equals("F")) return "float";
		else if (string.equals("I")) return "int";
		else if (string.equals("J")) return "long";
		else if (string.equals("S")) return "short";
		else if (string.equals("Z")) return "boolean";
		else if (string.equals("V")) return "void";
		else if ('L' == string.charAt(0)) {						
			return string.substring(1, string.length() - 1).replace("/", ".");
		} else if ('[' == string.charAt(0)) {
			return convertType(string.substring(1, string.length())) + "[]";
		}
		return null;
	}

	public String argumentString(String string) {
		String arguments = "(";
		int i = 0; for (String argument : arguments(string)) {
			if (0 < i++) arguments += ", ";
			arguments += argument;
		}
		return arguments + ")";
	}
	
	public static List<String> arguments(String string) {
		List<String> arguments = new LinkedList<String>();
		int left = string.indexOf("(");
		int right = string.lastIndexOf(")");
		if (left + 1 != right) {
			Lexer lexer = new Lexer(string.substring(left + 1, right));
			String type = lexer.next();
			while (null != type) {
				arguments.add(type);
				type = lexer.next();
			}
		}
		return arguments;
	}	

	public static String returnType(String string) {	
		int index = string.lastIndexOf(")");
		return convertType(string.substring(index + 1, string.length()));
	}

	protected static class Lexer {

		public Lexer(String string) {
			this.string = string;
		}

		private String string = null;		

		public String string() {
			return string;
		}
		
		private int index = 0;

		public int index() {
			return index;
		}
		
		public String next() {
			while (index < string.length()) {
				char letter = string.charAt(index++);
				if ('B' == letter) return "byte";
				else if ('C' == letter) return "char";
				else if ('D' == letter) return "double";
				else if ('F' == letter) return "float";
				else if ('I' == letter) return "int";
				else if ('J' == letter) return "long";
				else if ('S' == letter) return "short";
				else if ('Z' == letter) return "boolean";
				else if ('V' == letter) return "void";
				else if ('L' == letter) {
					String clazz = "";
					while (';' != string.charAt(index)) {
						clazz += string.charAt(index); ++index;
					}
					++index; return clazz.replace("/", ".");
				} else if ('[' == letter) {
					return next() + "[]";
				}
			}
			return null;
		}
	}
	
    public JsonObjectBuilder toJson() {
        return Json.createObjectBuilder().add("type", type).add("name", name());
    }
}
