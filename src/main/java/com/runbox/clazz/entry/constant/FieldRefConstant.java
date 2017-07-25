package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

public class FieldRefConstant extends TypeRefConstant {

    public FieldRefConstant(ConstantReader reader) {
        super(reader, TYPE_FIELD_REF);
    }
    
    public FieldRefConstant(ConstantReader reader, int clazz, int index) {
        super(reader, TYPE_FIELD_REF, clazz, index);
    }    	
}
