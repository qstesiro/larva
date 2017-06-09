package com.runbox.command.config;

import com.runbox.manager.ConfigManager;

import com.runbox.script.expression.Expression;
import com.runbox.script.expression.ExpressionFactory;
import com.runbox.script.expression.token.Token;

public class ConfigQueryCommand extends ConfigCommand {

	public ConfigQueryCommand(String command) throws Exception {
		super(command);
		if (null != argument()) {
			name = ExpressionFactory.build(argument()).execute().getString(0);			
		}		
	}

	private String name = null;
	
	@Override
	public boolean execute() throws Exception {
		if (null != name) print(name); else print();		
		return super.execute();
	}

	private void print(String name) throws Exception {
		if (null != name) {
			String value = null;
			if (name.equals(ConfigManager.IP)) {
				System.out.println(name + ": " + ConfigManager.instance().ip());
			} else if (name.equals(ConfigManager.PORT)) {
				System.out.println(name + ": " + ConfigManager.instance().port());
			} else if (name.equals(ConfigManager.SCRIPT)) {
				System.out.println(name + ": " + ConfigManager.instance().script());
			} else if (name.equals(ConfigManager.MODE)) {
				System.out.println(name + ": " + ConfigManager.instance().mode());
			} else if (name.equals(ConfigManager.LINE)) {
				System.out.println(name + ": " + ConfigManager.instance().line());
			} else if (name.equals(ConfigManager.BYTECODE)) {
				System.out.println(name + ": " + ConfigManager.instance().bytecode());
			} else {
				throw new Exception("invalid config name");
			}
		}
	}

	private void print() {
		int i = 0; System.out.printf("%-5s%-10s%s\n", "#", "name", "value");
		System.out.printf("%-5d%-10s%s\n", i++, ConfigManager.IP, ConfigManager.instance().ip());
		System.out.printf("%-5d%-10s%s\n", i++, ConfigManager.PORT, ConfigManager.instance().port());
		System.out.printf("%-5d%-10s%s\n", i++, ConfigManager.SCRIPT, ConfigManager.instance().script());
		System.out.printf("%-5d%-10s%s\n", i++, ConfigManager.MODE, ConfigManager.instance().mode());
		System.out.printf("%-5d%-10s%d\n", i++, ConfigManager.LINE, ConfigManager.instance().line());
		System.out.printf("%-5d%-10s%b\n", i++, ConfigManager.BYTECODE, ConfigManager.instance().bytecode());
	}
}
