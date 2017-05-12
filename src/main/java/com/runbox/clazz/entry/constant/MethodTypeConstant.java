package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

import javax.json.JsonObjectBuilder;

public class MethodTypeConstant extends Constant {

    public MethodTypeConstant(long offset, ConstantReader reader) {
        super(offset, reader, TYPE_METHOD_TYPE);
    }
    
    public MethodTypeConstant(long offset, ConstantReader reader, int index) {
        super(offset, reader, TYPE_METHOD_TYPE); this.index = index;
    }

    private int index = 0;

    public MethodTypeConstant index(int index) {
        this.index = index;
        return this;
    }

    public int index() {
        return index;
    }

	public String argumentString() {
		return argumentString(descriptor());
	}	

	public String returnType() {
		return returnType(descriptor());
	}
	
	private String descriptor() {
		return reader().getUTF8(index).string();
	}

    @Override
    public JsonObjectBuilder toJson() {
        return super.toJson().add("index", index());
    }
}
