package com.runbox.script.statement.node;

public class ConditionNode extends BlockNode {

    public ConditionNode(String name, String condition) {
        super(name);
        this.condition = condition;
    }

    private String condition = null;

    public String condition() {
        return condition;
    }
}
