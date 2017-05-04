package com.runbox.command.alias;

import java.util.Vector;

import com.sun.jdi.StringReference;

import com.runbox.command.Command;

import com.runbox.manager.AliasManager;

import com.runbox.script.expression.Expression;
import com.runbox.script.expression.ExpressionFactory;
import com.runbox.script.expression.token.Token;

public class AliasDefineCommand extends AliasCommand {

    public AliasDefineCommand(String command) throws Exception {
        super(command); 
        if (null != argument()) {
            values = ExpressionFactory.build(argument()).execute();
            this.name = name(); alias = alias();
            return;
        }
        throw new Exception("invalid operand");
    }

    private Expression.Values<? extends Token> values = null;	

    @Override
    public boolean execute() throws Exception {
        if (!AliasManager.instance().append(name, alias)) {
            throw new Exception("invalid alias -> " + alias);
        }
        return super.execute();
    }

    private static final int COMMAND = 0;
	private static final int ALIAS = 1;
	private static final int MAX = 2;	

    private String name = null;

    private String name() throws Exception {
        if (null != values && COMMAND < values.size()) {
            String name = values.getString(COMMAND);
			if (!name.equals("")) {
				return name;
			}
        }
        throw new Exception("inavlie command operand");
    }

    private String alias = null;

    private String alias() throws Exception {
        if (null != values && ALIAS < values.size()) {
            String alias = values.getString(ALIAS);
			if (!alias.equals("")) {
				return alias;
			}
        }
        throw new Exception("inavlie alias operand");
    }
}
