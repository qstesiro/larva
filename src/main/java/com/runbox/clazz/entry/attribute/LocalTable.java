package com.runbox.clazz.entry.attribute;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

public class LocalTable extends Attribute {
    
    public LocalTable(String name, Variable[] variables) {
        super(name); this.variables = variables;
    }

    private Variable[] variables = null;

    public LocalTable variables(Variable[] variables) {
        this.variables = variables;
        return this;
    }

    public Variable[] variables() {
        return variables;
    }

    @Override
    public JsonObjectBuilder toJson() {
        if (null != variables) {
            JsonArrayBuilder builder = Json.createArrayBuilder();
            for (int i = 0; i < variables.length; ++i) {
                builder.add(variables[i].toJson());
            }
            return super.toJson().add("variables", Json.createObjectBuilder().add("length", variables.length).add("array", builder));
        }
        return super.toJson();
    }
    
    public static class Variable {

        public Variable(int start, int length, int name, int descriptor, int index) {
            this.start = start;
            this.length = length;
            this.name = name;
            this.descriptor = descriptor;
            this.index = index;
        }        
        
        private int start = 0;
        private int length = 0;
        private int name = 0;
        private int descriptor = 0;
        private int index = 0;        

        public Variable start(int start) {
            this.start = start; return this;
        }
        
        public int start() {
            return start;
        }

        public Variable length(int length) {
            this.length = length; return this;
        }
        
        public int length() {
            return length;
        }

        public Variable name(int name) {
            this.name = name; return this;
        }
        
        public int name() {
            return name;
        }

        public Variable descriptor(int descriptor) {
            this.descriptor = descriptor; return this;
        }
        
        public int descriptor() {
            return descriptor;
        }

        public Variable index(int index) {
            this.index = index; return this;
        }
        
        public int index() {
            return index;
        }

        public JsonObjectBuilder toJson() {
            return Json.createObjectBuilder()
                .add("start", start)
                .add("length", length)
                .add("name", name)
                .add("descriptor", descriptor)
                .add("index", index);
        }
    }
}
