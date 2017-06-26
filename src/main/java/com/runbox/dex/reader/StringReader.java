package com.runbox.dex.reader;

import java.nio.channels.FileChannel;

import com.runbox.dex.entry.StringId;
import com.runbox.dex.entry.StringData;

public class StringReader extends Reader {

	public StringReader(FileChannel channel, Map map, DexReader reader) throws Exception {
		super(channel, map, reader);				
		ids = new StringId[map.count()];
	}	
	
	@Override
	public StringReader load() throws Exception {		
		int[] offsets = offsets();
		for (int i = 0; i < offsets.length; ++i) {
			position(offsets[i]); 
			int size = readU128();
			if (0 == size) {
				ids[i] = new StringId(offsets[i], new StringData(null), reader());
			} else {
				ids[i] = new StringId(offsets[i], new StringData(read(size)), reader());
			}
		}
		return this;
	}

	private int[] offsets() throws Exception {
		position(map().offset());
		int[] offsets = new int[map().count()];
		for (int i = 0; i < offsets.length; ++i) {
			offsets[i] = readU4();
		}
		return offsets;
	}

	private StringId[] ids = null;

	public StringId[] get() {
		return ids;
	}

	public StringId get(int index) {
		if (0 < index && ids.length > index) {
			return ids[index];
		}
		return null;
	}	
}
