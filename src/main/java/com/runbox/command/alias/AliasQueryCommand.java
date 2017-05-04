package com.runbox.command.alias;

import java.util.List;

import com.sun.tools.javac.util.Pair;

import com.runbox.manager.AliasManager;

public class AliasQueryCommand extends AliasCommand {

    public AliasQueryCommand(String command) throws Exception {
        super(command);        
    }

    @Override
    public boolean execute() throws Exception {
        System.out.printf("%-5s%-24s%s\n", "#", "command", "alias");
		List<Pair<String, String>> list = AliasManager.instance().get();		
		int index = 0; for (Pair<String, String> pair : list) {			
			System.out.printf("%-5d%-24s%s\n", index++, pair.fst, pair.snd);
		}
        return super.execute();
    }
}