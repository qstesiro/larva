package com.runbox.command.alias;

import java.util.Map;

import com.runbox.manager.AliasManager;

public class AliasQueryCommand extends AliasCommand {

    public AliasQueryCommand(String command) throws Exception {
        super(command);        
    }

    @Override
    public boolean execute() throws Exception {
        System.out.printf("%-5s%-24s%s\n", "#", "command", "alias");
		Map<String, String> alias = AliasManager.instance().get();		
		int i = 0; for (String key : alias.keySet()) {			
			System.out.printf("%-5d%-24s%s\n", i++, alias.get(key), key);
		}
        return super.execute();
    }
}
