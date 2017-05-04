package com.runbox.script.statement.node;

public class ReturnNode extends GotoNode {

    public ReturnNode(String expression) {
        super("return");
		this.expression = expression;
    }

	private String expression = null;

	public String expression() {
		return expression;
	}

	private RoutineNode routine = null;

	public RoutineNode routine(RoutineNode routine) {
		RoutineNode prev = this.routine;
		this.routine = routine;
		return prev;
	}

	public RoutineNode routine() {
		return routine;
	}
}
