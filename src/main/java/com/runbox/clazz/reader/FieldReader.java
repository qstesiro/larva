package com.runbox.clazz.reader;

import java.io.DataInputStream;

public class FieldReader extends MemberReader {

    public FieldReader(DataInputStream stream, int count, ConstantReader reader) throws Exception {
        super(stream, count, reader);
    }

    @Override
    protected FieldReader load() throws Exception {
        return (FieldReader)super.load();
    }    
}
