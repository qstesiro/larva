package com.runbox.manager;

import java.util.Map;
import java.util.HashMap;

import com.sun.tools.javac.util.Pair;

public class ImportManager extends Manager {

    private ImportManager() {

    }

    private static ImportManager instance = new ImportManager();

    public static ImportManager instance() {
        return instance;
    }

    public void clean() throws Exception {
        classes.clear();
    }

    private Map<String, String> classes = new HashMap<String, String>() {{
			put("java.lang", "Byte");
			put("java.lang", "Char");
			put("java.lang", "Short");
			put("java.lang", "Integer");
			put("java.lang", "Long");
			put("java.lang", "Float");
			put("java.lang", "Double");
			put("java.lang", "Boolean");			
			put("java.lang", "Number");			
			put("java.lang", "String");
			put("java.lang", "StringBuffer");
			put("java.lang", "StringBuilder");
			put("java.lang", "Object");			
			put("java.lang", "Thread");			
		}};

	public void append(String clazz, String path) {
		if (!classes.containsKey(clazz)) {
			classes.put(clazz, path);
		}
	}

	public void delete(String clazz) {
		if (null != clazz) {
			if (classes.containsKey(clazz)) {
				classes.remove(clazz);
			}
		}
	}

	public void delete() {
		classes.clear();
	}

	public String find(String clazz) {
		if (classes.containsKey(clazz)) {
			return classes.get(clazz);
		}
		return null;
	}
	
	public Map<String, String> get() {
		return classes;
	}
}
