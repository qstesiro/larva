package com.runbox.clazz.entry.attribute;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

public class Exceptions extends Attribute {

    public Exceptions(long offset) {
        super(offset, "Exceptions");
    }
    
    public Exceptions(long offset, int[] tables) {
        super(offset, "Exceptions"); this.tables = tables;
    }

    private int[] tables = null;

    public Exceptions tables(int[] tables) {
        this.tables = tables;
        return this;
    }

    public int[] tables() {
        return tables;
    }

    @Override
    public JsonObjectBuilder toJson() {
        if (null != tables) {
            JsonArrayBuilder builder = Json.createArrayBuilder();
            for (int i = 0; i < tables.length; ++i) {
                builder.add(tables[i]);
            }
            return super.toJson().add("tables", Json.createObjectBuilder().add("length", tables.length).add("array", builder));
        }
        return super.toJson();
    }
}
