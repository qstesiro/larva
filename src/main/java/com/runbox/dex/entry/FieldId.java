package com.runbox.dex.entry;

import com.runbox.dex.reader.DexReader;

public class FieldId extends Entry {

	public FieldId(DexReader reader) {
		super(reader);
	}

	public FieldId(int clazz, int type, int name, DexReader reader) {
		super(reader);
		this.clazz = clazz;
		this.type = type;
		this.name = name;
	}

	private int clazz = 0;

	public FieldId classIndex(int index) {
		clazz = index; return this;
	}
	
	public int classIndex() {
		return clazz;
	}

	private int type = 0;

	public FieldId typeIndex(int index) {
		type = index; return this;
	}

	public int typeIndex() {
		return type;
	}

	private int name = 0;

	public FieldId nameIndex(int index) {
		name = index; return this;
	}

	public int nameIndex() {
		return name;
	}
}
