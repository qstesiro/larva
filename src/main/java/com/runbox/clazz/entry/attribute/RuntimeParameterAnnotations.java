package com.runbox.clazz.entry.attribute;

import java.util.List;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

public class RuntimeParameterAnnotations extends Annotations {

    public RuntimeParameterAnnotations(long offset, java.lang.String name) {
        super(offset, name);
    }
    
    public RuntimeParameterAnnotations(long offset, java.lang.String name, List<Annotation[]> parameters) {
        super(offset, name); this.parameters = parameters;
    }
    
    public List<Annotation[]> parameters = null;

    public RuntimeParameterAnnotations parameters(List<Annotation[]> parameters) {
        this.parameters = parameters; return this;
    }

    public List<Annotation[]> parameters() {
        return parameters;
    }

    @Override
    public JsonObjectBuilder toJson() {
        if (null != parameters) {
            JsonArrayBuilder array1 = Json.createArrayBuilder();
            for (Annotation[] annotations : parameters) {
                JsonArrayBuilder array2 = Json.createArrayBuilder();
                for (int i = 0; i < annotations.length; ++i) {
                    array2.add(annotations[i].toJson());
                }
                array1.add(Json.createObjectBuilder().add("length", annotations.length).add("array", array2));
            }
            return super.toJson().add("parameters", Json.createObjectBuilder().add("length", parameters.size()).add("array", array1));
        }
        return super.toJson();
    }
}
