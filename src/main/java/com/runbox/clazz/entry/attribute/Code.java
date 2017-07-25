package com.runbox.clazz.entry.attribute;

import com.runbox.clazz.reader.BytecodeReader;
import com.runbox.clazz.reader.AttributeReader;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

public class Code extends Attribute {        
		
	public Code(int stack, int locals, BytecodeReader bytecodes, Exception[] exceptions, AttributeReader attributes) {
        super("Code");
        this.stack = stack;
        this.locals = locals;
		this.bytecodes = bytecodes;
        this.exceptions = exceptions;
        this.attributes = attributes;
    }

    private int stack = 0;
    private int locals = 0;
    private BytecodeReader bytecodes = null;
    private Exception[] exceptions = null;
    private AttributeReader attributes = null;

    public Code stack(int stack) {
        this.stack = stack; return this;
    }
    
    public int stack() {
        return stack;
    }

    public Code locals(int locals) {
        this.locals = locals; return this;
    }

    public int locals() {
        return locals;
    }

    public Code bytecodes(BytecodeReader bytecodes) {
        this.bytecodes = bytecodes; return this;
    }
    
    public BytecodeReader bytecodes() {
        return bytecodes;
    }

    public Code exceptions(Exception[] exceptions) {
        this.exceptions = exceptions; return this;
    }
    
    public Exception[] exceptions() {
        return exceptions;
    }

    public Code attributes(AttributeReader attributes) {
        this.attributes = attributes; return this;
    }
    
    public AttributeReader attributes() {
        return attributes;
    }

    @Override
    public JsonObjectBuilder toJson() {
        JsonObjectBuilder builder = super.toJson();
        builder.add("stack", stack);
        builder.add("locals", locals);
        if (null != exceptions) {
            JsonArrayBuilder array = Json.createArrayBuilder();
            for (int i = 0; i < exceptions.length; ++i) {                
                array.add(exceptions[i].toJson());
            }
            builder.add("exceptions", Json.createObjectBuilder().add("length", exceptions.length).add("array", array));
        }
        JsonObject object = attributes.toJson().build();
        if (!object.isEmpty()) {
            builder.add("attributes", object);
        }
        return builder;
    }
    
    public static class Exception {

        public Exception(int start, int end, int handler, int type) {
            this.start = start;
            this.end = end;
            this.handler = handler;
            this.type = type;
        }
        
        private int start = 0;
        private int end = 0;
        private int handler = 0;
        private int type = 0;        

        public Exception start(int start) {
            this.start = start; return this;
        }
        
        public int start() {
            return start;
        }

        public Exception end(int end) {
            this.end = end; return this;
        }
        
        public int end() {
            return end;
        }

        public Exception handler(int handler) {
            this.handler = handler; return this;
        }
        
        public int handler() {
            return handler;
        }

        public Exception type(int type) {
            this.type = type; return this;
        }
        
        public int type() {
            return type;
        }

        public JsonObjectBuilder toJson() {
            return Json.createObjectBuilder()
                .add("start", start)
                .add("end", end)
                .add("handler", handler)
                .add("type", type);
        }
    }    
}
