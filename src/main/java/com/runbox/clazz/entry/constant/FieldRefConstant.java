package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

public class FieldRefConstant extends TypeRefConstant {

    public FieldRefConstant(long offset, ConstantReader reader) {
        super(offset, reader, TYPE_FIELD_REF);
    }
    
    public FieldRefConstant(long offset, ConstantReader reader, int clazz, int index) {
        super(offset, reader, TYPE_FIELD_REF, clazz, index);
    }    	
}
