package com.runbox.manager;

import java.util.List;
import java.util.LinkedList;

import java.util.Map;
import java.util.HashMap;

public class ConfigManager extends Manager {

    private ConfigManager() {

    }

    private static ConfigManager instance = new ConfigManager();

    public static ConfigManager instance() {
        return instance;
    }

    public void clean() throws Exception {
        map.clear();
    }

    private final static int MIN = 2;
    private final static int MAX = 8;

	private Map<String, String> map = new HashMap<String, String>() {{
			put(IP, "localhost");
			put(PORT, null);	
			put(SCRIPT, null);
			put(MODE, MODE_DEBUG);
			put(LINE, "1");
			put(BYTECODE, "1");
		}};
	
    public void set(String[] arguments) throws Exception {
        if (MIN <= arguments.length && MAX >= arguments.length) {
            String address = find(arguments, PREFIX + ADDRESS);
            if (null != address) {
                map.put(IP, address.split(":")[0]);
				map.put(PORT, address.split(":")[1]);
                map.put(SCRIPT, find(arguments, PREFIX + SCRIPT));
				String mode = find(arguments, PREFIX + MODE);
				if (null != mode && verifyMode(mode)) map.put(MODE, mode);
                this.arguments = arguments;
                return;
            }			
        }
        throw new Exception("invalid arguments -> " + arguments);
    }

	public void set(String name, String value) throws Exception {
		if (null != name) {
			name = name.trim().toLowerCase();
			if (map.containsKey(name)) {
				if (!readOnly(name)) {
					map.put(name, value); return;
				}
				throw new Exception("read only config name");
			}
		}
		throw new Exception("invalid config name");
	}

	private List<String> names = new LinkedList<String>() {{
			add(IP);
			add(PORT);
			add(SCRIPT);
			add(MODE);
		}};
	
	private boolean readOnly(String name) {
		if (null != name) {
			for (String element : names) {
				if (element.equals(name)) {
					return true;
				}
			}
		}
		return false;
	}

	public Map<String, String> get() {
		return map;
	}

    public String get(String name) {
		if (null != name) {
			name.trim().toLowerCase();
			if (map.containsKey(name)) {
				return map.get(name);
			}
		}
		return null;
    }

	private String arguments[] = null;
	
    public String[] arguments() {
        return arguments;
    }

	public final static String PREFIX = "-";
    public final static String ADDRESS = "address"; // not exist in config map
	public final static String IP = "ip";
	public final static String PORT = "port";
    public final static String SCRIPT = "script";
    public final static String MODE = "mode";
	public final static String LINE = "line";
	public final static String BYTECODE = "bytecode";
	
    private String find(String arguments[], String key) {
        for (int i = 0; i < arguments.length; ++i) {
            if (arguments[i].equals(key)) {
                return arguments[++i];
            }
        }
        return null;
    }

	public final static String MODE_DEBUG = "debug";
	public final static String MODE_TRACE = "trace";
	
	private boolean verifyMode(String mode) throws Exception {
		if (null != mode) {
			if (mode.equals(MODE_DEBUG) ||
				mode.equals(MODE_TRACE)) {
				return true;
			}
		}
		return false;
	}
}
