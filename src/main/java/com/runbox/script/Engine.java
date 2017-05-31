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

    private static Engine instance = new Engine();

    public static Engine instance() {
        return instance;
    }    		

	public boolean execute(String script) throws Exception {
		return ExecuterFactory.build(script).execute();
	}

	public boolean execute(File file) throws Exception {
		return ExecuterFactory.build(file).execute();
	}

	public boolean execute(RoutineNode routine) throws Exception {
		return ExecuterFactory.build(routine).execute();
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
