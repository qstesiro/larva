package com.runbox.debug.command.template;

import com.runbox.debug.parser.expression.token.operand.ArrayOperand;
import com.runbox.debug.parser.expression.token.operand.Operand;
import com.sun.jdi.ArrayReference;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/6/1.
 */
public class TemplateQueueCommand extends TemplateCommand {

    public TemplateQueueCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        if (queue()) {
            print(entries());
        }
        return super.execute();
    }

    private void print(List<Operand> entries) throws Exception {
        int index = 0;
        System.out.println("index\tentry");
        for (Operand operand : entries) {
            System.out.println(index++ + "\t" + format(operand));
        }
    }

    private List<Operand> entries() throws Exception {
        if (priority()) {
            return priorityEntries();
        } else if (array()) {
            return arrayEntries();
        }
        throw new Exception("can not recognize template");
    }

    private List<Operand> priorityEntries() throws Exception {
        List<Operand> entries = new LinkedList<>();
        Operand queue = field("queue");
        for (int i = 0; i < ((ArrayReference)queue.value()).length(); ++i) {
            ArrayOperand entry = new ArrayOperand((ArrayReference)queue.value(), i);
            if (null != entry.value()) {
                entries.add(entry);
            }
        }
        return entries;
    }

    private List<Operand> arrayEntries() throws Exception {
        List<Operand> entries = new LinkedList<>();
        Operand elements = field("elements");
        for (int i = 0; i < ((ArrayReference)elements.value()).length(); ++i) {
            ArrayOperand entry = new ArrayOperand((ArrayReference)elements.value(), i);
            if (null != entry.value()) {
                entries.add(entry);
            }
        }
        return entries;
    }

    private boolean queue() throws Exception {
        boolean condition = superClass("java.util.ArrayQueue");
        condition = condition || superClass("java.util.PriorityQueue");
        condition = condition || superClass("java.util.ArrayDeque");
        return condition;
    }

    private boolean priority() throws Exception {
        if (superClass("java.util.PriorityQueue")) {
            boolean condition = exist("queue");
            condition = condition && exist("size");
            condition = condition && exist("comparator");
            condition = condition && exist("modCount");
            return condition;
        }
        return false;
    }

    private boolean array() throws Exception {
        if (superClass("java.util.ArrayDeque")) {
            boolean condition = exist("elements");
            condition = condition && exist("head");
            condition = condition && exist("tail");
            return condition;
        }
        return false;
    }
}
