package com.runbox.manager;

import java.util.HashMap;
import java.util.Map;

import com.sun.tools.javac.util.Pair;

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

    private final static int ARGUMENT_MINIMUM = 2;
    private final static int ARGUMENT_MAXIMUM = 4;

    public void set(String arguments[]) throws Exception {
        if (ARGUMENT_MINIMUM <= arguments.length && ARGUMENT_MAXIMUM <= arguments.length) {
            String address = find(arguments, ADDRESS);
            if (null != address) {
                map.put(ADDRESS, address);
                map.put(SCRIPT, find(arguments, SCRIPT));
                this.arguments = arguments;
                return;
            }
        }
		for (String str : arguments) {
			System.out.println(str);
		}
        throw new Exception("invalid arguments -> " + arguments.length);
    }

    public String[] get() {
        return arguments;
    }

    public String get(String key) {
        return map.get(key);
    }

    public Pair<String, String> getAddress() throws Exception {
        String address = map.get(ADDRESS);
        String[] strings = address.split(":");
        if (2 == strings.length) {
            return new Pair<String, String>(strings[0], strings[1]);
        }
        throw new Exception("invalid address");
    }

    public final static String ADDRESS = "-address";
    public final static String SCRIPT = "-script";
    public final static String MODE = "-mode";

    private String find(String arguments[], String key) {
        for (int i = 0; i < arguments.length; ++i) {
            if (arguments[i].equals(key)) {
                return arguments[++i];
            }
        }
        return null;
    }
    
    private String arguments[] = null;

    private Map<String, String> map = new HashMap<String, String>() {{
        put(ADDRESS, null);
        put(SCRIPT, null);
        put(MODE, null);
    }};
}
