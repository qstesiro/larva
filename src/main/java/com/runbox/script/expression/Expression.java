package com.runbox.script.expression;

import java.util.Stack;

import com.runbox.script.expression.token.Token;

public interface Expression {    

    Values<? extends Token> execute() throws Exception;
    
    public abstract class Values<T extends Token> extends Stack<T> {

		public abstract boolean isByte(int index) throws Exception;
        public abstract boolean isShort(int index) throws Exception;
        public abstract boolean isInteger(int index) throws Exception;
        public abstract boolean isLong(int index) throws Exception;
        public abstract boolean isFloat(int index) throws Exception;
        public abstract boolean isDouble(int index) throws Exception;
        public abstract boolean isBoolean(int index) throws Exception;
        public abstract boolean isString(int index) throws Exception;
		
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
