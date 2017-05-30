package com.runbox.command;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.runbox.manager.AliasManager;
import com.runbox.command.alias.*;
import com.runbox.command.imports.*;

public class CommandFactory {        

    public static Command build(String string) throws Exception {
		Command command = new Command(convert(string));
		if (command.key().equals(ALIAS_DEFINE)) {
			return new AliasDefineCommand(command.command());
		} else if (command.key().equals(ALIAS_DELETE)) {
			return new AliasDeleteCommand(command.command());
		} else if (command.key().equals(ALIAS_QUERY)) {
			return new AliasQueryCommand(command.command());
		} if (command.key().equals(IMPORT_CLASS)) {
			return new ImportClassCommand(command.command());
		} else if (command.key().equals(IMPORT_DELETE)) {
			return new ImportDeleteCommand(command.command());
		} else if (command.key().equals(IMPORT_QUERY)) {
			return new ImportQueryCommand(command.command());
		} else if (com.runbox.debug.command.CommandFactory.command(command.command())) {
			return com.runbox.debug.command.CommandFactory.build(command.command());
		}		
        return null;
    }    		
	
    public static boolean command(String string) throws Exception {
		Command command = new Command(convert(string));
		for (String entry : commands) {
            if (entry.equals(command.key())) {
                return true;
            }
        }
		if (com.runbox.debug.command.CommandFactory.command(command.command())) {
			return true;
		}
        return false;
    }

	private static String convert(String string) throws Exception {
		Command command = new Command(string);		
		String key = AliasManager.instance().find(command.key());
		if (null != key) {
			return key + " " + (null != command.argument() ? command.argument() : "");
		}
		return command.command();
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
}
