package com.runbox.dex.entry;

public class StringId {

    public StringId(long offset, StringData data) {
        this.offset = offset;        
        this.data = data;
    }

    private long offset = 0;

    public long offset() {
        return offset;
    }

    private StringData data = null;

    public StringData data() {
        return data;
    }
}