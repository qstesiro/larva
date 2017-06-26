package com.runbox.dex.reader;

import java.nio.channels.FileChannel;

import com.runbox.dex.entry.constant.FieldId;

public class FieldReader extends Reader {

	public FieldReader(FileChannel channel, Map map, DexReader reader) throws Exception {
		super(channel, map, reader);
		ids = new FieldId[map.count()];
	}

	@Override
	public FieldReader load() throws Exception {
		position(map().offset());
		for (int i = 0; i < ids.length; ++i) {
			ids[i] = new FieldId(readU2(), readU2(), readU4(), reader());
		}
		return this;
	}

	private FieldId[] ids = null;

	public FieldId[] get() {
		return ids;
	}

	public FieldId get(int index) {
		if (0 <= index && ids.length > index) {
			return ids[index];
		}
		return null;
	}

	public void print() {
		for (FieldId id : ids) {
			System.out.println(id.clazz());
			System.out.println(id.name());
			System.out.println(id.type());			
		}
	}
}
