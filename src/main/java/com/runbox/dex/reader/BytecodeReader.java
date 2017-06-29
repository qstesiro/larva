package com.runbox.dex.reader;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.io.IOException;

import java.nio.channels.FileChannel;

import com.runbox.dex.entry.constant.*;
import com.runbox.dex.entry.bytecode.*;

public class BytecodeReader extends Reader {

    public BytecodeReader(FileChannel channel, DexReader reader) throws Exception {
        super(channel, null, reader); 
		this.size = (int)channel.size();
		printer = new Printer(reader);
    }	
	
    private List<Bytecode> codes = new LinkedList<Bytecode>();	
	
    public List<Bytecode> get() {
        return codes;
    }

    public Bytecode get(long offset) {
        if (null != codes) {			
			for (Bytecode code : codes) {
				if ((long)offset == code.offset()) {
					return code;
				}
			}
        }
        return null;
    }	

    public int count() {
        if (null != codes) {
            return codes.size();
        }
        return 0;
    }
	
	private int size = 0;
    private int offset = 0;

	@Override
	protected byte[] read(int size) throws IOException {
		this.size -= size; offset += size;
        return super.read(size);
    }

	@Override
    protected short readU1() throws IOException {
		--size; ++offset;
        return super.readU1();
    }

	@Override
    protected int readU2() throws IOException {
		size -= SIZE2; offset += SIZE2;
        return super.readU2();        
    }

	@Override
    protected int readU4() throws Exception {
		size -= SIZE4; offset += SIZE4;
        return super.readU4();        
    }

	@Override
	protected byte readS1() throws IOException {
		--size; ++offset;
        return super.readS1();
	}

	@Override
	protected short readS2() throws IOException {
		size -= SIZE2; offset += SIZE2;
        return super.readS2();		
	}

	@Override
	protected int readS4() throws IOException {
		size -= SIZE4; offset += SIZE4;
        return super.readS4();
	}

	@Override
	protected long skip(long count) throws IOException {
		size -= count; offset += count;
        return super.skip(count);
	}

    @Override
    protected BytecodeReader load() throws Exception {
		while (0 < size) {
			Bytecode code = load(readS1(), offset - 1);			
			// System.out.printf("%d    [%02x]  %-24s\n", code.offset(), code.opcode(), code.name());
			codes.add(code);
		}
        return this;
    }    

    private Bytecode load(byte opcode, int offset) throws Exception {        
        switch (opcode) {
        case Bytecode.NOP: skip(SIZE1); return new Bytecode.NOP(offset);
		case Bytecode.MOVE: {byte v = readS1(); return new Bytecode.MOVE(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.MOVE_FROM16: return new Bytecode.MOVE_FROM16(offset, readU1(), readU2());
		case Bytecode.MOVE16: skip(SIZE1); return new Bytecode.MOVE16(offset, readU2(), readU2());
		case Bytecode.MOVE_WIDE: {byte v = readS1(); return new Bytecode.MOVE_WIDE(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.MOVE_WIDE_FROM16: return new Bytecode.MOVE_WIDE_FROM16(offset, readU1(), readU2());
		case Bytecode.MOVE_WIDE16: skip(SIZE1);  return new Bytecode.MOVE_WIDE16(offset, readU1(), readU2());
		case Bytecode.MOVE_OBJECT: {byte v = readS1(); return new Bytecode.MOVE_OBJECT(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.MOVE_OBJECT_FROM16: return new Bytecode.MOVE_OBJECT_FROM16(offset, readU1(), readU2());
		case Bytecode.MOVE_OBJECT16: skip(SIZE1); return new Bytecode.MOVE_OBJECT16(offset, readU2(), readU2());
		case Bytecode.MOVE_RESULT: return new Bytecode.MOVE_RESULT(offset, readU1());
		case Bytecode.MOVE_RESULT_WIDE: return new Bytecode.MOVE_RESULT_WIDE(offset, readU1());
		case Bytecode.MOVE_RESULT_OBJECT: return new Bytecode.MOVE_RESULT_OBJECT(offset, readU1());
		case Bytecode.MOVE_EXCEPTION: return new Bytecode.MOVE_EXCEPTION(offset, readU1());
		case Bytecode.RETURN_VOID: skip(SIZE1); return new Bytecode.RETURN_VOID(offset);
		case Bytecode.RETURN: return new Bytecode.RETURN(offset, readU1());
		case Bytecode.RETURN_WIDE: return new Bytecode.RETURN_WIDE(offset, readU1());
		case Bytecode.RETURN_OBJECT: return new Bytecode.RETURN_OBJECT(offset, readU1());
		case Bytecode.CONST4: {byte v = readS1(); return new Bytecode.CONST4(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.CONST16: return new Bytecode.CONST16(offset, readU1(), readS2());
		case Bytecode.CONST: return new Bytecode.CONST(offset, readU1(), readS4());
		case Bytecode.CONST_HIGH16: return new Bytecode.CONST_HIGH16(offset, readU1(), readU2() << 16);
		case Bytecode.CONST_WIDE16: return new Bytecode.CONST_WIDE16(offset, readU1(), readS2());
		case Bytecode.CONST_WIDE32: return new Bytecode.CONST_WIDE32(offset, readU1(), readS4());
		case Bytecode.CONST_WIDE: return new Bytecode.CONST_WIDE(offset, readU1(), readS8());
		case Bytecode.CONST_WIDE_HIGH16: return new Bytecode.CONST_WIDE_HIGH16(offset, readU1(), readS2() << 48);
		case Bytecode.CONST_STRING: {
			short vAA = readU1();
			int index = readU2();
			// String string = reader().getStringId(index).string();
			// return new Bytecode.CONST_STRING(offset, vAA, index, string);
			return new Bytecode.CONST_STRING(offset, vAA, index);
		}
		case Bytecode.CONST_STRING_JUMBO: {
			short vAA = readU1();
			int index = readU4();
			// String string = reader().getStringId(index).string();
			// return new Bytecode.CONST_STRING_JUMBO(offset, vAA, index, string);
			return new Bytecode.CONST_STRING_JUMBO(offset, vAA, index);
		}
		case Bytecode.CONST_CLASS: {
			short vAA = readU1();
			int index = readU2();
			// String clazz = reader().getTypeId(index).descriptor();
			// return new Bytecode.CONST_CLASS(offset, vAA, index, clazz);
			return new Bytecode.CONST_CLASS(offset, vAA, index);
		}
		case Bytecode.MONITOR_ENTER: return new Bytecode.MONITOR_ENTER(offset, readU1());
		case Bytecode.MONITOR_EXIT: return new Bytecode.MONITOR_EXIT(offset, readU1());
		case Bytecode.CHECK_CAST: {
			short vAA = readU1();
			int index = readU2();
			// String type = reader().getTypeId(index).descriptor();
			// return new Bytecode.CHECK_CAST(offset, vAA, index, type);
			return new Bytecode.CHECK_CAST(offset, vAA, index);
		}
		case Bytecode.INSTANCE_OF: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// String clazz = reader().getTypeId(index).descriptor();
			// return new Bytecode.INSTANCE_OF(offset, vA, vB, index, clazz);
			return new Bytecode.INSTANCE_OF(offset, vA, vB, index);
		}
		case Bytecode.ARRAY_LENGTH: {byte v = readS1(); return new Bytecode.ARRAY_LENGTH(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.NEW_INSTANCE: {
			short vAA = readU1();
			int index = readU2();
			// String clazz = reader().getTypeId(index).descriptor();
			// return new Bytecode.NEW_INSTANCE(offset, vAA, index, clazz);
			return new Bytecode.NEW_INSTANCE(offset, vAA, index);
		}
        case Bytecode.NEW_ARRAY: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// String type = reader().getTypeId(index).descriptor();
			// return new Bytecode.NEW_ARRAY(offset, vA, vB, index, type);
			return new Bytecode.NEW_ARRAY(offset, vA, vB, index);
		}
		case Bytecode.FILLED_NEW_ARRAY: {
            byte v = readS1();
			byte count = (byte)(v & 0xf0 >>> 4);
            int index = readU2();  
            short vp = readS2();
			// String type = reader().getTypeId(index).descriptor();
            // Bytecode.FILLED_NEW_ARRAY code = new Bytecode.FILLED_NEW_ARRAY(offset, count, index, type);
			Bytecode.FILLED_NEW_ARRAY code = new Bytecode.FILLED_NEW_ARRAY(offset, count, index);
            if (0 < code.count()) code.vC((byte)((vp >>> 12) & 0x000f));
            if (1 < code.count()) code.vD((byte)((vp >>> 8) & 0x000f));
            if (2 < code.count()) code.vE((byte)((vp >>> 4) & 0x000f));
            if (3 < code.count()) code.vF((byte)(vp & 0x000f));
            if (4 < code.count()) code.vG((byte)(v & 0x0f));
            return code;
        }
		case Bytecode.FILLED_NEW_ARRAY_RANGE: {
			short count = readU1();
			int index = readU2();
			int vCCCC = readU2();
			// String type = reader().getTypeId(index).descriptor();
			// return new Bytecode.FILLED_NEW_ARRAY_RANGE(offset, count, index, vCCCC, type);
			return new Bytecode.FILLED_NEW_ARRAY_RANGE(offset, count, index, vCCCC);
		}
		case Bytecode.FILL_ARRAY_DATA: return new Bytecode.FILL_ARRAY_DATA(offset, readU1(), readS4());
		case Bytecode.THROW: return new Bytecode.THROW(offset, readU1());
		case Bytecode.GOTO: return new Bytecode.GOTO(offset, readS1());
		case Bytecode.GOTO16: skip(SIZE1); return new Bytecode.GOTO16(offset, readS2());
		case Bytecode.GOTO32: skip(SIZE1); return new Bytecode.GOTO32(offset, readS4());
		case Bytecode.PACKED_SWITCH: return new Bytecode.PACKED_SWITCH(offset, readU1(), readS4());
		case Bytecode.SPARSE_SWITCH: return new Bytecode.SPARSE_SWITCH(offset, readU1(), readS4());
		case Bytecode.CMPL_FLOAT: return new Bytecode.CMPL_FLOAT(offset, readU1(), readU1(), readU1());
		case Bytecode.CMPG_FLOAT: return new Bytecode.CMPG_FLOAT(offset, readU1(), readU1(), readU1());
		case Bytecode.CMPL_DOUBLE: return new Bytecode.CMPL_DOUBLE(offset, readU1(), readU1(), readU1());
		case Bytecode.CMPG_DOUBLE: return new Bytecode.CMPG_DOUBLE(offset, readU1(), readU1(), readU1());
		case Bytecode.CMP_LONG: return new Bytecode.CMP_LONG(offset, readU1(), readU1(), readU1());				
		case Bytecode.IF_EQ: {byte v = readS1(); return new Bytecode.IF_EQ(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4), readS2());}        
		case Bytecode.IF_NE: {byte v = readS1(); return new Bytecode.IF_NE(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4), readS2());}        
		case Bytecode.IF_LT: {byte v = readS1(); return new Bytecode.IF_LT(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4), readS2());}        
		case Bytecode.IF_GE: {byte v = readS1(); return new Bytecode.IF_GE(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4), readS2());}        
		case Bytecode.IF_GT: {byte v = readS1(); return new Bytecode.IF_GT(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4), readS2());}        
		case Bytecode.IF_LE: {byte v = readS1(); return new Bytecode.IF_LE(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4), readS2());}        
		case Bytecode.IF_EQZ: return new Bytecode.IF_EQZ(offset, readU1(), readS2());
		case Bytecode.IF_NEZ: return new Bytecode.IF_NEZ(offset, readU1(), readS2());
		case Bytecode.IF_LTZ: return new Bytecode.IF_LTZ(offset, readU1(), readS2());
		case Bytecode.IF_GEZ: return new Bytecode.IF_GEZ(offset, readU1(), readS2());
		case Bytecode.IF_GTZ: return new Bytecode.IF_GTZ(offset, readU1(), readS2());
		case Bytecode.IF_LEZ: return new Bytecode.IF_LEZ(offset, readU1(), readS2());
		case Bytecode.AGET: return new Bytecode.AGET(offset, readU1(), readU1(), readU1());
		case Bytecode.AGET_WIDE: return new Bytecode.AGET_WIDE(offset, readU1(), readU1(), readU1());
		case Bytecode.AGET_OBJECT: return new Bytecode.AGET_OBJECT(offset, readU1(), readU1(), readU1());
		case Bytecode.AGET_BOOLEAN: return new Bytecode.AGET_BOOLEAN(offset, readU1(), readU1(), readU1());
		case Bytecode.AGET_BYTE: return new Bytecode.AGET_BYTE(offset, readU1(), readU1(), readU1());
		case Bytecode.AGET_CHAR: return new Bytecode.AGET_CHAR(offset, readU1(), readU1(), readU1());
		case Bytecode.AGET_SHORT: return new Bytecode.AGET_SHORT(offset, readU1(), readU1(), readU1());		
		case Bytecode.APUT: return new Bytecode.APUT(offset, readU1(), readU1(), readU1());
		case Bytecode.APUT_WIDE: return new Bytecode.APUT_WIDE(offset, readU1(), readU1(), readU1());
		case Bytecode.APUT_OBJECT: return new Bytecode.APUT_OBJECT(offset, readU1(), readU1(), readU1());
		case Bytecode.APUT_BOOLEAN: return new Bytecode.APUT_BOOLEAN(offset, readU1(), readU1(), readU1());
		case Bytecode.APUT_BYTE: return new Bytecode.APUT_BYTE(offset, readU1(), readU1(), readU1());
		case Bytecode.APUT_CHAR: return new Bytecode.APUT_CHAR(offset, readU1(), readU1(), readU1());
		case Bytecode.APUT_SHORT: return new Bytecode.APUT_SHORT(offset, readU1(), readU1(), readU1());
		case Bytecode.IGET: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.IGET(offset, vA, vB, index, field.clazz(), field.type(), field.name());
			return new Bytecode.IGET(offset, vA, vB, index);
		}
		case Bytecode.IGET_WIDE: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.IGET_WIDE(offset, vA, vB, index, field.clazz(), field.type(), field.name());
			return new Bytecode.IGET_WIDE(offset, vA, vB, index);
		}
		case Bytecode.IGET_OBJECT: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// FieldId field = reader().getFieldId(index);			
			// return new Bytecode.IGET_OBJECT(offset, vA, vB, index, field.clazz(), field.type(), field.name());
			return new Bytecode.IGET_OBJECT(offset, vA, vB, index);
		}
		case Bytecode.IGET_BOOLEAN: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.IGET_BOOLEAN(offset, vA, vB, index, field.clazz(), field.type(), field.name());
			return new Bytecode.IGET_BOOLEAN(offset, vA, vB, index);
		}
		case Bytecode.IGET_BYTE: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.IGET_BYTE(offset, vA, vB, index, field.clazz(), field.type(), field.name());
			return new Bytecode.IGET_BYTE(offset, vA, vB, index);
		}
		case Bytecode.IGET_CHAR: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.IGET_CHAR(offset, vA, vB, index, field.clazz(), field.type(), field.name());
			return new Bytecode.IGET_CHAR(offset, vA, vB, index);
		}
		case Bytecode.IGET_SHORT: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.IGET_SHORT(offset, vA, vB, index, field.clazz(), field.type(), field.name());
			return new Bytecode.IGET_SHORT(offset, vA, vB, index);
		}
		case Bytecode.IPUT: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.IPUT(offset, vA, vB, index, field.clazz(), field.type(), field.name());
			return new Bytecode.IPUT(offset, vA, vB, index);
		}
		case Bytecode.IPUT_WIDE: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.IPUT_WIDE(offset, vA, vB, index, field.clazz(), field.type(), field.name());
			return new Bytecode.IPUT_WIDE(offset, vA, vB, index);
		}
		case Bytecode.IPUT_OBJECT: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.IPUT_OBJECT(offset, vA, vB, index, field.clazz(), field.type(), field.name());
			return new Bytecode.IPUT_OBJECT(offset, vA, vB, index);
		}
		case Bytecode.IPUT_BOOLEAN: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.IPUT_BOOLEAN(offset, vA, vB, index, field.clazz(), field.type(), field.name());
			return new Bytecode.IPUT_BOOLEAN(offset, vA, vB, index);
		}
		case Bytecode.IPUT_BYTE: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.IPUT_BYTE(offset, vA, vB, index, field.clazz(), field.type(), field.name());
			return new Bytecode.IPUT_BYTE(offset, vA, vB, index);
		}
		case Bytecode.IPUT_CHAR: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.IPUT_CHAR(offset, vA, vB, index, field.clazz(), field.type(), field.name());
			return new Bytecode.IPUT_CHAR(offset, vA, vB, index);
		}
		case Bytecode.IPUT_SHORT: {
			byte v = readS1();
			byte vA = (byte)(v & 0x0f);
			byte vB = (byte)((v & 0xf0) >>> 4);
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.IPUT_SHORT(offset, vA, vB, index, field.clazz(), field.type(), field.name());
			return new Bytecode.IPUT_SHORT(offset, vA, vB, index);
		}
		case Bytecode.SGET: {
			short vAA = readU1();
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.SGET(offset, vAA, index, field.clazz(), field.type(), field.name());
			return new Bytecode.SGET(offset, vAA, index);
		}
		case Bytecode.SGET_WIDE: {
			short vAA = readU1();
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.SGET_WIDE(offset, vAA, index, field.clazz(), field.type(), field.name());
			return new Bytecode.SGET_WIDE(offset, vAA, index);
		}
		case Bytecode.SGET_OBJECT: {
			short vAA = readU1();
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.SGET_OBJECT(offset, vAA, index, field.clazz(), field.type(), field.name());
			return new Bytecode.SGET_OBJECT(offset, vAA, index);
		}
		case Bytecode.SGET_BOOLEAN: {
			short vAA = readU1();
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.SGET_BOOLEAN(offset, vAA, index, field.clazz(), field.type(), field.name());
			return new Bytecode.SGET_BOOLEAN(offset, vAA, index);
		}
		case Bytecode.SGET_BYTE: {
			short vAA = readU1();
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.SGET_BYTE(offset, vAA, index, field.clazz(), field.type(), field.name());
			return new Bytecode.SGET_BYTE(offset, vAA, index);
		}
		case Bytecode.SGET_CHAR: {
			short vAA = readU1();
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.SGET_CHAR(offset, vAA, index, field.clazz(), field.type(), field.name());
			return new Bytecode.SGET_CHAR(offset, vAA, index);
		}
		case Bytecode.SGET_SHORT: {
			short vAA = readU1();
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.SGET_SHORT(offset, vAA, index, field.clazz(), field.type(), field.name());
			return new Bytecode.SGET_SHORT(offset, vAA, index);
		}
		case Bytecode.SPUT: {
			short vAA = readU1();
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.SPUT(offset, vAA, index, field.clazz(), field.type(), field.name());
			return new Bytecode.SPUT(offset, vAA, index);
		}
		case Bytecode.SPUT_WIDE: {
			short vAA = readU1();
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.SPUT_WIDE(offset, vAA, index, field.clazz(), field.type(), field.name());
			return new Bytecode.SPUT_WIDE(offset, vAA, index);
		}
		case Bytecode.SPUT_OBJECT: {
			short vAA = readU1();
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.SPUT_OBJECT(offset, vAA, index, field.clazz(), field.type(), field.name());
			return new Bytecode.SPUT_OBJECT(offset, vAA, index);
		}
		case Bytecode.SPUT_BOOLEAN: {
			short vAA = readU1();
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.SPUT_BOOLEAN(offset, vAA, index, field.clazz(), field.type(), field.name());
			return new Bytecode.SPUT_BOOLEAN(offset, vAA, index);
		}
		case Bytecode.SPUT_BYTE: {
			short vAA = readU1();
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.SPUT_BYTE(offset, vAA, index, field.clazz(), field.type(), field.name());
		}
		case Bytecode.SPUT_CHAR: {
			short vAA = readU1();
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.SPUT_CHAR(offset, vAA, index, field.clazz(), field.type(), field.name());
			return new Bytecode.SPUT_CHAR(offset, vAA, index);
		}
		case Bytecode.SPUT_SHORT: {
			short vAA = readU1();
			int index = readU2();
			// FieldId field = reader().getFieldId(index);
			// return new Bytecode.SPUT_SHORT(offset, vAA, index, field.clazz(), field.type(), field.name());
			return new Bytecode.SPUT_SHORT(offset, vAA, index);
		}
		case Bytecode.INVOKE_VIRTUAL: {
            byte v = readS1();
			byte count = (byte)(v & 0xf0 >>> 4);
            int index = readU2();  
            short vp = readS2();
			// MethodId method = reader().getMethodId(index);
            // Bytecode.INVOKE_VIRTUAL code = new Bytecode.INVOKE_VIRTUAL(offset, count, index,
			// 														   method.clazz(), method.name(), method.returnType(), method.parameterString());
			Bytecode.INVOKE_VIRTUAL code = new Bytecode.INVOKE_VIRTUAL(offset, count, index);
            if (0 < code.count()) code.vC((byte)((vp >>> 12) & 0x000f));
            if (1 < code.count()) code.vD((byte)((vp >>> 8) & 0x000f));
            if (2 < code.count()) code.vE((byte)((vp >>> 4) & 0x000f));
            if (3 < code.count()) code.vF((byte)(vp & 0x000f));
            if (4 < code.count()) code.vG((byte)(v & 0x0f));
            return code;
        }
		case Bytecode.INVOKE_SUPER: {
            byte v = readS1();
			byte count = (byte)(v & 0xf0 >>> 4);
            int index = readU2();  
            short vp = readS2();
			// MethodId method = reader().getMethodId(index);
            // Bytecode.INVOKE_SUPER code = new Bytecode.INVOKE_SUPER(offset, count, index,
			// 													   method.clazz(), method.name(), method.returnType(), method.parameterString());
			Bytecode.INVOKE_SUPER code = new Bytecode.INVOKE_SUPER(offset, count, index);
            if (0 < code.count()) code.vC((byte)((vp >>> 12) & 0x000f));
            if (1 < code.count()) code.vD((byte)((vp >>> 8) & 0x000f));
            if (2 < code.count()) code.vE((byte)((vp >>> 4) & 0x000f));
            if (3 < code.count()) code.vF((byte)(vp & 0x000f));
            if (4 < code.count()) code.vG((byte)(v & 0x0f));
            return code;
        }
		case Bytecode.INVOKE_DIRECT: {
            byte v = readS1();
			byte count = (byte)(v & 0xf0 >>> 4);
            int index = readU2();  
            short vp = readS2();
			// MethodId method = reader().getMethodId(index);
            // Bytecode.INVOKE_DIRECT code = new Bytecode.INVOKE_DIRECT(offset, count, index,
			// 														 method.clazz(), method.name(), method.returnType(), method.parameterString());
			Bytecode.INVOKE_DIRECT code = new Bytecode.INVOKE_DIRECT(offset, count, index);
            if (0 < code.count()) code.vC((byte)((vp >>> 12) & 0x000f));
            if (1 < code.count()) code.vD((byte)((vp >>> 8) & 0x000f));
            if (2 < code.count()) code.vE((byte)((vp >>> 4) & 0x000f));
            if (3 < code.count()) code.vF((byte)(vp & 0x000f));
            if (4 < code.count()) code.vG((byte)(v & 0x0f));
            return code;
        }
		case Bytecode.INVOKE_STATIC: {
            byte v = readS1();
			byte count = (byte)(v & 0xf0 >>> 4);
            int index = readU2();  
            short vp = readS2();
			// MethodId method = reader().getMethodId(index);
            // Bytecode.INVOKE_STATIC code = new Bytecode.INVOKE_STATIC(offset, count, index,
			// 														 method.clazz(), method.name(), method.returnType(), method.parameterString());
			Bytecode.INVOKE_STATIC code = new Bytecode.INVOKE_STATIC(offset, count, index);
            if (0 < code.count()) code.vC((byte)((vp >>> 12) & 0x000f));
            if (1 < code.count()) code.vD((byte)((vp >>> 8) & 0x000f));
            if (2 < code.count()) code.vE((byte)((vp >>> 4) & 0x000f));
            if (3 < code.count()) code.vF((byte)(vp & 0x000f));
            if (4 < code.count()) code.vG((byte)(v & 0x0f));
            return code;
        }
		case Bytecode.INVOKE_INTERFACE: {
            byte v = readS1();
			byte count = (byte)(v & 0xf0 >>> 4);
            int index = readU2();  
            short vp = readS2();
			// MethodId method = reader().getMethodId(index);
            // Bytecode.INVOKE_INTERFACE code = new Bytecode.INVOKE_INTERFACE(offset, count, index,
			// 															   method.clazz(), method.name(), method.returnType(), method.parameterString());
			Bytecode.INVOKE_INTERFACE code = new Bytecode.INVOKE_INTERFACE(offset, count, index);
            if (0 < code.count()) code.vC((byte)((vp >>> 12) & 0x000f));
            if (1 < code.count()) code.vD((byte)((vp >>> 8) & 0x000f));
            if (2 < code.count()) code.vE((byte)((vp >>> 4) & 0x000f));
            if (3 < code.count()) code.vF((byte)(vp & 0x000f));
            if (4 < code.count()) code.vG((byte)(v & 0x0f));
            return code;
        }
		case Bytecode.INVOKE_VIRTUAL_RANGE: {
			short count = readU1();
			int index = readU2();
			int vCCCC = readU2();
			// MethodId method = reader().getMethodId(index);
			// return new Bytecode.INVOKE_VIRTUAL_RANGE(offset, count, index, vCCCC,
			// 										 method.clazz(), method.name(), method.returnType(), method.parameterString());
			return new Bytecode.INVOKE_VIRTUAL_RANGE(offset, count, index, vCCCC);
		}
		case Bytecode.INVOKE_SUPER_RANGE: {
			short count = readU1();
			int index = readU2();
			int vCCCC = readU2();
			// MethodId method = reader().getMethodId(index);
			// return new Bytecode.INVOKE_SUPER_RANGE(offset, count, index, vCCCC,
			// 									   method.clazz(), method.name(), method.returnType(), method.parameterString());
			return new Bytecode.INVOKE_SUPER_RANGE(offset, count, index, vCCCC);
		}
		case Bytecode.INVOKE_DIRECT_RANGE: {
			short count = readU1();
			int index = readU2();
			int vCCCC = readU2();
			// MethodId method = reader().getMethodId(index);
			// return new Bytecode.INVOKE_DIRECT_RANGE(offset, count, index, vCCCC,
			// 										method.clazz(), method.name(), method.returnType(), method.parameterString());
			return new Bytecode.INVOKE_DIRECT_RANGE(offset, count, index, vCCCC);
		}
		case Bytecode.INVOKE_STATIC_RANGE: {
			short count = readU1();
			int index = readU2();
			int vCCCC = readU2();
			// MethodId method = reader().getMethodId(index);
			// return new Bytecode.INVOKE_STATIC_RANGE(offset, count, index, vCCCC,
			// 										method.clazz(), method.name(), method.returnType(), method.parameterString());
			return new Bytecode.INVOKE_STATIC_RANGE(offset, count, index, vCCCC);
		}
		case Bytecode.INVOKE_INTERFACE_RANGE: {
			short count = readU1();
			int index = readU2();
			int vCCCC = readU2();
			// MethodId method = reader().getMethodId(index);
			// return new Bytecode.INVOKE_INTERFACE_RANGE(offset, count, index, vCCCC,
			// 										   method.clazz(), method.name(), method.returnType(), method.parameterString());
			return new Bytecode.INVOKE_INTERFACE_RANGE(offset, count, index, vCCCC);
		}
		case Bytecode.NEG_INT: {byte v = readS1(); return new Bytecode.NEG_INT(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.NOT_INT: {byte v = readS1(); return new Bytecode.NOT_INT(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.NEG_LONG: {byte v = readS1(); return new Bytecode.NEG_LONG(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.NOT_LONG: {byte v = readS1(); return new Bytecode.NOT_LONG(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.NEG_FLOAT: {byte v = readS1(); return new Bytecode.NEG_FLOAT(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.NEG_DOUBLE: {byte v = readS1(); return new Bytecode.NEG_DOUBLE(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.INT_TO_LONG: {byte v = readS1(); return new Bytecode.INT_TO_LONG(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.INT_TO_FLOAT: {byte v = readS1(); return new Bytecode.INT_TO_FLOAT(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.INT_TO_DOUBLE: {byte v = readS1(); return new Bytecode.INT_TO_DOUBLE(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.LONG_TO_INT: {byte v = readS1(); return new Bytecode.LONG_TO_INT(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.LONG_TO_FLOAT: {byte v = readS1(); return new Bytecode.LONG_TO_FLOAT(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.LONG_TO_DOUBLE: {byte v = readS1(); return new Bytecode.LONG_TO_DOUBLE(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.FLOAT_TO_INT: {byte v = readS1(); return new Bytecode.FLOAT_TO_INT(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}       
		case Bytecode.FLOAT_TO_LONG: {byte v = readS1(); return new Bytecode.FLOAT_TO_LONG(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.FLOAT_TO_DOUBLE: {byte v = readS1(); return new Bytecode.FLOAT_TO_DOUBLE(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.DOUBLE_TO_INT: {byte v = readS1(); return new Bytecode.DOUBLE_TO_INT(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.DOUBLE_TO_LONG: {byte v = readS1(); return new Bytecode.DOUBLE_TO_LONG(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.DOUBLE_TO_FLOAT: {byte v = readS1(); return new Bytecode.DOUBLE_TO_FLOAT(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.INT_TO_BYTE: {byte v = readS1(); return new Bytecode.INT_TO_BYTE(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
        case Bytecode.INT_TO_CHAR: {byte v = readS1(); return new Bytecode.INT_TO_CHAR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}        
		case Bytecode.INT_TO_SHORT: {byte v = readS1(); return new Bytecode.INT_TO_SHORT(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.ADD_INT: return new Bytecode.ADD_INT(offset, readU1(), readU1(), readU1());
		case Bytecode.SUB_INT: return new Bytecode.SUB_INT(offset, readU1(), readU1(), readU1());
		case Bytecode.MUL_INT: return new Bytecode.MUL_INT(offset, readU1(), readU1(), readU1());
		case Bytecode.DIV_INT: return new Bytecode.DIV_INT(offset, readU1(), readU1(), readU1());
		case Bytecode.REM_INT: return new Bytecode.REM_INT(offset, readU1(), readU1(), readU1());
		case Bytecode.AND_INT: return new Bytecode.AND_INT(offset, readU1(), readU1(), readU1());
		case Bytecode.OR_INT: return new Bytecode.OR_INT(offset, readU1(), readU1(), readU1());
		case Bytecode.XOR_INT: return new Bytecode.XOR_INT(offset, readU1(), readU1(), readU1());
		case Bytecode.SHL_INT: return new Bytecode.SHL_INT(offset, readU1(), readU1(), readU1());
		case Bytecode.SHR_INT: return new Bytecode.SHR_INT(offset, readU1(), readU1(), readU1());
		case Bytecode.USHR_INT: return new Bytecode.USHR_INT(offset, readU1(), readU1(), readU1());
		case Bytecode.ADD_LONG: return new Bytecode.ADD_LONG(offset, readU1(), readU1(), readU1());
		case Bytecode.SUB_LONG: return new Bytecode.SUB_LONG(offset, readU1(), readU1(), readU1());
		case Bytecode.MUL_LONG: return new Bytecode.MUL_LONG(offset, readU1(), readU1(), readU1());
		case Bytecode.DIV_LONG: return new Bytecode.DIV_LONG(offset, readU1(), readU1(), readU1());
		case Bytecode.REM_LONG: return new Bytecode.REM_LONG(offset, readU1(), readU1(), readU1());
		case Bytecode.AND_LONG: return new Bytecode.AND_LONG(offset, readU1(), readU1(), readU1());
		case Bytecode.OR_LONG: return new Bytecode.OR_LONG(offset, readU1(), readU1(), readU1());
		case Bytecode.XOR_LONG: return new Bytecode.XOR_LONG(offset, readU1(), readU1(), readU1());
		case Bytecode.SHL_LONG: return new Bytecode.SHL_LONG(offset, readU1(), readU1(), readU1());
		case Bytecode.SHR_LONG: return new Bytecode.SHR_LONG(offset, readU1(), readU1(), readU1());
		case Bytecode.USHR_LONG: return new Bytecode.USHR_LONG(offset, readU1(), readU1(), readU1());
		case Bytecode.ADD_FLOAT: return new Bytecode.ADD_FLOAT(offset, readU1(), readU1(), readU1());
		case Bytecode.SUB_FLOAT: return new Bytecode.SUB_FLOAT(offset, readU1(), readU1(), readU1());
		case Bytecode.MUL_FLOAT: return new Bytecode.MUL_FLOAT(offset, readU1(), readU1(), readU1());
		case Bytecode.DIV_FLOAT: return new Bytecode.DIV_FLOAT(offset, readU1(), readU1(), readU1());
		case Bytecode.REM_FLOAT: return new Bytecode.REM_FLOAT(offset, readU1(), readU1(), readU1()); 
		case Bytecode.ADD_DOUBLE: return new Bytecode.ADD_DOUBLE(offset, readU1(), readU1(), readU1());
		case Bytecode.SUB_DOUBLE: return new Bytecode.SUB_DOUBLE(offset, readU1(), readU1(), readU1());
		case Bytecode.MUL_DOUBLE: return new Bytecode.MUL_DOUBLE(offset, readU1(), readU1(), readU1());
		case Bytecode.DIV_DOUBLE: return new Bytecode.DIV_DOUBLE(offset, readU1(), readU1(), readU1());
		case Bytecode.REM_DOUBLE: return new Bytecode.REM_DOUBLE(offset, readU1(), readU1(), readU1());
		case Bytecode.ADD_INT_2ADDR: {byte v = readS1(); return new Bytecode.ADD_INT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.SUB_INT_2ADDR: {byte v = readS1(); return new Bytecode.SUB_INT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.MUL_INT_2ADDR: {byte v = readS1(); return new Bytecode.MUL_INT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.DIV_INT_2ADDR: {byte v = readS1(); return new Bytecode.DIV_INT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.REM_INT_2ADDR: {byte v = readS1(); return new Bytecode.REM_INT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.AND_INT_2ADDR: {byte v = readS1(); return new Bytecode.AND_INT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.OR_INT_2ADDR: {byte v = readS1(); return new Bytecode.OR_INT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.XOR_INT_2ADDR: {byte v = readS1(); return new Bytecode.XOR_INT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.SHL_INT_2ADDR: {byte v = readS1(); return new Bytecode.SHL_INT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.SHR_INT_2ADDR: {byte v = readS1(); return new Bytecode.SHR_INT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.USHR_INT_2ADDR: {byte v = readS1(); return new Bytecode.USHR_INT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.ADD_LONG_2ADDR: {byte v = readS1();return new Bytecode.ADD_LONG_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.SUB_LONG_2ADDR: {byte v = readS1(); return new Bytecode.SUB_LONG_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.MUL_LONG_2ADDR: {byte v = readS1(); return new Bytecode.MUL_LONG_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.DIV_LONG_2ADDR: {byte v = readS1(); return new Bytecode.DIV_LONG_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.REM_LONG_2ADDR: {byte v = readS1(); return new Bytecode.REM_LONG_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.AND_LONG_2ADDR: {byte v = readS1(); return new Bytecode.AND_LONG_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.OR_LONG_2ADDR: {byte v = readS1(); return new Bytecode.OR_LONG_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.XOR_LONG_2ADDR: {byte v = readS1(); return new Bytecode.XOR_LONG_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.SHL_LONG_2ADDR: {byte v = readS1(); return new Bytecode.SHL_LONG_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.SHR_LONG_2ADDR: {byte v = readS1(); return new Bytecode.SHR_LONG_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.USHR_LONG_2ADDR: {byte v = readS1(); return new Bytecode.USHR_LONG_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));} 
		case Bytecode.ADD_FLOAT_2ADDR: {byte v = readS1(); return new Bytecode.ADD_FLOAT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.SUB_FLOAT_2ADDR: {byte v = readS1(); return new Bytecode.SUB_FLOAT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.MUL_FLOAT_2ADDR: {byte v = readS1(); return new Bytecode.MUL_FLOAT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.DIV_FLOAT_2ADDR: {byte v = readS1(); return new Bytecode.DIV_FLOAT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.REM_FLOAT_2ADDR: {byte v = readS1(); return new Bytecode.REM_FLOAT_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.ADD_DOUBLE_2ADDR: {byte v = readS1(); return new Bytecode.ADD_DOUBLE_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.SUB_DOUBLE_2ADDR: {byte v = readS1(); return new Bytecode.SUB_DOUBLE_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.MUL_DOUBLE_2ADDR: {byte v = readS1(); return new Bytecode.MUL_DOUBLE_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.DIV_DOUBLE_2ADDR: {byte v = readS1(); return new Bytecode.DIV_DOUBLE_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.REM_DOUBLE_2ADDR: {byte v = readS1(); return new Bytecode.REM_DOUBLE_2ADDR(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4));}
		case Bytecode.ADD_INT_LIT16: {byte v = readS1(); return new Bytecode.ADD_INT_LIT16(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4), readS2());}
		case Bytecode.RSUB_INT_LIT16: {byte v = readS1(); return new Bytecode.RSUB_INT_LIT16(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4), readS2());}
		case Bytecode.MUL_INT_LIT16: {byte v = readS1(); return new Bytecode.MUL_INT_LIT16(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4), readS2());}
		case Bytecode.DIV_INT_LIT16: {byte v = readS1(); return new Bytecode.DIV_INT_LIT16(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4), readS2());}
		case Bytecode.REM_INT_LIT16: {byte v = readS1(); return new Bytecode.REM_INT_LIT16(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4), readS2());}
        case Bytecode.AND_INT_LIT16: {byte v = readS1(); return new Bytecode.AND_INT_LIT16(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4), readS2());}
		case Bytecode.OR_INT_LIT16: {byte v = readS1(); return new Bytecode.OR_INT_LIT16(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4), readS2());}
		case Bytecode.XOR_INT_LIT16: {byte v = readS1(); return new Bytecode.XOR_INT_LIT16(offset, (byte)(v & 0x0f), (byte)((v & 0xf0) >>> 4), readS2());}
		case Bytecode.ADD_INT_LIT8: return new Bytecode.ADD_INT_LIT8(offset, readU1(), readU1(), readS1());
		case Bytecode.RSUB_INT_LIT8: return new Bytecode.RSUB_INT_LIT8(offset, readU1(), readU1(), readS1());
		case Bytecode.MUL_INT_LIT8: return new Bytecode.MUL_INT_LIT8(offset, readU1(), readU1(), readS1());
		case Bytecode.DIV_INT_LIT8: return new Bytecode.DIV_INT_LIT8(offset, readU1(), readU1(), readS1());
		case Bytecode.REM_INT_LIT8: return new Bytecode.REM_INT_LIT8(offset, readU1(), readU1(), readS1()); 
		case Bytecode.AND_INT_LIT8: return new Bytecode.AND_INT_LIT8(offset, readU1(), readU1(), readS1());
		case Bytecode.OR_INT_LIT8: return new Bytecode.OR_INT_LIT8(offset, readU1(), readU1(), readS1());
		case Bytecode.XOR_INT_LIT8: return new Bytecode.XOR_INT_LIT8(offset, readU1(), readU1(), readS1());
		case Bytecode.SHL_INT_LIT8: return new Bytecode.SHL_INT_LIT8(offset, readU1(), readU1(), readS1());
		case Bytecode.SHR_INT_LIT8: return new Bytecode.SHR_INT_LIT8(offset, readU1(), readU1(), readS1());
		case Bytecode.USHR_INT_LIT8: return new Bytecode.USHR_INT_LIT8(offset, readU1(), readU1(), readS1());
		case Bytecode.INVOKE_POLYMORPHIC: {
            byte v = readS1();
			byte count = (byte)(v & 0xf0 >>> 4);
            int methodIndex = readU2();
            short vp = readS2();
            int protoIndex = readU2();
			// MethodId method = reader().getMethodId(methodIndex);
			// ProtoId proto = reader().getProtoId(protoIndex);
            // Bytecode.INVOKE_POLYMORPHIC code = new Bytecode.INVOKE_POLYMORPHIC(offset, count, methodIndex, protoIndex,
			// 																   method.clazz(), method.name(), method.returnType(), method.parameterString(),
			// 																   proto.descriptor(), proto.returnType(), proto.parameterString());
			Bytecode.INVOKE_POLYMORPHIC code = new Bytecode.INVOKE_POLYMORPHIC(offset, count, methodIndex, protoIndex);
            if (0 < code.count()) code.vC((byte)((vp >>> 12) & 0x000f));
            if (1 < code.count()) code.vD((byte)((vp >>> 8) & 0x000f));
            if (2 < code.count()) code.vE((byte)((vp >>> 4) & 0x000f));
            if (3 < code.count()) code.vF((byte)(vp & 0x000f));
            if (4 < code.count()) code.vG((byte)(v & 0x0f));
            return code;
        }
		case Bytecode.INVOKE_POLYMORPHIC_RANGE: {
            short vAA = readU1();
            int methodIndex = readU2();
            int vCCCC = readU2();
            int protoIndex = readU2();
			// MethodId method = reader().getMethodId(methodIndex);
			// ProtoId proto = reader().getProtoId(protoIndex);
            // return new Bytecode.INVOKE_POLYMORPHIC_RANGE(offset, vAA, methodIndex, vCCCC, protoIndex,
			// 											 method.clazz(), method.name(), method.returnType(), method.parameterString(),
			// 											 proto.descriptor(), proto.returnType(), proto.parameterString());
			return new Bytecode.INVOKE_POLYMORPHIC_RANGE(offset, vAA, methodIndex, vCCCC, protoIndex);
        }
		case Bytecode.INVOKE_CUSTOM: {
            skip(5);
            return new Bytecode.INVOKE_CUSTOM(offset);
        }
		case Bytecode.INVOKE_CUSTOM_RANGE: {
            skip(5);
            return new Bytecode.INVOKE_CUSTOM_RANGE(offset);
        }
        default: throw new Exception("invalid bytecode " + String.format("%02x", opcode));
        }
    }

	private Printer printer = null;
	
	public Printer printer() {
		return printer;
	}
	
	public static class Printer {

		private Printer(DexReader reader) {
			this.reader = reader;			
		}

		private DexReader reader = null;

		public DexReader reader() {
			return reader;
		}

		private String prefix = "";
		
		public Printer prefix(String prefix) {
			this.prefix = prefix; return this;
		}

		public String prefix() {
			return prefix;
		}		
		
		private static final String FORMAT_OFFSET = "%04x    ";
		private static final String FORMAT_OPCODE = "[%02x]  ";		
		private static final String FORMAT_NAME = "%-26s";
		private static final String FORMAT = FORMAT_OFFSET + FORMAT_OPCODE + FORMAT_NAME;

		public void print(Bytecode bytecode) throws Exception {
			System.out.printf("%s", prefix);
			switch (bytecode.opcode()) {
			case Bytecode.NOP: {
				Bytecode.NOP code = (Bytecode.NOP)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.println();
				return;
			}
			case Bytecode.MOVE: {
				Bytecode.MOVE code = (Bytecode.MOVE)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%01x, v%01x\n", code.vA(), code.vB());		
				return;
			}     
			case Bytecode.MOVE_FROM16: {
				Bytecode.MOVE_FROM16 code = (Bytecode.MOVE_FROM16)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, v%04x\n", code.vAA(), code.vBBBB());	
				return;
			} 
			case Bytecode.MOVE16: {
				Bytecode.MOVE16 code = (Bytecode.MOVE16)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%04x, v%04x\n", code.vAAAA(), code.vBBBB());
				return;
			}
			case Bytecode.MOVE_WIDE: {
				Bytecode.MOVE_WIDE code = (Bytecode.MOVE_WIDE)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%01x, v%01x\n", code.vA(), code.vB());			
				return;
			}       
			case Bytecode.MOVE_WIDE_FROM16: {
				Bytecode.MOVE_WIDE_FROM16 code = (Bytecode.MOVE_WIDE_FROM16)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, v%04x\n", code.vAA(), code.vBBBB());	
				return;
			} 
			case Bytecode.MOVE_WIDE16: {
				Bytecode.MOVE_WIDE16 code = (Bytecode.MOVE_WIDE16)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, v%04x\n", code.vAA(), code.vBBBB());	
				return;
			} 
			case Bytecode.MOVE_OBJECT: {
				Bytecode.MOVE_OBJECT code = (Bytecode.MOVE_OBJECT)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%01x, v%01x\n", code.vA(), code.vB());
				return;
			}   
			case Bytecode.MOVE_OBJECT_FROM16: {
				Bytecode.MOVE_OBJECT_FROM16 code = (Bytecode.MOVE_OBJECT_FROM16)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, v%04x\n", code.vAA(), code.vBBBB());
				return;
			}  
			case Bytecode.MOVE_OBJECT16: {
				Bytecode.MOVE_OBJECT16 code = (Bytecode.MOVE_OBJECT16)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%04x, v%04x\n", code.vAAAA(), code.vBBBB());
				return;
			}
			case Bytecode.MOVE_RESULT: {
				Bytecode.MOVE_RESULT code = (Bytecode.MOVE_RESULT)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x\n", code.vAA());
				return;
			}
			case Bytecode.MOVE_RESULT_WIDE: {
				Bytecode.MOVE_RESULT_WIDE code = (Bytecode.MOVE_RESULT_WIDE)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x\n", code.vAA());
				return;
			} 
			case Bytecode.MOVE_RESULT_OBJECT: {
				Bytecode.MOVE_RESULT_OBJECT code = (Bytecode.MOVE_RESULT_OBJECT)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x\n", code.vAA());
				return;
			} 
			case Bytecode.MOVE_EXCEPTION: {
				Bytecode.MOVE_EXCEPTION code = (Bytecode.MOVE_EXCEPTION)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x\n", code.vAA());
				return;
			}
			case Bytecode.RETURN_VOID: {
				Bytecode.RETURN_VOID code = (Bytecode.RETURN_VOID)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.println();
				return;
			} 
			case Bytecode.RETURN: {
				Bytecode.RETURN code = (Bytecode.RETURN)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x\n", code.vAA());
				return;
			}
			case Bytecode.RETURN_WIDE: {
				Bytecode.RETURN_WIDE code = (Bytecode.RETURN_WIDE)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x\n", code.vAA());
				return;
			}
			case Bytecode.RETURN_OBJECT: {
				Bytecode.RETURN_OBJECT code = (Bytecode.RETURN_OBJECT)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x\n", code.vAA());
				return;
			}
			case Bytecode.CONST4: {
				Bytecode.CONST4 code = (Bytecode.CONST4)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%01x, %d\n", code.vA(), code.value());
				return;
			}
			case Bytecode.CONST16: {
				Bytecode.CONST16 code = (Bytecode.CONST16)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, %d\n", code.vAA(), code.value());
				return;
			}
			case Bytecode.CONST: {
				Bytecode.CONST code = (Bytecode.CONST)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, %d\n", code.vAA(), code.value());
				return;
			} 
			case Bytecode.CONST_HIGH16: {
				Bytecode.CONST_HIGH16 code = (Bytecode.CONST_HIGH16)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, %d\n", code.vAA(), code.value());
				return;
			}
			case Bytecode.CONST_WIDE16: {
				Bytecode.CONST_WIDE16 code = (Bytecode.CONST_WIDE16)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, %d\n", code.vAA(), code.value());
				return;
			} 
			case Bytecode.CONST_WIDE32: {
				Bytecode.CONST_WIDE32 code = (Bytecode.CONST_WIDE32)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, %d\n", code.vAA(), code.value());
				return;
			}
			case Bytecode.CONST_WIDE: {
				Bytecode.CONST_WIDE code = (Bytecode.CONST_WIDE)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, %d\n", code.vAA(), code.value());
				return;
			} 
			case Bytecode.CONST_WIDE_HIGH16: {
				Bytecode.CONST_WIDE_HIGH16 code = (Bytecode.CONST_WIDE_HIGH16)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, %d\n", code.vAA(), code.value());
				return;
			} 
			case Bytecode.CONST_STRING: {
				Bytecode.CONST_STRING code = (Bytecode.CONST_STRING)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, string@%d ", code.vAA(), code.index());
				if (null != code.string()) {
					System.out.printf("%s", code.string());
				}
				System.out.println();
				return;
			}
			case Bytecode.CONST_STRING_JUMBO: {
				Bytecode.CONST_STRING_JUMBO code = (Bytecode.CONST_STRING_JUMBO)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, string@%d ", code.vAA(), code.index());
				if (null != code.string()) {
					System.out.printf("%s", code.string());
				}
				System.out.println();
				return;
			}
			case Bytecode.CONST_CLASS: {
				Bytecode.CONST_CLASS code = (Bytecode.CONST_CLASS)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, type@%d ", code.vAA(), code.index());
				if (null != code.clazz()) {
					System.out.printf("%s", code.clazz());
				}				
				System.out.println();
				return;
			}
			case Bytecode.MONITOR_ENTER: {
				Bytecode.MONITOR_ENTER code = (Bytecode.MONITOR_ENTER)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x\n", code.vAA());
				return;
			}
			case Bytecode.MONITOR_EXIT: {
				Bytecode.MONITOR_EXIT code = (Bytecode.MONITOR_EXIT)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x\n", code.vAA());
				return;
			}
			case Bytecode.CHECK_CAST: {
				Bytecode.CHECK_CAST code = (Bytecode.CHECK_CAST)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, %d ", code.vAA(), code.index());
				if (null != code.type()) {
					System.out.printf("%s", code.type());
				}
				System.out.println();
				return;
			}
			case Bytecode.INSTANCE_OF: {
				Bytecode.INSTANCE_OF code = (Bytecode.INSTANCE_OF)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%01x, v%01x, type@%d ", code.vA(), code.vB(), code.index());
				if (null != code.clazz()) {
					System.out.printf("%s", code.clazz());
				}
				System.out.println();
				return;
			}
			case Bytecode.ARRAY_LENGTH: {
				Bytecode.ARRAY_LENGTH code = (Bytecode.ARRAY_LENGTH)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%01x, v%01x\n", code.vA(), code.vB());
				return;
			}
			case Bytecode.NEW_INSTANCE: {
				Bytecode.NEW_INSTANCE code = (Bytecode.NEW_INSTANCE)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, type@%d ", code.vAA(), code.index());
				if (null != code.clazz()) {
					System.out.printf("%s", code.clazz());
				}
				System.out.println();
				return;
			}
			case Bytecode.NEW_ARRAY: {
				Bytecode.NEW_ARRAY code = (Bytecode.NEW_ARRAY)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%01x, v%01x, type@%d ", code.vA(), code.vB(), code.index());
				if (null != code.type()) {
					System.out.printf("%s", code.type());
				}
				System.out.println();
				return;
			}
			case Bytecode.FILLED_NEW_ARRAY: {
				Bytecode.FILLED_NEW_ARRAY code = (Bytecode.FILLED_NEW_ARRAY)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());				
			    System.out.printf("{");
				if (1 <= code.count()) System.out.printf("v%01x", code.vC());
				if (2 <= code.count()) System.out.printf(", v%01x", code.vD());
				if (3 <= code.count()) System.out.printf(", v%01x", code.vE());
				if (4 <= code.count()) System.out.printf(", v%01x", code.vF());
				if (5 == code.count()) System.out.printf(", v%01x ", code.vG());
				System.out.printf("}, ");
				System.out.printf("type@%d ", code.index());
				if (null != code.type()) {
					System.out.printf("%s", code.type());
				}
				System.out.println();
				return;
			}
			case Bytecode.FILLED_NEW_ARRAY_RANGE: {
				Bytecode.FILLED_NEW_ARRAY_RANGE code = (Bytecode.FILLED_NEW_ARRAY_RANGE)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("{v%04x..v%04x}, type@%d ", code.vCCCC(), code.vCCCC() + code.count() - 1, code.index());				
				if (null != code.type()) {
					System.out.printf("%s", code.type());
				}
				System.out.println();
				return;
			}
			case Bytecode.FILL_ARRAY_DATA: {
				Bytecode.FILL_ARRAY_DATA code = (Bytecode.FILL_ARRAY_DATA)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, %d\n", code.vAA(), code.value());
				return;
			}
			case Bytecode.THROW: {
				Bytecode.THROW code = (Bytecode.THROW)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x\n", code.vAA());
				return;
			}
			case Bytecode.GOTO: {
				Bytecode.GOTO code = (Bytecode.GOTO)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("%d\n", code.value());
				return;
			} 
			case Bytecode.GOTO16: {
				Bytecode.GOTO16 code = (Bytecode.GOTO16)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("%d\n", code.value());
				return;
			}
			case Bytecode.GOTO32: {
				Bytecode.GOTO32 code = (Bytecode.GOTO32)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("%d\n", code.value());
				return;
			}
			case Bytecode.PACKED_SWITCH: {
				Bytecode.PACKED_SWITCH code = (Bytecode.PACKED_SWITCH)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, %d\n", code.vAA(), code.value());
				return;
			}
			case Bytecode.SPARSE_SWITCH: {
				Bytecode.SPARSE_SWITCH code = (Bytecode.SPARSE_SWITCH)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, %d\n", code.vAA(), code.value());
				return;
			}
			case Bytecode.CMPL_FLOAT:
			case Bytecode.CMPG_FLOAT:
			case Bytecode.CMPL_DOUBLE:
			case Bytecode.CMPG_DOUBLE:
			case Bytecode.CMP_LONG: {
				Bytecode.CMP_KIND code = (Bytecode.CMP_KIND)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, v%02x, v%02x\n", code.vAA(), code.vBB(), code.vCC());
				return;
			}			
			case Bytecode.IF_EQ:    
			case Bytecode.IF_NE:   
			case Bytecode.IF_LT:    
			case Bytecode.IF_GE:        
			case Bytecode.IF_GT:    
			case Bytecode.IF_LE: {
				Bytecode.IF_TEST code = (Bytecode.IF_TEST)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%01x, v%01x, %d\n", code.vA(), code.vB(), code.value());
				return;
			}     
			case Bytecode.IF_EQZ: 
			case Bytecode.IF_NEZ: 
			case Bytecode.IF_LTZ: 
			case Bytecode.IF_GEZ: 
			case Bytecode.IF_GTZ: 
			case Bytecode.IF_LEZ: {
				Bytecode.IF_TESTZ code = (Bytecode.IF_TESTZ)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, %d\n", code.vAA(), code.value());
				return;
			} 
			case Bytecode.AGET: 
			case Bytecode.AGET_WIDE: 
			case Bytecode.AGET_OBJECT:
			case Bytecode.AGET_BOOLEAN: 
			case Bytecode.AGET_BYTE: 
			case Bytecode.AGET_CHAR: 
			case Bytecode.AGET_SHORT: 
			case Bytecode.APUT: 
			case Bytecode.APUT_WIDE: 
			case Bytecode.APUT_OBJECT: 
			case Bytecode.APUT_BOOLEAN: 
			case Bytecode.APUT_BYTE: 
			case Bytecode.APUT_CHAR: 
			case Bytecode.APUT_SHORT: {
				Bytecode.ARRAY_OP code = (Bytecode.ARRAY_OP)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, v%02x, v%02x\n", code.vAA(), code.vBB(), code.vCC());
				return;
			} 
			case Bytecode.IGET:
			case Bytecode.IGET_WIDE:
			case Bytecode.IGET_OBJECT:
			case Bytecode.IGET_BOOLEAN:
			case Bytecode.IGET_BYTE:
			case Bytecode.IGET_CHAR:
			case Bytecode.IGET_SHORT:
			case Bytecode.IPUT:
			case Bytecode.IPUT_WIDE:
			case Bytecode.IPUT_OBJECT:
			case Bytecode.IPUT_BOOLEAN:
			case Bytecode.IPUT_BYTE:
			case Bytecode.IPUT_CHAR:
			case Bytecode.IPUT_SHORT: {
				Bytecode.IOP code = (Bytecode.IOP)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%01x, v%01x, field@%d ", code.vA(), code.vB(), code.index());
				if (null != code.clazz() && null != code.field() && null != code.type()) {
					System.out.printf("%s", code.clazz() + "." + code.field() + ":" + code.type());
				}
				System.out.println();
				return;
			}
			case Bytecode.SGET:
			case Bytecode.SGET_WIDE:
			case Bytecode.SGET_OBJECT:
			case Bytecode.SGET_BOOLEAN:
			case Bytecode.SGET_BYTE:
			case Bytecode.SGET_CHAR:
			case Bytecode.SGET_SHORT: 
			case Bytecode.SPUT:
			case Bytecode.SPUT_WIDE:
			case Bytecode.SPUT_OBJECT:
			case Bytecode.SPUT_BOOLEAN:
			case Bytecode.SPUT_BYTE:
			case Bytecode.SPUT_CHAR:
			case Bytecode.SPUT_SHORT: {
				Bytecode.SOP code = (Bytecode.SOP)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, field@%d ", code.vAA(), code.index());
				if (null != code.clazz() && null != code.field() && null != code.type()) {
					System.out.printf("%s", code.clazz() + "." + code.field() + ":" + code.type());
				}
				System.out.println();
				return;
			}
			case Bytecode.INVOKE_VIRTUAL:
			case Bytecode.INVOKE_SUPER:
			case Bytecode.INVOKE_DIRECT:
			case Bytecode.INVOKE_STATIC:
			case Bytecode.INVOKE_INTERFACE: {
				Bytecode.INVOKE_KIND code = (Bytecode.INVOKE_KIND)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("{");
				if (1 <= code.count()) System.out.printf("v%01x", code.vC());
				if (2 <= code.count()) System.out.printf(", v%01x", code.vD());
				if (3 <= code.count()) System.out.printf(", v%01x", code.vE());
				if (4 <= code.count()) System.out.printf(", v%01x", code.vF());
				if (5 == code.count()) System.out.printf(", v%01x ", code.vG());
				System.out.printf("}, ");
				System.out.printf("method@%d ", code.index());
				if (null != code.clazz() && null != code.method() && null != code.parameter() && null != code.returnType()) {
					System.out.printf("%s", code.clazz() + "." + code.method() + code.parameter() + ":" + code.returnType());
				}
				System.out.println();
				return;
			}
			case Bytecode.INVOKE_VIRTUAL_RANGE:
			case Bytecode.INVOKE_SUPER_RANGE:
			case Bytecode.INVOKE_DIRECT_RANGE:
			case Bytecode.INVOKE_STATIC_RANGE:
			case Bytecode.INVOKE_INTERFACE_RANGE: {
				Bytecode.INVOKE_KIND_RANGE code = (Bytecode.INVOKE_KIND_RANGE)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("{v%04x..v%04x}, type@%d ", code.vCCCC(), code.vCCCC() + code.count() - 1, code.index());
				if (null != code.clazz() && null != code.method() && null != code.parameter() && null != code.returnType()) {
					System.out.printf("%s", code.clazz() + "." + code.method() + code.parameter() + ":" + code.returnType());
				}
				System.out.println();
				return;
			} 
			case Bytecode.NEG_INT:       
			case Bytecode.NOT_INT:       
			case Bytecode.NEG_LONG:      
			case Bytecode.NOT_LONG:      
			case Bytecode.NEG_FLOAT:         
			case Bytecode.NEG_DOUBLE: 
			case Bytecode.INT_TO_LONG:        
			case Bytecode.INT_TO_FLOAT:        
			case Bytecode.INT_TO_DOUBLE:       
			case Bytecode.LONG_TO_INT: 
			case Bytecode.LONG_TO_FLOAT:       
			case Bytecode.LONG_TO_DOUBLE:  
			case Bytecode.FLOAT_TO_INT:         
			case Bytecode.FLOAT_TO_LONG:     
			case Bytecode.FLOAT_TO_DOUBLE:
			case Bytecode.DOUBLE_TO_INT:      
			case Bytecode.DOUBLE_TO_LONG:
			case Bytecode.DOUBLE_TO_FLOAT:      
			case Bytecode.INT_TO_BYTE:   
			case Bytecode.INT_TO_CHAR:       
			case Bytecode.INT_TO_SHORT: {
				Bytecode.UNOP code = (Bytecode.UNOP)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%01x, v%01x\n", code.vA(), code.vB());
				return;
			}
			case Bytecode.ADD_INT:
			case Bytecode.SUB_INT: 
			case Bytecode.MUL_INT: 
			case Bytecode.DIV_INT: 
			case Bytecode.REM_INT: 
			case Bytecode.AND_INT: 
			case Bytecode.OR_INT: 
			case Bytecode.XOR_INT: 
			case Bytecode.SHL_INT: 
			case Bytecode.SHR_INT: 
			case Bytecode.USHR_INT:
			case Bytecode.ADD_LONG:
			case Bytecode.SUB_LONG:
			case Bytecode.MUL_LONG: 
			case Bytecode.DIV_LONG:
			case Bytecode.REM_LONG: 
			case Bytecode.AND_LONG: 
			case Bytecode.OR_LONG: 
			case Bytecode.XOR_LONG: 
			case Bytecode.SHL_LONG: 
			case Bytecode.SHR_LONG: 
			case Bytecode.USHR_LONG: 
			case Bytecode.ADD_FLOAT: 
			case Bytecode.SUB_FLOAT: 
			case Bytecode.MUL_FLOAT: 
			case Bytecode.DIV_FLOAT:
			case Bytecode.REM_FLOAT:
			case Bytecode.ADD_DOUBLE:
			case Bytecode.SUB_DOUBLE:
			case Bytecode.MUL_DOUBLE:
			case Bytecode.DIV_DOUBLE:
			case Bytecode.REM_DOUBLE: {
				Bytecode.BINOP code = (Bytecode.BINOP)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, v%02x, v%02x\n", code.vAA(), code.vBB(), code.vCC());
				return;
			}
			case Bytecode.ADD_INT_2ADDR:
			case Bytecode.SUB_INT_2ADDR:
			case Bytecode.MUL_INT_2ADDR:
			case Bytecode.DIV_INT_2ADDR:
			case Bytecode.REM_INT_2ADDR: 
			case Bytecode.AND_INT_2ADDR:
			case Bytecode.OR_INT_2ADDR: 
			case Bytecode.XOR_INT_2ADDR: 
			case Bytecode.SHL_INT_2ADDR: 
			case Bytecode.SHR_INT_2ADDR: 
			case Bytecode.USHR_INT_2ADDR: 
			case Bytecode.ADD_LONG_2ADDR: 
			case Bytecode.SUB_LONG_2ADDR: 
			case Bytecode.MUL_LONG_2ADDR: 
			case Bytecode.DIV_LONG_2ADDR: 
			case Bytecode.REM_LONG_2ADDR: 
			case Bytecode.AND_LONG_2ADDR: 
			case Bytecode.OR_LONG_2ADDR: 
			case Bytecode.XOR_LONG_2ADDR: 
			case Bytecode.SHL_LONG_2ADDR: 
			case Bytecode.SHR_LONG_2ADDR: 
			case Bytecode.USHR_LONG_2ADDR:
			case Bytecode.ADD_FLOAT_2ADDR:
			case Bytecode.SUB_FLOAT_2ADDR: 
			case Bytecode.MUL_FLOAT_2ADDR: 
			case Bytecode.DIV_FLOAT_2ADDR: 
			case Bytecode.REM_FLOAT_2ADDR: 
			case Bytecode.ADD_DOUBLE_2ADDR: 
			case Bytecode.SUB_DOUBLE_2ADDR: 
			case Bytecode.MUL_DOUBLE_2ADDR: 
			case Bytecode.DIV_DOUBLE_2ADDR: 
			case Bytecode.REM_DOUBLE_2ADDR: {
				Bytecode.BINOP_2ADDR code = (Bytecode.BINOP_2ADDR)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%01x, v%01x\n", code.vA(), code.vB());
				return;
			}
			case Bytecode.ADD_INT_LIT16: 
			case Bytecode.RSUB_INT_LIT16:
			case Bytecode.MUL_INT_LIT16: 
			case Bytecode.DIV_INT_LIT16:
			case Bytecode.REM_INT_LIT16:
			case Bytecode.AND_INT_LIT16:
			case Bytecode.OR_INT_LIT16:
			case Bytecode.XOR_INT_LIT16: {
				Bytecode.BINOP_LIT16 code = (Bytecode.BINOP_LIT16)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%01x, v%01x, %d\n", code.vA(), code.vB(), code.value());				
				return;
			}
			case Bytecode.ADD_INT_LIT8:
			case Bytecode.RSUB_INT_LIT8:
			case Bytecode.MUL_INT_LIT8:
			case Bytecode.DIV_INT_LIT8:
			case Bytecode.REM_INT_LIT8:
			case Bytecode.AND_INT_LIT8:
			case Bytecode.OR_INT_LIT8:
			case Bytecode.XOR_INT_LIT8:
			case Bytecode.SHL_INT_LIT8:
			case Bytecode.SHR_INT_LIT8:
			case Bytecode.USHR_INT_LIT8: {
				Bytecode.BINOP_LIT8 code = (Bytecode.BINOP_LIT8)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("v%02x, v%02x, %d\n", code.vAA(), code.vBB(), code.value());
				return;
			}
			case Bytecode.INVOKE_POLYMORPHIC: {
				Bytecode.INVOKE_POLYMORPHIC code = (Bytecode.INVOKE_POLYMORPHIC)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("{");
				if (1 <= code.count()) System.out.printf("v%01x", code.vC());
				if (2 <= code.count()) System.out.printf(", v%01x", code.vD());
				if (3 <= code.count()) System.out.printf(", v%01x", code.vE());
				if (4 <= code.count()) System.out.printf(", v%01x", code.vF());
				if (5 == code.count()) System.out.printf(", v%01x ", code.vG());
				System.out.printf("}, ");
				System.out.printf("method@%d, proto@%d ", code.method(), code.proto());
				if (null != code.clazz() && null != code.methodName() && null != code.methodParameter() && null != code.methodReturn()) {
					System.out.printf("%s ", code.clazz() + "." + code.methodName() + code.methodParameter() + ":" + code.methodReturn());
				}
				if (null != code.descriptor() && null != code.protoParameter() && null != code.protoReturn()) {
					System.out.printf("%s", code.descriptor() + "."  + code.protoParameter() + ":" + code.protoReturn());
				}
				System.out.println();
				return;
			}
			case Bytecode.INVOKE_POLYMORPHIC_RANGE: {
				Bytecode.INVOKE_POLYMORPHIC_RANGE code = (Bytecode.INVOKE_POLYMORPHIC_RANGE)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.printf("{v%04x..v%04x}, method@%d, proto@%d", code.vCCCC(), code.vCCCC() + code.count() - 1, code.method(), code.proto());
				if (null != code.clazz() && null != code.methodName() && null != code.methodParameter() && null != code.methodReturn()) {
					System.out.printf("%s ", code.clazz() + "." + code.methodName() + code.methodParameter() + ":" + code.methodReturn());
				}
				if (null != code.descriptor() && null != code.protoParameter() && null != code.protoReturn()) {
					System.out.printf("%s", code.descriptor() + "."  + code.protoParameter() + ":" + code.protoReturn());
				}
				System.out.println();
				return;
			}
			case Bytecode.INVOKE_CUSTOM: {
				Bytecode.INVOKE_CUSTOM code = (Bytecode.INVOKE_CUSTOM)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());																						 
				System.out.println();
				return;
			}
			case Bytecode.INVOKE_CUSTOM_RANGE: {
				Bytecode.INVOKE_CUSTOM_RANGE code = (Bytecode.INVOKE_CUSTOM_RANGE)bytecode;
				System.out.printf(FORMAT, code.offset(), code.opcode(), code.name());
				System.out.println();
				return;
			}
			default: throw new Exception("invalid bytecode " + String.format("%02x", bytecode.opcode()));	
			}			
		}
	}
}
