package com.runbox.dex.reader;

import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;

public class ReaderFactory {

	public static ConstantReader create(byte[] data, int count) throws Exception {
		DataInputStream stream = new DataInputStream(new ByteArrayInputStream(data));
		ConstantReader reader = new ConstantReader(stream, count).load();
		stream.close();
		return reader;
	}

	public static BytecodeReader create(byte[] data, ConstantReader constants) throws Exception {
		DataInputStream stream = new DataInputStream(new ByteArrayInputStream(data));
		BytecodeReader reader = new BytecodeReader(stream, data.length, constants).load();
		stream.close();
		return reader;
	}
}
