package com.runbox.script.statement.node;

import java.util.List;
import java.util.LinkedList;

public class BlockNode extends Node {

    public BlockNode(String name) {
        super(name);
    }

    public Node right(Node node) {
        return super.next(node);
    }

    public Node right() {
        return super.next();
	}

	private Node left = null;

    public Node left(Node node) {
        Node prev = left;
        left = node;
        return prev;
    }

    public Node left() {
        return left;
    }

	private List<RoutineNode> routines = new LinkedList<RoutineNode>();

	public boolean append(RoutineNode routine) {
        for (RoutineNode item : routines) {
            if (item.name().equals(routine.name())) {
                return false;
            }
        }
		routines.add(routine); 
        return true;		
	}	

	public RoutineNode find(String name) {
        for (RoutineNode item : routines) {
            if (item.name().equals(name)) {
                return item;
            }
        }        
        return null;
	}
}
