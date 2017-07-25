package com.runbox.clazz.entry.attribute;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Signature extends Attribute {
    
    public Signature(int index) {
        super("Signature"); this.index = index;
    }

    private int index = 0;

    public Signature index(int index) {
        this.index = index; return this;
    }

    public int index() {
        return index;
    }

    @Override
    public JsonObjectBuilder toJson() {
        return Json.createObjectBuilder().add("index", index);
    }
}
