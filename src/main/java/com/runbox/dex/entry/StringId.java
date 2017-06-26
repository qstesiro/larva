package com.runbox.dex.entry;

import com.runbox.dex.reader.DexReader;

public class StringId extends Entry {

	public StringId(DexReader reader) {
		super(reader);
	}
	
    public StringId(long offset, StringData data, DexReader reader) {
		super(reader);
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
