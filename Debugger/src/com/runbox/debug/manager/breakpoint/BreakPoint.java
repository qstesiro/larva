package com.runbox.debug.manager.breakpoint;

import com.runbox.debug.parser.statement.node.BlockNode;
import com.sun.jdi.request.EventRequest;

/**
 * Created by qstesiro
 */
public class BreakPoint {

    public static final String OBJECT = "object";

    public BreakPoint(String clazz, int type) {
        this.clazz = clazz;
        this.type = type;
        status = STATUS_PENDING;
    }

    public static final int TYPE_METHOD = 1;
    public static final int TYPE_LINE = 2;
    public static final int TYPE_ACCESS = 3;
    public static final int TYPE_MODIFY = 4;

    private int type;

    public int type() {
        return type;
    }

    public static final int STATUS_PENDING = 0;
    public static final int STATUS_ENABLE = 1;
    public static final int STATUS_DISABLE = 2;

    private int status;

    public void status(int status) {
        this.status = status;
    }

    public int status() {
        return status;
    }

    private String clazz;

    public void clazz(String clazz) {
        this.clazz = clazz;
    }

    public String clazz() {
        return clazz;
    }

    private EventRequest request;

    public void request(EventRequest request) {
        this.request = request;
    }

    public EventRequest request() {
        return request;
    }

    public String location() {
        return null;
    }

    public boolean equals(BreakPoint point) {
        if (type == point.type && clazz.equals(point.clazz)) {
            return true;
        }
        return false;
    }

    BlockNode block = null;

    public BlockNode block(BlockNode block) {
        BlockNode previous = this.block;
        this.block = block;
        return previous;
    }

    public BlockNode block() {
        return block;
    }
}
