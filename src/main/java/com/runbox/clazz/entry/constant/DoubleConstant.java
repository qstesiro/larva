package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

import javax.json.JsonObjectBuilder;

public class DoubleConstant extends Constant {

    public DoubleConstant(long offset, ConstantReader reader) {
        super(offset, reader, TYPE_DOUBLE);
    }
    
    public DoubleConstant(long offset, ConstantReader reader, double value) {
        super(offset, reader, TYPE_DOUBLE); this.value = value;
    }

    public double value = 0;

    public DoubleConstant value(double value) {
        this.value = value;
        return this;
    }

    public double value() {
        return value;
    }

    @Override
    public JsonObjectBuilder toJson() {
        return super.toJson().add("value", value);
    }
}
