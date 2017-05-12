package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

import javax.json.JsonObjectBuilder;

public class InvokeDynamicConstant extends Constant {

    public InvokeDynamicConstant(long offset, ConstantReader reader) {
        super(offset, reader, TYPE_INVOKE_DYNAMIC);
    }
    
    public InvokeDynamicConstant(long offset, ConstantReader reader, int method, int name) {
        super(offset, reader, TYPE_INVOKE_DYNAMIC);
        this.method = method;
        this.name = name;
    }

    private int method = 0;
    private int name = 0;

    public InvokeDynamicConstant bootstrapMethod(int index) {
        method = index; return this;
    }

    public int bootstrapMethod() {
        return method;
    }

    public InvokeDynamicConstant nameType(int index) {
        name = index; return this;
    }

    public int nameType() {
        return name;
    }

	public String methodName() {
		return reader().getUTF8(reader().getNameType(name).nameIndex()).string();	
	}

	public String argumentString() {
		return argumentString(descriptor());
	}	

	public String returnType() {
		return returnType(descriptor());
	}
	
	private String descriptor() {		
		return reader().getUTF8(reader().getNameType(name).descriptor()).string();		
	}
	
    @Override
    public JsonObjectBuilder toJson() {
        return super.toJson().add("bootstrapMethod", method).add("nameType", name);
    }
}
