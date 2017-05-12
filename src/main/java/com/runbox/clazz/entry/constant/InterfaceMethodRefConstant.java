package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

public class InterfaceMethodRefConstant extends TypeRefConstant {

    public InterfaceMethodRefConstant(long offset, ConstantReader reader) {
        super(offset, reader, TYPE_INTERFACE_METHOD_REF);
    }
    
    public InterfaceMethodRefConstant(long offset, ConstantReader reader, int clazz, int index) {
        super(offset, reader, TYPE_INTERFACE_METHOD_REF, clazz, index);
    }

	public String className() {
		return convertClass(reader().getUTF8(reader().getClass(clazz()).index()).string());
	}

	public String methodName() {		
		return reader().getUTF8(reader().getNameType(index()).nameIndex()).string();
	}

	public String descriptor() {
		return reader().getUTF8(reader().getNameType(index()).descriptor()).string();
	}
}
