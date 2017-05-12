package com.runbox.clazz.reader;

import java.io.DataInputStream;
import java.io.IOException;

import java.util.List;
import java.util.LinkedList;

import com.runbox.clazz.entry.constant.Constant;
import com.runbox.clazz.entry.constant.ClassConstant;
import com.runbox.clazz.entry.constant.FieldRefConstant;
import com.runbox.clazz.entry.constant.MethodRefConstant;
import com.runbox.clazz.entry.constant.InterfaceMethodRefConstant;
import com.runbox.clazz.entry.constant.StringConstant;
import com.runbox.clazz.entry.constant.IntegerConstant;
import com.runbox.clazz.entry.constant.FloatConstant;
import com.runbox.clazz.entry.constant.LongConstant;
import com.runbox.clazz.entry.constant.DoubleConstant;
import com.runbox.clazz.entry.constant.NameTypeConstant;
import com.runbox.clazz.entry.constant.UTF8Constant;
import com.runbox.clazz.entry.constant.MethodHandleConstant;
import com.runbox.clazz.entry.constant.MethodTypeConstant;
import com.runbox.clazz.entry.constant.InvokeDynamicConstant;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

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

    public ClassConstant getClass(int index) {
        Constant constant = get(index);
        if (constant instanceof ClassConstant) {
            return (ClassConstant)constant;
        }
        return null;
    }  

    public FieldRefConstant getFieldRef(int index) {
        Constant constant = get(index);
        if (constant instanceof FieldRefConstant)  {
            return (FieldRefConstant)constant;
        }
        return null;
    }  

    public MethodRefConstant getMethodRef(int index) {
        Constant constant = get(index);
        if (constant instanceof MethodRefConstant)  {
            return (MethodRefConstant)constant;
        }
        return null;
    }

    public InterfaceMethodRefConstant getInterfaceMethodRef(int index) {
        Constant constant = get(index);
        if (constant instanceof InterfaceMethodRefConstant) {
            return (InterfaceMethodRefConstant)constant;
        }
        return null;
    }

    public StringConstant getString(int index) {
        Constant constant = get(index);
        if (constant instanceof StringConstant)  {
            return (StringConstant)constant;
        }
        return null;
    }

    public IntegerConstant getInteger(int index) {
        Constant constant = get(index);
        if (constant instanceof IntegerConstant)  {
            return (IntegerConstant)constant;
        }
        return null;
    }

    public LongConstant getLong(int index) {
        Constant constant = get(index);
        if (constant instanceof LongConstant)  {
            return (LongConstant)constant;
        }
        return null;
    }

    public FloatConstant getFloat(int index) {
        Constant constant = get(index);
        if (constant instanceof FloatConstant)  {
            return (FloatConstant)constant;
        }
        return null;
    }

    public DoubleConstant getDouble(int index) {
        Constant constant = get(index);
        if (constant instanceof DoubleConstant)  {
            return (DoubleConstant)constant;
        }
        return null;
    }

    public NameTypeConstant getNameType(int index) {
        Constant constant = get(index);
        if (constant instanceof NameTypeConstant)  {
            return (NameTypeConstant)constant;
        }
        return null;
    }

    public UTF8Constant getUTF8(int index) {
        Constant constant = get(index);
        if (constant instanceof UTF8Constant)  {
            return (UTF8Constant)constant;
        }
        return null;
    }

    public MethodHandleConstant getMethodHandle(int index) {
        Constant constant = get(index);
        if (constant instanceof MethodHandleConstant)  {
            return (MethodHandleConstant)constant;
        }
        return null;
    }

    public MethodTypeConstant getMethodType(int index) {
        Constant constant = get(index);
        if (constant instanceof MethodTypeConstant)  {
            return (MethodTypeConstant)constant;
        }
        return null;
    }

    public InvokeDynamicConstant getInvokeDynamic(int index) {
        Constant constant = get(index);
        if (constant instanceof InvokeDynamicConstant)  {
            return (InvokeDynamicConstant)constant;
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
				if (constants[i] instanceof LongConstant || constants[i] instanceof DoubleConstant) {
					constants[++i] = new Constant(offset(), this);
				}				
			}		   
		}
        return this;
    }	
	
    private Constant load(short type) throws Exception {
        switch (type) {
        case Constant.TYPE_CLASS: return new ClassConstant(offset(), this, readU2());
        case Constant.TYPE_FIELD_REF: return new FieldRefConstant(offset(), this, readU2(), readU2());
        case Constant.TYPE_METHOD_REF: return new MethodRefConstant(offset(), this, readU2(), readU2());
        case Constant.TYPE_INTERFACE_METHOD_REF: return new InterfaceMethodRefConstant(offset(), this, readU2(), readU2());
        case Constant.TYPE_STRING: return new StringConstant(offset(), this, readU2());	
        case Constant.TYPE_INTEGER: return new IntegerConstant(offset(), this, readInt());
        case Constant.TYPE_FLOAT: return new FloatConstant(offset(), this, readFloat());
        case Constant.TYPE_LONG: return new LongConstant(offset(), this, readLong());
        case Constant.TYPE_DOUBLE: return new DoubleConstant(offset(), this, readDouble());
        case Constant.TYPE_NAME_TYPE: return new NameTypeConstant(offset(), this, readU2(), readU2());
        case Constant.TYPE_UTF8: return new UTF8Constant(offset(), this, read(readU2()));
        case Constant.TYPE_METHOD_HANDLE: return new MethodHandleConstant(offset(), this, readU1(), readU2());
        case Constant.TYPE_METHOD_TYPE: return new MethodTypeConstant(offset(), this, readU2());
        case Constant.TYPE_INVOKE_DYNAMIC: return new InvokeDynamicConstant(offset(), this, readU2(), readU2());
        default: throw new Exception("unknow constant type #" + type);
        }            
    }    

	private void print() throws Exception {
		for (int i = 1; i < constants.length; ++i) {
			print(i, constants[i]);
			if (constants[i] instanceof LongConstant ||
				constants[i] instanceof DoubleConstant) {
				++i;
			}				
		}
	}
	
	private static final String FORMAT = "#%04x\t%-20s";

	private void print(int index, Constant constant) throws Exception {
		System.out.printf(FORMAT, index, constant.name());
		switch (constant.type()) {
        case Constant.TYPE_CLASS: System.out.printf("#%04x\n", ((ClassConstant)constant).index()); return;
        case Constant.TYPE_FIELD_REF: {
			FieldRefConstant ref = (FieldRefConstant)constant;
            System.out.printf("#%04x.#%04x\n", ref.clazz(), ref.index());
			return;
		}
        case Constant.TYPE_METHOD_REF: {
            MethodRefConstant ref = (MethodRefConstant)constant;
			System.out.printf("#%04x.#%04x\n", ref.clazz(), ref.index());
			return;
		}
        case Constant.TYPE_INTERFACE_METHOD_REF: {
            InterfaceMethodRefConstant ref = (InterfaceMethodRefConstant)constant;
			System.out.printf("#%04x.#%04x\n", ref.clazz(), ref.index());
			return;
		}
        case Constant.TYPE_STRING: System.out.printf("#%04x\n", ((StringConstant)constant).index()); return;
        case Constant.TYPE_INTEGER: System.out.printf("%04x\n", ((IntegerConstant)constant).value()); return;
        case Constant.TYPE_FLOAT: System.out.printf("%f\n", ((FloatConstant)constant).value()); return;
        case Constant.TYPE_LONG: System.out.printf("%08x\n", ((LongConstant)constant).value()); return;
        case Constant.TYPE_DOUBLE: System.out.printf("%f\n", ((DoubleConstant)constant).value()); return;
        case Constant.TYPE_NAME_TYPE: {
			NameTypeConstant nameType = (NameTypeConstant)constant;
			System.out.printf("#%04x.#%04x\n", nameType.nameIndex(), nameType.descriptor());
			return;
		}
        case Constant.TYPE_UTF8: {
			System.out.printf("%s\n", "\"" + ((UTF8Constant)constant).string() + "\"");
			return;
		}
        case Constant.TYPE_METHOD_HANDLE: {
            MethodHandleConstant handle = (MethodHandleConstant)constant;
			System.out.printf("%04x.#%04x\n", handle.kind(), handle.index());
			return;
		}
        case Constant.TYPE_METHOD_TYPE: System.out.printf("#%04x\n", ((MethodTypeConstant)constant).index()); return;
        case Constant.TYPE_INVOKE_DYNAMIC: {
            InvokeDynamicConstant invoke = (InvokeDynamicConstant)constant;
			System.out.printf("%04x.#%04x\n", invoke.bootstrapMethod(), invoke.nameType());
			return;
		}
        default: throw new Exception("unknow constant type #" + constant.type());
        }
	}	
	
    @Override
    public JsonObjectBuilder toJson() {
        if (null != constants) {
            JsonArrayBuilder builder = Json.createArrayBuilder();            
            for (int i = 0; i < constants.length; ++i) {
                builder.add(constants[i].toJson());
            }            
            return super.toJson().add("length", constants.length).add("array", builder);
        }
        return super.toJson();
    }
}
