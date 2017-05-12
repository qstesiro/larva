package com.runbox.clazz.entry.attribute;

public class LocalVariableTypeTable extends LocalTable {

    public LocalVariableTypeTable(long offset) {
        super(offset, "LocalVariableTypeTable");
    }
    
    public LocalVariableTypeTable(long offset, Variable[] variables) {
        super(offset, "LocalVariableTypeTable", variables);
    }
}
