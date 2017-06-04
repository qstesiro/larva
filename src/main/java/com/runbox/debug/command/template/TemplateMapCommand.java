package com.runbox.debug.command.template;

import java.util.List;
import java.util.LinkedList;

import com.sun.jdi.*;

import com.runbox.debug.script.expression.token.operand.ArrayOperand;
import com.runbox.debug.script.expression.token.operand.FieldOperand;
import com.runbox.debug.script.expression.token.operand.Operand;

public class TemplateMapCommand extends TemplateCommand {

    public TemplateMapCommand(String command) throws Exception {
        super(command);
    }

	private static final String MAP = "java.util.Map";
	private static final String HASH_MAP = "java.util.HashMap";
	private static final String HASHTABLE = "java.util.Hashtable";
	private static final String IDENTITY_HASH_MAP = "java.util.IdentityHashMap";
	private static final String LINKED_HASH_MAP = "java.util.LinkedHashMap";
	private static final String TREE_MAP = "java.util.TreeMap";
	private static final String WEAK_HASH_MAP = "java.util.WeakHashMap";
	private static final String CONCURRENT_HASH_MAP = "java.util.concurrent.ConcurrentHashMap";
	private static final String CONCURRENT_SKIP_LIST_MAP = "java.util.concurrent.ConcurrentSkipListMap";
	
	@Override
    protected boolean type() throws Exception {
        return superInterface(MAP);
    }	

	private static final int FLAG_KEY_TYPE = FLAG_ELEMENT_TYPE;
	private static final int FLAG_VALUE_TYPE = FLAG_KEY_TYPE << 2;
	
	@Override
	protected void printDefault() throws Exception {
		if (null != operand()) {			
			System.out.printf("%-8s%s\n", "type:", operand().type().name());
			if (null != operand().value()) {
				System.out.printf("%-8s%s\n", "vtype:", operand().value().type().name());
				System.out.printf("%-8s%s\n", "object:", operand().value());
				System.out.printf("%-8s%d\n", "size:", entries().size());
			} else {
				System.out.printf("%-8s%s\n", "value:", "null");
			}
		}
	}
	
	@Override
    protected void printElements() throws Exception {
		if (null != operand()) {
			int i = 0; for (Entry pair : entries()) {
				System.out.printf("#%-7d", i++);
				print(pair.key(), FLAG_KEY_TYPE == (FLAG_KEY_TYPE & flags()));
				System.out.print(" -> ");
				print(pair.value(), FLAG_VALUE_TYPE == (FLAG_VALUE_TYPE & flags()));
				System.out.println();
			}
		}
    }
	
	private void print(Operand operand, boolean flag) throws Exception {
		if (null != operand) {
			if (null != operand.value()) {
				if (operand.value() instanceof StringReference) {
					String string = "instance of ";
					string += operand.type().name();
					string += "(" + "id=" + ((ObjectReference)operand.value()).uniqueID() + ")";
					System.out.print(string);
				} else {
					System.out.print(operand.value());
				}
				if (flag) System.out.print(" :" + operand.value().type().name());
			} else {
				System.out.print("null");
			}
		}
	}	
   
    private List<Entry> entries() throws Exception {
        if (superClass(HASH_MAP)) {
            return hashEntries();
        } else if (superClass(HASHTABLE)) {
            return tableEntries();
        } else if (superClass(IDENTITY_HASH_MAP)) {
            return identityEntries();
        } else if (superClass(LINKED_HASH_MAP)) {
            return linkedEntries();
        } else if (superClass(TREE_MAP)) {
            return treeEntries();
        } else if (superClass(WEAK_HASH_MAP)) {
            return weakEntries();
        } else if (superClass(CONCURRENT_HASH_MAP)) {
			return concurrentHashEntries();
		} else if (superClass(CONCURRENT_SKIP_LIST_MAP)) {
			return concurrentSkipListEntries();
		}
        throw new Exception("can not recognize template");
    }    	
	
    private List<Entry> hashEntries() throws Exception {
        List<Entry> entries = new LinkedList<Entry>();
        Operand table = field("table");
		if (null != table) {
			for (int i = 0; i < ((ArrayReference)table.value()).length(); ++i) {
				ArrayOperand operand = new ArrayOperand((ArrayReference)table.value(), i);
				if (null != operand.value()) {
					Operand key = new FieldOperand((ObjectReference)operand.value(), "key");
					Operand value = new FieldOperand((ObjectReference)operand.value(), "value");
					entries.add(new Entry(key, value));
				}
			}
		}
        return entries;
    }

    private List<Entry> tableEntries() throws Exception {
        List<Entry> entries = new LinkedList<Entry>();
        Operand table = field("table");
		if (null != table) {
			for (int i = 0; i < ((ArrayReference)table.value()).length(); ++i) {
				ArrayOperand operand = new ArrayOperand((ArrayReference)table.value(), i);
				if (null != operand.value()) {					
					Operand key = new FieldOperand((ObjectReference)operand.value(), "key");
					Operand value = new FieldOperand((ObjectReference)operand.value(), "value");
					entries.add(new Entry(key, value));
				}
			}
		}
        return entries;
    }

    private List<Entry> identityEntries() throws Exception {
        List<Entry> entries = new LinkedList<Entry>();
        Operand table = field("table");
		if (null != table) {
			for (int i = 0; i < ((ArrayReference)table.value()).length(); i += 2) {
				Operand key = new ArrayOperand((ArrayReference)table.value(), i);
				Operand value = new ArrayOperand((ArrayReference)table.value(), i + 1);				
				entries.add(new Entry(key, value));				
			}
		}
        return entries;
    }

    private List<Entry> linkedEntries() throws Exception {        
		List<Entry> entries = new LinkedList<Entry>();
        Operand element = field("head");
		if (null != element) {
			while (null != element.value()) {
				Operand key = new FieldOperand((ObjectReference)element.value(), "key");
				Operand value = new FieldOperand((ObjectReference)element.value(), "value");
				entries.add(new Entry(key, value));
				element = new FieldOperand((ObjectReference)element.value(), "next");
			}
		}
        return entries;
    }
	
    private List<Entry> treeEntries() {
        List<Entry> entries = new LinkedList<Entry>();
        return entries;
    }

    private List<Entry> weakEntries() {
        List<Entry> entries = new LinkedList<Entry>();
        return entries;
    }    

	private List<Entry> concurrentHashEntries() throws Exception {
		List<Entry> entries = new LinkedList<Entry>();
		return entries;
	}

	private List<Entry> concurrentSkipListEntries() throws Exception {
		List<Entry> entries = new LinkedList<Entry>();
		return entries;
	}
	
    private static class Entry {

		public Entry() {
			
		}
		
		public Entry(Operand key, Operand value) {
			this.key = key;
			this.value = value;
		}
		
        public Operand key = null;

		public void key(Operand key) {
			this.key = key;
		}

		public Operand key() {
			return key;
		}
		
        public Operand value = null;

		public void value(Operand value) {
			this.value = value;
		}

		public Operand value() {
			return value;
		}
    }
}
