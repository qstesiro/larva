package com.runbox.debug.command.source;

import java.util.Map;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.SourceManager;

public class SourceQueryCommand extends Command {

    public SourceQueryCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        Map<Integer, String> map = SourceManager.instance().paths();
		if (0 < map.size()) {
			int index = 0;
			System.out.printf("%-5s%-5s%s\n", "#", "id", "path");
			for (Integer id : map.keySet()) {
				System.out.printf("%-5d%-5d%s\n", index++, id, map.get(id));				
			}
		}
        return super.execute();
    }
}
