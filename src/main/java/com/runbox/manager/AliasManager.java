package com.runbox.manager;

import java.util.Map;
import java.util.HashMap;

public class AliasManager extends Manager {

    private AliasManager() {

    }

    private static AliasManager instance = new AliasManager();

    public static AliasManager instance() {
        return instance;
    }

    public void clean() throws Exception {
        alias.clear();
    }

    private Map<String, String> alias = new HashMap<String, String>();

    public void append(String command, String name) {
        if (!alias.containsKey(name)) {
            alias.put(name, command);
        }               
	}

	public void delete(String name) {
		if (null != name) {
			if (alias.containsKey(name)) {
				alias.remove(name);
			}
		}        
	}

    public void delete() {
        alias.clear();
    }

	public String find(String name) {
        if (alias.containsKey(name)) {
			return alias.get(name);
		}
		return null;
	}
	
	public Map<String, String> get() {
		return alias;
	}
}
