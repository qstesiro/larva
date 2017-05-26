package com.runbox.script.statement;

import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Stack;

import com.runbox.script.Engine;
import com.runbox.script.statement.token.StringLexer;
import com.runbox.script.statement.token.FileLexer;
import com.runbox.script.statement.node.*;
import com.runbox.script.expression.Expression;
import com.runbox.script.expression.ExpressionFactory;
import com.runbox.script.expression.token.Token;
import com.runbox.command.Command;
import com.runbox.command.CommandFactory;

public class Script {		
	
    public Script(String script) throws Exception {
        if (null != script && !script.equals("")) {
			next = Engine.instance().generate(script);
        }
    }

    public Script(File file) throws Exception {
        if (null != file && 0 < file.length()) {
            next = Engine.instance().generate(file);
        }
    }	
	
	public Script(RoutineNode routine) throws Exception {
		if (null != routine) {
			next = routine;
		}
	}		

	private Node next = null;

	public boolean terminal() {
        return (null == next);		
    }
		
	public boolean execute() throws Exception {
        while (null != next) {
			push(next);
            if (next instanceof ConditionNode) {
                execute((ConditionNode)next);
            } else if (next instanceof ExpressionNode) {
                execute((ExpressionNode)next);
            } else if (next instanceof CommandNode) {
				CommandNode command = (CommandNode)next;
                if (!execute(command)) {
					execute(command.end()); return false;
				}
            } else if (next instanceof ReturnNode) {
                execute((ReturnNode)next);
            } else if (next instanceof ContinueNode) {
				execute((ContinueNode)next);
			} else if (next instanceof EndNode) {
				execute((EndNode)next);
			} else {
                next = next.next();
            }
        }
		return true;
    }

	private void push(Node node) throws Exception {
		if ((node instanceof BlockNode) && !(node instanceof RootNode)) {
			Engine.instance().push((BlockNode)node);
		}
	}
	
    private void execute(ConditionNode node) throws Exception {
        Expression.Values<? extends Token> values = ExpressionFactory.build(node.condition()).execute();
		if (1 == values.size()) {			
			next = (values.getBoolean(0) ? node.right() : node.left());
			return;
		}		
        throw new Exception("invalid expression -> " + node.condition());
    }    

    private void execute(ExpressionNode node) throws Exception {
        ExpressionFactory.build(node.name()).execute();
        next = node.next();
    }

    private boolean execute(CommandNode node) throws Exception {
        Command command = CommandFactory.build(node.name());
		command.routine(node.routine());		
        next = node.next();
        return command.execute();
    }

	private void execute(ReturnNode node) throws Exception {
        if (null != node.routine()) {
			if (null != node.expression()) {
				Expression.Values<? extends Token> values = ExpressionFactory.build(node.expression()).execute();
				node.routine().result(values.firstElement());
			}
			next = node.next();
			return;
        }
        throw new Exception("invalid return statement -> " + node.name() + " " + node.expression());
    }

	private void execute(ContinueNode node) throws Exception {
		Engine.instance().pop((BlockNode)node.next());
		next = node.next();
	}
	
	private void execute(EndNode node) throws Exception {
		if (!(node.block() instanceof RootNode)) {
			Engine.instance().pop(node.block());
		}
		next = node.next();
	}
}
