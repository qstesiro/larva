package com.runbox.debug.manager;

import com.sun.tools.javac.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qstesiro
 */
public class ArgumentManager extends Manager {

    private ArgumentManager() {

    }

    private static ArgumentManager manager = new ArgumentManager();

    public static ArgumentManager instance() {
        return manager;
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
        throw new Exception("invalid arguments");
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
            return new Pair<>(strings[0], strings[1]);
        }
        throw new Exception("invalid address");
    }

    public final static String ADDRESS = "-address";
    public final static String SCRIPT = "-script";

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
    }};
}
