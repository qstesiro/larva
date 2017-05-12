package com.runbox.clazz.entry.attribute;

import java.util.Map;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

public class Annotations extends Attribute {

    public Annotations(long offset, java.lang.String name) {
        super(offset, name);
    }

    public static class Annotation extends Value {

        public Annotation() {
            super(TYPE_ANNOTATION);
        }
        
        public Annotation(int type, Map<java.lang.Integer, Value> values) {
            super(TYPE_ANNOTATION);
            this.type = type;
            this.values = values;
        }
        
        private int type = 0;
        private Map<java.lang.Integer, Value> values = null;        
        
        public Annotation typeIndex(int type) {
            this.type = type; return this;
        }
        
        public int typeIndex() {
            return type;
        }        
        
        public Annotation values(Map<java.lang.Integer, Value> values) {
            this.values = values; return this;
        }
        
        public Map<java.lang.Integer, Value> values() {
            return values;
        }

        @Override
        public JsonObjectBuilder toJson() {
            if (null != values) {
                JsonObjectBuilder builder = Json.createObjectBuilder();
                for (int key : values.keySet()) {
                    builder.add(java.lang.String.valueOf(key), values.get(key).toJson());
                }
                return super.toJson().add("typeIndex", type).add("values", builder);
            }
            return super.toJson().add("typeIndex", type);
        }
    }

    public static class Value {

        public static final char TYPE_BYTE = 'B';
        public static final char TYPE_CHAR = 'C';
        public static final char TYPE_SHORT = 'S';
        public static final char TYPE_INTEGER = 'I';
        public static final char TYPE_FLOAT = 'F';
        public static final char TYPE_LONG = 'J';
        public static final char TYPE_DOUBLE = 'D';        
        public static final char TYPE_BOOLEAN = 'Z';
        public static final char TYPE_STRING = 's';
        public static final char TYPE_ENUM = 'e';
        public static final char TYPE_CLASS = 'c';
        public static final char TYPE_ANNOTATION = '@';
        public static final char TYPE_ARRAY = '[';        
        
        private char type = 0;

        public Value(char type) {
            this.type = type;
        }

        public Value type(char type) {
            this.type = type; return this;
        }

        public char type() {
            return type;
        }

        public JsonObjectBuilder toJson() {
            return Json.createObjectBuilder().add("type", type);
        }
    }

    public static class Constant extends Value {

        public Constant(char type, int index) {
            super(type); this.index = index;
        }

        private int index = 0;

        public Constant index(int index) {
            this.index = index; return this;
        }

        public int index() {
            return index;
        }

        @Override
        public JsonObjectBuilder toJson() {
           return super.toJson().add("index", index); 
        }
    }

    public static class Byte extends Constant {

        public Byte(int index) {
            super(TYPE_BYTE, index);
        }
    }

    public static class Char extends Constant {
        
        public Char(int index) {
            super(TYPE_CHAR, index);
        }
    }

    public static class Short extends Constant {
        
        public Short(int index) {
            super(TYPE_SHORT, index);
        }
    }

    public static class Integer extends Constant {
        
        public Integer(int index) {
            super(TYPE_INTEGER, index);
        }
    }

    public static class Float extends Constant {
        
        public Float(int index) {
            super(TYPE_FLOAT, index);
        }
    }

    public static class Long extends Constant {
        
        public Long(int index) {
            super(TYPE_LONG, index);
        }
    }

    public static class Double extends Constant {
        
        public Double(int index) {
            super(TYPE_DOUBLE, index);
        }
    }

    public static class Boolean extends Constant {
        
        public Boolean(int index) {
            super(TYPE_BOOLEAN, index);
        }
    }

    public static class String extends Constant {
        
        public String(int index) {
            super(TYPE_STRING, index);
        }
    }

    public static class Enum extends Value {

        public Enum(int[] indexes) {
            super(TYPE_ENUM); this.indexes = indexes;
        }

        private static final int TYPE = 0;
        private static final int CONST = 1;
        private static final int INDEX = 2;

        private int[] indexes = new int[INDEX];

        public Enum nameIndex(int index) {
            indexes[TYPE] = index; return this;
        }

        public int nameIndex() {
            return indexes[TYPE];
        }

        public Enum constIndex(int index) {
            indexes[CONST] = index; return this;
        }
        
        public int constIndex() {
            return indexes[CONST];
        }

        @Override
        public JsonObjectBuilder toJson() {
            return super.toJson().add("nameIndex", indexes[TYPE]).add("constIndex", indexes[CONST]);
        }
    }

    public static class Class extends Value {

        public Class(int index) {
            super(TYPE_CLASS); this.index = index;
        }

        private int index = 0;

        public Class index(int index) {
            this.index = index; return this;
        }

        public int index() {
            return index;
        }    

        @Override
        public JsonObjectBuilder toJson() {
            return super.toJson().add("index", index);
        }
    }

    public static class Array extends Value {

        public Array(Value[] values) {
            super(TYPE_ARRAY); this.values = values;
        }

        private Value[] values = null;

        public Array values(Value[] values) {
            this.values = values; return this;
        }

        public Value[] values() {
            return values;
        }

        @Override
        public JsonObjectBuilder toJson() {
            if (null != values) {
                JsonArrayBuilder builder = Json.createArrayBuilder();
                for (int i = 0; i < values.length; ++i) {
                    builder.add(values[i].toJson());
                }
                return super.toJson().add("values", Json.createObjectBuilder().add("length", values.length).add("array", builder));
            }
            return super.toJson();
        }
    }

    public static class TypeAnnotation {

        public TypeAnnotation() {
            
        }

        public TypeAnnotation(Target target, Path[] paths, int index, Map<java.lang.Integer, Value> values) {
            this.target = target;
            this.paths = paths;
            this.index = index;
            this.values = values;
        }        

        private Target target = null;
        private Path[] paths = null;
        private int index = 0;
        private Map<java.lang.Integer, Value> values = null;
        
        public TypeAnnotation target(Target target) {
            this.target = target; return this;
        }

        public Target target() {
            return target;
        }

        public TypeAnnotation paths(Path[] paths) {
            this.paths = paths; return this;
        }

        public Path[] paths() {
            return paths;
        }

        public TypeAnnotation index(int index) {
            this.index = index; return this;
        }

        public int index() {
            return index;
        }

        public TypeAnnotation values(Map<java.lang.Integer, Value> values) {
            this.values = values; return this;
        }
        
        public Map<java.lang.Integer, Value> values() {
            return values;
        }

        public JsonObjectBuilder toJson() {            
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("target", target.toJson());
            if (null != paths) {
                JsonArrayBuilder array = Json.createArrayBuilder();
                for (int i = 0; i < paths.length; ++i) {
                    array.add(paths[i].toJson());
                }
                builder.add("paths", Json.createObjectBuilder().add("length", paths.length).add("array", array));
            }
            builder.add("index", index);
            if (null != values) {
                JsonObjectBuilder object = Json.createObjectBuilder();
                for (int key : values.keySet()) {
                    object.add(java.lang.String.valueOf(key), values.get(key).toJson());
                }
                builder.add("values", object);
            }
            return builder;      
        }               
    }

    public static class Target {

        public static final short TYPE_PARAMETER_START = (short) 0x0000;
        public static final short TYPE_PARAMETER_END = (short) 0x0001;
        public static final short TYPE_SUPER = (short) 0x0010;
        public static final short TYPE_PARAMETER_BOUND_START = (short) 0x0011;
        public static final short TYPE_PARAMETER_BOUND_END = (short) 0x0012;
        public static final short TYPE_EMPTY_START = (short) 0x0013;
        public static final short TYPE_EMPTY_END = (short) 0x0015;
        public static final short TYPE_FORMAL_PARAMETER = (short) 0x0016;
        public static final short TYPE_THROWS = (short) 0x0017;
        public static final short TYPE_LOCALS_START = (short) 0x0040;
        public static final short TYPE_LOCALS_END = (short) 0x0041;
        public static final short TYPE_CATCH = (short) 0x0042;
        public static final short TYPE_OFFSET_START = (short) 0x0043;
        public static final short TYPE_OFFSET_END = (short) 0x0046;
        public static final short TYPE_ARGUMENT_START = (short) 0x0047;
        public static final short TYPE_ARGUMENT_END = (short) 0x004b;
        
        public Target(short type) {
            
        }

        private short type = 0;

        public Target type(short type) {
            this.type = type; return this;
        }

        public short type() {
            return type;
        }

        public JsonObjectBuilder toJson() {
            return Json.createObjectBuilder().add("type", type);
        }
    }

    public static class Parameter extends Target {

        public Parameter(short type) {
            super(type);
        }

        public Parameter(short type, short index) {
            super(type); this.index = index;
        }

        private short index = 0;
        
        public Parameter index(short index) {
            this.index = index; return this;
        }

        public short index() {
            return index;
        }

        @Override
        public JsonObjectBuilder toJson() {
            return super.toJson().add("index", index);
        }
    }

    public static class Super extends Target {

        public Super(short type) {
            super(type);
        }

        public Super(short type, int index) {
            super(type); this.index = index;
        }

        private int index = 0;
        
        public Super index(int index) {
            this.index = index; return this;
        }

        public int index() {
            return index;
        }

        @Override
        public JsonObjectBuilder toJson() {
            return super.toJson().add("index", index);
        }
    }

    public static class ParameterBound extends Target {

        public ParameterBound(short type) {
            super(type);
        }

        public ParameterBound(short type, short parameter, short bound) {
            super(type);
            this.parameter = parameter;
            this.bound = bound;
        }

        private short parameter = 0;
        private short bound = 0;

        public ParameterBound parameter(short parameter) {
            this.parameter = parameter; return this;
        }

        public short parameter() {
            return parameter;
        }

        public ParameterBound bound(short bound) {
            this.bound = bound; return this;
        }

        public short bound() {
            return bound;
        }

        @Override
        public JsonObjectBuilder toJson() {
            return super.toJson().add("parameter", parameter).add("bound", bound);
        }
    }

    public static class Empty extends Target {

        public Empty(short type) {
            super(type);
        }
    }

    public static class FormalParameter extends Target {

        public FormalParameter(short type) {
            super(type);
        }

        public FormalParameter(short type, short index) {
            super(type); this.index = index;
        }
        
        private short index = 0;

        public FormalParameter index(short index) {
            this.index = index; return this;
        }

        public short index() {
            return index;
        }

        @Override
        public JsonObjectBuilder toJson() {
            return super.toJson().add("index", index);
        }
    }

    public static class Throws extends Target {

        public Throws(short type) {
            super(type);
        }

        public Throws(short type, int index) {
            super(type); this.index = index;
        }

        private int index = 0;
        
        public Throws index(int index) {
            this.index = index; return this;
        }

        public int index() {
            return index;
        }

        @Override
        public JsonObjectBuilder toJson() {
            return super.toJson().add("index", index);
        }
    }

    public static class Locals extends Target {

        public Locals(short type) {
            super(type);
        }

        public Locals(short type, Variable[] variables) {
            super(type); this.variables = variables;
        }

        private Variable[] variables = null;
        
        public Locals variables(Variable[] variables) {
            this.variables = variables; return this;
        }

        public Variable[] variables() {
            return variables;
        }
        
        @Override
        public JsonObjectBuilder toJson() {
            if (null != variables)             {
                JsonArrayBuilder builder = Json.createArrayBuilder();
                for (int i = 0; i < variables.length; ++i) {
                    builder.add(variables[i].toJson());
                }
                return super.toJson().add("variables", Json.createObjectBuilder().add("length", variables.length).add("array", builder));
            }
            return super.toJson();
        }

        public static class Variable {

            public Variable() {
                
            }

            public Variable(int start, int length, int index) {
                this.start = start;
                this.length = length;
                this.index = index;
            }            

            private int start = 0;
            private int length = 0;
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

            public Variable index(int index) {
                this.index = index; return this;
            }

            public int index() {
                return index;
            }

            public JsonObjectBuilder toJson() {
                return Json.createObjectBuilder().add("start", start).add("length", length).add("index", index);
            }
        }
    }

    public static class Catch extends Target {

        public Catch(short type) {
            super(type);
        }

        public Catch(short type, int index) {
            super(type); this.index = index;
        }

        private int index = 0;

        public Catch index(int index) {
            this.index = index; return this;
        }

        public int index() {
            return index;
        }

        @Override
        public JsonObjectBuilder toJson() {
            return super.toJson().add("index", index);
        }
    }

    public static class Offset extends Target {

        public Offset(short type) {
            super(type);
        }

        public Offset(short type, int offset) {
            super(type); this.offset = offset;
        }

        private int offset = 0;

        public Offset offset(int offset) {
            this.offset = offset; return this;
        }

        public int offset() {
            return offset;
        }

        @Override
        public JsonObjectBuilder toJson() {
            return super.toJson().add("offset", offset);
        }
    }

    public static class Argument extends Target {

        public Argument(short type) {
            super(type);
        }

        public Argument(short type, int offset, short index) {
            super(type);
            this.offset = offset;
            this.index = index;
        }

        private int offset = 0;
        private short index = 0;
        
        public Argument offset(int offset) {
            this.offset = offset; return this;
        }

        public int offset() {
            return offset;
        }

        public Argument index(short index) {
            this.index = index; return this;
        }

        public short index() {
            return index;
        }

        @Override
        public JsonObjectBuilder toJson() {
            return super.toJson().add("offset", offset).add("index", index);
        }
    }

    public static class Path {        

        public Path(short kind, short argument) {
            this.kind = kind;
            this.argument = argument;
        }

        private short kind = 0;
        private short argument = 0;

        private Path kind(short kind) {
            this.kind = kind; return this;
        }

        public short kind() {
            return kind;
        }

        public Path argument(short argument) {
            this.argument = argument; return this;
        }

        public short argument() {
            return argument;
        }
        
        public JsonObjectBuilder toJson() {
            return Json.createObjectBuilder().add("kind", kind).add("argument", argument);
        }
    }
}
