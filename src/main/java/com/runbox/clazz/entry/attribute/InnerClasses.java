package com.runbox.clazz.entry.attribute;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

public class InnerClasses extends Attribute {
    
    public InnerClasses(Class[] classes) {
        super("InnerClasses"); this.classes = classes;
    }

    public Class[] classes = null;

    public InnerClasses classes(Class[] classes) {
        this.classes = classes;
        return this;
    }

    public Class[] classes() {
        return classes;
    }

    @Override
    public JsonObjectBuilder toJson() {
        if (null != classes) {
            JsonArrayBuilder builder = Json.createArrayBuilder();
            for (int i = 0; i < classes.length; ++i) {
                builder.add(classes[i].toJson());
            }
            return super.toJson().add("classes", Json.createObjectBuilder().add("length", classes.length).add("array", builder));
        }
        return super.toJson();
    }
    
    public static class Class {

        private static final int INNER_CLASS = 0;
        private static final int OUTER_CLASS = 1;
        private static final int INNER_NAME = 2;
        private static final int INNER_FLAGS = 3;
        private static final int FIELD_LENGTH = 4;        
        
        public Class(int[] fields) {
            this.fields = fields;
        }
        
        private int[] fields = new int[FIELD_LENGTH];

        public Class innerClass(int index) {
            fields[INNER_CLASS] = index; return this;
        }
        
        public int innerClass() {
            return fields[INNER_CLASS];
        }

        public Class outerClass(int index) {
            fields[OUTER_CLASS] = index; return this;
        }
        
        public int outerClass() {
            return fields[OUTER_CLASS];
        }

        public Class innerName(int index) {
            fields[INNER_NAME] = index; return this;
        }
        
        public int innerName() {
            return fields[INNER_NAME];
        }

        public Class innerFlags(int flags) {
            fields[INNER_FLAGS] = flags; return this;
        }
        
        public int innerFlags() {
            return fields[INNER_FLAGS];
        }

        public JsonObjectBuilder toJson() {
            return Json.createObjectBuilder()
                .add("innerClass", fields[INNER_CLASS])
                .add("outerClass", fields[OUTER_CLASS])
                .add("innerName", fields[INNER_NAME])
                .add("innerFlags", fields[INNER_FLAGS]);
        }
    }
}
