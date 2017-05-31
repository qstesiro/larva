package com.runbox.script;

import java.io.File;

import java.util.List;
import java.util.Map;

import com.runbox.script.statement.Script;
import com.runbox.script.statement.token.StringLexer;
import com.runbox.script.statement.token.FileLexer;
import com.runbox.script.statement.node.*;
import com.runbox.script.expression.Expression;
import com.runbox.script.expression.ExpressionFactory;
import com.runbox.script.expression.token.Token;
import com.runbox.command.Command;
import com.runbox.command.CommandFactory;

public abstract class Executer {

	public Executer(String script) throws Exception {
		if (null != script && !script.equals("")) {
			next = Script.instance().generate(script);
		}
	}

	public Executer(File file) throws Exception {
		if (null != file && 0 < file.length()) {
			next = Script.instance().generate(file);
		}
	}

	public Executer(RoutineNode routine) throws Exception {
		if (null != routine) {
			next = routine;
		}
	}

	private Node next = null;
		
	public boolean execute() throws Exception {
		if (!(next instanceof BuiltinRoutineNode)) {
			while (null != next) {
				push(next);
				if (next instanceof ConditionNode) {
					execute((ConditionNode)next);
				} else if (next instanceof CommandNode) {
					if (!execute((CommandNode)next)) return false;
				} else if (next instanceof UnsolvedNode) {
					if (!execute((UnsolvedNode)next)) return false;
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
		} else {
			execute((BuiltinRoutineNode)next);
		}
		return true;
	}	
		
	protected abstract void execute(BuiltinRoutineNode routine) throws Exception;

	private void push(Node node) throws Exception {
		if ((node instanceof BlockNode) && !(node instanceof RootNode)) {
			Engine.instance().push((BlockNode)node);
		}
	}
	
	private void execute(ConditionNode node) throws Exception {
		Expression.Values<? extends Token> values = ExpressionFactory.build(node.condition()).execute();
		if (1 == values.size()) {			
			next = (values.getBoolean(0) ? node.right() : node.left()); return;
		}		
		throw new Exception("invalid expression -> " + node.condition());
	}    

	private void execute(ExpressionNode node) throws Exception {
		ExpressionFactory.build(node.name()).execute();
		next = node.next();
	}

	private boolean execute(CommandNode node) throws Exception {
		Command command = CommandFactory.build(node.name());
		if (null != node.routine()) {
			command.routine(node.routine());
		}			
		if (!command.execute()) {			   
			execute(node.end()); return false;
		}
		next = node.next(); return true;
	}

	private boolean execute(UnsolvedNode node) throws Exception {
		if (CommandFactory.command(node.name())) {
			CommandNode command = new CommandNode(node.name());
			command.next(node.next());
			command.end(node.end());
			return execute(command);
		} else {
			ExpressionNode expression = new ExpressionNode(node.name());
			expression.next(node.next());
			execute(expression);
			return true;
		}			
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
