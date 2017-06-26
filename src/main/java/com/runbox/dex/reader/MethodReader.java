package com.runbox.dex.reader;

import java.nio.channels.FileChannel;

import com.runbox.dex.entry.constant.MethodId;

public class MethodReader extends Reader {

	public MethodReader(FileChannel channel, Map map, DexReader reader) throws Exception {
		super(channel, map, reader);
		ids = new MethodId[map.count()];
	}

	@Override
	public MethodReader load() throws Exception {		
		position(map().offset());
		for (int i = 0; i < ids.length; ++i) {
			ids[i] = new MethodId(readU2(), readU2(), readU4(), reader());
		}
		return this;
	}

	private MethodId[] ids = null;

	public MethodId[] get() {
		return ids;
	}

	public MethodId get(int index) {
		if (0 <= index && ids.length > index) {
			return ids[index];
		}
		return null;
	}

	public void print() {
		for (MethodId id : ids) {
			System.out.println(id.clazz());
			System.out.println(id.name());
			System.out.println(id.descriptor());
			System.out.println(id.returnType());
			System.out.println(id.parameterString());			
		}
	}
}
