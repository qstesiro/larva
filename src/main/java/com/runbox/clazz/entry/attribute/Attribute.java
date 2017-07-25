package com.runbox.clazz.entry.attribute;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Attribute {    
    
    public Attribute(String name) {
        this.name = name;
    }

    private String name = null;
    
    public String name() {
        return name;
    }  

    public JsonObjectBuilder toJson() {
        return Json.createObjectBuilder().add("name", name);
    }
}
