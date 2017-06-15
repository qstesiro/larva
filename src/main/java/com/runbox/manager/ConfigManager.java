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

    private final static int MIN = 2;
    private final static int MAX = 8;	
	
    public void set(String[] arguments) throws Exception {
        if (MIN <= arguments.length && MAX >= arguments.length) {
            String address = find(arguments, PREFIX + ADDRESS);
            if (null != address) {
                ip(address.split(":")[0]);
				port(Integer.valueOf(address.split(":")[1]));
                script(find(arguments, PREFIX + SCRIPT));
				mode(find(arguments, PREFIX + MODE));				
                this.arguments = arguments;
                return;
            }			
        }
        throw new Exception("invalid arguments -> " + arguments);
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

	private String ip = "localhost";

	private void ip(String value) {
		if (null != value) {
			ip = value;
		}
	}	
	
	public String ip() {
		return ip;
	}

	private int port = 0;

	private static final int MIN_PORT = 0;
	private static final int MAX_PORT = 65535;
	
	private void port(int value) throws Exception {
		if (MIN_PORT <= value && MAX_PORT >= value) {
			port = value; return;
		}
		throw new Exception("invalid port range");
	}

	public int port() {
		return port;
	}

	private String script = "";
	
	private void script(String value) {
		if (null != value) {
			script = value; return;
		}		
	}

	public String script() {
		return script;
	}

	private String mode = "debug";
	
	private void mode(String value) throws Exception {
		if (null != value) {			
			if (verifyMode(value)) {
				mode = value; return;
			}
			throw new Exception("invalid value");
		}		
	}

	public String mode() {
		return mode;
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
	
	private int line = 1;

	public void line(int value) {
		line = value;
	}

	public int line() {
		return line;
	}

	private boolean bytecode = true;

	public void bytecode(boolean value) {
		bytecode = value;
	}

	public boolean bytecode() {
		return bytecode;
	}   

	private String arguments[] = null;
	
    public String[] arguments() {
        return arguments;
    }	
}
