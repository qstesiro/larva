package com.runbox.dex.entry;

import com.runbox.dex.reader.DexReader;

public class MethodId extends Entry {

	public MethodId(DexReader reader) {
		super(reader);
	}

	public MethodId(int clazz, int proto, int name, DexReader reader) {
		super(reader);
		this.clazz = clazz;
		this.proto = proto;
		this.name = name;
	}

	private int clazz = 0;

	public MethodId classIndex(int index) {
		clazz = index; return this;
	}
	
	public int classIndex() {
		return clazz;
	}

	private int proto = 0;

	public MethodId protoIndex(int index) {
		proto = index; return this;
	}

	public int protoIndex() {
		return proto;
	}

	private int name = 0;

	public MethodId nameIndex(int index) {
		name = index; return this;
	}

	public int nameIndex() {
		return name;
	}
}
