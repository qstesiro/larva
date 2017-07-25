package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

import javax.json.JsonObjectBuilder;

public class NameTypeConstant extends Constant {

    public NameTypeConstant(ConstantReader reader) {
        super(reader, TYPE_NAME_TYPE);
    }
    
    public NameTypeConstant(ConstantReader reader, int name, int descriptor) {
        super(reader, TYPE_NAME_TYPE);
        this.name = name;
        this.descriptor = descriptor;
    }

    private int name = 0;
    private int descriptor = 0;

    public NameTypeConstant nameIndex(int index) {
        name = index;
        return this;
    }

    public int nameIndex() {
        return name;
    }

    public NameTypeConstant descriptor(int index) {
        descriptor = index;
        return this;
    }

    public int descriptor() {
        return descriptor;
    }

    @Override
    public JsonObjectBuilder toJson() {
        return super.toJson().add("nameIndex", name).add("descriptor", descriptor);
    }
}
