package com.runbox.dex.entry.constant;

import com.runbox.dex.reader.DexReader;

public class FieldId extends Constant {

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

	public String clazz() {
		if (null != reader()) {
			return reader().getTypeId(clazz).descriptor();
		}
		return null;
	}
	
	private int type = 0;

	public FieldId typeIndex(int index) {
		type = index; return this;
	}

	public int typeIndex() {
		return type;
	}

	public String type() {
		if (null != reader()) {
			return reader().getTypeId(type).descriptor();
		}
		return null;
	}

	private int name = 0;

	public FieldId nameIndex(int index) {
		name = index; return this;
	}

	public int nameIndex() {
		return name;
	}

	public String name() {
		if (null != reader()) {
			return reader().getStringId(name).string();
		}
		return null;
	}
}
