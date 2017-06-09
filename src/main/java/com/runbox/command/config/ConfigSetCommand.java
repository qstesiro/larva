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
			name = name(); value = value();
			return;
		}
		throw new Exception("invalid argument");
	}

	private Expression.Values<? extends Token> values = null;
	
	@Override
	public boolean execute() throws Exception {
		if (null != name && null != value) {			
			ConfigManager.instance().set(name, value);
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
				return name;
			}
		}
		return null;
	}

	private String value = null;

	private String value() throws Exception {
		if (null != values && VALUE < values.size()) {
			String value = values.getString(VALUE);
			if (!value.equals("")) {
				return value;
			}
		}
		return null;
	}
}
