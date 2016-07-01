package com.runbox.debug.command.template;

import com.runbox.debug.parser.expression.token.operand.ArrayOperand;
import com.runbox.debug.parser.expression.token.operand.FieldOperand;
import com.runbox.debug.parser.expression.token.operand.Operand;
import com.sun.jdi.ArrayReference;
import com.sun.jdi.ObjectReference;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by qstesiro
 */
public class TemplateMapCommand extends TemplateCommand {

    public TemplateMapCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        if (map()) {
            print(entries());
        }
        return super.execute();
    }

    private void print(List<Entry> entries) throws Exception {
        int index = 0;
        System.out.println("index\tkey -> value");
        for (Entry entry : entries) {
            System.out.println(index++ + "\t" + format(entry.key, entry.value));
        }
    }

    private List<Entry> entries() throws Exception {
        if (hash()) {
            return hashEntries();
        } else if (table()) {
            return tableEntries();
        } else if (identity()) {
            return identityEntries();
        } else if (linked()) {
            return linkedEntries();
        } else if (tree()) {
            return treeEntries();
        } else if (weak()) {
            return weakEntries();
        }
        throw new Exception("can not recognize template");
    }

    private String format(Operand key, Operand value) throws Exception {
        return format(key) + " -> " + format(value);
    }

    private List<Entry> hashEntries() throws Exception {
        List<Entry> entries = new LinkedList<>();
        Operand table = field("table");
        for (int i = 0; i < ((ArrayReference)table.value()).length(); ++i) {
            ArrayOperand operand = new ArrayOperand((ArrayReference)table.value(), i);
            if (null != operand.value()) {
                Entry entry = new Entry();
                entry.key = new FieldOperand((ObjectReference)operand.value(), "key");
                entry.value = new FieldOperand((ObjectReference)operand.value(), "value");
                entries.add(entry);
            }
        }
        return entries;
    }

    private List<Entry> tableEntries() throws Exception {
        List<Entry> entries = new LinkedList<>();
        Operand table = field("table");
        for (int i = 0; i < ((ArrayReference)table.value()).length(); ++i) {
            ArrayOperand operand = new ArrayOperand((ArrayReference)table.value(), i);
            if (null != operand.value()) {
                Entry entry = new Entry();
                entry.key = new FieldOperand((ObjectReference)operand.value(), "key");
                entry.value = new FieldOperand((ObjectReference)operand.value(), "value");
                entries.add(entry);
            }
        }
        return entries;
    }

    private List<Entry> identityEntries() throws Exception {
        List<Entry> entries = new LinkedList<>();
        Operand table = field("table");
        for (int i = 0; i < ((ArrayReference)table.value()).length(); i += 2) {
            ArrayOperand key = new ArrayOperand((ArrayReference)table.value(), i);
            ArrayOperand value = new ArrayOperand((ArrayReference)table.value(), i + 1);
            if (null != key.value() && null != value.value()) {
                Entry entry = new Entry();
                entry.key = key;
                entry.value = value;
                entries.add(entry);
            }
        }
        return entries;
    }

    private List<Entry> linkedEntries() {
        List<Entry> entries = new LinkedList<>();
        return entries;
    }

    private List<Entry> treeEntries() {
        List<Entry> entries = new LinkedList<>();
        return entries;
    }

    private List<Entry> weakEntries() {
        List<Entry> entries = new LinkedList<>();
        return entries;
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

    private static class Entry {

        public Operand key;
        public Operand value;
    }
}
