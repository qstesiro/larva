package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

public class ClassConstant extends ClassStringConstant {

    public ClassConstant(long offset, ConstantReader reader) {
        super(offset, reader, TYPE_CLASS);
    }
    
    public ClassConstant(long offset, ConstantReader reader, int index) {
        super(offset, reader, TYPE_CLASS, index);
	}   	

	public String className() {
		return convertClass(reader().getUTF8(index()).string());	
	}
}
