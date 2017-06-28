package com.runbox.dex.entry.constant;

import com.runbox.dex.reader.DexReader;

public class TypeId extends Constant {

	public TypeId(DexReader reader) {
		super(reader);
	}
	
	public TypeId(int index, DexReader reader) {
		super(reader);
		this.index = index;
	}

	private int index = 0;

	public TypeId index(int index) {
		this.index = index; return this;
	}

	public int index() {
		return index;
	}

	public String descriptor() {
		if (null != reader()) {
			return convertType(reader().getStringId(index).string());			
		}
		return null;
	}	
}
