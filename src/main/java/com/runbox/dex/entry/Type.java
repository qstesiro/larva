package com.runbox.dex.entry;

public class Type {

	public Type() {
		
	}
	
	public Type(int index) {
		this.index = index;
	}

	private int index = 0;

	public Type index(int index) {
		this.index = index; return this;
	}
	
	private int index() {
		return index;
	}
}
