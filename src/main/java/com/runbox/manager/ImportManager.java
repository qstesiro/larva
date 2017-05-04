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

    private Map<String, String> classes = new HashMap<String, String>();

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
		} else {
			classes.clear();
		}		
	}

	public String find(String clazz) {
		if (classes.containsKey(clazz)) {
			return classes.get(clazz);
		}
		return null;
	}
	
	public Map<String, String> classes() {
		return classes;
	}
}
