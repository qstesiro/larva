package com.runbox.dex.entry;

public class ProtoId {

	public ProtoId() {
		
	}
	
	public ProtoId(int stringIndex, int returnIndex, Parameter parameter) {
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
