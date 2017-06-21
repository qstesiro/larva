package com.runbox.dex.entry.bytecode;

public class Bytecode {        

    public Bytecode(String name) {
        this.name = name.trim().toLowerCase();
    }	
	
    public Bytecode(String name, byte opcode) {
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
