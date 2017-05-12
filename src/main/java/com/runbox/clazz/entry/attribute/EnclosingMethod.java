package com.runbox.clazz.entry.attribute;

import javax.json.JsonObjectBuilder;

public class EnclosingMethod extends Attribute {

    public EnclosingMethod(long offset) {
        super(offset, "EnclosingMethod");
    }
    
    public EnclosingMethod(long offset, int clazz, int method) {
        super(offset, "EnclosingMethod");
        this.clazz = clazz;
        this.method = method;
    }

    private int clazz = 0;
    private int method = 0;

    public EnclosingMethod clazz(int index) {
        clazz = index; return this;
    }

    public int clazz() {
        return clazz;
    }

    public EnclosingMethod method(int index) {
        method = index; return this;
    }

    public int method() {
        return method;
    }

    @Override
    public JsonObjectBuilder toJson() {
        return super.toJson().add("class",  clazz).add("method", method);
    }
}
