package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

public class InterfaceMethodRefConstant extends TypeRefConstant {

    public InterfaceMethodRefConstant(ConstantReader reader) {
        super(reader, TYPE_INTERFACE_METHOD_REF);
    }
    
    public InterfaceMethodRefConstant(ConstantReader reader, int clazz, int index) {
        super(reader, TYPE_INTERFACE_METHOD_REF, clazz, index);
    }

	public String className() {
		return reader().getUTF8(reader().getClass(clazz()).index()).string().replace("/", ".");
	}

	public String methodName() {		
		return reader().getUTF8(reader().getNameType(index()).nameIndex()).string();
	}

	public String descriptor() {
		return reader().getUTF8(reader().getNameType(index()).descriptor()).string();
	}
}
