package com.runbox.dex.reader;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.io.IOException;

import java.nio.channels.FileChannel;

import com.runbox.dex.entry.bytecode.*;

public class BytecodeReader extends Reader {

    public BytecodeReader(FileChannel channel, DexReader reader) throws Exception {
        super(channel, reader); 
		this.size = channel.size();
    }	
	
    private List<Bytecode> codes = new LinkedList<Bytecode>();	
	
    public List<Bytecode> get() {
        return codes;
    }

    public Bytecode get(long offset) {
        if (null != codes) {            
        }
        return null;
    }	

    public int count() {
        if (null != codes) {
            return codes.size();
        }
        return 0;
    }
	
	private long size = 0;

	@Override
	protected byte[] read(int size) throws IOException {
		this.size -= size;
        return super.read(size);
    }

	@Override
    protected short readU1() throws IOException {
		--size;
        return super.readU1();
    }

	@Override
    protected int readU2() throws IOException {
		size -= SIZE2;
        return super.readU2();        
    }

	@Override
    protected long readU4() throws IOException {
		size -= SIZE4;
        return super.readU4();        
    }

	@Override
	protected byte readS1() throws IOException {
		--size;
        return super.readS1();
	}

	@Override
	protected short readS2() throws IOException {
		size -= SIZE2;
        return super.readS2();		
	}

	@Override
	protected int readS4() throws IOException {
		size -= SIZE4;
        return super.readS4();
	}

	@Override
	protected long skip(long count) throws IOException {
		size -= count;
        return super.skip(count);
	}

    @Override
    protected BytecodeReader load() throws Exception {
		while (0 < size) {
			codes.add(load(readS1()));
		}
        return this;
    }    

    private Bytecode load(byte opcode) throws Exception {        
        return null;
    }
}
