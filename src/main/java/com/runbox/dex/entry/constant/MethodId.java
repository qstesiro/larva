package com.runbox.dex.entry.constant;

import com.runbox.dex.reader.DexReader;

public class MethodId extends Constant {

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

	public String clazz() {
		if (null != reader()) {
			return reader().getTypeId(clazz).descriptor();
		}
		return null;
	}
	
	private int proto = 0;

	public MethodId protoIndex(int index) {
		proto = index; return this;
	}

	public int protoIndex() {
		return proto;
	}

	public String descriptor() {
		if (null != reader()) {
			return reader().getProtoId(proto).descriptor();
		}
		return null;
	}

	public String returnType() {
		if (null != reader()) {
			return reader().getProtoId(proto).returnType();
		}
		return null;
	}

	public String parameterString() {
		if (null != reader()) {
			return reader().getProtoId(proto).parameterString();
		}
		return null;
	}
	
	private int name = 0;

	public MethodId nameIndex(int index) {
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
