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
			put("Byte", "java.lang");
			put("Char", "java.lang");
			put("Short", "java.lang");
			put("Integer", "java.lang");
			put("Long", "java.lang");
			put("Float", "java.lang");
			put("Double", "java.lang");
			put("Boolean", "java.lang");			
			put("Number", "java.lang");			
			put("String", "java.lang");
			put("StringBuffer", "java.lang");
			put("StringBuilder", "java.lang");
			put("Object", "java.lang");			
			put("Thread", "java.lang");			
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
