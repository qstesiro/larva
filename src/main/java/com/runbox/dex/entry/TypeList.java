package com.runbox.dex.entry;

public class TypeList {

	public TypeList() {
		
	}
	
	public TypeList(Type[] types) {
		this.types = types;
	}

	private Type[] types = null;

	public TypeList items(Type[] types) {
		this.types = types; return this;
	}
	
	public Type[] types() {
		return types;
	}
}
