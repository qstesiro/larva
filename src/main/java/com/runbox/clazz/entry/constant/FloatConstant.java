package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

import javax.json.JsonObjectBuilder;

public class FloatConstant extends Constant {

    public FloatConstant(ConstantReader reader) {
        super(reader, TYPE_FLOAT);
    }
    
    public FloatConstant(ConstantReader reader, float value) {
        super(reader, TYPE_FLOAT); this.value = value;
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
