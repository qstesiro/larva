package com.runbox.clazz.entry.attribute;

public class SourceDebugExtension extends Attribute {
    
    public SourceDebugExtension(byte[] data) {
        super("SourceDebugExtension"); this.data = data;
    }

    private byte[] data = null;

    public SourceDebugExtension data(byte[] data) {
        this.data = data; return this;
    }

    public byte[] data() {
        return data;
    }
}
