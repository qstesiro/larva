package com.runbox.clazz.entry.attribute;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

public class BootstrapMethods extends Attribute {
    
    public BootstrapMethods(Method[] methods) {
        super("BootstrapMethods"); this.methods = methods;
    }

    private Method[] methods = null;

    public BootstrapMethods methods(Method[] methods) {
        this.methods = methods; return this;
    }

    public Method[] methods() {
        return methods;
    }

    @Override
    public JsonObjectBuilder toJson() {
        if (null != methods) {
            JsonArrayBuilder builder = Json.createArrayBuilder();
            for (int i = 0; i < methods.length; ++i) {
                builder.add(methods[i].toJson());
            }
            return super.toJson().add("length", methods.length).add("methods", builder);
        }
        return super.toJson();
    }

    public static class Method {

        public Method(int index, int[] arguments) {
            this.index = index;
            this.arguments = arguments;
        }
        
        private int index = 0;
        private int[] arguments = null;

        public Method index(int index) {
            this.index = index; return this;
        }

        public int index() {
            return index;
        }

        public Method arguments(int[] arguments) {
            this.arguments = arguments; return this;
        }

        public int[] arguments() {
            return arguments;
        }

        public JsonObjectBuilder toJson() {
            JsonObjectBuilder object = Json.createObjectBuilder().add("index", index);        
            if (null != arguments) {
                JsonArrayBuilder builder = Json.createArrayBuilder();
                for (int i = 0; i < arguments.length; ++i) {
                    builder.add(arguments[i]);
                }
                return object.add("arguments", Json.createObjectBuilder().add("length", arguments.length).add("array", builder));
            }
            return object;
        }
    }
}
