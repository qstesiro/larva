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
		if (null != argument()) {
			values = new Expression(argument()).execute();
		}		
    }

	public static String CLASS = "class";
	
	private Expression.Values<Operand> values = null;
	
    protected String clazz() throws Exception {
		if (this instanceof ClassMonitorQueryCommand) {
			if (null != values) {
				return clazz(values.getString(0));
			}
			return null;
		} else if (this instanceof ClassMonitorPrepareCommand ||
				   this instanceof ClassMonitorUnloadCommand) {
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
		if (null != values) {
			return values.getString(0);
		}
		return null;
	}
	
    protected List<Integer> ids() throws Exception {		
        if (null != values) {
            List<Integer> ids = new LinkedList<Integer>();
			for (int i = 0; i < values.size(); ++i) {
				ids.add(values.getInteger(i));
			}            
			return ids;
		}
		return null;
	}	
}
