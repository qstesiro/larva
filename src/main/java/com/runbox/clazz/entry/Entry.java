package com.runbox.clazz.entry;

public class Entry {

    public Entry(long offset) {
        this.offset = offset;
    }

    private long offset = 0;

    public Entry offset(long offset) {
        this.offset = offset; return this;
    }

    public long offset() {
        return offset;
    }
}