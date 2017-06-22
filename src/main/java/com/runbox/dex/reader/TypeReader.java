package com.runbox.dex.reader;

import java.nio.channels.FileChannel;

import com.runbox.dex.entry.TypeId;

public class TypeReader extends Reader {

	public TypeReader(FileChannel channel, Map map, DexReader reader) throws Exception {
		super(channel, map, reader);				
		ids = new TypeId[map.count()];
	}   	

	@Override
	public TypeReader load() throws Exception {
		position(map().offset());
		for (int i = 0; i < ids.length; ++i) {
			ids[i] = new TypeId(readU4());
		}
		return this;
	}
	
	private TypeId[] ids = null;

	public TypeId[] get() {
		return ids;
	}
}
