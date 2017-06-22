package com.runbox.dex.reader;

import java.nio.channels.FileChannel;

import com.runbox.dex.entry.ProtoId;
import com.runbox.dex.entry.Parameter;
import com.runbox.dex.entry.TypeList;
import com.runbox.dex.entry.Type;

public class ProtoReader extends Reader {

	public ProtoReader(FileChannel channel, Map map, DexReader reader) throws Exception {
		super(channel, map, reader);				
		ids = new ProtoId[map.count()];
	}	
	
	@Override
	public ProtoReader load() throws Exception {		
		position(map().offset());
		for (int i = 0; i < ids.length; ++i) {
			int index = readU4();
			int returnIndex = readU4();
			int offset = readU4();
			if (0 != offset) {
				long position = position();
				position(offset);
				Type[] types = types();
				position(position);
				ids[i] = new ProtoId(index, returnIndex, new Parameter(new TypeList(types)));
			} else {
				ids[i] = new ProtoId(index, returnIndex, null);
			}
		}
		return this;
	}

	private Type[] types() throws Exception {
		Type[] types = new Type[readU4()];
		for (int i = 0; i < types.length; ++i) {
			types[i] = new Type(readU2());
		}
		return types;
	}

	private ProtoId[] ids = null;

	public ProtoId[] get() {
		return ids;
	}
}
