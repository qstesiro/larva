package com.runbox.script.statement.node;

public class CommandNode extends Node {

    public CommandNode(String command) {
        super(command);
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
