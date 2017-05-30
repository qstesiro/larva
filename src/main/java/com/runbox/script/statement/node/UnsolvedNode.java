package com.runbox.script.statement.node;

public class UnsolvedNode extends Node {

	public UnsolvedNode(String name) {
		super(name);
	}

	private EndNode node = null;

	public EndNode end(EndNode node) {
		EndNode prev = this.node;
		this.node = node;
		return prev;
	}

	public EndNode end() {
		return node;
	}
}
