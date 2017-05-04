package com.runbox.command;

import com.runbox.manager.AliasManager;

import com.runbox.script.statement.node.BlockNode;
import com.runbox.script.statement.node.RoutineNode;

public class Command {        	
	
    public Command(String command) throws Exception {
        if (null != command) {
            this.command = command.trim();
            int index = this.command.indexOf(' ');
            if (-1 != index) {
                key = this.command.substring(0, index).trim().toLowerCase();
                argument = this.command.substring(index + 1).trim();
            } else {
                key = this.command;
            }
        }
    }

    public boolean execute() throws Exception {
        return true;
    }

    private String command = null;

    public String command() {
        return command;
    }

    private String key = null;
    
    public String key() {
        return key;
    }

    private String argument = null;

    public String argument() {
        return argument;
    }

    public static final String ROUTINE = "routine";

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
