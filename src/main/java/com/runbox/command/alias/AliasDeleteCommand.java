package com.runbox.command.alias;

import java.util.List;
import java.util.LinkedList;
import java.util.Vector;

import com.runbox.manager.AliasManager;

import com.runbox.script.expression.Expression;
import com.runbox.script.expression.ExpressionFactory;
import com.runbox.script.expression.token.Token;

public class AliasDeleteCommand extends AliasCommand {

    public AliasDeleteCommand(String command) throws Exception {
        super(command);
        if (null != argument()) {
            values = ExpressionFactory.build(argument()).execute();
        }
    }

    private Expression.Values<? extends Token> values = null;	

    @Override
    public boolean execute() throws Exception {
        List<String> names = names();
        if (null != names) {
            for (String name : names) {
                AliasManager.instance().delete(name);
            }
        } else {
            AliasManager.instance().delete();
        }
        return super.execute();
    }

    private List<String> names() throws Exception {
        if (null != values) {
            List<String> names = new LinkedList<String>();
            for (int i = 0; i < values.size(); ++i) {
				names.add(values.getString(i));
			}
			return names;
		}
		return null;
	}     
}
