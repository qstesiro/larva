package com.runbox.command.imports;

import java.util.List;
import java.util.LinkedList;
import java.util.Vector;

import com.sun.jdi.StringReference;

import com.runbox.manager.ImportManager;

import com.runbox.script.expression.Expression;
import com.runbox.script.expression.ExpressionFactory;

public class ImportDeleteCommand extends ImportCommand {

	public ImportDeleteCommand(String command) throws Exception {
		super(command); 
		if (null != argument()) {
            clazz = ExpressionFactory.build(argument()).execute().getString(0);
        }		
	}

	private String clazz = null;	

	@Override
	public boolean execute() throws Exception {
		if (null != clazz) {
			ImportManager.instance().delete(clazz);
		} else {
			ImportManager.instance().delete(null);
		}
		return super.execute();
	}
}
