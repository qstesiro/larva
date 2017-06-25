package com.runbox.command.config;

import java.util.List;
import java.util.LinkedList;

import com.runbox.manager.ConfigManager;

import com.runbox.script.expression.Expression;
import com.runbox.script.expression.ExpressionFactory;
import com.runbox.script.expression.token.Token;

public class ConfigQueryCommand extends ConfigCommand {

	public ConfigQueryCommand(String command) throws Exception {
		super(command);
		if (null != argument()) {
			values = ExpressionFactory.build(argument()).execute();
		}
	}

	private Expression.Values<? extends Token> values = null;
	
	@Override
	public boolean execute() throws Exception {
		System.out.printf("%-5s%-10s%s\n", "#", "name", "value");
		List<String> names = names();
		if (0 < names.size()) print(names); else print();		
		return super.execute();
	}

	private List<String> names() throws Exception {
		List<String> names = new LinkedList<String>();
		if (null != values) {
			for (int i = 0; i < values.size(); ++i) {
				names.add(values.getString(i));
			}
		}
		return names;
	}

	private void print(List<String> names) throws Exception {
		if (null != names) {
			int i = 0; for (String name : names) {
				print(i, name);
			}
		}
	}
	
	private void print(int i, String name) throws Exception {
		if (null != name) {
			String value = null;
			if (name.equals(ConfigManager.IP)) {
				System.out.printf("%-5d%-10s%s\n", i++, ConfigManager.IP, ConfigManager.instance().ip());
			} else if (name.equals(ConfigManager.PORT)) {
				System.out.printf("%-5d%-10s%s\n", i++, ConfigManager.PORT, ConfigManager.instance().port());
			} else if (name.equals(ConfigManager.SCRIPT)) {
				System.out.printf("%-5d%-10s%s\n", i++, ConfigManager.SCRIPT, ConfigManager.instance().script());
			} else if (name.equals(ConfigManager.MODE)) {
				System.out.printf("%-5d%-10s%s\n", i++, ConfigManager.MODE, ConfigManager.instance().mode());
			} else if (name.equals(ConfigManager.BYTECODE)) {
				System.out.printf("%-5d%-10s%d\n", i++, ConfigManager.BYTECODE, ConfigManager.instance().bytecode());
			} else if (name.equals(ConfigManager.LINE)) {
				System.out.printf("%-5d%-10s%d\n", i++, ConfigManager.LINE, ConfigManager.instance().line());
			} else {
				throw new Exception("invalid config name");
			}
		}
	}

	private void print() {
		int i = 0;
		System.out.printf("%-5d%-10s%s\n", i++, ConfigManager.IP, ConfigManager.instance().ip());
		System.out.printf("%-5d%-10s%s\n", i++, ConfigManager.PORT, ConfigManager.instance().port());
		System.out.printf("%-5d%-10s%s\n", i++, ConfigManager.SCRIPT, ConfigManager.instance().script());
		System.out.printf("%-5d%-10s%s\n", i++, ConfigManager.MODE, ConfigManager.instance().mode());
		System.out.printf("%-5d%-10s%d\n", i++, ConfigManager.BYTECODE, ConfigManager.instance().bytecode());
		System.out.printf("%-5d%-10s%d\n", i++, ConfigManager.LINE, ConfigManager.instance().line());
	}
}
