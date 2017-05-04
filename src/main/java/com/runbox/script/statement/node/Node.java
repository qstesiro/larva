package com.runbox.script.statement.node;

public class Node {

    public Node(String name) {
        this.name = name.trim();
    }

    private String name = null;

    public String name() {
        return name;
    }

    private Node next = null;

    public Node next(Node node) {
        Node prev = next;
        next = node;
        return prev;
    }

    public Node next() {
        return next;
    }	
}
