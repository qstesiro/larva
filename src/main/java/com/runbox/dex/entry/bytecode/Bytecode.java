package com.runbox.dex.entry.bytecode;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.runbox.dex.entry.Entry;

public class Bytecode extends Entry {        

    public Bytecode(long offset, String name) {
        super(offset); this.name = name.trim().toLowerCase();
    }	
	
    public Bytecode(long offset, String name, byte opcode) {
        super(offset); 
		this.name = name.trim().toLowerCase();
		this.opcode = opcode; 		
    }       

	private String name = null;

	public Bytecode name(String name) {
		this.name = name; return this;
	}

	public String name() {
		return name;
	}

	private byte opcode = 0;
	
    public Bytecode opcode(byte opcode) {
        this.opcode = opcode; return this;
    }

    public byte opcode() {
        return opcode;
    }
}
