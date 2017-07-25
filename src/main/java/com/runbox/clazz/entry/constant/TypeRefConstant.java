package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

import javax.json.JsonObjectBuilder;

public class TypeRefConstant extends Constant {

    public TypeRefConstant(ConstantReader reader, short type) {
        super(reader, type);
    }

	public TypeRefConstant(ConstantReader reader, short type, int index) {
		super(reader, type);
		this.index = index;
	}
	
    public TypeRefConstant(ConstantReader reader, short type, int clazz, int index) {
        super(reader, type);
        this.clazz = clazz;
        this.index = index;
    }

    private int clazz = 0;    

    public TypeRefConstant clazz(int index) {
        clazz = index; return this;
    }

    public int clazz() {
        return clazz;
    }

	private int index = 0;
	
    public TypeRefConstant index(int index) {
        this.index = index; return this;
    }

    public int index() {
        return index;
    }

	public String className() {
		return reader().getUTF8(reader().getClass(clazz).index()).string().replace("/", ".");
	}

	public String fieldName() {		
		return reader().getUTF8(reader().getNameType(index).nameIndex()).string();		
	}

	public String typeName() {		
		return convertType(reader().getUTF8(reader().getNameType(index).descriptor()).string());
	}
	
	public String methodName() {		
		return reader().getUTF8(reader().getNameType(index).nameIndex()).string();	
	}		

	public String argumentString() {		
		return argumentString(descriptor());
	}

	public String returnType() {
		return returnType(descriptor());
	}
	
	private String descriptor() {
		return reader().getUTF8(reader().getNameType(index).descriptor()).string();
	}
	
    @Override
    public JsonObjectBuilder toJson() {
        return super.toJson().add("class", clazz).add("index", index);
    }
}
