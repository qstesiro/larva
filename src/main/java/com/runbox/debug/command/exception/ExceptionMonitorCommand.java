package com.runbox.debug.command.exception;

import java.util.List;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.ArrayType;

import com.runbox.manager.ImportManager;
import com.runbox.debug.command.Command;
import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.manager.ExceptionManager;
import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.ArrayOperand;

public class ExceptionMonitorCommand extends Command {

	public ExceptionMonitorCommand(String command) throws Exception {
		super(command);
		if (null != argument()) {
			values = new Expression(argument()).execute();
			clazz = clazz(); caught = caught(); uncaught = uncaught();
		}
	}

	private Expression.Values<Operand> values = null;
	
	@Override
	public boolean execute() throws Exception {
		if (null != clazz) {
            List<ReferenceType> classes = MachineManager.instance().allClasses();			
            for (ReferenceType type : classes) {
				if (!(type instanceof ArrayType)) {
					if (type.name().equals(clazz)) {
						ExceptionManager.instance().append(type, caught, uncaught);
					}
				}
            }
        } 
		return super.execute();
	}

	private static final int CLASS = 0;
	private static final int CAUGHT = 1;
	private static final int UNCAUGHT = 2;
	private static final int MAX = 3;

	public String clazz = null;

	private String clazz() throws Exception {
		if (null != values && CLASS < values.size()) {
			String clazz = values.getString(CLASS);
			if (!clazz.equals("")) {
				return clazz(clazz);
			}
		}
		throw new Exception("invalid operand");
	}

	private String clazz(String clazz) throws Exception {
		String path = ImportManager.instance().find(clazz);			
		return (null != path ? path + "." + clazz : clazz);
	} 

	public boolean caught = true;

	private boolean caught() throws Exception {
		if (null != values && CAUGHT < values.size()) {
			return values.getBoolean(CAUGHT);
		}
		return true;
	}

	public boolean uncaught = true;

	private boolean uncaught() throws Exception {
		if (null != values && UNCAUGHT < values.size()) {
			return values.getBoolean(UNCAUGHT);
		}
		return true;
	}
}
