package com.runbox.debug.manager.breakpoint;

/**
 * Created by qstesiro on 2016/5/1.
 */
public class LineBreakPoint extends BreakPoint {

    public LineBreakPoint(String clazz, int line) {
        super(clazz, TYPE_LINE);
        this.line = line;
    }

    private int line;

    public void line(int line) {
        this.line = line;
    }

    public int line() {
        return line;
    }

    public String location() {
        return clazz() + ":" + line;
    }

    public boolean equals(LineBreakPoint point) {
        if (super.equals(point) && line == point.line) {
            return true;
        }
        return false;
    }
}
