package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

public class MethodRefConstant extends TypeRefConstant {

    public MethodRefConstant(ConstantReader reader) {
        super(reader, TYPE_METHOD_REF);
    }
    
    public MethodRefConstant(ConstantReader reader, int clazz, int index) {
        super(reader, TYPE_METHOD_REF, clazz, index);
    }	
}
