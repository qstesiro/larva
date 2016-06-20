package com.runbox.debug.manager.breakpoint;

/**
 * Created by qstesiro on 2016/5/1.
 */
public class AccessBreakPoint extends BreakPoint {

    public AccessBreakPoint(String clazz, String field) {
        super(clazz, TYPE_ACCESS);
        this.field = field;
    }

    private String field;

    public void field(String field) {
        this.field = field;
    }

    public String field() {
        return field;
    }

    public String location() {
        return clazz() + "." + field;
    }

    public boolean equals(AccessBreakPoint point) {
        if (super.equals(point) && field.equals(point.field)) {
            return true;
        }
        return false;
    }
}
