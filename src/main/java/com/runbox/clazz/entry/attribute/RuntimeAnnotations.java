package com.runbox.clazz.entry.attribute;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

public class RuntimeAnnotations extends Annotations {

    public RuntimeAnnotations(long offset, java.lang.String name) {
        super(offset, name);
    }
    
    public RuntimeAnnotations(long offset, java.lang.String name, Annotation[] annotations) {
        super(offset, name); this.annotations = annotations;
    }

    private Annotation[] annotations = null;    

    public RuntimeAnnotations annotations(Annotation[] annotations) {
        this.annotations = annotations; return this;
    }

    public Annotation[] annotations() {
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
