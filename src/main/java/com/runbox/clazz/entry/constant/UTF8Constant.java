package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

import java.nio.charset.Charset;

import javax.json.JsonObjectBuilder;

public class UTF8Constant extends Constant {

    public UTF8Constant(long offset, ConstantReader reader) {
        super(offset, reader, TYPE_UTF8);
    }
    
    public UTF8Constant(long offset, ConstantReader reader, byte[] data) {
        super(offset, reader, TYPE_UTF8);
        this.data = data;
        string = new String(data, Charset.forName("UTF-8"));
    }

    private byte[] data = null;
    private String string = null;
    
    public UTF8Constant data(byte[] data) {
        this.data = data;
        string = new String(data, Charset.forName("UTF-8"));
        return this;
    }

    public byte[] data() {
        return data;
    }

	public UTF8Constant string(String string) throws Exception {
		this.string = string;
		data = string.getBytes("UTF-8");
		return this;
	}
	
    public String string() {
        return string;
    }

    @Override
    public JsonObjectBuilder toJson() {
        return super.toJson().add("string", string);
    }
}
