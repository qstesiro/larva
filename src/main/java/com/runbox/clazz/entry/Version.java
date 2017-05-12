package com.runbox.clazz.entry;

public class Version extends Entry {

    public Version(long offset, int minor, int major) {
        super(offset);
        this.minor = minor;
        this.major = major;
    }

    private int minor = 0;

    public Version minor(int minor) {
        this.minor = minor; return this;
    }

    public int minor() {
        return minor;
    }

    private int major = 0;

    public Version major(int major) {
        this.major = major; return this;
    }

    public int major() {
        return major;
    }
}