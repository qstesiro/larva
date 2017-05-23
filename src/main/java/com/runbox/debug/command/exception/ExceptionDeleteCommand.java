package com.runbox.debug.command.exception;

import java.util.List;
import java.util.LinkedList;
import java.util.Collection;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.ExceptionManager;
import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;

public class ExceptionDeleteCommand extends Command {

	public ExceptionDeleteCommand(String command) throws Exception {
		super(command);
		if (null != argument()) {
			values = new Expression(argument()).execute();
		}
	}

	private Expression.Values<Operand> values = null;
	
	@Override
	public boolean execute() throws Exception {
		Collection<Integer> ids = ids();
        if (null != ids) {
            for (int id : ids) {
                ExceptionManager.instance().delete(id);
            }            
        } else {
            ExceptionManager.instance().delete();
        }
		return super.execute();
	}

	private List<Integer> ids() throws Exception {
        if (null != values) {
            List<Integer> ids = new LinkedList<Integer>();
			for (int i = 0; i < values.size(); ++i) {
				ids.add(values.getInteger(i));
			}            
			return ids;
		}
		return null;
	}	
}
