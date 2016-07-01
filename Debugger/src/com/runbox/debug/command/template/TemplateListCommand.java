package com.runbox.debug.command.template;

import com.runbox.debug.parser.expression.token.operand.ArrayOperand;
import com.runbox.debug.parser.expression.token.operand.ConstOperand;
import com.runbox.debug.parser.expression.token.operand.FieldOperand;
import com.runbox.debug.parser.expression.token.operand.Operand;
import com.sun.jdi.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/6/1.
 */
public class TemplateListCommand extends TemplateCommand {

    public TemplateListCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        if (list()) {
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
        if (array()) {
            return arrayEntries();
        } else if (linked()) {
            return linkedEntries();
        }
        throw new Exception("can not recognize template");
    }

    private List<Operand> arrayEntries() throws Exception {
        List<Operand> entries = new LinkedList<>();
        Operand array = field("elementData");
        Operand size = field("size");
        for (int i = 0; i < ((IntegerValue)size.value()).value(); ++i) {
            entries.add(new ConstOperand(new ArrayOperand((ArrayReference)array.value(), i).value()));
        }
        return entries;
    }

    private List<Operand> linkedEntries() throws Exception {
        List<Operand> entries = new LinkedList<>();
        Operand entry = field("first");
        while (null != entry.value()) {
            entries.add(new FieldOperand((ObjectReference)entry.value(), "item"));
            entry = new FieldOperand((ObjectReference)entry.value(), "next");
        }
        return entries;
    }

    private boolean list() throws Exception {
        boolean condition = superClass("java.util.ArrayList");
        condition = condition || superClass("java.util.LinkedList");
        return condition;
    }

    private boolean array() throws Exception {
        if (superClass("java.util.ArrayList")) {
            boolean condition = exist("elementData");
            condition = condition && exist("size");
            return condition;
        }
        return false;
    }

    private boolean linked() throws Exception {
        if (superClass("java.util.LinkedList")) {
            boolean condition = exist("first");
            condition = condition && exist("last");
            condition = condition && exist("size");
            return condition;
        }
        return false;
    }
}
