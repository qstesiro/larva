package com.runbox.command;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.runbox.manager.AliasManager;
import com.runbox.command.alias.*;
import com.runbox.command.imports.*;

public class CommandFactory {        

    public static Command build(String command) throws Exception {
		String key = AliasManager.instance().find(key(command));
		if (null != key) command = key + " " + (null != argument(command) ? argument(command) : "");
		if (null == key) key = key(command);
		if (key.equals(ALIAS_DEFINE)) {
			return new AliasDefineCommand(command);
		} else if (key.equals(ALIAS_DELETE)) {
			return new AliasDeleteCommand(command);
		} else if (key.equals(ALIAS_QUERY)) {
			return new AliasQueryCommand(command);
		} if (key.equals(IMPORT_CLASS)) {
			return new ImportClassCommand(command);
		} else if (key.equals(IMPORT_DELETE)) {
			return new ImportDeleteCommand(command);
		} else if (key.equals(IMPORT_QUERY)) {
			return new ImportQueryCommand(command);
		} else if (com.runbox.debug.command.CommandFactory.command(command)) {
			return com.runbox.debug.command.CommandFactory.build(command);
		}		
        return null;
    }

    public final static int COMMAND_ALIAS = 1;
    public final static String ALIAS_DEFINE = "alias.define";
    public final static String ALIAS_DELETE = "alias.delete";
    public final static String ALIAS_QUERY = "alias.query";

	public final static int COMMAND_IMPORT = COMMAND_ALIAS + 1;
	public final static String IMPORT_CLASS = "import.class";
	public final static String IMPORT_DELETE = "import.delete";
	public final static String IMPORT_QUERY = "import.query";  
    
    private static List<String> commands = new LinkedList<String>() {{     
        // alias command 
        add(ALIAS_DEFINE);
        add(ALIAS_DELETE);
        add(ALIAS_QUERY);
		// import command
		add(IMPORT_CLASS);
		add(IMPORT_DELETE);
		add(IMPORT_QUERY);                
    }};	
	
    public static boolean command(String command) throws Exception {
		String key = key(command);
        for (String entry : commands) {
            if (entry.equals(key)) {
                return true;
            }
        }
		if (com.runbox.debug.command.CommandFactory.command(command)) {
			return true;
		}
        return false;
    }

	public static String key(String command) {
		command = command.trim();
		int index = command.indexOf(' ');
		if (-1 != index) {
			return command.substring(0, index).trim().toLowerCase();			
		}
		return command;
	}

	public static String argument(String command) {
		command = command.trim();
		int index = command.indexOf(' ');
		if (-1 != index) {
			return command.substring(index + 1).trim();
		}
		return null;
	}
}
