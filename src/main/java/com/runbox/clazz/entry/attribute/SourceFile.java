package com.runbox.clazz.entry.attribute;

public class SourceFile extends Attribute {
    
    public SourceFile(int index) {
        super("SourceFile"); this.index = index;
    }

    private int index = 0;

    public SourceFile index(int index) {
        this.index = index; return this;
    }

    public int index() {
        return index;
    }
}
