package com.runbox.debug.command.template;

import java.util.List;
import java.util.LinkedList;

import com.sun.jdi.*;

import com.runbox.debug.script.expression.token.operand.ArrayOperand;
import com.runbox.debug.script.expression.token.operand.Operand;

public class TemplateSetCommand extends TemplateCommand {

    public TemplateSetCommand(String command) throws Exception {
        super(command);
    }    	

	private static final String SET = "java.util.Set";
	private static final String HASH_SET = "java.util.HashSet";
	private static final String LINKED_HASH_SET = "java.util.LinkedHashSet";
	private static final String TREE_SET = "java.util.TreeSet";
	private static final String COPY_ON_WRITE_ARRAY_SET = "java.util.concurrent.CopyOnWriteArraySet";
	private static final String CONCURRENT_SKIP_LIST_SET = "java.util.concurrent.ConcurrentSkipListSet";	
	
	@Override
    protected boolean type() throws Exception {
        return superInterface(SET);
    }	

	@Override
    protected List<Operand> elements() throws Exception {
        if (superClass(HASH_SET)) {
			return hashElements();
        } else if (superClass(LINKED_HASH_SET)) {
            return linkedHashElements();
        } else if (superClass(TREE_SET)) {
			return treeElements();
		} else if (superClass(COPY_ON_WRITE_ARRAY_SET)) {
			return copyOnWriteArrayElements();
		} else if (superClass(CONCURRENT_SKIP_LIST_SET)) {
			return concurrentSkipListElements();
		}
        throw new Exception("can not recognize template");
    }	

	private List<Operand> hashElements() throws Exception {
		List<Operand> operands = new LinkedList<Operand>();
		return operands;
	}

	private List<Operand> linkedHashElements() throws Exception {
		List<Operand> operands = new LinkedList<Operand>();
		return operands;
	}

	private List<Operand> treeElements() throws Exception {
		List<Operand> operands = new LinkedList<Operand>();
		return operands;
	}
   	
	private List<Operand> copyOnWriteArrayElements() throws Exception {
		List<Operand> operands = new LinkedList<Operand>();
		return operands;
	}

	private List<Operand> concurrentSkipListElements() throws Exception {
		List<Operand> operands = new LinkedList<Operand>();
		return operands;
	}
}
