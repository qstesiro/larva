package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

public class MethodRefConstant extends TypeRefConstant {

    public MethodRefConstant(long offset, ConstantReader reader) {
        super(offset, reader, TYPE_METHOD_REF);
    }
    
    public MethodRefConstant(long offset, ConstantReader reader, int clazz, int index) {
        super(offset, reader, TYPE_METHOD_REF, clazz, index);
    }	
}
