package com.runbox.clazz.reader;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.io.DataInputStream;
import java.io.IOException;

import com.runbox.clazz.entry.constant.*;
import com.runbox.clazz.entry.bytecode.Bytecode;

public class BytecodeReader extends Reader {

    public BytecodeReader(DataInputStream stream, int size, ConstantReader reader) throws Exception {
        super(stream, reader); this.size = size;
    }	
	
    private List<Bytecode> codes = new LinkedList<Bytecode>();

    public List<Bytecode> get() {
        return codes;
    }

    public Bytecode get(long offset) {
        if (null != codes) {
            for (Bytecode code : codes) {
				if (offset == code.offset()) {
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
    protected long readU4() throws IOException {
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
			codes.add(load(readS1()));
		}
        return this;
    }    

    private Bytecode load(byte opcode) throws Exception {
        long offset = this.offset - 1;
        switch (opcode) {
        case Bytecode.AALOAD: return new Bytecode.AALOAD(offset);
		case Bytecode.AASTORE: return new Bytecode.AASTORE(offset);
		case Bytecode.ACONST_NULL: return new Bytecode.ACONST_NULL(offset);
		case Bytecode.ALOAD: return new Bytecode.ALOAD(offset, readU1());
		case Bytecode.ALOAD_0: return new Bytecode.ALOAD_0(offset);
		case Bytecode.ALOAD_1: return new Bytecode.ALOAD_1(offset);
		case Bytecode.ALOAD_2: return new Bytecode.ALOAD_2(offset);
		case Bytecode.ALOAD_3: return new Bytecode.ALOAD_3(offset);
		case Bytecode.ANEWARRAY: return ANEWARRAY(offset);
		case Bytecode.ARETURN: return new Bytecode.ARETURN(offset);
		case Bytecode.ARRAYLENGTH: return new Bytecode.ARRAYLENGTH(offset);
		case Bytecode.ASTORE: return new Bytecode.ASTORE(offset, readU1());
		case Bytecode.ASTORE_0: return new Bytecode.ASTORE_0(offset);
		case Bytecode.ASTORE_1: return new Bytecode.ASTORE_1(offset);
		case Bytecode.ASTORE_2: return new Bytecode.ASTORE_2(offset);
		case Bytecode.ASTORE_3: return new Bytecode.ASTORE_3(offset);
		case Bytecode.ATHROW: return new Bytecode.ATHROW(offset);
		case Bytecode.BALOAD: return new Bytecode.BALOAD(offset);
		case Bytecode.BASTORE: return new Bytecode.BASTORE(offset);
		case Bytecode.BIPUSH: return new Bytecode.BIPUSH(offset, readS1());
		case Bytecode.CALOAD: return new Bytecode.CALOAD(offset);
		case Bytecode.CASTORE: return new Bytecode.CASTORE(offset);
		case Bytecode.CHECKCAST: return CHECKCAST(offset);
		case Bytecode.D2F: return new Bytecode.D2F(offset);
		case Bytecode.D2I: return new Bytecode.D2I(offset);
		case Bytecode.D2L: return new Bytecode.D2L(offset);
		case Bytecode.DADD: return new Bytecode.DADD(offset);
		case Bytecode.DALOAD: return new Bytecode.DALOAD(offset);
		case Bytecode.DASTORE: return new Bytecode.DASTORE(offset);
		case Bytecode.DCMPG: return new Bytecode.DCMPG(offset);
		case Bytecode.DCMPL: return new Bytecode.DCMPL(offset);
		case Bytecode.DCONST_0: return new Bytecode.DCONST_0(offset);
		case Bytecode.DCONST_1: return new Bytecode.DCONST_1(offset);
		case Bytecode.DDIV: return new Bytecode.DDIV(offset);
		case Bytecode.DLOAD: return new Bytecode.DLOAD(offset, readU1());
		case Bytecode.DLOAD_0: return new Bytecode.DLOAD_0(offset);
		case Bytecode.DLOAD_1: return new Bytecode.DLOAD_1(offset);
		case Bytecode.DLOAD_2: return new Bytecode.DLOAD_2(offset);
		case Bytecode.DLOAD_3: return new Bytecode.DLOAD_3(offset);
		case Bytecode.DMUL: return new Bytecode.DMUL(offset);
		case Bytecode.DNEG: return new Bytecode.DNEG(offset);
		case Bytecode.DREM: return new Bytecode.DREM(offset);
		case Bytecode.DRETURN: return new Bytecode.DRETURN(offset);
		case Bytecode.DSTORE: return new Bytecode.DSTORE(offset, readU1());
		case Bytecode.DSTORE_0: return new Bytecode.DSTORE_0(offset);
		case Bytecode.DSTORE_1: return new Bytecode.DSTORE_1(offset);
		case Bytecode.DSTORE_2: return new Bytecode.DSTORE_2(offset);
		case Bytecode.DSTORE_3: return new Bytecode.DSTORE_3(offset);				
		case Bytecode.DSUB: return new Bytecode.DSUB(offset);
		case Bytecode.DUP: return new Bytecode.DUP(offset);
		case Bytecode.DUP_X1: return new Bytecode.DUP_X1(offset);
		case Bytecode.DUP_X2: return new Bytecode.DUP_X2(offset);
		case Bytecode.DUP2: return new Bytecode.DUP2(offset);
		case Bytecode.DUP2_X1: return new Bytecode.DUP2_X1(offset);
		case Bytecode.DUP2_X2: return new Bytecode.DUP2_X2(offset);
		case Bytecode.F2D: return new Bytecode.F2D(offset);
		case Bytecode.F2I: return new Bytecode.F2I(offset);
		case Bytecode.F2L: return new Bytecode.F2L(offset);
		case Bytecode.FADD: return new Bytecode.FADD(offset);
		case Bytecode.FALOAD: return new Bytecode.FALOAD(offset);
		case Bytecode.FASTORE: return new Bytecode.FASTORE(offset);
		case Bytecode.FCMPG: return new Bytecode.FCMPG(offset);
		case Bytecode.FCMPL: return new Bytecode.FCMPL(offset);
		case Bytecode.FCONST_0: return new Bytecode.FCONST_0(offset);
		case Bytecode.FCONST_1: return new Bytecode.FCONST_1(offset);
		case Bytecode.FCONST_2: return new Bytecode.FCONST_2(offset);
		case Bytecode.FDIV: return new Bytecode.FDIV(offset);		
		case Bytecode.FLOAD: return new Bytecode.FLOAD(offset, readU1());
		case Bytecode.FLOAD_0: return new Bytecode.FLOAD_0(offset);
		case Bytecode.FLOAD_1: return new Bytecode.FLOAD_1(offset);
		case Bytecode.FLOAD_2: return new Bytecode.FLOAD_2(offset);
		case Bytecode.FLOAD_3: return new Bytecode.FLOAD_3(offset);
		case Bytecode.FMUL: return new Bytecode.FMUL(offset);
		case Bytecode.FNEG: return new Bytecode.FNEG(offset);
		case Bytecode.FREM: return new Bytecode.FREM(offset);
		case Bytecode.FRETURN: return new Bytecode.FRETURN(offset);
		case Bytecode.FSTORE: return new Bytecode.FSTORE(offset, readU1());
		case Bytecode.FSTORE_0: return new Bytecode.FSTORE_0(offset);
		case Bytecode.FSTORE_1: return new Bytecode.FSTORE_1(offset);
		case Bytecode.FSTORE_2: return new Bytecode.FSTORE_2(offset);
		case Bytecode.FSTORE_3: return new Bytecode.FSTORE_3(offset);
		case Bytecode.FSUB: return new Bytecode.FSUB(offset);
		case Bytecode.GETFIELD: return GETFIELD(offset); 
		case Bytecode.GETSTATIC: return GETSTATIC(offset);
		case Bytecode.GOTO: return new Bytecode.GOTO(offset, readS2());
		case Bytecode.GOTO_W: return new Bytecode.GOTO_W(offset, readS4());
		case Bytecode.I2B: return new Bytecode.I2B(offset);
		case Bytecode.I2C: return new Bytecode.I2C(offset);
		case Bytecode.I2D: return new Bytecode.I2D(offset);
		case Bytecode.I2F: return new Bytecode.I2F(offset);
		case Bytecode.I2L: return new Bytecode.I2L(offset);
		case Bytecode.I2S: return new Bytecode.I2S(offset);
		case Bytecode.IADD: return new Bytecode.IADD(offset);
		case Bytecode.IALOAD: return new Bytecode.IALOAD(offset);
		case Bytecode.IAND: return new Bytecode.IAND(offset);
		case Bytecode.IASTORE: return new Bytecode.IAND(offset);
		case Bytecode.ICONST_M1: return new Bytecode.ICONST_M1(offset);
		case Bytecode.ICONST_0: return new Bytecode.ICONST_0(offset);
		case Bytecode.ICONST_1: return new Bytecode.ICONST_1(offset);
		case Bytecode.ICONST_2: return new Bytecode.ICONST_2(offset);
		case Bytecode.ICONST_3: return new Bytecode.ICONST_3(offset);
		case Bytecode.ICONST_4: return new Bytecode.ICONST_4(offset);
		case Bytecode.ICONST_5: return new Bytecode.ICONST_5(offset);
		case Bytecode.IDIV: return new Bytecode.IDIV(offset);
		case Bytecode.IF_ACMPEQ: return new Bytecode.IF_ACMPEQ(offset, readS2());
		case Bytecode.IF_ACMPNE: return new Bytecode.IF_ACMPNE(offset, readS2());
		case Bytecode.IF_ICMPEQ: return new Bytecode.IF_ICMPEQ(offset, readS2());
		case Bytecode.IF_ICMPNE: return new Bytecode.IF_ICMPNE(offset, readS2());
		case Bytecode.IF_ICMPLT: return new Bytecode.IF_ICMPLT(offset, readS2());
		case Bytecode.IF_ICMPGE: return new Bytecode.IF_ICMPGE(offset, readS2());
		case Bytecode.IF_ICMPGT: return new Bytecode.IF_ICMPGT(offset, readS2());
		case Bytecode.IF_ICMPLE: return new Bytecode.IF_ICMPLE(offset, readS2());
		case Bytecode.IFEQ: return new Bytecode.IFEQ(offset, readS2());
		case Bytecode.IFNE: return new Bytecode.IFNE(offset, readS2());
		case Bytecode.IFLT: return new Bytecode.IFLT(offset, readS2());
		case Bytecode.IFGE: return new Bytecode.IFGE(offset, readS2());
		case Bytecode.IFGT: return new Bytecode.IFGT(offset, readS2());
		case Bytecode.IFLE: return new Bytecode.IFLE(offset, readS2());
		case Bytecode.IFNONNULL: return new Bytecode.IFNONNULL(offset, readS2());
		case Bytecode.IFNULL: return new Bytecode.IFNULL(offset, readS2());
		case Bytecode.IINC: return new Bytecode.IINC(offset, readU1(), readS1());
		case Bytecode.ILOAD: return new Bytecode.ILOAD(offset, readU1());
		case Bytecode.ILOAD_0: return new Bytecode.ILOAD_0(offset);
		case Bytecode.ILOAD_1: return new Bytecode.ILOAD_1(offset);
		case Bytecode.ILOAD_2: return new Bytecode.ILOAD_2(offset);
		case Bytecode.ILOAD_3: return new Bytecode.ILOAD_3(offset);
		case Bytecode.IMUL: return new Bytecode.IMUL(offset);
		case Bytecode.INEG: return new Bytecode.INEG(offset);
		case Bytecode.INSTANCEOF: return INSTANCEOF(offset);
		case Bytecode.INVOKEDYNAMIC: return INVOKEDYNAMIC(offset);
		case Bytecode.INVOKEINTERFACE: return INVOKEINTERFACE(offset);
        case Bytecode.INVOKESPECIAL: return INVOKESPECIAL(offset);
		case Bytecode.INVOKESTATIC: return INVOKESTATIC(offset);
		case Bytecode.INVOKEVIRTUAL: return INVOKEVIRTUAL(offset);
		case Bytecode.IOR: return new Bytecode.IOR(offset);
		case Bytecode.IREM: return new Bytecode.IREM(offset);
		case Bytecode.IRETURN: return new Bytecode.IRETURN(offset);
		case Bytecode.ISHL: return new Bytecode.ISHL(offset);
		case Bytecode.ISHR: return new Bytecode.ISHR(offset);
		case Bytecode.ISTORE: return new Bytecode.ISTORE(offset, readU1());
		case Bytecode.ISTORE_0: return new Bytecode.ISTORE_0(offset);
		case Bytecode.ISTORE_1: return new Bytecode.ISTORE_1(offset);
		case Bytecode.ISTORE_2: return new Bytecode.ISTORE_2(offset);
		case Bytecode.ISTORE_3: return new Bytecode.ISTORE_3(offset);
		case Bytecode.ISUB: return new Bytecode.ISUB(offset);
		case Bytecode.IUSHR: return new Bytecode.IUSHR(offset);
		case Bytecode.IXOR: return new Bytecode.IXOR(offset);
		case Bytecode.JSR: return new Bytecode.JSR(offset, readS2());
		case Bytecode.JSR_W: return new Bytecode.JSR_W(offset, readS4());
		case Bytecode.L2D: return new Bytecode.L2D(offset);
		case Bytecode.L2F: return new Bytecode.L2F(offset);
		case Bytecode.L2I: return new Bytecode.L2I(offset);
		case Bytecode.LADD: return new Bytecode.LADD(offset);
		case Bytecode.LALOAD: return new Bytecode.LALOAD(offset);
		case Bytecode.LAND: return new Bytecode.LAND(offset);
		case Bytecode.LASTORE: return new Bytecode.LASTORE(offset);
		case Bytecode.LCMP: return new Bytecode.LCMP(offset);
		case Bytecode.LCONST_0: return new Bytecode.LCONST_0(offset);
		case Bytecode.LCONST_1: return new Bytecode.LCONST_1(offset);
		case Bytecode.LDC: return LDC(offset); 
		case Bytecode.LDC_W: return LDC_W(offset);
		case Bytecode.LDC2_W: return LDC2_W(offset);
		case Bytecode.LDIV: return new Bytecode.LDIV(offset);
		case Bytecode.LLOAD: return new Bytecode.LLOAD(offset, readU1());
		case Bytecode.LLOAD_0: return new Bytecode.LLOAD_0(offset);
		case Bytecode.LLOAD_1: return new Bytecode.LLOAD_1(offset);
		case Bytecode.LLOAD_2: return new Bytecode.LLOAD_2(offset);
		case Bytecode.LLOAD_3: return new Bytecode.LLOAD_3(offset);
		case Bytecode.LMUL: return new Bytecode.LMUL(offset);
		case Bytecode.LNEG: return new Bytecode.LNEG(offset);
		case Bytecode.LOOKUPSWITCH: return LOOKUPSWITCH(offset);
		case Bytecode.LOR: return new Bytecode.LOR(offset);
		case Bytecode.LREM: return new Bytecode.LREM(offset);
		case Bytecode.LRETURN: return new Bytecode.LRETURN(offset);
		case Bytecode.LSHL: return new Bytecode.LSHL(offset);
		case Bytecode.LSHR: return new Bytecode.LSHR(offset);
		case Bytecode.LSTORE: return new Bytecode.LSTORE(offset, readU1());
		case Bytecode.LSTORE_0: return new Bytecode.LSTORE_0(offset);
		case Bytecode.LSTORE_1: return new Bytecode.LSTORE_1(offset);
		case Bytecode.LSTORE_2: return new Bytecode.LSTORE_2(offset);
		case Bytecode.LSTORE_3: return new Bytecode.LSTORE_3(offset);
		case Bytecode.LSUB: return new Bytecode.LSUB(offset);
		case Bytecode.LUSHR: return new Bytecode.LUSHR(offset);
		case Bytecode.LXOR: return new Bytecode.LXOR(offset);
		case Bytecode.MONITORENTER: return new Bytecode.MONITORENTER(offset);
		case Bytecode.MONITOREXIT: return new Bytecode.MONITOREXIT(offset);
		case Bytecode.MULTIANEWARRAY: return MULTIANEWARRAY(offset); 
		case Bytecode.NEW: return NEW(offset);
		case Bytecode.NEWARRAY: return new Bytecode.NEWARRAY(offset, readS1());
		case Bytecode.NOP: return new Bytecode.NOP(offset);
		case Bytecode.POP: return new Bytecode.POP(offset);
		case Bytecode.POP2: return new Bytecode.POP2(offset);
		case Bytecode.PUTFIELD: return PUTFIELD(offset);
		case Bytecode.PUTSTATIC: return PUTSTATIC(offset);
		case Bytecode.RET: return new Bytecode.RET(offset, readU1());
		case Bytecode.RETURN: return new Bytecode.RETURN(offset);
		case Bytecode.SALOAD: return new Bytecode.SALOAD(offset);
		case Bytecode.SASTORE: return new Bytecode.SASTORE(offset);
		case Bytecode.SIPUSH: return new Bytecode.SIPUSH(offset, readS2());
		case Bytecode.SWAP: return new Bytecode.SWAP(offset);
		case Bytecode.TABLESWITCH: return TABLESWITCH(offset);
		case Bytecode.WIDE: return WIDE(offset);
        default: throw new Exception("invalid bytecode " + String.format("%02x", opcode));
        }        
    }

    private Bytecode ANEWARRAY(long offset) throws Exception {
        int index = readU2();
        String type = constants().getClass(index).className();
        return new Bytecode.ANEWARRAY(offset, index, type);
    }

    private Bytecode CHECKCAST(long offset) throws Exception {
        int index = readU2();
        String type = constants().getClass(index).className();
        return new Bytecode.CHECKCAST(offset, index, type);
    }

    public Bytecode GETFIELD(long offset) throws Exception {
        int index = readU2();
        FieldRefConstant ref = constants().getFieldRef(index);
        return new Bytecode.GETFIELD(offset, index, ref.className(), ref.fieldName(), ref.typeName());
    }

    public Bytecode GETSTATIC(long offset) throws Exception {
        int index = readU2();
        FieldRefConstant ref = constants().getFieldRef(index);                        
        return new Bytecode.GETSTATIC(offset, index, ref.className(), ref.fieldName(), ref.typeName());
    }

    private Bytecode INSTANCEOF(long offset) throws Exception {
        int index = readU2();
        String type = constants().getClass(index).className();
        return new Bytecode.INSTANCEOF(offset, index, type);
    }

	private Bytecode INVOKEDYNAMIC(long offset) throws Exception {
		int index = readU2();
		InvokeDynamicConstant invoke = constants().getInvokeDynamic(index);		
		Bytecode bytecode = new Bytecode.INVOKEDYNAMIC(offset, index,
													   invoke.bootstrapMethod(),
													   invoke.methodName(),
													   invoke.argumentString(),
													   invoke.returnType());
		skip(SIZE2); return bytecode;
	}

    private Bytecode INVOKEINTERFACE(long offset) throws Exception {
        int index = readU2();
        InterfaceMethodRefConstant ref = constants().getInterfaceMethodRef(index);                          
		Bytecode bytecode = new Bytecode.INVOKEINTERFACE(offset, index, readU1(),
														 ref.className(),
														 ref.methodName(),
														 ref.argumentString(),
														 ref.returnType());
		skip(SIZE1); return bytecode;
	}

    private Bytecode INVOKESPECIAL(long offset) throws Exception {
        int index = readU2();
		TypeRefConstant ref = null;
        if (null != constants().getMethodRef(index)) {
            ref = constants().getMethodRef(index);			
        } else if (null != constants().getInterfaceMethodRef(index)) {
            ref = constants().getInterfaceMethodRef(index);
        }
		return new Bytecode.INVOKESPECIAL(offset, index,
										  ref.className(),
										  ref.methodName(),
										  ref.argumentString(),
										  ref.returnType());
    }

    public Bytecode INVOKESTATIC(long offset) throws Exception {
        int index = readU2();            
        TypeRefConstant ref = null;
        if (null != constants().getMethodRef(index)) {
            ref = constants().getMethodRef(index);            
        } else if (null != constants().getInterfaceMethodRef(index)) {
            ref = constants().getInterfaceMethodRef(index);            
        }		
        return new Bytecode.INVOKESTATIC(offset, index,
										 ref.className(),
										 ref.methodName(),
										 ref.argumentString(),
										 ref.returnType());
    }

    public Bytecode INVOKEVIRTUAL(long offset) throws Exception {
        int index = readU2();            
        MethodRefConstant ref = constants().getMethodRef(index);        
        return new Bytecode.INVOKEVIRTUAL(offset, index,
										  ref.className(),
										  ref.methodName(),
										  ref.argumentString(),
										  ref.returnType());
    }
	
    public Bytecode LDC(long offset) throws Exception {		
		short index = readU1(); return LDC_B(new Bytecode.LDC(offset, index), index);				
    }

    public Bytecode LDC_W(long offset) throws Exception {
		int index = readU2(); return LDC_B(new Bytecode.LDC_W(offset, index), index);
    }

	private Bytecode LDC_B(Bytecode.LDC_B code, int index) throws Exception {
		if (null != constants().getInteger(index)) {
			code.flag(Bytecode.LDC_B.FLAG_INTEGER);
			code.intValue(constants().getInteger(index).value());
			return code;
		} else if (null != constants().getFloat(index)) {
			code.flag(Bytecode.LDC_B.FLAG_FLOAT);
			code.floatValue(constants().getFloat(index).value());
			return code;
		} else if (null != constants().getString(index)) {
			code.flag(Bytecode.LDC_B.FLAG_STRING);
			code.string(constants().getUTF8(constants().getString(index).index()).string());
			return code;
		} else if (null != constants().getClass(index)) {
			code.flag(Bytecode.LDC_B.FLAG_CLASS);
			code.clazz(constants().getClass(index).className());
			return code;
		} else if (null != constants().getMethodType(index)) {
			MethodTypeConstant type = constants().getMethodType(index);
			code.flag(Bytecode.LDC_B.FLAG_METHODTYPE);
			code.arguments(type.argumentString());
			code.returnType(type.returnType());
			return code;
		} else if (null != constants().getMethodHandle(index)) {
			MethodHandleConstant handle = constants().getMethodHandle(index);
			code.flag(Bytecode.LDC_B.FLAG_METHODHANDLE);
			code.kind(handle.kind());
			if (MethodHandleConstant.REF_GETFIELD == handle.kind() ||
				MethodHandleConstant.REF_GETSTATIC == handle.kind() ||
				MethodHandleConstant.REF_PUTFIELD == handle.kind() ||
				MethodHandleConstant.REF_PUTSTATIC == handle.kind()) {												
				FieldRefConstant ref = constants().getFieldRef(handle.index());                				
				code.clazz(ref.className());        
				code.field(ref.fieldName());
				code.type(ref.typeName());
				return code;
			} else if (MethodHandleConstant.REF_INVOKEVIRTUAL == handle.kind() ||
					   MethodHandleConstant.REF_INVOKESTATIC == handle.kind() ||
					   MethodHandleConstant.REF_INVOKESPECIAL == handle.kind() ||
					   MethodHandleConstant.REF_NEWINVOKESPECIAL == handle.kind()) {				
				MethodRefConstant ref = constants().getMethodRef(handle.index());                    				
				code.clazz(ref.className());        
				code.method(ref.methodName());				
				code.arguments(ref.argumentString());
				code.returnType(ref.returnType());
				return code;
			} else if (MethodHandleConstant.REF_INVOKEINTERFACE == handle.kind()) {
				InterfaceMethodRefConstant ref = constants().getInterfaceMethodRef(index);				
				code.clazz(ref.className());
				code.method(ref.methodName());
				code.arguments(ref.argumentString());
				code.returnType(ref.returnType());
				return code;
			}
		}
		throw new Exception("invalid constant index #" + index);
	}

    public Bytecode LDC2_W(long offset) throws Exception {
		int index = readU2();
		if (null != constants().getLong(index)) {
			Bytecode.LDC2_W code = new Bytecode.LDC2_W(offset, index);
			code.flag(Bytecode.LDC_B.FLAG_LONG);
			code.longValue(constants().getLong(index).value());
			return code;
		} else if (null != constants().getDouble(index)) {
			Bytecode.LDC2_W code = new Bytecode.LDC2_W(offset, index);
			code.flag(Bytecode.LDC_B.FLAG_DOUBLE);
			code.doubleValue(constants().getDouble(index).value());
			return code;
		}		
		throw new Exception("invalid constant index #" + index);
    }

	private Bytecode LOOKUPSWITCH(long offset) throws Exception {
        long mode = (offset + 1) % 4; 
        if (0 != mode) skip(4 - mode);
		int defaultOffset = (int)(offset + readS4()); int count = readS4();
		Map<Integer, Integer> matches = new LinkedHashMap<Integer, Integer>();
		for (int i = 0; i < count; ++i) {
			matches.put(readS4(), (int)(offset + readS4()));
		}
		return new Bytecode.LOOKUPSWITCH(offset, defaultOffset, matches);
	}

    private Bytecode MULTIANEWARRAY(long offset) throws Exception {
        int index = readU2();
        String type = constants().getClass(index).className();
        return new Bytecode.MULTIANEWARRAY(offset, index, readU1(), type);
    }

    private Bytecode NEW(long offset) throws Exception {
        int index = readU2();
        String type = constants().getClass(index).className(); 
        return new Bytecode.NEW(offset, index, type);
    }

    private Bytecode PUTFIELD(long offset) throws Exception {
        int index = readU2();
        FieldRefConstant ref = constants().getFieldRef(index);
        return new Bytecode.PUTFIELD(offset, index, ref.className(), ref.fieldName(), ref.typeName());
    }

    private Bytecode PUTSTATIC(long offset) throws Exception {
        int index = readU2();
        FieldRefConstant ref = constants().getFieldRef(index);                        
        return new Bytecode.PUTSTATIC(offset, index, ref.className(), ref.fieldName(), ref.typeName());
    }

	private Bytecode TABLESWITCH(long offset) throws Exception {
		long mode = (offset + 1) % 4; 
        if (0 != mode) skip(4 - mode);
		int defaultOffset = (int)(offset + readS4()); int low = readS4(); int high = readS4();
		List<Integer> jumps = new LinkedList<Integer>();
		for (int i = 0; i < (high - low + 1); ++i) {
			jumps.add((int)(offset + readS4()));
		}
		return new Bytecode.TABLESWITCH(offset, defaultOffset, jumps);
	}

	private Bytecode WIDE(long offset) throws Exception {
		byte minor = readS1();
		switch (minor) {
		case Bytecode.ILOAD:
		case Bytecode.FLOAD:
		case Bytecode.ALOAD:
		case Bytecode.LLOAD:
		case Bytecode.DLOAD:
		case Bytecode.ISTORE:
		case Bytecode.FSTORE:
		case Bytecode.ASTORE:
		case Bytecode.LSTORE:
		case Bytecode.DSTORE:
		case Bytecode.RET: return new Bytecode.WIDE(offset, minor, readU2());				
		case Bytecode.IINC: return new Bytecode.WIDE(offset, minor, readU2(), readS2());
		default: throw new Exception("invalid code #" + minor);
		}
	}	
        
    // private static Map<Byte, String> opcodes = new HashMap<Byte, String>() {{
            
    //         put(AALOAD, "aaload");
    //         put(AASTORE, "aastore");
    //         put(ACONST_NULL, "aconst_null");
            
    //         put(ALOAD, "aload");
    //         put(ALOAD_0, "aload_0");
    //         put(ALOAD_1, "aload_1");
    //         put(ALOAD_2, "aload_2");
    //         put(ALOAD_3, "aload_3");
            
    //         put(ANEWARRAY, "anewarray");
    //         put(ARETURN, "areturn");
    //         put(ARRAYLENGTH, "arraylength");
            
    //         put(ASTORE, "astore");
    //         put(ASTORE_0, "astore_0");
    //         put(ASTORE_1, "astore_1");
    //         put(ASTORE_2, "astore_2");
    //         put(ASTORE_3, "astore_3");
            
    //         put(ATHROW, "athrow");
    //         put(BALOAD, "baload");
    //         put(BASTORE, "bastore");
    //         put(BIPUSH, "bipush");
    //         put(CALOAD, "caload");
    //         put(CASTORE, "castore");
    //         put(CHECKCAST, "checkcast");
            
    //         put(D2F, "d2f");
    //         put(D2I, "d2i");
    //         put(D2L, "d2l");
            
    //         put(DADD, "dadd");
    //         put(DALOAD, "daload");
    //         put(DASTORE, "dastore");
            
    //         put(DCMPG, "dcmpg");
    //         put(DCMPL, "dcmpl");
            
    //         put(DCONST_0, "dconst_0");
    //         put(DCONST_1, "dconst_1");
            
    //         put(DDIV, "ddiv");
    //         put(DMUL, "dmul");
            
    //         put(DLOAD, "dload");
    //         put(DLOAD_0, "dload_0");
    //         put(DLOAD_1, "dload_1");
    //         put(DLOAD_2, "dload_2");
    //         put(DLOAD_3, "dload_3");                       
            
    //         put(DNEG, "dneg");
    //         put(DREM, "drem");
            
    //         put(DRETURN, "dreturn");
            
    //         put(DSTORE, "dstore");
    //         put(DSTORE_0, "dstore_0");
    //         put(DSTORE_1, "dstore_1");
    //         put(DSTORE_2, "dstore_2");
    //         put(DSTORE_3, "dstore_3");
            
    //         put(DSUB, "dsub");
            
    //         put(DUP, "dup");
    //         put(DUP_X1, "dup_x1");
    //         put(DUP_X2, "dup_x2");
    //         put(DUP2, "dup2");
    //         put(DUP2_X1, "dup2_x1");
    //         put(DUP2_X2, "dup2_x2");
            
    //         put(F2D, "f2d");
    //         put(F2I, "f2i");
    //         put(F2L, "f2l");
            
    //         put(DADD, "fadd");
    //         put(FALOAD, "faload");
    //         put(FASTORE, "fastore");
            
    //         put(FCMPG, "fcmpg");
    //         put(FCMPL, "fcmpl");
            
    //         put(FCONST_0, "fconst_0");
    //         put(FCONST_1, "fconst_1");
    //         put(FCONST_2, "fconst_2");
            
    //         put(FDIV, "fdiv");
    //         put(FMUL, "fmul");
            
    //         put(FLOAD, "fload");
    //         put(FLOAD_0, "fload_0");
    //         put(FLOAD_1, "fload_1");
    //         put(FLOAD_2, "fload_2");
    //         put(FLOAD_3, "fload_3");
                        
    //         put(FNEG, "fneg");
    //         put(FREM, "frem");
    //         put(FRETURN, "freturn");
            
    //         put(FSTORE, "fstore");
    //         put(FSTORE_0, "fstore_0");
    //         put(FSTORE_1, "fstore_1");
    //         put(FSTORE_2, "fstore_2");
    //         put(FSTORE_3, "fstore_3");
            
    //         put(FSUB, "fsub");
            
    //         put(GETFIELD, "getfield");
    //         put(GETSTATIC, "getstatic");
            
    //         put(GOTO, "goto");
    //         put(GOTO_W, "goto_w");
            
    //         put(I2B, "i2b");
    //         put(I2C, "i2c");
    //         put(I2D, "i2d");
    //         put(I2F, "i2f");
    //         put(I2L, "i2l");
    //         put(I2S, "i2s");
            
    //         put(IADD, "iadd");            
    //         put(IALOAD, "iaload");
    //         put(IAND, "iand");
    //         put(IASTORE, "iastore");
            
    //         put(ICONST_M1, "iconst_m1");
    //         put(ICONST_0, "iconst_0");
    //         put(ICONST_1, "iconst_1");
    //         put(ICONST_2, "iconst_2");
    //         put(ICONST_3, "iconst_3");
    //         put(ICONST_4, "iconst_4");
    //         put(ICONST_5, "iconst_5");
            
    //         put(IDIV, "idiv");
            
    //         put(IF_ACMPEQ, "if_acmpeq");
    //         put(IF_ACMPNE, "if_acmpne");
            
    //         put(IF_ICMPEQ, "if_icmpeq");
    //         put(IF_ICMPNE, "if_icmpne");
    //         put(IF_ICMPLT, "if_icmplt");
    //         put(IF_ICMPGE, "if_icmpge");
    //         put(IF_ICMPGT, "if_icmpgt");
    //         put(IF_ICMPLE, "if_icmple");
            
    //         put(IFEQ, "ifeq");
    //         put(IFNE, "ifne");
    //         put(IFLT, "iflt");
    //         put(IFGE, "ifge");
    //         put(IFGT, "ifgt");
    //         put(IFLE, "ifle");
            
    //         put(IFNONNULL, "ifnonnull");
    //         put(IFNULL, "ifnull");
    //         put(IINC, "iinc");
            
    //         put(ILOAD, "iload");
    //         put(ILOAD_0, "iload_0");
    //         put(ILOAD_1, "iload_1");
    //         put(ILOAD_2, "iload_2");
    //         put(ILOAD_3, "iload_3");
            
    //         put(IMUL, "imul");
    //         put(INEG, "ineg");
            
    //         put(INSTANCEOF, "instanceof");
            
    //         put(INVOKEDYNAMIC, "invokedynamic");
    //         put(INVOKEINTERFACE, "invokeinterface");
    //         put(INVOKESPECIAL, "invokespecial");
    //         put(INVOKESTATIC, "invokestatic");
    //         put(INVOKEVIRTUAL, "invokevirtual");
            
    //         put(IOR, "ior");
            
    //         put(IREM, "irem");
    //         put(IRETURN, "ireturn");
            
    //         put(ISHL, "ishl");
    //         put(ISHR, "ishr");
            
    //         put(ISTORE, "istore");
    //         put(ISTORE_0, "istore_0");
    //         put(ISTORE_1, "istore_1");
    //         put(ISTORE_2, "istore_2");
    //         put(ISTORE_3, "istore_3");
            
    //         put(ISUB, "isub");
            
    //         put(LUSHR, "iushr");
    //         put(IXOR, "ixor");
            
    //         put(JSR, "jsr");
    //         put(JSR_W, "jsr_w");
            
    //         put(L2D, "l2d");
    //         put(L2F, "l2f");
    //         put(L2I, "l2i");
            
    //         put(LADD, "ladd");
    //         put(LALOAD, "laload");
    //         put(LAND, "land");
    //         put(LASTORE, "lastore");
            
    //         put(LCMP, "lcmp");
            
    //         put(LCONST_0, "lconst_0");
    //         put(LCONST_1, "lconst_1");
            
    //         put(LDC, "ldc");
    //         put(LDC_W, "ldc_w");
    //         put(LDC2_W, "ldc2_w");
            
    //         put(LDIV, "ldiv");
            
    //         put(LLOAD, "lload");
    //         put(LLOAD_0, "lload_0");
    //         put(LLOAD_1, "lload_1");
    //         put(LLOAD_2, "lload_2");
    //         put(LLOAD_3, "lload_3");
            
    //         put(LMUL, "lmul");
            
    //         put(LNEG, "lneg");
            
    //         put(LOOKUPSWITCH, "lookupswitch");
            
    //         put(LOR, "lor");
            
    //         put(LREM, "lrem");
            
    //         put(LRETURN, "lreturn");
            
    //         put(LSHL, "lshl");
    //         put(LSHR, "lshr");
            
    //         put(LSTORE, "lstore");
    //         put(LSTORE_0, "lstore_0");
    //         put(LSTORE_1, "lstore_1");
    //         put(LSTORE_2, "lstore_2");
    //         put(LSTORE_3, "lstore_3");
            
    //         put(LSUB, "lsub");
    //         put(LUSHR, "lushr");
    //         put(LXOR, "lxor");
            
    //         put(MONITORENTER, "monitorenter");
    //         put(MONITOREXIT, "monitorexit");
            
    //         put(MULTIANEWARRAY, "multianewarray");
            
    //         put(NEW, "new");
    //         put(NEWARRAY, "newarray");
            
    //         put(NOP, "nop");
            
    //         put(POP, "pop");
    //         put(POP2, "pop2");
            
    //         put(PUTFIELD, "putfield");
    //         put(PUTSTATIC, "putstatic");
            
    //         put(RET, "ret");
    //         put(RETURN, "return");
            
    //         put(SALOAD, "saload");
    //         put(SASTORE, "sastore");
            
    //         put(SIPUSH, "sipush");
            
    //         put(SWAP, "swap");
            
    //         put(TABLESWITCH, "tableswitch");
            
    //         put(WIDE, "wide");                        
    //     }};	
}
