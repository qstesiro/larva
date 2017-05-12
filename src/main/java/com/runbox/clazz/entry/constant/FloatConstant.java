package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

import javax.json.JsonObjectBuilder;

public class FloatConstant extends Constant {

    public FloatConstant(long offset, ConstantReader reader) {
        super(offset, reader, TYPE_FLOAT);
    }
    
    public FloatConstant(long offset, ConstantReader reader, float value) {
        super(offset, reader, TYPE_FLOAT); this.value = value;
    }

    public float value = 0;

    public FloatConstant value(float value) {
        this.value = value;
        return this;
    }

    public float value() {
        return value;
    }

    @Override
    public JsonObjectBuilder toJson() {
        return super.toJson().add("value", value);
    }
}
