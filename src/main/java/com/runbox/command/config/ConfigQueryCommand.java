package com.runbox.command.config;

import java.util.Map;

import com.runbox.manager.ConfigManager;

public class ConfigQueryCommand extends ConfigCommand {

	public ConfigQueryCommand(String command) throws Exception {
		super(command);
	}

	@Override
	public boolean execute() throws Exception {
		Map<String, String> map = ConfigManager.instance().get();
		System.out.printf("%-5s%-10s%s\n", "#", "name", "value");
		int i = 0; for (String key : map.keySet()) {
			System.out.printf("%-5d%-10s%s\n", i++, key, map.get(key));
		}
		return super.execute();
	}
}
