package com.runbox.script.statement.node;

import java.util.List;
import java.util.LinkedList;

import com.runbox.script.statement.token.Token;

public class RoutineNode extends BlockNode {

    public RoutineNode(String name, BlockNode block) {
        super(name);
		if (null != block) {
			block.append(this);
		}
    }    		    

	private List<String> names = new LinkedList<String>();
	
	public void arguments(List<String> names) {
		this.names = names;
	}

	public List<String> arguments() {
		return names;
	}	
	
	public int count() {
		return names.size();
	}	

    private Token result = null;

	public void result(Token result) {
		this.result = result;
	}

	public Token result() {
		return result;
	}	
}
