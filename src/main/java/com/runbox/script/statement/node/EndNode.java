package com.runbox.script.statement.node;

public class EndNode extends Node {

    public EndNode(BlockNode block) {
        super("}");
		this.block = block;
    }

	private BlockNode block = null;

	public void block(BlockNode block) {
		this.block = block;
	}

	public BlockNode block() {
		return block;
	}
}
