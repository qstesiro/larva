package com.runbox.command.imports;

import com.runbox.manager.ImportManager;
import com.runbox.script.expression.Expression;
import com.runbox.script.expression.ExpressionFactory;

public class ImportClassCommand extends ImportCommand {

	public ImportClassCommand(String command) throws Exception {
		super(command); 
		if (null != argument()) {
            value = ExpressionFactory.build(argument()).execute().getString(0);
			return;
        }
		throw new Exception("invalid operand");
	}

	private String value = null;

	@Override
	public boolean execute() throws Exception {
		ImportManager.instance().append(clazz(), path());
		return super.execute();
	}

	private String clazz = null;

	private String clazz() throws Exception {
		if (null != value) {			
			if (!value.equals("")) {
				int index = value.lastIndexOf("."); 
				if (-1 != index) {
					return value.substring(index + 1).trim();
				}
				return value;
			}
		}
		throw new Exception("invalid empty operand");
	}

	private String path() throws Exception {
		if (null != value) {			
			if (!value.equals("")) {
				int index = value.lastIndexOf("."); 
				if (-1 != index) {
					return value.substring(0, index).trim();
				}
				return "";
			}
		}
		throw new Exception("invalid empty operand");
	}	
}
