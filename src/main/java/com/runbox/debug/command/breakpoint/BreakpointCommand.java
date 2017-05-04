package com.runbox.debug.command.breakpoint;

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

public class BreakpointCommand extends Command {

    public BreakpointCommand(String command) throws Exception {
        super(command); 
		if (null != argument()) {
			values = new Expression(argument()).execute();
		}
    }

	private Expression.Values<Operand> values = null;
	
    protected String clazz() throws Exception {
		if (this instanceof BreakpointMethodCommand) {
			if (null != values) {
				String value = values.getString(0);
				int left = value.lastIndexOf("(");
				int right = value.lastIndexOf(")");
				if (-1 != left && -1 != right) {
					int index = value.lastIndexOf(".", left);
					if (-1 != index) {
						return clazz(value.substring(0, index).trim());
					}
				}
			}
		} else if (this instanceof BreakpointLineCommand) {
			if (null != values) {
				String value = values.getString(0);
				int index = value.lastIndexOf(":");
				if (-1 != index) {
					return clazz(value.substring(0, index).trim());
				}
			}				
		} else if (this instanceof BreakpointAccessCommand ||
				   this instanceof BreakpointModifyCommand) {
			if (null != values) {
				String value = values.getString(0);
				int index = value.lastIndexOf(".");
				if (-1 != index) {
					return clazz(value.substring(0, index).trim());
				}
			}
		}                        
        throw new Exception("invalid argument -> " + argument());
    }

    protected String clazz(String clazz) throws Exception {        
        String path = ImportManager.instance().find(clazz);			
		return (null != path ? path + "." + clazz : clazz);
    }  

    protected String method() throws Exception {		
		if (null != values) {
			String value = values.getString(0);
			int left = value.lastIndexOf("(");
			int right = value.lastIndexOf(")");
			if (-1 != left && -1 != right) {
				int index = value.lastIndexOf(".", left);
				if (-1 != index) {
					return value.substring(index + 1, left).trim();
				}
			}
		}		
        throw new Exception("invalid argument -> " + argument());
    }

    protected List<String> arguments() throws Exception {		
		if (null != values) {	
			String value = values.getString(0);
			int left = value.lastIndexOf("("); int right = value.lastIndexOf(")");
			if (-1 != left && -1 != right) {
				List<String> arguments = new LinkedList<String>();            
				if (left + 1 < right) {
					for (String argument : value.substring(left + 1, right).split(",", -1)) {
						argument = argument.trim();
						if (argument.equals("")) {
							throw new Exception("invalid method argument -> " + argument());
						}
						String path = ImportManager.instance().find(argument);
					    if (null != path) {
							argument = path + "." + argument;
						}
						arguments.add(argument);
					}
				}
				return arguments;
			}				
		}        
        throw new Exception("invalid argument -> " + argument());
    }
    
    protected String field() throws Exception {		
		if (null != values) {
			String value = values.getString(0);
			int index = value.lastIndexOf(".");
			if (-1 != index) {
				return value.substring(index + 1).trim();
			}
		}
        throw new Exception("invalid argument -> " + argument()); 
    }

    protected int line() throws Exception {		
		if (null != values) {
			String value = values.getString(0);
			int index = value.lastIndexOf(":");
			if (-1 != index) {
				return Integer.valueOf(value.substring(index + 1).trim());
			}
		}
        throw new Exception("invalid argument -> " + argument());
    }

    public List<Integer> ids() throws Exception {
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
