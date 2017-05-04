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

    @Override
    public boolean execute() throws Exception {
        if (map()) {            
			return super.execute();
        }
        throw new Exception("invalid operand");
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
				System.out.printf("%-8s%d\n", "size:", pairs().size());
			} else {
				System.out.printf("%-8s%s\n", "value:", "null");
			}
		}
	}
	
	@Override
    protected void printElements() throws Exception {
		if (null != operand()) {
			int i = 0; for (Pair pair : pairs()) {
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

    protected List<Pair> pairs() throws Exception {
        if (hash()) {
            return hashPairs();
        } else if (table()) {
            return tablePairs();
        } else if (identity()) {
            return identityPairs();
        } else if (linked()) {
            return linkedPairs();
        } else if (tree()) {
            return treePairs();
        } else if (weak()) {
            return weakPairs();
        }
        throw new Exception("can not recognize template");
    }    

    private List<Pair> hashPairs() throws Exception {
        List<Pair> pairs = new LinkedList<Pair>();
        Operand table = field("table");
		if (null != table) {
			for (int i = 0; i < ((ArrayReference)table.value()).length(); ++i) {
				ArrayOperand operand = new ArrayOperand((ArrayReference)table.value(), i);
				if (null != operand.value()) {
					Operand key = new FieldOperand((ObjectReference)operand.value(), "key");
					Operand value = new FieldOperand((ObjectReference)operand.value(), "value");
					pairs.add(new Pair(key, value));
				}
			}
		}
        return pairs;
    }

    private List<Pair> tablePairs() throws Exception {
        List<Pair> pairs = new LinkedList<Pair>();
        Operand table = field("table");
		if (null != table) {
			for (int i = 0; i < ((ArrayReference)table.value()).length(); ++i) {
				ArrayOperand operand = new ArrayOperand((ArrayReference)table.value(), i);
				if (null != operand.value()) {					
					Operand key = new FieldOperand((ObjectReference)operand.value(), "key");
					Operand value = new FieldOperand((ObjectReference)operand.value(), "value");
					pairs.add(new Pair(key, value));
				}
			}
		}
        return pairs;
    }

    private List<Pair> identityPairs() throws Exception {
        List<Pair> pairs = new LinkedList<Pair>();
        Operand table = field("table");
		if (null != table) {
			for (int i = 0; i < ((ArrayReference)table.value()).length(); i += 2) {
				Operand key = new ArrayOperand((ArrayReference)table.value(), i);
				Operand value = new ArrayOperand((ArrayReference)table.value(), i + 1);				
				pairs.add(new Pair(key, value));				
			}
		}
        return pairs;
    }

    private List<Pair> linkedPairs() {        
		List<Pair> pairs = new LinkedList<Pair>();
        Operand element = field("head");
		if (null != element) {
			while (null != element.value()) {
				Operand key = new FieldOperand((ObjectReference)element.value(), "key");
				Operand value = new FieldOperand((ObjectReference)element.value(), "value");
				pairs.add(new Pair(key, value));
				element = new FieldOperand((ObjectReference)element.value(), "next");
			}
		}
        return pairs;
    }

    private List<Pair> treePairs() {
        List<Pair> pairs = new LinkedList<Pair>();
        return pairs;
    }

    private List<Pair> weakPairs() {
        List<Pair> pairs = new LinkedList<Pair>();
        return pairs;
    }

    private boolean map() throws Exception {
        boolean condition = superClass("java.util.HashMap");
        condition = condition || superClass("java.util.Hashtable");
        condition = condition || superClass("java.util.IdentityHashMap");
        condition = condition || superClass("java.util.LinkedHashMap");
        condition = condition || superClass("java.util.TreeMap");
        condition = condition || superClass("java.util.WeakHashMap");
        return condition;
    }

    private boolean hash() throws Exception {
        if (superClass("java.util.HashMap")) {
            boolean condition = exist("table");
            condition = condition && exist("entrySet");
            condition = condition && exist("size");
            condition = condition && exist("modCount");
            condition = condition && exist("threshold");
            condition = condition && exist("loadFactor");
            return condition;
        }
        return false;
    }

    private boolean table() throws Exception {
        if (superClass("java.util.Hashtable")) {
            boolean condition = exist("table");
            condition = condition && exist("count");
            condition = condition && exist("threshold");
            condition = condition && exist("loadFactor");
            condition = condition && exist("modCount");
            return condition;
        }
        return false;
    }

    private boolean identity() throws Exception {
        if (superClass("java.util.IdentityHashMap")) {
            boolean condition = exist("table");
            condition = condition && exist("size");
            condition = condition && exist("modCount");
            condition = condition && exist("NULL_KEY");
            return condition;
        }
        return false;
    }

    private boolean linked() throws Exception {
        if (superClass("java.util.LinkedHashMap")) {
            boolean condition = exist("head");
            condition = condition && exist("tail");
            condition = condition && exist("accessOrder");
            return condition;
        }
        return false;
    }

    private boolean tree() throws Exception {
        if (superClass("java.util.TreeMap")) {
            boolean condition = exist("comparator");
            condition = condition && exist("root");
            condition = condition && exist("size");
            condition = condition && exist("modCount");
            return condition;
        }
        return false;
    }

    private boolean weak() throws Exception {
        if (superClass("java.util.WeakHashMap")) {
            boolean condition = exist("table");
            condition = condition && exist("size");
            condition = condition && exist("threshold");
            condition = condition && exist("loadFactor");
            condition = condition && exist("queue");
            condition = condition && exist("modCount");
            return condition;
        }
        return false;
    }

    private static class Pair {

		public Pair() {
			
		}
		
		public Pair(Operand key, Operand value) {
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
