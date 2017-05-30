package com.runbox.command.imports;

import java.util.List;
import java.util.LinkedList;
import java.util.Vector;

import com.sun.jdi.StringReference;

import com.runbox.manager.ImportManager;

import com.runbox.script.expression.Expression;
import com.runbox.script.expression.ExpressionFactory;
import com.runbox.script.expression.token.Token;

public class ImportDeleteCommand extends ImportCommand {

	public ImportDeleteCommand(String command) throws Exception {
		super(command); 
		if (null != argument()) {            
			values = ExpressionFactory.build(argument()).execute();
        }		
	}

	private Expression.Values<? extends Token> values = null;	

	@Override
	public boolean execute() throws Exception {
		List<String> classes = names();
        if (null != classes) {
            for (String clazz : classes) {
				ImportManager.instance().delete(clazz);
            }
        } else {
			ImportManager.instance().delete();
		}
		return super.execute();
	}

	private List<String> names() throws Exception {		
        if (null != values) {
            List<String> names = new LinkedList<String>();
            for (int i = 0; i < values.size(); ++i) {
				names.add(values.getString(i));
			}
			return names;
		}
		return null;
	}
}
