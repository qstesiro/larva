package com.runbox.clazz.entry.attribute;

public class LocalVariableTable extends LocalTable {

    public LocalVariableTable(long offset) {
        super(offset, "LocalVariableTable");
    }
    
    public LocalVariableTable(long offset, Variable[] variables) {
        super(offset, "LocalVariableTable", variables);
    }    
}
