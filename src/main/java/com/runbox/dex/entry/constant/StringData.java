package com.runbox.dex.entry.constant;

import java.nio.charset.Charset;

public class StringData {

    public StringData(byte[] data) {
        this.data = data;
        if (null != data) {
            this.string = new String(data, Charset.forName("UTF-8"));
        } else {
            this.string = new String("");
        }
    }
        
    private byte[] data = null;

    public byte[] data() {
        return data;
    }

    private String string = null;    

    public String string() {
        return string;
    }
}