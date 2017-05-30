package com.runbox.debug.command.clazz;

import java.util.List;

import com.runbox.script.statement.node.RoutineNode;

public class ClassMonitorCommand extends ClassCommand {

	public ClassMonitorCommand(String command) throws Exception {
		super(command);
	}

	private RoutineNode routine = null;
	
	@Override
	public RoutineNode routine(RoutineNode routine) throws Exception {	
        RoutineNode prev = this.routine;
        this.routine = routine; arguments();
        return prev;
    }

	@Override
    public RoutineNode routine() throws Exception {
		return routine;
    }

	private void arguments() {
		if (null != routine) {
			List<String> list = routine.arguments(); list.clear();
			if (this instanceof ClassMonitorPrepareCommand) {
				list.add("@id");
				list.add("@thread");
			}
			list.add("@type");
		}
	}
}
