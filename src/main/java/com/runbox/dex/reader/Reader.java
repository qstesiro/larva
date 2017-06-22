package com.runbox.dex.reader;

import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;

import java.io.IOException;

public abstract class Reader {    
    
    public Reader(FileChannel channel, Map map, DexReader reader) throws Exception {
        this.channel = channel;
		this.map = map;
        this.reader = reader;				
    }    

	private FileChannel channel = null;
	
    protected FileChannel channel() {
        return channel;
    }	

	private Map map = null;

	protected Map map() {
		return map;
	}
	
    private DexReader reader = null;
    
    protected abstract Reader load() throws Exception;    

    protected static final int SIZE1 = 1;
    protected static final int SIZE2 = 2;
    protected static final int SIZE4 = 4;
    protected static final int SIZE8 = 8;

    protected byte[] read(int size) throws IOException {
		if (0 < size) {
			byte[] data = new byte[size];
			ByteBuffer buffer = ByteBuffer.wrap(data); 
			channel.read(buffer);                
			return data;
		}
		return null;
    }
    
    protected short readU1() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(SIZE1); 
        channel.read(buffer);
        return (short)(0x00ff & buffer.get(0));
    }

    protected int readU2() throws IOException {
        byte[] data = new byte[SIZE2];
        ByteBuffer buffer = ByteBuffer.wrap(data); 
        channel.read(buffer);
		return ((0x0000ff00 & (data[1] << 8)) | (0x000000ff & data[0]));
    }	
	
	protected int readU128() throws Exception {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE1);
		channel.read(buffer);
		long value = 0x00000000000000ff & buffer.get(0);
		if (0x7f < value) {
			buffer = ByteBuffer.allocate(SIZE1);
			channel.read(buffer);
			value = (value & 0x7f) | ((buffer.get(0) & 0x7f) << 7);
			if (0x7f < buffer.get(0)) {
				buffer = ByteBuffer.allocate(SIZE1);
				channel.read(buffer);
				value |= (buffer.get(0) & 0x7f) << 14;
				if (0x7f < buffer.get(0)) {
					buffer = ByteBuffer.allocate(SIZE1);
					channel.read(buffer);
					value |= (buffer.get(0) & 0x7f) << 21;
					if (0x7f < buffer.get(0)) {
						buffer = ByteBuffer.allocate(SIZE1);
						channel.read(buffer);
						value |= (buffer.get(0) & 0x7f) << 28;
					}
				}
			}
		}
		if (Integer.MAX_VALUE < value) {
			throw new Exception("beyond max value");
		}
		return (int)value;
	}

	protected int readU128P1() throws Exception {		
		return readU128() - 1;
	}
	
    protected int readU4() throws Exception {
        byte[] data = new byte[SIZE4];
        ByteBuffer buffer = ByteBuffer.wrap(data); 
        channel.read(buffer);
		long value = ((0x00000000ff000000L & (long)(data[3] << 24)) |
					  (0x0000000000ff0000L & (long)(data[2] << 16)) |
					  (0x000000000000ff00L & (long)(data[1] << 8)) |
					  (0x00000000000000ffL & (long)(data[0])));
		if (Integer.MAX_VALUE < value) {
			throw new Exception("beyond max value");
		}
		return (int)value;
    }		
	
	protected byte readS1() throws IOException {        
        ByteBuffer buffer = ByteBuffer.allocate(SIZE1);
        channel.read(buffer);
		return buffer.get(0);
	}

	protected short readS2() throws IOException {
		byte[] data = new byte[SIZE2];
        ByteBuffer buffer = ByteBuffer.wrap(data); 
        channel.read(buffer);		
		return (short)(data[1] << 8 | (0x000000ff & data[0]));
	}

	protected int readS4() throws IOException {
		byte[] data = new byte[SIZE4];
		ByteBuffer buffer = ByteBuffer.wrap(data); 
        channel.read(buffer);
		return ((data[3] << 24) |
				(0x00ff0000 & data[2] << 16) |
				(0x0000ff00 & data[1] << 8) |
				(0x000000ff & data[0])); 
	}

	protected int readS128() throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE1);
		channel.read(buffer);
		int value = buffer.get(0);
		if (0x7f >= value) {
			value = (value << 25) >> 25;			
		} else {
			buffer = ByteBuffer.allocate(SIZE1);
			channel.read(buffer);
			value = (value & 0x7f) | ((buffer.get(0) & 0x7f) << 7);
			if (0x7f >= buffer.get(0)) {
				value = (value << 18) >> 18;
			} else {
				buffer = ByteBuffer.allocate(SIZE1);
				channel.read(buffer);
				value |= (buffer.get(0) & 0x7f) << 14;
				if (0x7f >= buffer.get(0)) {
					value = (value << 11) >> 11;
				} else {
					buffer = ByteBuffer.allocate(SIZE1);
					channel.read(buffer);
					value |= (buffer.get(0) & 0x7f) << 21;
					if (0x7f >= buffer.get(0)) {
						value = (value << 4) >> 4;
					} else {
						buffer = ByteBuffer.allocate(SIZE1);
						channel.read(buffer);
						value |= buffer.get(0) << 28;
					}
				}
			}
		}
		return value;
	}
	
    protected int readInt() throws IOException {
        byte[] data = new byte[SIZE4];
		ByteBuffer buffer = ByteBuffer.wrap(data);
        channel.read(buffer);
        return buffer.getInt();
    }

    protected long readLong() throws IOException {
        byte[] data = new byte[SIZE8];
		ByteBuffer buffer = ByteBuffer.wrap(data);
        channel.read(buffer);
        return buffer.getLong();
    }

    protected float readFloat() throws IOException {
        byte[] data = new byte[SIZE4];
		ByteBuffer buffer = ByteBuffer.wrap(data);
        channel.read(buffer);
        return buffer.getFloat();
    }

    protected double readDouble() throws IOException {
        byte[] data = new byte[SIZE8];
		ByteBuffer buffer = ByteBuffer.wrap(data);
        channel.read(buffer);
        return buffer.getDouble();
    }

	protected long position() throws IOException {
		return channel.position();
	}
	
    protected long position(long position) throws IOException {
        return channel.position(position).position();
    }

	protected long skip(long count) throws IOException {
        return channel.position(channel.position() + count).position();        
	}

	protected static class Map {

		public Map(int type, int unused, int count, int offset) {
			this.type = type;
			this.count = count;
			this.offset = offset;
		}

		private int type = 0;

		public int type() {
			return type;
		}				
	   
		private int count = 0;

		public int count() {
			return count;
		}
		
		private int offset = 0;

		public int offset() {
			return offset;
		}
	}
}
