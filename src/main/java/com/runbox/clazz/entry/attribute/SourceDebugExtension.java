package com.runbox.clazz.entry.attribute;

public class SourceDebugExtension extends Attribute {

    public SourceDebugExtension(long offset) {
        super(offset, "SourceDebugExtension");
    }
    
    public SourceDebugExtension(long offset, byte[] data) {
        super(offset, "SourceDebugExtension"); this.data = data;
    }

    private byte[] data = null;

    public SourceDebugExtension data(byte[] data) {
        this.data = data; return this;
    }

    public byte[] data() {
        return data;
    }
}
