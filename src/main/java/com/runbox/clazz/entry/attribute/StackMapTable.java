package com.runbox.clazz.entry.attribute;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

public class StackMapTable extends Attribute {
    
    public StackMapTable(Frame[] frames) {
        super("StackMapTable"); this.frames = frames;        
    }

    private Frame[] frames = null;

    public StackMapTable frames(Frame[] frames) {
        this.frames = frames; return this;
    }

    public Frame[] frames() {
        return frames;
    }

    @Override
    public JsonObjectBuilder toJson() {
        if (null != frames) {
            JsonArrayBuilder builder = Json.createArrayBuilder();
            for (int i = 0; i < frames.length; ++i) {
                builder.add(frames[i].toJson());
            }
            return super.toJson().add("frames", Json.createObjectBuilder().add("length", frames.length).add("array", builder));
        }
        return super.toJson();
    }
    
    public static class Frame {

        public static final short TYPE_SAME_START = (short) 0;
        public static final short TYPE_SAME_END = (short) 63;
        
        public static final short TYPE_SAME_LOCALS1_START = (short) 64;
        public static final short TYPE_SAME_LOCALS1_END = (short) 127;
        
        public static final short TYPE_SAME_LOCALS1_EXTENDED = (short) 247;
        
        public static final short TYPE_CHOP_START = (short) 248;
        public static final short TYPE_CHOP_END = (short) 250;
        
        public static final short TYPE_SAME_EXTENDED = (short) 251;
        
        public static final short TYPE_APPEND_START = (short) 252;
        public static final short TYPE_APPEND_END = (short) 254;
        
        private short type = 0;

        public Frame(short type) {
            this.type = type;
        }

        public JsonObjectBuilder toJson() {
            return Json.createObjectBuilder().add("type", type);
        }
    }

    public static class Same extends Frame {

        public Same(short type) {
            super(type);
        }
    }

    public static class SameLocals1 extends Frame {

        public SameLocals1(short type) {
            super(type);
        }
        
        public SameLocals1(short type, Verification verification) {
            super(type);
            this.verification = verification;
        }

        private Verification verification = null;

        public SameLocals1 verification(Verification verification) {
            this.verification = verification; return this;
        }

        public Verification verification() {
            return verification;
        }

        @Override
        public JsonObjectBuilder toJson() {
            if (null != verification) {                
                return super.toJson().add("verification", verification.toJson());
            }
            return super.toJson();
        }
    }

    public static class SameLocals1Extended extends SameLocals1 {

        public SameLocals1Extended(short type) {
            super(type);
        }
        
        public SameLocals1Extended(short type, int offset, Verification verification) {
            super(type, verification);
            this.offset = offset;
        }

        private int offset = 0;

        public SameLocals1Extended offset(int offset) {
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

    public static class Chop extends Frame {

        public Chop(short type) {
            super(type);
        }
        
        public Chop(short type, int offset) {
            super(type); this.offset = offset;
        }

        private int offset = 0;

        public Chop offset(int offset) {
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

    public static class SameExtended extends Chop {

        public SameExtended(short type) {
            super(type);
        }
        
        public SameExtended(short type, int offset) {
            super(type, offset);
        }        
    }

    public static class Append extends Chop {        

        public Append(short type) {
            super(type);
        }
        
        public Append(short type, int offset, Verification[] verifications) {
            super(type, offset);
			verifications = verifications;
        }

		private Verification[] verifications = null;

        public Append verifications(Verification[] verifications) {
            this.verifications = verifications; return this;
        }

        public Verification[] verifications() {
            return verifications;
        }

		@Override
        public JsonObjectBuilder toJson() {
            if (null != verifications) {
                JsonArrayBuilder builder = Json.createArrayBuilder();
                for (int i = 0; i < verifications.length; ++i) {
                    builder.add(verifications[i].toJson());
                }
                return super.toJson().add("verifications", Json.createObjectBuilder().add("length", verifications.length).add("array", builder));
            }
            return super.toJson();
        }
    }

    public static class Verification {

        public static final short TYPE_TOP = (short) 0;
        public static final short TYPE_INTEGER = (short) 1;
        public static final short TYPE_FLOAT = (short) 2;
        public static final short TYPE_LONG = (short) 4;
        public static final short TYPE_DOUBLE = (short) 3;
        public static final short TYPE_NULL = (short) 5;
        public static final short TYPE_UNINITIALIZED_THIS = (short) 6;
        public static final short TYPE_OBJECT = (short) 7;
        public static final short TYPE_UNINITIALIZED = (short) 8;

        private short type = 0;
        
        public Verification(short type) {
            this.type = type;
        }

        public short type() {
            return type;
        }

        public JsonObjectBuilder toJson() {
            return Json.createObjectBuilder().add("type", type);
        }
    }

    public static class Top extends Verification {

        public Top() {
            super(TYPE_TOP);
        }
    }

    public static class Integer extends Verification {

        public Integer() {
            super(TYPE_INTEGER);
        }
    }

    public static class Float extends Verification {

        public Float() {
            super(TYPE_FLOAT);
        }
    }

    public static class Long extends Verification {

        public Long() {
            super(TYPE_LONG);
        }
    }

    public static class Double extends Verification {

        public Double() {
            super(TYPE_DOUBLE);
        }
    }

    public static class Null extends Verification {

        public Null() {
            super(TYPE_NULL);
        }
    }

    public static class UnitializedThis extends Verification {

        public UnitializedThis() {
            super(TYPE_UNINITIALIZED_THIS);
        }
    }

    public static class Object extends Verification {

        public Object() {
            super(TYPE_OBJECT);
        }
        
        public Object(int index) {
            super(TYPE_OBJECT); this.index = index;
        }

        private int index = 0;

        public Object index(int index) {
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

    public static class Unitialized extends Verification {

        public Unitialized() {
            super(TYPE_UNINITIALIZED);
        }
        
        public Unitialized(int offset) {
            super(TYPE_UNINITIALIZED); this.offset = offset;
        }

        private int offset = 0;

        public Unitialized offset(int offset) {
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
}
