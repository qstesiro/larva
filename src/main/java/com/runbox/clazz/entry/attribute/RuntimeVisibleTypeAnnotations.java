package com.runbox.clazz.entry.attribute;

import java.util.Map;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

public class RuntimeVisibleTypeAnnotations extends Annotations {    
    
    public RuntimeVisibleTypeAnnotations(long offset) {
        super(offset, "RuntimeVisibleTypeAnnotations");
    }

    public RuntimeVisibleTypeAnnotations(long offset, TypeAnnotation[] annotations) {
        super(offset, "RuntimeVisibleTypeAnnotations"); this.annotations = annotations;
    }

    private TypeAnnotation[] annotations = null;
    
    public RuntimeVisibleTypeAnnotations annotations(TypeAnnotation[] annotation) {
        this.annotations = annotations; return this;
    }

    public TypeAnnotation[] annotations() {
        return annotations;
    }

    @Override
    public JsonObjectBuilder toJson() {
        if (null != annotations) {
            JsonArrayBuilder builder = Json.createArrayBuilder();
            for (int i = 0; i < annotations.length; ++i) {
                builder.add(annotations[i].toJson());
            }
            return super.toJson().add("annotations", Json.createObjectBuilder().add("length", annotations.length).add("array", builder));
        }
        return super.toJson();
    }
}
