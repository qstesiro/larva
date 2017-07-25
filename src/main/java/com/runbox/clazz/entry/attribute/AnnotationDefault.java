package com.runbox.clazz.entry.attribute;

import javax.json.JsonObjectBuilder;

public class AnnotationDefault extends Annotations {

    
    public AnnotationDefault(Value value) {
        super("AnnotationDefault"); this.value = value;
    }

    private Value value = null;

    public AnnotationDefault value(Value value) {
        this.value = value; return this;
    }

    public Value value() {
        return value;
    }

    @Override
    public JsonObjectBuilder toJson() {
        return super.toJson().add("value", value.toJson());
    } 
}
