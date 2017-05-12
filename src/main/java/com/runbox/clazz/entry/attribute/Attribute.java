package com.runbox.clazz.entry.attribute;

import com.runbox.clazz.entry.Entry;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Attribute extends Entry {    
    
    public Attribute(long offset, String name) {
        super(offset); this.name = name;
    }

    private String name = null;
    
    public String name() {
        return name;
    }  

    public JsonObjectBuilder toJson() {
        return Json.createObjectBuilder().add("name", name);
    }
}
