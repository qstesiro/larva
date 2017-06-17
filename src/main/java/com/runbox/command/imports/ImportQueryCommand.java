package com.runbox.command.imports;

import java.util.Map;

import com.runbox.manager.ImportManager;

public class ImportQueryCommand extends ImportCommand {

	public ImportQueryCommand(String command) throws Exception {
		super(command);
	}

	@Override
	public boolean execute() throws Exception {
		System.out.printf("%-5s%-32s%s\n", "#", "class", "package");
		Map<String, String> classes = ImportManager.instance().get();		
		int i = 0; for (String clazz : classes.keySet()) {
			String path = classes.get(clazz).equals("") ? "n/a" : classes.get(clazz);
			System.out.printf("%-5d%-32s%s\n", i++, clazz, path);
		}
		return super.execute();
	}	
}
