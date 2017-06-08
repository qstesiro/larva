package com.runbox.debug.command.method;

import java.util.List;
import java.util.LinkedList;

import java.util.Vector;

import com.sun.jdi.StringReference;

import com.runbox.manager.ImportManager;

import com.runbox.debug.command.Command;
import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.ArrayOperand;

public class MethodCommand extends Command {

    public MethodCommand(String command) throws Exception {
        super(command);
        if (this instanceof MethodArgumentCommand ||
            this instanceof MethodLocalCommand ||
            this instanceof MethodBytecodeCommand) {
            if (null != argument()) {
				value = new Expression(argument()).execute().getString(0);
				return;
			}
			throw new Exception("invalid empty operand");
        }
    }

	private String value = null;
	
    protected String clazz() throws Exception {
        if (null != value) {			
			int left = value.lastIndexOf("(");
			int right = value.lastIndexOf(")");
			if (-1 != left && -1 != right) {
				int index = value.lastIndexOf(".", left);
				if (-1 != index) {
					return clazz(value.substring(0, index).trim());
				}
			} else if (-1 == left && -1 == right) {
				int index = value.lastIndexOf(".");
				if (-1 != index) {
					return clazz(value.substring(0, index).trim());
				}
			}            
        }
        throw new Exception("invalid empty operand");
    }

    protected String method() throws Exception {
        if (null != value) {
			int left = value.lastIndexOf("(");
			int right = value.lastIndexOf(")");
			if (-1 != left && -1 != right) {
				int index = value.lastIndexOf(".", left);
				if (-1 != index) {
					return value.substring(index + 1, left).trim();
				}
			} else if (-1 == left && -1 == right) {
				int index = value.lastIndexOf(".");
				if (-1 != index) {
					return clazz(value.substring(index + 1).trim());
				}
			}
        }
        throw new Exception("invalid empty operand");
    }

	protected List<String> arguments() throws Exception {
		if (null != value) {
			int left = value.lastIndexOf("("); int right = value.lastIndexOf(")");
			if (-1 != left && -1 != right) {
				List<String> arguments = new LinkedList<String>();
				if (left + 1 < right) {
					for (String argument : value.substring(left + 1, right).split(",", -1)) {
						arguments.add(convert(argument));
					}
				}
				return arguments;
			} else if (-1 == left && -1 == right) {
				return null;
			}
		}
        throw new Exception("invalid argument -> " + argument());
    }

	private String convert(String argument) throws Exception {
		argument = argument.trim();
		if (argument.equals("")) {
			throw new Exception("invalid method argument -> " + argument());
		}
		int index = argument.indexOf("[]");
		if (-1 != index) {
			argument = argument.substring(0, index);
		}
		String path = ImportManager.instance().find(argument);
		if (null != path) {
			argument = path + "." + argument;
		}
		if (-1 != index) {
			argument += "[]";
		}
		return argument;
	}
	
	protected String clazz(String clazz) throws Exception {
		String path = ImportManager.instance().find(clazz);
		return (null != path ? path + "." + clazz : clazz);
	}    
}
