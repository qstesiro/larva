package com.runbox.clazz.entry.attribute;

public class SourceFile extends Attribute {

    public SourceFile(long offset) {
        super(offset, "SourceFile");
    }
    
    public SourceFile(long offset, int index) {
        super(offset, "SourceFile"); this.index = index;
    }

    private int index = 0;

    public SourceFile index(int index) {
        this.index = index; return this;
    }

    public int index() {
        return index;
    }
}
