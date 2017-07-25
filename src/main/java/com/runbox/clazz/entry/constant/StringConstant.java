package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

public class StringConstant extends ClassStringConstant {

    public StringConstant(ConstantReader reader) {
        super(reader, TYPE_STRING);
    }
    
    public StringConstant(ConstantReader reader, int index) {
        super(reader, TYPE_STRING, index);
    }

	public String string() {
		return reader().getUTF8(index()).string();
	}
}
