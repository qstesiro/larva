package com.runbox.clazz.reader;

import java.io.DataInputStream;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public abstract class Reader {    
    
    public Reader(DataInputStream stream, ConstantReader reader) throws Exception {
        this.stream = stream;
        this.reader = reader;				
    }    

	private DataInputStream stream = null;
	
    protected DataInputStream stream() {
        return stream;
    }	
	
    private ConstantReader reader = null;

    protected void constants(ConstantReader reader) {
        this.reader = reader;
    }

    protected ConstantReader constants() {
        return reader;
    }
    
    protected abstract Reader load() throws Exception;    

    protected static final int SIZE1 = 1;
    protected static final int SIZE2 = 2;
    protected static final int SIZE4 = 4;
    protected static final int SIZE8 = 8;

    protected byte[] read(int size) throws IOException {
        byte[] data = new byte[size];
        stream.readFully(data);
        return data;
    }
    
    protected short readU1() throws IOException {
		return (short)(0x00ff & stream.readByte());
    }

    protected int readU2() throws IOException {
        byte[] data = new byte[SIZE2];
        stream().readFully(data);
		return ((0x0000ff00 & (data[0] << 8)) | (0x000000ff & data[1]));
    }

    protected long readU4() throws IOException {
        byte[] data = new byte[SIZE4];
        stream().readFully(data);
		return ((0x00000000ff000000L & (long)(data[0] << 24)) |
				(0x0000000000ff0000L & (long)(data[1] << 16)) |
				(0x000000000000ff00L & (long)(data[2] << 8)) |
				(0x00000000000000ffL & (long)(data[3])));
    }

	protected byte readS1() throws IOException {        
		return stream().readByte();
	}

	protected short readS2() throws IOException {
		byte[] data = new byte[SIZE2];
		stream().readFully(data);
		return (short)(data[0] << 8 | (0x000000ff & data[1]));
	}

	protected int readS4() throws IOException {
		byte[] data = new byte[SIZE4];
		stream().readFully(data);
		return ((data[0] << 24) |
				(0x00ff0000 & data[1] << 16) |
				(0x0000ff00 & data[2] << 8) |
				(0x000000ff & data[3])); 
	}

    protected int readInt() throws IOException {
        return stream().readInt();
    }

    protected long readLong() throws IOException {
        return stream().readLong();
    }

    protected float readFloat() throws IOException {
        return stream().readFloat();
    }

    protected double readDouble() throws IOException {
        return stream().readDouble();
    }
	
	protected long skip(long count) throws IOException {
        return stream().skip(count);
	}

	protected long available() throws IOException {
		return stream().available();
	}

	// private short toShort(byte value) {
    //     return ;
    // }
    
    // private int toInteger(byte[] data) {
    //     if (SIZE2 == data.length) {
			
    //         // return (int)data[0] << 0x18 >> 0x10 | (int)data[1] << 0x18 >>> 0x18;
    //     }
    //     return 0;
    // }

    // private long toLong(byte[] data) {
    //     if (SIZE4 == data.length) {			
    //         // return (long)((int)data[0] << 0x18
	// 		// 			  | (int)data[1] << 0x18 >>> 0x8
	// 		// 			  | (int)data[2] << 0x18 >>> 0x10
	// 		// 			  | (int)data[3] << 0x18 >>> 0x18) << 0x20 >>> 0x20;			
    //     }
    //     return 0;
    // }
	
    public JsonObjectBuilder toJson() {
        return Json.createObjectBuilder();
    }	
}
