package com.runbox.dex.reader;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.io.DataInputStream;
import java.io.IOException;

import com.runbox.dex.entry.constant.*;
import com.runbox.dex.entry.bytecode.*;

public class BytecodeReader extends Reader {

    public BytecodeReader(DataInputStream stream, int size, ConstantReader reader) throws Exception {
        super(stream, reader);
		this.size = size;
		printer = new Printer(reader);
    }	
	
    private List<Bytecode> codes = new LinkedList<Bytecode>();	
	
    public List<Bytecode> get() {
        return codes;
    }

    public Bytecode get(long offset) {
        if (null != codes) {
            for (Bytecode code : codes) {
				if (offset == code.offset()) {
					return code;
				}
			}
        }
        return null;
    }	

    public int count() {
        if (null != codes) {
            return codes.size();
        }
        return 0;
    }

	private int size = 0;
    private long offset = 0;

	@Override
	protected byte[] read(int size) throws IOException {
		this.size -= size; offset += size;
        return super.read(size);
    }

	@Override
    protected short readU1() throws IOException {
		--size; ++offset;
        return super.readU1();
    }

	@Override
    protected int readU2() throws IOException {
		size -= SIZE2; offset += SIZE2;
        return super.readU2();        
    }

	@Override
    protected long readU4() throws IOException {
		size -= SIZE4; offset += SIZE4;
        return super.readU4();        
    }

	@Override
	protected byte readS1() throws IOException {
		--size; ++offset;
        return super.readS1();
	}

	@Override
	protected short readS2() throws IOException {
		size -= SIZE2; offset += SIZE2;
        return super.readS2();		
	}

	@Override
	protected int readS4() throws IOException {
		size -= SIZE4; offset += SIZE4;
        return super.readS4();
	}

	@Override
	protected long skip(long count) throws IOException {
		size -= count; offset += count;
        return super.skip(count);
	}
	
    @Override
    protected BytecodeReader load() throws Exception {
		while (0 < size) {
			codes.add(load(readS1(), offset - 1));
		}
        return this;
    }    

    private Bytecode load(byte opcode, long offset) throws Exception {        
        return null;
    }

	private Printer printer = null;
	
	public Printer printer() {
		return printer;
	}
	
	public static class Printer {

		private Printer(ConstantReader reader) {
			this.reader = reader;			
		}

		private ConstantReader reader = null;

		public ConstantReader reader() {
			return reader;
		}

		private String prefix = "";
		
		public Printer prefix(String prefix) {
			this.prefix = prefix; return this;
		}

		public String prefix() {
			return prefix;
		}		
		
		public void print(Bytecode bytecode) throws Exception {		
			
		}

		private static final String FORMAT_OFFSET = "%04x    ";
		private static final String FORMAT_OPCODE_INDEX = "[%02x %04x]\t";
		private static final String FORMAT_OPCODE = "[%02x %4s]\t";
		private static final String FORMAT_NAME = "%-16s";
		private static final String FORMAT_NONE = "%4s    %2s %4s \t%16s";
		
		private void print(long offset) {
			System.out.printf(prefix + FORMAT_OFFSET, offset);			
		}

		private void print(byte opcode, int index) {
			System.out.printf(FORMAT_OPCODE_INDEX, opcode, index);		
		}
		
		private void print(byte opcode) {
			System.out.printf(FORMAT_OPCODE, opcode, "");
		}		

		private void print(String name) {
			System.out.printf(FORMAT_NAME, name);
		}

		private void print() {
			System.out.printf(prefix + FORMAT_NONE, "", "", "", "");
		}
	}
}
