package com.runbox.manager;

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
			put(IP, null);
			put(PORT, null);	
			put(SCRIPT, null);
			put(MODE, MODE_DEBUG);			
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
	    if (map.containsKey(name)) {
			map.put(name, value); return;
		}
		throw new Exception("invalid config name");
	}

	public Map<String, String> get() {
		return map;
	}

    public String get(String key) {		
		if (map.containsKey(key)) {
			return map.get(key);
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
