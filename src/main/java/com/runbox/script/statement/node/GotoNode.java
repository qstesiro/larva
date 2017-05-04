package com.runbox.script.statement.node;

public class GotoNode extends Node {

    public GotoNode(String name) {
        super(name);
    }

    private Node step = null;

    public Node step(Node step) {
        Node prev = this.step;
        this.step = step;
        return prev;
    }

    public Node step() {
        return step;
    }
}