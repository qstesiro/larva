package com.runbox.debug.script;

import java.io.File;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import com.runbox.script.Engine;
import com.runbox.script.statement.node.RoutineNode;
import com.runbox.script.statement.node.BuiltinRoutineNode;
import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.script.expression.token.operand.*;

public class Executer extends com.runbox.script.Executer {
   
	public Executer(String script) throws Exception {
		super(script);
	}

	public Executer(File file) throws Exception {
		super(file);
	}

	public Executer(RoutineNode routine) throws Exception {
		super(routine);
	}

	@Override
	protected void execute(BuiltinRoutineNode routine) throws Exception {
		Engine.instance().push(routine);
		invoke(routine);
		Engine.instance().pop(routine);
	}

	private void invoke(BuiltinRoutineNode routine) throws Exception {
		if (routine.name().equals(ROUTINE_TIME)) {
			time(routine);
		} else if (routine.name().equals(ROUTINE_SLEEP)) {
			sleep(routine);
		} else if (routine.name().equals(ROUTINE_EQUALS)) {
			equals(routine);
		} else if (routine.name().equals(ROUTINE_FIND)) {
			find(routine);
		} else if (routine.name().equals(ROUTINE_LENGTH)) {
			length(routine);
		} else {
			throw new Exception("unknow builtin routine name");
		}		
	}

	private void time(BuiltinRoutineNode routine) throws Exception {
		routine.result(new ConstOperand(System.currentTimeMillis()));
	}

	private void sleep(BuiltinRoutineNode routine) throws Exception {
		Operand operand = (Operand)Engine.instance().findAuto("@timeout");
		if (null != operand) {			
			MachineManager.instance().suspend();
			Thread.sleep(operand.longValue());
			MachineManager.instance().resume();
			return;
		}
		throw new Exception("invalid routine argument");
	}

	private void equals(BuiltinRoutineNode routine) throws Exception {
		Operand operand1 = (Operand)Engine.instance().findAuto("@string1");
		Operand operand2 = (Operand)Engine.instance().findAuto("@string2");
		if (null != operand1 && null != operand2) {
			String string1 = operand1.strValue();
			String string2 = operand2.strValue();
			routine.result(new ConstOperand(string1.equals(string2)));
			return;
		}
		throw new Exception("invalid routine argument");
	}

	private void find(BuiltinRoutineNode routine) throws Exception {
		Operand operand1 = (Operand)Engine.instance().findAuto("@string1");
		Operand operand2 = (Operand)Engine.instance().findAuto("@string2");
		if (null != operand1 && null != operand2) {
			String string1 = operand1.strValue();
			String string2 = operand2.strValue();
			routine.result(new ConstOperand(string1.indexOf(string2)));
			return;
		}
		throw new Exception("invalid routine argument");
	}

	private void length(BuiltinRoutineNode routine) throws Exception {
		Operand operand = (Operand)Engine.instance().findAuto("@string");
		if (null != operand) {
			routine.result(new ConstOperand(operand.strValue().length())); return;
		}
		throw new Exception("invalid routine argument");
	}

	private static final String ROUTINE_TIME = "$time";
	private static final String ROUTINE_SLEEP = "$sleep";
	private static final String ROUTINE_EQUALS = "$equals";
	private static final String ROUTINE_FIND = "$find";
	private static final String ROUTINE_LENGTH = "$length";
	
	private static Map<String, List<String>> routines = new HashMap<String, List<String>>() {{
			put(ROUTINE_TIME, new ArrayList<String>());
			put(ROUTINE_SLEEP, new ArrayList<String>() {{add("@timeout");}});
			put(ROUTINE_EQUALS, new ArrayList<String>() {{add("@string1"); add("@string2");}});
			put(ROUTINE_FIND, new ArrayList<String>() {{add("@string1"); add("@string2");}});
			put(ROUTINE_LENGTH, new ArrayList<String>() {{add("@string");}});
		}};
   
	public static Map<String, List<String>> routines() {
		return routines;
	}
}
