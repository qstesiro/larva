package com.runbox.dex.entry.constant;

import com.runbox.dex.reader.DexReader;

public class ProtoId extends Constant {

	public ProtoId(DexReader reader) {
		super(reader);
	}
	
	public ProtoId(int index, int returnIndex, Parameter parameter, DexReader reader) {
		super(reader);
		this.index = index;
		this.returnIndex = returnIndex;
		this.parameter = parameter;
	}

	private int index = 0;

	public ProtoId index(int index) {
		this.index = index; return this;
	}
	
	public int index() {
		return index;
	}

	public String descriptor() {
		if (null != reader()) {
			return reader().getStringId(index).string();
		}
		return null;
	}
	
	private int returnIndex = 0;

	public ProtoId returnIndex(int index) {
		returnIndex = index; return this;
	}
	
	public int returnIndex() {
		return returnIndex;
	}

	public String returnType() {
		if (null != reader()) {
			return reader().getTypeId(returnIndex).descriptor();
		}
		return null;
	}
	
	private Parameter parameter = null;

	public ProtoId parameter(Parameter parameter) {
		this.parameter = parameter; return this;
	}
	
	public Parameter parameter() {
		return parameter;
	}

	public String parameterString() {
		String arguments = "(";
		if (null != parameter) {		
			int i = 0; for (Type type : parameter.list().types()) {
				if (0 < i++) arguments += ",";
				arguments += reader().getTypeId(type.index()).descriptor();
			}		
		}
		return arguments + ")";
	}
}
