package com.runbox.script;

import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Stack;

import com.runbox.script.statement.Script;
import com.runbox.script.statement.token.StringLexer;
import com.runbox.script.statement.token.FileLexer;
import com.runbox.script.statement.node.*;
import com.runbox.script.expression.Expression;
import com.runbox.script.expression.ExpressionFactory;
import com.runbox.script.expression.token.Token;
import com.runbox.command.Command;
import com.runbox.command.CommandFactory;

public class Engine {

    private Engine() {

    }    

    private static Engine engine = new Engine();

    public static Engine instance() {
        return engine;
    }    		

	public boolean execute(String script) throws Exception {
		return new Executer(script).execute();
	}

	public boolean execute(File file) throws Exception {
		return new Executer(file).execute();
	}

	public boolean execute(RoutineNode routine) throws Exception {
		return new Executer(routine).execute();
	}

	private static class Executer {

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

		public boolean terminal() {
			return (null == next);		
		}
	}		
	
    private Stack<Frame> frames = new Stack<Frame>() {{
			push(new Frame(Script.instance().root(), 0));
		}};

	public BlockNode push(BlockNode block) {
		if (null != block) {
			Frame frame = frames.push(new Frame(block, autos.size()));
			if (null != frame) {
				return frame.block();
			}
		}
		return null;
	}

	public void pop(BlockNode block) {
		if (null != block) {
			while (!frames.empty()) {
				if (pop() == block) {
					break;
				}
			}
		}
	}
	
	private BlockNode pop() {
		if (!frames.empty()) {
			Frame frame = frames.pop();
			autos.setSize(frame.index());
			if (frame.block() instanceof RoutineNode) {
				autos.setSize(autos.size() - ((RoutineNode)frame.block()).count());
			}			
			return frame.block();
		}
		return null;
	}   	

    public RoutineNode findRoutine(String name) {
        if (null != name) {
			for (int i = frames.size() - 1; i >= 0; --i) {
				RoutineNode routine = frames.get(i).block().find(name);
                if (null != routine) {
                    return routine;
                }
			}
		}
		return null;
    }	
	
	private Stack<Token> autos = new Stack<Token>();

	public Token append(Token auto) {
		if (null != auto) {
			return autos.push(auto);
		}
		return null;
	}
	
	public Token findAuto(String name) {
		if (null != name) {
			for (int i = autos.size() - 1; i >= 0; --i) {
				Token auto = autos.get(i);
				if (auto.name().equals(name)) {
					return auto;
				}
			}
		}
		return null;
	}

	public List<Token> autos() {
		return autos;
	}

    private static class Frame {

		public Frame (BlockNode block, int index) {
			this.block = block;
			this.index = index;
		}
		
		BlockNode block = null;

		public void block(BlockNode block) {
			this.block = block;
		}

		public BlockNode block() {
			return block;
		}
		
		int index = 0;

		public void index(int index) {
			this.index = index;
		}

		public int index() {
			return index;
		}
	}       
}
