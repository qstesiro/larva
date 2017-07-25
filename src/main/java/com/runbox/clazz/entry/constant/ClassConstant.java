package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

public class ClassConstant extends ClassStringConstant {

    public ClassConstant(ConstantReader reader) {
        super(reader, TYPE_CLASS);
    }
    
    public ClassConstant(ConstantReader reader, int index) {
        super(reader, TYPE_CLASS, index);
	}   	

	public String className() {
		return reader().getUTF8(index()).string().replace("/", ".");	
	}
}
