package com.runbox.debug.parser.statement.node;

/**
 * Created by qstesiro
 */
public class ConditionNode extends Node {

    public ConditionNode(String name, String condition) {
        super(name);
        this.condition = condition;
    }

    private String condition = null;

    public String condition() {
        return condition;
    }
}
