package com.runbox.clazz.entry;

public class Class {

    public Class(int thisClass, int superClass) {
        thisClass = thisClass;
        superClass = superClass;
    }

    private int thisClass = 0;

    public Class thisClass(int thisClass) {
        this.thisClass = thisClass; return this;
    }

    public int thisClass() {
        return thisClass;
    }

    private int superClass = 0;

    public Class superClass(int superClass) {
        this.superClass = superClass; return this;
    }

    public int superClass() {
        return superClass;
    }
}