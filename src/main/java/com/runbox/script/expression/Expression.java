package com.runbox.script.expression;

import java.util.Stack;

import com.runbox.script.expression.token.Token;

public interface Expression {    

    Values<? extends Token> execute() throws Exception;
    
    public abstract class Values<T extends Token> extends Stack<T> {

        public abstract byte getByte(int index) throws Exception;
        public abstract short getShort(int index) throws Exception;
        public abstract int getInteger(int index) throws Exception;
        public abstract long getLong(int index) throws Exception;
        public abstract float getFloat(int index) throws Exception;
        public abstract double getDouble(int index) throws Exception;
        public abstract boolean getBoolean(int index) throws Exception;
        public abstract String getString(int index) throws Exception;
    }    
}
