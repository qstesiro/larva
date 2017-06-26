package com.runbox.dex.entry.constant;

import com.runbox.dex.reader.DexReader;

public class Constant {
	
	public Constant(DexReader reader) {
		this.reader = reader;
	}

	private DexReader reader = null;

	public Constant reader(DexReader reader) {
		this.reader = reader; return this;
	}

	public DexReader reader() {
		return reader;
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
}
