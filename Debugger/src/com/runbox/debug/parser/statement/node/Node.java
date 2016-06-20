package com.runbox.debug.parser.statement.node;

/**
 * Created by qstesiro
 */
public class Node {

    public Node(String name) {
        this.name = name.trim();
    }

    private String name = null;

    public String name() {
        return name;
    }

    private Node left = null;

    public Node left(Node node) {
        Node previous = left;
        left = node;
        return previous;
    }

    public Node left() {
        return left;
    }

    private Node right = null;

    public Node right(Node node) {
        Node previous = right;
        right = node;
        return previous;
    }

    public Node right() {
        return right;
    }
}
