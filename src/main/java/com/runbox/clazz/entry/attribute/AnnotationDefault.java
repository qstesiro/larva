package com.runbox.clazz.entry.attribute;

import javax.json.JsonObjectBuilder;

public class AnnotationDefault extends Annotations {

    public AnnotationDefault(long offset) {        
        super(offset, "AnnotationDefault");
    }
    
    public AnnotationDefault(long offset, Value value) {
        super(offset, "AnnotationDefault"); this.value = value;
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
