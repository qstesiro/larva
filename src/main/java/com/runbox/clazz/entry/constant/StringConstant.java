package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

public class StringConstant extends ClassStringConstant {

    public StringConstant(long offset, ConstantReader reader) {
        super(offset, reader, TYPE_STRING);
    }
    
    public StringConstant(long offset, ConstantReader reader, int index) {
        super(offset, reader, TYPE_STRING, index);
    }

	public String string() {
		return reader().getUTF8(index()).string();
	}
}
