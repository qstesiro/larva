package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

import javax.json.JsonObjectBuilder;

public class ClassStringConstant extends Constant {

    public ClassStringConstant(long offset, ConstantReader reader, short type) {
        super(offset, reader, type);		
    }
    
    public ClassStringConstant(long offset, ConstantReader reader, short type, int index) {
        super(offset, reader, type); this.index = index;
    }	
	
    private int index = 0;
    
    public ClassStringConstant index(int index) {
        this.index = index; return this;
    }

    public int index() {
        return index;
    }

    @Override
    public JsonObjectBuilder toJson() {
        return super.toJson().add("index", index);        
    }	
}
