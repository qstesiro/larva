package com.runbox.debug.command.source;

import java.util.Map;

import com.runbox.debug.manager.SourceManager;

public class SourceQueryCommand extends SourceCommand {

    public SourceQueryCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        Map<Integer, String> paths = SourceManager.instance().paths();
		if (0 < paths.size()) {			
			System.out.printf("%-5s%-5s%s\n", "#", "id", "path");
			int i = 0; for (Integer id : paths.keySet()) {
				System.out.printf("%-5d%-5d%s\n", i++, id, paths.get(id));
			}
		}
        return super.execute();
    }
}
