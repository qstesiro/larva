package com.runbox.dex.reader;

import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

import java.nio.channels.FileChannel;

public class ReaderFactory {

	public static DexReader create(String path) throws Exception {
        RandomAccessFile file = new RandomAccessFile(path, "r");
		FileChannel channel = file.getChannel();
        DexReader reader = new DexReader(channel).load();
        channel.close(); file.close();
        return reader;
    }

	public static BytecodeReader create(byte[] data, DexReader dex) throws Exception {
		File temp = createTempFile(data);                	        		
		RandomAccessFile file = new RandomAccessFile(temp, "r");
		FileChannel channel = file.getChannel();
		BytecodeReader reader = new BytecodeReader(channel, dex).load();
		channel.close(); file.close(); temp.delete();
		return reader;
	}

	private static File createTempFile(byte[] data) throws Exception {
		File file = null;
        BufferedOutputStream stream = null;
		try {
			file = File.createTempFile("prefix", ".suffix");
			stream = new BufferedOutputStream(new FileOutputStream(file));
        	stream.write(data, 0, data.length);
		} finally {
			if (null != stream) stream.close();
		} 
		return file;
	}
}
