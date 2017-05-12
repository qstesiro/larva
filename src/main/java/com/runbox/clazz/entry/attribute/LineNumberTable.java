package com.runbox.clazz.entry.attribute;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

public class LineNumberTable extends Attribute {

    public LineNumberTable(long offset) {
        super(offset, "LineNumberTable");
    }
    
    public LineNumberTable(long offset, LineNumber[] lines) {
        super(offset, "LineNumberTable"); this.lines = lines;
    }

    private LineNumber[] lines = null;

    public LineNumberTable lines(LineNumber[] lines) {
        this.lines = lines; return this;
    }

    public LineNumber[] lines() {
        return lines;
    }

    @Override
    public JsonObjectBuilder toJson() {
        if (null != lines) {
            JsonArrayBuilder builder = Json.createArrayBuilder();
            for (int i = 0; i < lines.length; ++i) {
                builder.add(lines[i].toJson());
            }
            return super.toJson().add("lines", Json.createObjectBuilder().add("length", lines.length).add("array", builder));
        }
        return super.toJson();
    }
    
    public static class LineNumber {

        public LineNumber(int start, int number) {
            this.start = start;
            this.number = number;
        }
        
        private int start = 0;
        private int number = 0;

        public LineNumber start(int start) {
            this.start = start; return this;
        }

        public int start() {
            return start;
        }

        public LineNumber number(int number) {
            this.number = number; return this;
        }
        
        public int number() {
            return number;
        }

        public JsonObjectBuilder toJson() {
            return Json.createObjectBuilder().add("start", start).add("number", number);
        }
    }
}
