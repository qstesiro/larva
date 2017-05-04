package com.runbox.script.statement.node;

public class CommandNode extends Node {

    public CommandNode(String command) {
        super(command);
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
