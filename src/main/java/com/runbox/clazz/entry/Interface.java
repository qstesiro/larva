package com.runbox.clazz.entry;

public class Interface {

    public Interface(int[] array) {
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