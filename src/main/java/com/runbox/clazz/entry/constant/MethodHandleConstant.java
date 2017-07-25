package com.runbox.clazz.entry.constant;

import com.runbox.clazz.reader.ConstantReader;

import javax.json.JsonObjectBuilder;

public class MethodHandleConstant extends Constant {

    public MethodHandleConstant(ConstantReader reader) {
        super(reader, TYPE_METHOD_HANDLE);
    }
    
    public MethodHandleConstant(ConstantReader reader, short kind, int index) {
        super(reader, TYPE_METHOD_HANDLE);
        this.kind = kind;
        this.index = index;
    }

	public static final short REF_GETFIELD = (short)1;
	public static final short REF_GETSTATIC = (short)2;
	public static final short REF_PUTFIELD = (short)3;
	public static final short REF_PUTSTATIC = (short)4;
	public static final short REF_INVOKEVIRTUAL = (short)5;
	public static final short REF_INVOKESTATIC = (short)6;
	public static final short REF_INVOKESPECIAL = (short)7;
	public static final short REF_NEWINVOKESPECIAL = (short)8;
	public static final short REF_INVOKEINTERFACE = (short)9;
	
    private short kind = 0;

    public MethodHandleConstant kind(short kind) {
        this.kind = kind; return this;
    }

    public short kind() {
        return kind;
    }

	private int index = 0;
	
    public MethodHandleConstant index(int index) {
        this.index = index;
        return this;
    }

    public int index() {
        return index;
    }

    @Override
    public JsonObjectBuilder toJson() {
        return super.toJson().add("kind", kind).add("index", index);
    }
}
