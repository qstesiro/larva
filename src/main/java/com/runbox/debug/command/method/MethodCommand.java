package com.runbox.debug.command.method;

import java.util.List;
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
            int index = value.lastIndexOf(".");
            if (-1 != index) {
                return clazz(value.substring(0, index).trim());
            }
        }
        throw new Exception("invalid empty operand");
    }

    protected String clazz(String clazz) throws Exception {
		String path = ImportManager.instance().find(clazz);			
		return (null != path ? path + "." + clazz : clazz);
	}    

    protected String method() throws Exception {
        if (null != value) {
            int index = value.lastIndexOf(".");
            if (-1 != index) {
                return clazz(value.substring(index + 1).trim());
            }
        }
        throw new Exception("invalid empty operand");
    }   	
}
