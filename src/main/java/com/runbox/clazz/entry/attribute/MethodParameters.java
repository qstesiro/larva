package com.runbox.clazz.entry.attribute;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

public class MethodParameters extends Attribute {

    public MethodParameters(long offset) {
        super(offset, "MethodParameters");
    }
    
    public MethodParameters(long offset, Parameter[] parameters) {
        super(offset, "MethodParameters"); this.parameters = parameters;
    }

    private Parameter[] parameters = null;

    public MethodParameters parameters(Parameter[] parameters) {
        this.parameters = parameters; return this;
    }

    public Parameter[] parameters() {
        return parameters;
    }

    @Override
    public JsonObjectBuilder toJson() {
        if (null != parameters) {
            JsonArrayBuilder builder = Json.createArrayBuilder();
            for (int i = 0; i < parameters.length; ++i) {
                builder.add(parameters[i].toJson());
            }
            return super.toJson().add("parameters", Json.createObjectBuilder().add("length", parameters.length).add("array", builder));
        }
        return super.toJson();
    }
    
    public static class Parameter {

        public Parameter(int name, int flags) {
            this.name = name;
            this.flags = flags;
        }
        
        private int name = 0;
        private int flags = 0;

        public Parameter name(int name) {
            this.name = name; return this;
        }

        public int name() {
            return name;
        }

        public Parameter flags(int flags) {
            this.flags = flags; return this;
        }

        public int flags() {
            return flags;
        }

        public JsonObjectBuilder toJson() {
            return Json.createObjectBuilder().add("name", name).add("flags", flags);
        }
    }
}
