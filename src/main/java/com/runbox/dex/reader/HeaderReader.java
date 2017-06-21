package com.runbox.dex.reader;

import java.io.IOException;
import java.nio.channels.FileChannel;

public class HeaderReader extends Reader {

	public HeaderReader(FileChannel channel, Item item, DexReader reader) throws Exception {
		super(channel, reader);		
		this.item = item;
	}

	private Item item = null;
	
	@Override
	public HeaderReader load() throws Exception {
		position(item.offset());
		magic = read(SIZE8);
		checksum = readU4();
		signature = read(20);
		fileSize = readU4();
		headerSize = readU4();
		endianTag = readU4();
		linkSize = readU4(); linkOffset = readU4();
		mapOffset = readU4();
		stringSize = readU4(); stringOffset = readU4();
		typeSize = readU4(); typeOffset = readU4();
		protoSize = readU4(); protoOffset = readU4();
		fieldSize = readU4(); fieldOffset = readU4();
		methodSize = readU4(); methodOffset = readU4();
		classSize = readU4(); classOffset = readU4();
		dataSize = readU4(); dataOffset = readU4();
		return this;
	}	
	
	private byte[] magic = new byte[SIZE8];	

	public byte[] magic() {
		return magic;
	}

	private long checksum = 0;

	private byte[] signature = new byte[20];

	private long fileSize = 0;
	
	private long headerSize = 0;
	
	private long endianTag = 0;

	private long linkSize = 0;
	private long linkOffset = 0;

	private long mapOffset = 0;

	private long stringSize = 0;
	private long stringOffset = 0;

	private long typeSize = 0;
	private long typeOffset = 0;

	private long protoSize = 0;
	private long protoOffset = 0;

	private long fieldSize = 0;
	private long fieldOffset = 0;

	private long methodSize = 0;
	private long methodOffset = 0;

	private long classSize = 0;
	private long classOffset = 0;

	private long dataSize = 0;
	private long dataOffset = 0;
}
