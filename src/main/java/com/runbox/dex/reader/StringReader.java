package com.runbox.dex.reader;

import java.util.List;
import java.util.LinkedList;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.channels.FileChannel;

public class StringReader extends Reader {

	public StringReader(FileChannel channel, Item item, DexReader reader) throws Exception {
		super(channel, reader);		
		this.item = item;
	}

	private Item item = null;
	
	@Override
	public StringReader load() throws Exception {
		position(item.offset());
		for (long offset : offsets()) {
			position(offset);
			int size = readU128();
			if (0 == size) strings.add(new String(""));
			else strings.add(new String(read(size), Charset.forName("UTF-8")));			
		}
		return this;
	}

	public List<Long> offsets() throws Exception {
		List<Long> offsets = new LinkedList<Long>();
		for (int i = 0; i < item.count(); ++i) {
			offsets.add(readU4());
		}
		return offsets;
	}

	private List<String> strings = new LinkedList<String>();

	public List<String> strings() {
		return strings;
	}
}
