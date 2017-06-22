package com.runbox.dex.entry;

public class TypeId {

	public TypeId(int index) {
		this.index = index;
	}

	private int index = 0;

	public TypeId index(int index) {
		this.index = index; return this;
	}

	public int index() {
		return index;
	}
}
