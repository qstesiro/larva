package com.runbox.dex.reader;

import java.io.DataInputStream;
import java.io.IOException;

import java.util.List;
import java.util.LinkedList;

import com.runbox.dex.entry.constant.Constant;

public class ConstantReader extends Reader {

    public ConstantReader(DataInputStream stream, int count) throws Exception { 
        super(stream, null);
		constants = new Constant[count];
		constants[0] = new Constant(offset(), this);
    }

    private Constant[] constants = null;

    public Constant[] get() {
        return constants;
    }

    public Constant get(int index) {
        if (null != constants) {
            if (index < constants.length) {
                return constants[index];
            }
        }
        return null;
    }

    public int count() {
        if (null != constants) {
            return constants.length;
        }
        return 0;
    }
    
    @Override
    protected ConstantReader load() throws Exception {
		if (null != constants) {
			for (int i = 1; i < constants.length; ++i) {
				short type = readU1();
				constants[i] = load(type);	
			}		   
		}
        return this;
    }	
	
    private Constant load(short type) throws Exception {
        switch (type) {
        default: throw new Exception("unknow constant type #" + type);
        }            
    }    
}
