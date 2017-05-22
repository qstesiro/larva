package com.runbox.debug.command.source;

import java.io.File;

import com.runbox.debug.manager.SourceManager;
import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;

public class SourceAppendCommand extends SourceCommand {

    public SourceAppendCommand(String command) throws Exception {
        super(command);
        if (null == argument()) {
			throw new Exception("invalid argument");
        }		
    }
	
    @Override
    public boolean execute() throws Exception {
		SourceManager.instance().append(path());
        return super.execute();
    }

    private String path() throws Exception {
        String path = new Expression(argument()).execute().getString(0);
        if ('\\' == path.charAt(path.length() - 1) ||
			'/' == path.charAt(path.length() - 1)) {
            return path.substring(0, path.length() - 1);
        }
        return path;
    }
}
