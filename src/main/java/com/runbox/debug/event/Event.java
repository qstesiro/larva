package com.runbox.debug.event;

import java.util.List;
import java.util.LinkedList;

import com.sun.jdi.request.EventRequest;

import com.runbox.script.Engine;
import com.runbox.script.statement.Script;
import com.runbox.script.statement.node.RoutineNode;
import com.runbox.debug.command.Command;
import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.AutoOperand;

public class Event<T extends com.sun.jdi.event.Event> {

    public Event() {
        event = null;
    }    

    public Event(T event) {
        this.event = event;
		routine = routine();
    }

	private T event = null;
	
    public T event() {
        return event;
    }

	private RoutineNode routine = null;
	
	protected RoutineNode routine() {
		if (null == routine) {
			EventRequest request = event.request();
			if (null != request)  {
				if (null != request.getProperty(Command.ROUTINE)) {
					routine = (RoutineNode)request.getProperty(Command.ROUTINE);
				}
			}
		}
		return routine;
	}
	
    public boolean handle() throws Exception {
        execute(); return true;
    }

	private List<Operand> autos = new LinkedList<Operand>();
	
	protected void arguments(List<Operand> autos) {
		this.autos = autos;
	}

	private void execute() throws Exception {
		if (null != routine) {
			List<String> names = routine.arguments();
			int i = 0; for (Operand item : autos) {
				AutoOperand auto = new AutoOperand(names.get(i++));
				auto.type(item.type());
				auto.value(item.value());
				Engine.instance().append(auto);
			}
			new Script(routine).execute();
		}
	}
}
