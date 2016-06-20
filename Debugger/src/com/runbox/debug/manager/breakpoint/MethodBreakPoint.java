package com.runbox.debug.manager.breakpoint;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by qstesiro on 2016/5/1.
 */
public class MethodBreakPoint extends BreakPoint {

    public MethodBreakPoint(String clazz, String method) {
        super(clazz, TYPE_METHOD);
        this.method = method;
    }

    private String method = null;

    public void method(String method) {
        this.method = method;
    }

    public String method() {
        return method;
    }

    private List<String> list = new LinkedList<>();

    public void list(List<String> list) {
        this.list = list;
    }

    public List<String> list() {
        return list;
    }

    public String location() {
        String location = clazz() + "." + method;
        location += "(";
        for (int i = 0; i < list.size(); ++i) {
            location += list.get(i);
            if (i < list.size() - 1) {
                location += ", ";
            }
        }
        location += ")";
        return location;
    }

    public boolean equals(MethodBreakPoint point) {
        if (super.equals(point) && method.equals(point.method)) {
            return equals(point.list());
        }
        return false;
    }

    public boolean equals(List<String> list) {
        if (this.list.size() == list.size()) {
            for (int i = 0; i < list.size(); ++i) {
                if (!this.list.get(i).equals(list.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
