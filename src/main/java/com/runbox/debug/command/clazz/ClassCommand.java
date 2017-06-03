package com.runbox.debug.command.clazz;

import java.util.List;
import java.util.LinkedList;
import java.util.Vector;

import com.sun.jdi.ByteValue;
import com.sun.jdi.CharValue;
import com.sun.jdi.ShortValue;
import com.sun.jdi.IntegerValue;
import com.sun.jdi.StringReference;

import com.runbox.manager.ImportManager;
import com.runbox.debug.command.Command;
import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;

public class ClassCommand extends Command {
	
    public ClassCommand(String command) throws Exception {
        super(command);
		if (this instanceof ClassMonitorPrepareCommand ||
			this instanceof ClassMonitorUnloadCommand ||
			this instanceof ClassMonitorQueryCommand ||
			this instanceof ClassMonitorEnableCommand ||
			this instanceof ClassMonitorDisableCommand ||
			this instanceof ClassMonitorDeleteCommand ||
			this instanceof ClassConstantCommand) {
			if (null != argument()) {
				values = new Expression(argument()).execute();
			}
		}
    }	
	
	private Expression.Values<Operand> values = null;
	
    protected String clazz() throws Exception {
	    if (this instanceof ClassMonitorPrepareCommand ||
			this instanceof ClassMonitorUnloadCommand ||
			this instanceof ClassConstantCommand) {
			if (null != values) {
				return clazz(values.getString(0));
			}
		}
        throw new Exception("invalid operand");
    }   

	protected String clazz(String clazz) throws Exception {
		String path = ImportManager.instance().find(clazz);			
		return (null != path ? path + "." + clazz : clazz);
	} 

	protected String type() throws Exception {
		if (this instanceof ClassMonitorQueryCommand) {
			if (null != values) {
				return values.getString(0);
			}
			return null;
		}
		throw new Exception("invalid operand");
	}
	
    protected List<Integer> ids() throws Exception {
		if (this instanceof ClassMonitorEnableCommand ||
			this instanceof ClassMonitorDisableCommand ||
			this instanceof ClassMonitorDeleteCommand) {
			if (null != values) {
				List<Integer> ids = new LinkedList<Integer>();
				for (int i = 0; i < values.size(); ++i) {
					ids.add(values.getInteger(i));
				}
				return ids;
			}
		}
		throw new Exception("invalid operand");
	}	
}
