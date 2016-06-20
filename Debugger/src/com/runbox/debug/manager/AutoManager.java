package com.runbox.debug.manager;

import com.sun.jdi.Type;
import com.sun.jdi.Value;
import com.sun.tools.javac.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangmengmeng01 on 2016/5/25.
 */
public class AutoManager extends Manager {

    private AutoManager() {

    }

    private static AutoManager manager = new AutoManager();

    public static AutoManager instance() {
        return manager;
    }

    private Map<String, Pair<Type, Value>> map = new HashMap<>();

    public Pair<Type, Value> set(String name, Type type, Value value) {
        Pair<Type, Value> previous = map.get(name);
        map.put(name, new Pair<>(type, value));
        return previous;
    }

    public Pair<Type, Value> get(String name) {
        return map.get(name);
    }

    public Map<String, Pair<Type, Value>> autos() {
        return map;
    }
}
