package com.runbox.script;

import java.io.File;

import java.util.List;
import java.util.Map;

import com.runbox.script.statement.node.RoutineNode;

public class ExecuterFactory {

	public static Executer build(String script) throws Exception {
		return new com.runbox.debug.script.Executer(script);
	}

	public static Executer build(File file) throws Exception {
		return new com.runbox.debug.script.Executer(file);
	}

	public static Executer build(RoutineNode routine) throws Exception {
		return new com.runbox.debug.script.Executer(routine);
	}

	public static Map<String, List<String>> routines() {
		return com.runbox.debug.script.Executer.routines();
	}
}
