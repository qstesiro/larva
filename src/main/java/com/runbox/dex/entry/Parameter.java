package com.runbox.dex.entry;

public class Parameter {

	public Parameter() {
		
	}
	
	public Parameter(TypeList list) {
		this.list = list;
	}

	private TypeList list = null;

	public Parameter list(TypeList list) {
		this.list = list; return this;
	}
	
	public TypeList list() {
		return list;
	}
}
