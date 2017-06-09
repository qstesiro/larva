package com.runbox.command.config;

import com.runbox.manager.ConfigManager;

import com.runbox.script.expression.Expression;
import com.runbox.script.expression.ExpressionFactory;
import com.runbox.script.expression.token.Token;

public class ConfigSetCommand extends ConfigCommand {

	public ConfigSetCommand(String command) throws Exception {
		super(command);
		if (null != argument()) {
			values = ExpressionFactory.build(argument()).execute();
			if (MAX == values.size()) {
				name = name(); return;
			}
		}
		throw new Exception("invalid argument");
	}

	private Expression.Values<? extends Token> values = null;
	
	@Override
	public boolean execute() throws Exception {
		if (null != name && MAX == values.size()) {
			if (name.equals(ConfigManager.LINE) && values.isInteger(VALUE)) {
				ConfigManager.instance().line(values.getInteger(VALUE));
			} else if (name.equals(ConfigManager.BYTECODE) && values.isBoolean(VALUE)) {
				ConfigManager.instance().bytecode(values.getBoolean(VALUE));
			} else {
				throw new Exception("invalid config name");
			}
		}
		return super.execute();
	}

	private static final int NAME = 0;
	private static final int VALUE = 1;
	private static final int MAX = 2;
	
	private String name = null;
	
	private String name() throws Exception {
		if (null != values && NAME < values.size()) {
			String name = values.getString(NAME);
			if (!name.equals("")) {
				return name.trim().toLowerCase();
			}
		}
		return null;
	}	
}
