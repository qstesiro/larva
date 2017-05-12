package com.runbox.clazz.reader;

import java.io.DataInputStream;

public class MethodReader extends MemberReader {

    public MethodReader(DataInputStream stream, int count, ConstantReader reader) throws Exception {
        super(stream, count, reader);
    }

    @Override
    protected MethodReader load() throws Exception {
        return (MethodReader)super.load();
    }    
}
