package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

import javax.json.JsonObjectBuilder;

public class IntegerConstant extends Constant {

    public IntegerConstant(ConstantReader reader) {
        super(reader, TYPE_INTEGER);
    }
    
    public IntegerConstant(ConstantReader reader, int value) {
        super(reader, TYPE_INTEGER); this.value = value;
    }

    public int value = 0;

    public IntegerConstant value(int value) {
        this.value = value; return this;
    }

    public int value() {
        return value;
    }

    @Override
    public JsonObjectBuilder toJson() {
        return super.toJson().add("value", value);
    }
}
