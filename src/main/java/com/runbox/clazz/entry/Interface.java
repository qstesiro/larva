package com.runbox.clazz.entry;

public class Interface extends Entry {

    public Interface(long offset) {
        super(offset);
    }

    public Interface(long offset, int[] array) {
        super(offset);
        this.array = array;
    }

    private int[] array = null;
    
    public Interface set(int[] array) {
        this.array = array; return this;
    }

    public int[] get() {
        return array;
    }
}