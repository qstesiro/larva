package com.runbox.debug.command.source;

import java.util.LinkedList;
import java.util.List;

import com.runbox.debug.manager.SourceManager;
import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;

public class SourceDeleteCommand extends SourceCommand {

    public SourceDeleteCommand(String command) throws Exception {
        super(command);        
    }

    @Override
    public boolean execute() throws Exception {
		List<Integer> ids = ids();
		if (null != ids) {
			for (Integer id : ids) {
				SourceManager.instance().delete(id);
			}        
		} else {
			SourceManager.instance().delete();
		}
        return super.execute();
    }

	public List<Integer> ids() throws Exception {
		if (null != argument()) {
			Expression.Values<Operand> values = new Expression(argument()).execute();
			if (null != values) {
				List<Integer> ids = new LinkedList<Integer>();
				for (int i = 0; i < values.size(); ++i) {
					ids.add(values.getInteger(i));
				}            
				return ids;
			}
		}
		return null;
	}       
}
