package com.runbox.debug.manager.breakpoint;

/**
 * Created by qstesiro
 */
public class ModifyBreakPoint extends BreakPoint {

    public ModifyBreakPoint(String clazz, String field) {
        super(clazz, TYPE_MODIFY);
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
    
    public boolean equals(ModifyBreakPoint point) {
        if (super.equals(point) && field.equals(point.field)) {
            return true;
        }
        return false;
    }
}
