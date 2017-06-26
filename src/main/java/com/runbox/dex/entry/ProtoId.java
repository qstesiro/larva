package com.runbox.dex.entry;

import com.runbox.dex.reader.DexReader;

public class ProtoId extends Entry {

	public ProtoId(DexReader reader) {
		super(reader);
	}
	
	public ProtoId(int stringIndex, int returnIndex, Parameter parameter, DexReader reader) {
		super(reader);
		this.stringIndex = stringIndex;
		this.returnIndex = returnIndex;
		this.parameter = parameter;
	}

	private int stringIndex = 0;

	public ProtoId stringIndex(int index) {
		stringIndex = index; return this;
	}
	
	public int stringIndex() {
		return stringIndex;
	}
	
	private int returnIndex = 0;

	public ProtoId returnIndex(int index) {
		returnIndex = index; return this;
	}
	
	public int returnIndex() {
		return returnIndex;
	}
	
	private Parameter parameter = null;

	public ProtoId parameter(Parameter parameter) {
		this.parameter = parameter; return this;
	}
	
	public Parameter parameter() {
		return parameter;
	}
}
