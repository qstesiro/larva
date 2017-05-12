package com.runbox.clazz.entry.bytecode;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.runbox.clazz.entry.Entry;

public class Bytecode extends Entry {        

    public Bytecode(long offset, String name) {
        super(offset); this.name = name.trim().toLowerCase();
    }	
	
    public Bytecode(long offset, String name, byte opcode) {
        super(offset); 
		this.name = name.trim().toLowerCase();
		this.opcode = opcode; 		
    }       

	private String name = null;

	public Bytecode name(String name) {
		this.name = name; return this;
	}

	public String name() {
		return name;
	}

	private byte opcode = 0;
	
    public Bytecode opcode(byte opcode) {
        this.opcode = opcode; return this;
    }

    public byte opcode() {
        return opcode;
    }

	public static class AALOAD extends Bytecode {
		
		public AALOAD(long offset) {
			super(offset, "AALOAD", AALOAD);
		}
	}

	public static class AASTORE extends Bytecode {

		public AASTORE(long offset) {
			super(offset, "AASTORE", AASTORE);
		}
	}

	public static class ACONST_NULL extends Bytecode {

		public ACONST_NULL(long offset) {
			super(offset, "ACONST_NULL", ACONST_NULL);
		}
	}

	public static class ALOAD extends Bytecode {

		public ALOAD(long offset, short index) {
			super(offset, "ALOAD", ALOAD);
			this.index = index;
		}

		private short index = 0;

		public void index(short index) {
			this.index = index;
		}

		public short index() {
			return index;
		}
	}

	public static class ALOAD_0 extends Bytecode {

		public ALOAD_0(long offset) {
			super(offset, "ALOAD_0", ALOAD_0);
		}
	}

	public static class ALOAD_1 extends Bytecode {

		public ALOAD_1(long offset) {
			super(offset, "ALOAD_1", ALOAD_1);
		}
	}

	public static class ALOAD_2 extends Bytecode {

		public ALOAD_2(long offset) {
			super(offset, "ALOAD_2", ALOAD_2);
		}
	}

	public static class ALOAD_3 extends Bytecode {

		public ALOAD_3(long offset) {
			super(offset, "ALOAD_3", ALOAD_3);
		}
	}


	public static class ANEWARRAY extends Bytecode {

		public ANEWARRAY(long offset, int index, String type) {
			super(offset, "ANEWARRAY", ANEWARRAY);
			this.index = index;
			this.type = type;
		}

		private int index = 0;
		
		public void index(int index) {
			this.index = index;
		}

		public int index() {
			return index;
		}

		private String type = null;

		public void type(String type) {
			this.type = type;
		}

		public String type() {
			return type;
		}
	}

	public static class ARETURN extends Bytecode {

		public ARETURN(long offset) {
			super(offset, "ARETURN", ARETURN);
		}
	}

	public static class ARRAYLENGTH extends Bytecode {

		public ARRAYLENGTH(long offset) {
			super(offset, "ARRAYLENGTH", ARRAYLENGTH);
		}
	}

	public static class ASTORE extends Bytecode {

		public ASTORE(long offset, short index) {
			super(offset, "ASTORE", ASTORE);
			this.index = index;
		}

		private short index = 0;

		public void index(short index) {
			this.index = index;
		}

		public short index() {
			return index;
		}
	}

	public static class ASTORE_0 extends Bytecode {

		public ASTORE_0(long offset) {
			super(offset, "ASTORE_0", ASTORE_0);
		}
	}

	public static class ASTORE_1 extends Bytecode {

		public ASTORE_1(long offset) {
			super(offset, "ASTORE_1", ASTORE_1);
		}
	}

	public static class ASTORE_2 extends Bytecode {

		public ASTORE_2(long offset) {
			super(offset, "ASTORE_2", ASTORE_2);
		}
	}

	public static class ASTORE_3 extends Bytecode {

		public ASTORE_3(long offset) {
			super(offset, "ASTORE_3", ASTORE_3);
		}
	}

	public static class ATHROW extends Bytecode {

		public ATHROW(long offset) {
			super(offset, "ATHROW", ATHROW);
		}
	}

	public static class BALOAD extends Bytecode {

		public BALOAD(long offset) {
			super(offset, "BALOAD", BALOAD);
		}
	}

	public static class BASTORE extends Bytecode {

		public BASTORE(long offset) {
			super(offset, "BASTORE", BASTORE);
		}
	}
	
	public static class BIPUSH extends Bytecode {

		public BIPUSH(long offset, byte value) {
			super(offset, "BIPUSH", BIPUSH);
			this.value = value;
		}

		private byte value = 0;

		public void value(byte value) {
			this.value = value;
		}

		public byte value() {
			return value;
		}
	}

	public static class CALOAD extends Bytecode {

		public CALOAD(long offset) {
			super(offset, "CALOAD", CALOAD);
		}
	}

	public static class CASTORE extends Bytecode {

		public CASTORE(long offset) {
			super(offset, "CASTORE", CASTORE);
		}
	}

	public static class CHECKCAST extends Bytecode {

		public CHECKCAST(long offset, int index, String type) {
			super(offset, "CHECKCAST", CHECKCAST);
			this.index = index;
			this.type = type;
		}

		private int index = 0;

		public void index(int index) {
			this.index = index;
		}

		public int index() {
			return index;
		}

		private String type = null;

		public void type(String type) {
			this.type = type;
		}

		public String type() {
			return type;
		}
	}

	public static class D2F extends Bytecode {

		public D2F(long offset) {
			super(offset, "D2F", D2F);
		}
	}

	public static class D2I extends Bytecode {

		public D2I(long offset) {
			super(offset, "D2I", D2I);
		}
	}

	public static class D2L extends Bytecode {

		public D2L(long offset) {
			super(offset, "D2L", D2L);
		}
	}

	public static class DADD extends Bytecode {

		public DADD(long offset) {
			super(offset, "DADD", DADD);
		}
	}

	public static class DALOAD extends Bytecode {

		public DALOAD(long offset) {
			super(offset, "DALOAD", DALOAD);
		}
	}

	public static class DASTORE extends Bytecode {

		public DASTORE(long offset) {
			super(offset, "DASTORE", DASTORE);
		}
	}

	public static class DCMPG extends Bytecode {

		public DCMPG(long offset) {
			super(offset, "DCMPG", DCMPG);
		}
	}

	public static class DCMPL extends Bytecode {

		public DCMPL(long offset) {
			super(offset, "DCMPL", DCMPL);
		}
	}

	public static class DCONST_0 extends Bytecode {

		public DCONST_0(long offset) {
			super(offset, "DCONST_0", DCONST_0);
		}
	}

	public static class DCONST_1 extends Bytecode {

		public DCONST_1(long offset) {
			super(offset, "DCONST_1", DCONST_1);
		}
	}

	public static class DDIV extends Bytecode {

		public DDIV(long offset) {
			super(offset, "DDIV", DDIV);
		}
	}

	public static class DLOAD extends Bytecode {

		public DLOAD(long offset, short index) {
			super(offset, "DLOAD", DLOAD);
			this.index = index;
		}

		private short index = 0;

		public void index(short index) {
			this.index = index;
		}

		public short index() {
			return index;
		}
	}

	public static class DLOAD_0 extends Bytecode {

		public DLOAD_0(long offset) {
			super(offset, "DLOAD_0", DLOAD_0);
		}
	}

	public static class DLOAD_1 extends Bytecode {

		public DLOAD_1(long offset) {
			super(offset, "DLOAD_1", DLOAD_1);
		}
	}

	public static class DLOAD_2 extends Bytecode {

		public DLOAD_2(long offset) {
			super(offset, "DLOAD_2", DLOAD_2);
		}
	}

	public static class DLOAD_3 extends Bytecode {

		public DLOAD_3(long offset) {
			super(offset, "DLOAD_3", DLOAD_3);
		}
	}

	public static class DMUL extends Bytecode {

		public DMUL(long offset) {
			super(offset, "DMUL", DMUL);
		}
	}

	public static class DNEG extends Bytecode {

		public DNEG(long offset) {
			super(offset, "DNEG", DNEG);
		}
	}

	public static class DREM extends Bytecode {

		public DREM(long offset) {
			super(offset, "DREM", DREM);
		}
	}

	public static class DRETURN extends Bytecode {

		public DRETURN(long offset) {
			super(offset, "DRETURN", DRETURN);
		}
	}

	public static class DSTORE extends Bytecode {

		public DSTORE(long offset, short index) {
			super(offset, "DSTORE", DSTORE);
			this.index = index;
		}

		public short index = 0;

		public void index(short index) {
			this.index = index;
		}

		public short index() {
			return index;
		}
	}

	public static class DSTORE_0 extends Bytecode {

		public DSTORE_0(long offset) {
			super(offset, "DSTORE_0", DSTORE_0);
		}
	}

	public static class DSTORE_1 extends Bytecode {

		public DSTORE_1(long offset) {
			super(offset, "DSTORE_1", DSTORE_1);
		}
	}

	public static class DSTORE_2 extends Bytecode {

		public DSTORE_2(long offset) {
			super(offset, "DSTORE_2", DSTORE_2);
		}
	}

	public static class DSTORE_3 extends Bytecode {

		public DSTORE_3(long offset) {
			super(offset, "DSTORE_3", DSTORE_3);
		}
	}

	public static class DSUB extends Bytecode {

		public DSUB(long offset) {
			super(offset, "DSUB", DSUB);
		}
	}

	public static class DUP extends Bytecode {

		public DUP(long offset) {
			super(offset, "DUP", DUP);
		}
	}

	public static class DUP_X1 extends Bytecode {

		public DUP_X1(long offset) {
			super(offset, "DUP_X1", DUP_X1);
		}
	}

	public static class DUP_X2 extends Bytecode {

		public DUP_X2(long offset) {
			super(offset, "DUP_X2", DUP_X2);
		}
	}

	public static class DUP2 extends Bytecode {

		public DUP2(long offset) {
			super(offset, "DUP2", DUP2);
		}
	}

	public static class DUP2_X1 extends Bytecode {

		public DUP2_X1(long offset) {
			super(offset, "DUP2_X1", DUP2_X1);
		}
	}

	public static class DUP2_X2 extends Bytecode {

		public DUP2_X2(long offset) {
			super(offset, "DUP2_X2", DUP2_X2);
		}
	}

	public static class F2D extends Bytecode {

		public F2D(long offset) {
			super(offset, "F2D", F2D);
		}
	}

	public static class F2I extends Bytecode {

		public F2I(long offset) {
			super(offset, "F2I", F2I);
		}
	}

	public static class F2L extends Bytecode {

		public F2L(long offset) {
			super(offset, "F2L", F2L);
		}
	}

	public static class FADD extends Bytecode {

		public FADD(long offset) {
			super(offset, "FADD", FADD);
		}
	}

	public static class FALOAD extends Bytecode {

		public FALOAD(long offset) {
			super(offset, "FALOAD", FALOAD);
		}
	}

	public static class FASTORE extends Bytecode {

		public FASTORE(long offset) {
			super(offset, "FASTORE", FASTORE);
		}
	}

	public static class FCMPG extends Bytecode {

		public FCMPG(long offset) {
			super(offset, "FCMPG", FCMPG);
		}
	}

	public static class FCMPL extends Bytecode {

		public FCMPL(long offset) {
			super(offset, "FCMPL", FCMPL);
		}
	}

	public static class FCONST_0 extends Bytecode {

		public FCONST_0(long offset) {
			super(offset, "FCONST_0", FCONST_0);
		}
	}

	public static class FCONST_1 extends Bytecode {

		public FCONST_1(long offset) {
			super(offset, "FCONST_1", FCONST_1);
		}
	}

	public static class FCONST_2 extends Bytecode {

		public FCONST_2(long offset) {
			super(offset, "FCONST_2", FCONST_2);
		}
	}

	public static class FDIV extends Bytecode {

		public FDIV(long offset) {
			super(offset, "FDIV", FDIV);
		}
	}

	public static class FLOAD extends Bytecode {

		public FLOAD(long offset, short index) {
			super(offset, "FLOAD", FLOAD);
			this.index = index;
		}

		public short index = 0;

		public void index(short index) {
			this.index = index;
		}

		public short index() {
			return index;
		}
	}

	public static class FLOAD_0 extends Bytecode {

		public FLOAD_0(long offset) {
			super(offset, "FLOAD_0", FLOAD_0);
		}
	}

	public static class FLOAD_1 extends Bytecode {

		public FLOAD_1(long offset) {
			super(offset, "FLOAD_1", FLOAD_1);
		}
	}

	public static class FLOAD_2 extends Bytecode {

		public FLOAD_2(long offset) {
			super(offset, "FLOAD_2", FLOAD_2);
		}
	}

	public static class FLOAD_3 extends Bytecode {

		public FLOAD_3(long offset) {
			super(offset, "FLOAD_3", FLOAD_3);
		}
	}

	public static class FMUL extends Bytecode {

		public FMUL(long offset) {
			super(offset, "FMUL", FMUL);
		}
	}

	public static class FNEG extends Bytecode {

		public FNEG(long offset) {
			super(offset, "FNEG", FNEG);
		}
	}

	public static class FREM extends Bytecode {

		public FREM(long offset) {
			super(offset, "FREM", FREM);
		}
	}

	public static class FRETURN extends Bytecode {

		public FRETURN(long offset) {
			super(offset, "FRETURN", FRETURN);
		}
	}

	public static class FSTORE extends Bytecode {

		public FSTORE(long offset, short index) {
			super(offset, "FSTORE", FSTORE);
			this.index = index;
		}

		public short index = 0;

		public void index(short index) {
			this.index = index;
		}

		public short index() {
			return index;
		}
	}

	public static class FSTORE_0 extends Bytecode {

		public FSTORE_0(long offset) {
			super(offset, "FSTORE_0", FSTORE_0);
		}
	}

	public static class FSTORE_1 extends Bytecode {

		public FSTORE_1(long offset) {
			super(offset, "FSTORE_1", FSTORE_1);
		}
	}

	public static class FSTORE_2 extends Bytecode {

		public FSTORE_2(long offset) {
			super(offset, "FSTORE_2", FSTORE_2);
		}
	}

	public static class FSTORE_3 extends Bytecode {

		public FSTORE_3(long offset) {
			super(offset, "FSTORE_3", FSTORE_3);
		}
	}

	public static class FSUB extends Bytecode {

		public FSUB(long offset) {
			super(offset, "FSUB", FSUB);
		}
	}

	public static class GET extends Bytecode {

		public GET(long offset, String name, byte opcode, int index, String clazz, String field, String type) {
			super(offset, name, opcode);
			this.index = index;
			this.clazz = clazz;
			this.field = field;
			this.type = type;
		}

		private int index = 0;

		public void index(int index) {
			this.index = index;
		}

		public int index() {
			return index;
		}

		private String clazz = null;

		public void clazz(String clazz) {
			this.clazz = clazz;
		}

		public String clazz() {
			return clazz;
		}

		private String field = null;

		public void field(String field) {
			this.field = field;
		}

		public String field() {
			return field;
		}

		private String type = null;

		public void type(String type) {
			this.type = type;
		}

		public String type() {
			return type;
		}
	}
	
	public static class GETFIELD extends GET {

		public GETFIELD(long offset, int index, String clazz, String field, String type) {
			super(offset, "GETFIELD", GETFIELD, index, clazz, field, type);
		}		
	}
	
	public static class GETSTATIC extends GET {

		public GETSTATIC(long offset, int index, String clazz, String field, String type) {
			super(offset, "GETSTATIC", GETSTATIC, index, clazz, field, type);
		}		
	}

	public static class GOTO extends Bytecode {

		public GOTO(long offset, short target) {
			super(offset, "GOTO", GOTO);
			this.target = target;
		}

		public short target = 0;

		public void target(short target) {
			this.target = target;
		}

		public short target() {
			return target;
		}
	}
	
	public static class GOTO_W extends Bytecode {

		public GOTO_W(long offset, int target) {
			super(offset, "GOTO_W", GOTO_W);
			this.target = target;
		}

		public int target = 0;

		public void target(int target) {
			this.target = target;
		}

		public int target() {
			return target;
		}
	}

	public static class I2B extends Bytecode {

		public I2B(long offset) {
			super(offset, "I2B", I2B);
		}
	}
	
	public static class I2C extends Bytecode {

		public I2C(long offset) {
			super(offset, "I2C", I2C);
		}
	}

	public static class I2D extends Bytecode {

		public I2D(long offset) {
			super(offset, "I2D", I2D);
		}
	}
	
	public static class I2F extends Bytecode {

		public I2F(long offset) {
			super(offset, "I2F", I2F);
		}
	}

	public static class I2L extends Bytecode {

		public I2L(long offset) {
			super(offset, "I2L", I2L);
		}
	}
	
	public static class I2S extends Bytecode {

		public I2S(long offset) {
			super(offset, "I2S", I2S);
		}
	}

	public static class IADD extends Bytecode {

		public IADD(long offset) {
			super(offset, "IADD", IADD);
		}
	}
	
	public static class IALOAD extends Bytecode {

		public IALOAD(long offset) {
			super(offset, "IALOAD", IALOAD);
		}
	}

	public static class IAND extends Bytecode {

		public IAND(long offset) {
			super(offset, "IAND", IAND);
		}
	}
	
	public static class IASTORE extends Bytecode {

		public IASTORE(long offset) {
			super(offset, "IASTORE", IASTORE);
		}
	}

	public static class ICONST_M1 extends Bytecode {

		public ICONST_M1(long offset) {
			super(offset, "ICONST_M1", ICONST_M1);
		}
	}
	
	public static class ICONST_0 extends Bytecode {

		public ICONST_0(long offset) {
			super(offset, "ICONST_0", ICONST_0);
		}
	}

	public static class ICONST_1 extends Bytecode {

		public ICONST_1(long offset) {
			super(offset, "ICONST_1", ICONST_1);
		}
	}
	
	public static class ICONST_2 extends Bytecode {

		public ICONST_2(long offset) {
			super(offset, "ICONST_2", ICONST_2);
		}
	}

	public static class ICONST_3 extends Bytecode {

		public ICONST_3(long offset) {
			super(offset, "ICONST_3", ICONST_3);
		}
	}
	
	public static class ICONST_4 extends Bytecode {

		public ICONST_4(long offset) {
			super(offset, "ICONST_4", ICONST_4);
		}
	}

	public static class ICONST_5 extends Bytecode {

		public ICONST_5(long offset) {
			super(offset, "ICONST_5", ICONST_5);
		}
	}
	
	public static class IDIV extends Bytecode {

		public IDIV(long offset) {
			super(offset, "IDIV", IDIV);
		}
	}

	public static class IF extends Bytecode {

		public IF(long offset, String name, byte opcode, short branch) {
			super(offset, name, opcode);
			this.branch = branch;
		}
		
		private short branch = 0;

		public void branch(short branch) {
			this.branch = branch;
		}

		public short branch() {
			return branch;
		}
	}
	
	public static class IF_ACMP extends IF {

		public IF_ACMP(long offset, String name, byte opcode, short branch) {
			super(offset, name, opcode, branch);
		}		
	}
	
	public static class IF_ACMPEQ extends IF_ACMP {

		public IF_ACMPEQ(long offset, short branch) {
			super(offset, "IF_ACMPEQ", IF_ACMPEQ, branch);
		}		
	}

	public static class IF_ACMPNE extends IF_ACMP {

		public IF_ACMPNE(long offset, short branch) {
			super(offset, "IF_ACMPNE", IF_ACMPNE, branch);
		}		
	}

	public static class IF_ICMP extends IF {

		public IF_ICMP(long offset, String name, byte opcode, short branch) {
			super(offset, name, opcode, branch);
		}
	}
	
	public static class IF_ICMPEQ extends IF_ICMP {

		public IF_ICMPEQ(long offset, short branch) {
			super(offset, "IF_ICMPEQ", IF_ICMPEQ, branch);
		}
	}

	public static class IF_ICMPNE extends IF_ICMP {

		public IF_ICMPNE(long offset, short branch) {
			super(offset, "IF_ICMPNE", IF_ICMPNE, branch);
		}
	}
	
	public static class IF_ICMPLT extends IF_ICMP {

		public IF_ICMPLT(long offset, short branch) {
			super(offset, "IF_ICMPLT", IF_ICMPLT, branch);
		}
	}

	public static class IF_ICMPGE extends IF_ICMP {

		public IF_ICMPGE(long offset, short branch) {
			super(offset, "IF_ICMPGE", IF_ICMPGE, branch);
		}
	}
	
	public static class IF_ICMPGT extends IF_ICMP {

		public IF_ICMPGT(long offset, short branch) {
			super(offset, "IF_ICMPGT", IF_ICMPGT, branch);
		}
	}

	public static class IF_ICMPLE extends IF_ICMP {

		public IF_ICMPLE(long offset, short branch) {
			super(offset, "IF_ICMPLE", IF_ICMPLE, branch);
		}
	}
	
	public static class IFEQ extends IF {

		public IFEQ(long offset, short branch) {
			super(offset, "IFEQ", IFEQ, branch);
		}
	}

	public static class IFNE extends IF {

		public IFNE(long offset, short branch) {
			super(offset, "IFNE", IFNE, branch);
		}
	}
	
	public static class IFLT extends IF {

		public IFLT(long offset, short branch) {
			super(offset, "IFLT", IFLT, branch);
		}
	}

	public static class IFGE extends IF {

		public IFGE(long offset, short branch) {
			super(offset, "IFGE", IFGE, branch);
		}
	}
	
	public static class IFGT extends IF {

		public IFGT(long offset, short branch) {
			super(offset, "IFGT", IFGT, branch);
		}
	}

	public static class IFLE extends IF {

		public IFLE(long offset, short branch) {
			super(offset, "IFLE", IFLE, branch);
		}
	}
	
	public static class IFNONNULL extends IF {

		public IFNONNULL(long offset, short branch) {
			super(offset, "IFNONNULL", IFNONNULL, branch);
		}
	}

	public static class IFNULL extends IF {

		public IFNULL(long offset, short branch) {
			super(offset, "IFNULL", IFNULL, branch);
		}
	}
	
	public static class IINC extends Bytecode {

		public IINC(long offset, short index, byte value) {
			super(offset, "IINC", IINC);
			this.index = index;
			this.value = value;
		}

		private short index = 0;

		public void index(short index) {
			this.index = index;
		}

		public short index() {
			return index;
		}

		private byte value = 0;

		public void value(byte value) {
			this.value = value;
		}

		public byte value() {
			return value;
		}
	}

	public static class ILOAD extends Bytecode {

		public ILOAD(long offset, short index) {
			super(offset, "ILOAD", ILOAD);
			this.index = index;
		}

		private short index = 0;

		public void index(short index) {
			this.index = index;
		}

		public short index() {
			return index;
		}
	}
	
	public static class ILOAD_0 extends Bytecode {

		public ILOAD_0(long offset) {
			super(offset, "ILOAD_0", ILOAD_0);
		}
	}

	public static class ILOAD_1 extends Bytecode {

		public ILOAD_1(long offset) {
			super(offset, "ILOAD_1", ILOAD_1);
		}
	}
	
	public static class ILOAD_2 extends Bytecode {

		public ILOAD_2(long offset) {
			super(offset, "ILOAD_2", ILOAD_2);
		}
	}

	public static class ILOAD_3 extends Bytecode {

		public ILOAD_3(long offset) {
			super(offset, "ILOAD_3", ILOAD_3);
		}
	}
	
	public static class IMUL extends Bytecode {

		public IMUL(long offset) {
			super(offset, "IMUL", IMUL);
		}
	}

	public static class INEG extends Bytecode {

		public INEG(long offset) {
			super(offset, "INEG", INEG);
		}
	}
	
	public static class INSTANCEOF extends Bytecode {

		public INSTANCEOF(long offset, int index, String type) {
			super(offset, "INSTANCEOF", INSTANCEOF);
			this.index = index;
			this.type = type;
		}

		private int index = 0;

		public void index(int index) {
			this.index = index;
		}

		public int index() {
			return index;
		}

		private String type = null;

		public void type(String type) {
			this.type = type;
		}

		public String type() {
			return type;
		}
	}

	public static class INVOKE extends Bytecode {

		public INVOKE(long offset, String name, byte opcode, int index) {
			super(offset, name, opcode);
			this.index = index;
		}

		public INVOKE(long offset, String name, byte opcode, int index, String method, String arguments, String type) {
			super(offset, name, opcode);
			this.index = index;
			this.method = method;
			this.arguments = arguments;
			this.type = type;
		}
		
		public INVOKE(long offset, String name, byte opcode, int index, String clazz, String method, String arguments, String type) {
			super(offset, name, opcode);
			this.index = index;
			this.clazz = clazz;
			this.method = method;
			this.arguments = arguments;
			this.type = type;
		}

		private int index = 0;

		public void index(int index) {
			this.index = index;
		}

		public int index() {
			return index;
		}

		private String clazz = null;

		public void clazz(String clazz) {
			this.clazz = clazz;
		}

		public String clazz() {
			return clazz;
		}

		private String method = null;

		public void method(String method) {
			this.method = method;
		}

		public String method() {
			return method;
		}

		private String arguments = null;

		public void arguments(String arguments) {
			this.arguments = arguments;
		}

		public String arguments() {
			return arguments;
		}

		private String type = null;

		public void type(String type) {
			this.type = type;
		}

		public String type() {
			return type;
		}
	}
	
	public static class INVOKEDYNAMIC extends INVOKE {

		public INVOKEDYNAMIC(long offset, int index, int bootstrap, String method, String arguments, String type) {
			super(offset, "INVOKEDYNAMIC", INVOKEDYNAMIC, index, method, arguments, type);
			this.bootstrap = bootstrap;			
		}

		private int bootstrap = 0;

		public void bootstrap(int bootstrap) {
			this.bootstrap = bootstrap;
		}

		public int bootstrap() {
			return bootstrap;
		}	   		
	}
	
	public static class INVOKEINTERFACE extends INVOKE {

		public INVOKEINTERFACE(long offset, int index, short count, String clazz, String method, String arguments, String type) {
			super(offset, "INVOKEINTERFACE", INVOKEINTERFACE, index, clazz, method, arguments, type);
			this.count = count;
		}

		private short count = 0;

		public void count(short count) {
			this.count = count;
		}

		public short count() {
			return count;
		}
	}

	public static class INVOKESPECIAL extends INVOKE {

		public INVOKESPECIAL(long offset, int index, String clazz, String method, String arguments, String type) {
			super(offset, "INVOKESPECIAL", INVOKESPECIAL, index, clazz, method, arguments, type);
		}
	}
	
	public static class INVOKESTATIC extends INVOKE {

		public INVOKESTATIC(long offset, int index, String clazz, String method, String arguments, String type) {
			super(offset, "INVOKESTATIC", INVOKESTATIC, index, clazz, method, arguments, type);
		}
	}

	public static class INVOKEVIRTUAL extends INVOKE {

		public INVOKEVIRTUAL(long offset, int index, String clazz, String method, String arguments, String type) {
			super(offset, "INVOKEVIRTUAL", INVOKEVIRTUAL, index, clazz, method, arguments, type);
		}
	}
	
	public static class IOR extends Bytecode {

		public IOR(long offset) {
			super(offset, "IOR", IOR);
		}
	}

	public static class IREM extends Bytecode {

		public IREM(long offset) {
			super(offset, "IREM", IREM);
		}
	}
	
	public static class IRETURN extends Bytecode {

		public IRETURN(long offset) {
			super(offset, "IRETURN", IRETURN);
		}
	}

	public static class ISHL extends Bytecode {

		public ISHL(long offset) {
			super(offset, "ISHL", ISHL);
		}
	}
	
	public static class ISHR extends Bytecode {

		public ISHR(long offset) {
			super(offset, "ISHR", ISHR);
		}
	}

	public static class ISTORE extends Bytecode {

		public ISTORE(long offset, short index) {
			super(offset, "ISTORE", ISTORE);
			this.index = index;
		}

		private short index = 0;

		public void index(short index) {
			this.index = index;
		}

		public short index() {
			return index;
		}
	}
	
	public static class ISTORE_0 extends Bytecode {

		public ISTORE_0(long offset) {
			super(offset, "ISTORE_0", ISTORE_0);
		}
	}

	public static class ISTORE_1 extends Bytecode {

		public ISTORE_1(long offset) {
			super(offset, "ISTORE_1", ISTORE_1);
		}
	}

	public static class ISTORE_2 extends Bytecode {

		public ISTORE_2(long offset) {
			super(offset, "ISTORE_2", ISTORE_2);
		}
	}

	public static class ISTORE_3 extends Bytecode {

		public ISTORE_3(long offset) {
			super(offset, "ISTORE_3", ISTORE_3);
		}
	}

	public static class ISUB extends Bytecode {

		public ISUB(long offset) {
			super(offset, "ISUB", ISUB);
		}
	}

	public static class IUSHR extends Bytecode {

		public IUSHR(long offset) {
			super(offset, "IUSHR", IUSHR);
		}
	}

	public static class IXOR extends Bytecode {

		public IXOR(long offset) {
			super(offset, "IXOR", IXOR);
		}
	}

	public static class JSR extends Bytecode {

		public JSR(long offset, short branch) {
			super(offset, "JSR", JSR);
			this.branch = branch;
		}

		private short branch = 0;

		public void branch(short branch) {
			this.branch = branch;
		}

		public short branch() {
			return branch;
		}
	}

	public static class JSR_W extends Bytecode {

		public JSR_W(long offset, int branch) {
			super(offset, "JSR_W", JSR_W);
			this.branch = branch;
		}

		private int branch = 0;

		public void branch(int branch) {
			this.branch = branch;
		}

		public int branch() {
			return branch;
		}
	}

	public static class L2D extends Bytecode {

		public L2D(long offset) {
			super(offset, "L2D", L2D);
		}
	}

	public static class L2F extends Bytecode {

		public L2F(long offset) {
			super(offset, "L2F", L2F);
		}
	}

	public static class L2I extends Bytecode {

		public L2I(long offset) {
			super(offset, "L2I", L2I);
		}
	}

	public static class LADD extends Bytecode {

		public LADD(long offset) {
			super(offset, "LADD", LADD);
		}
	}

	public static class LALOAD extends Bytecode {

		public LALOAD(long offset) {
			super(offset, "LALOAD", LALOAD);
		}
	}

	public static class LAND extends Bytecode {

		public LAND(long offset) {
			super(offset, "LAND", LAND);
		}
	}

	public static class LASTORE extends Bytecode {

		public LASTORE(long offset) {
			super(offset, "LASTORE", LASTORE);
		}
	}

	public static class LCMP extends Bytecode {

		public LCMP(long offset) {
			super(offset, "LCMP", LCMP);
		}
	}

	public static class LCONST_0 extends Bytecode {

		public LCONST_0(long offset) {
			super(offset, "LCONST_0", LCONST_0);
		}
	}

	public static class LCONST_1 extends Bytecode {

		public LCONST_1(long offset) {
			super(offset, "LCONST_1", LCONST_1);
		}
	}

	public static class LDC_B extends Bytecode {

		public LDC_B(long offset, String name, byte opcode) {
			super(offset, name, opcode);
		}	   				

		public static final short FLAG_INTEGER = (short)1;
		public static final short FLAG_FLOAT = (short)2;
		public static final short FLAG_LONG = (short)3;
		public static final short FLAG_DOUBLE = (short)4;
		public static final short FLAG_STRING = (short)5;
		public static final short FLAG_CLASS = (short)6;
		public static final short FLAG_METHODTYPE = (short)7;
		public static final short FLAG_METHODHANDLE = (short)8;		

		private short flag = 0;

		public void flag(short flag) {
			this.flag = flag;
		}

		public short flag() {
			return flag;
		}

		public short kind = 0;

		public void kind(short kind) {
			this.kind = kind;
		}

		public short kind() {
			return kind;
		}
		
		private int intValue = 0;

		public void intValue(int value) {
			intValue = value;
		}

		public int intValue() {
			return intValue;
		}

		private float floatValue = 0.0F;

		public void floatValue(float value) {
			floatValue = value;
		}

		public float floatValue() {
			return floatValue;
		}

		private long longValue = 0;

		public void longValue(long value) {
			this.longValue = value;
		}

		public long longValue() {
			return longValue;
		}

		private double doubleValue = 0.0;
		
		public void doubleValue(double value) {
			this.doubleValue = value;
		}

		public double doubleValue() {
			return doubleValue;
		}
		
		private String string = null;

		public void string(String value) {
			string = value;
		}

		public String string() {
			return string;
		}

		private String clazz = null;

		public void clazz(String clazz) {
			this.clazz = clazz;
		}

		public String clazz() {
			return clazz;
		}

		private String field = null;
		
		public void field(String field) {
			this.field = field;
		}

		public String field() {
			return field;
		}
		
		private String type = null;

		public void type(String type) {
			this.type = type;
		}

		public String type() {
			return type;
		}				

		public String method = null;
		
		public void method(String value) {
			method = value;
		}

		public String method() {
			return method;
		}

		public String arguments = null;

		public void arguments(String arguments) {
			this.arguments = arguments;
		}

		public String arguments() {
			return arguments;
		}

		public String returnType = null;

		public void returnType(String returnType) {
			this.returnType = returnType;
		}

		public String returnType() {
			return returnType;
		}
	}
	
	public static class LDC extends LDC_B {

		public LDC(long offset, short index) {
			super(offset, "LDC", LDC);
			this.index = index;
		}		
		
		private short index = 0;

		public void index(short index) {
			this.index = index;
		}

		public short index() {
			return index;
		}
	}

	public static class LDC_W extends LDC_B {

		public LDC_W(long offset, int index) {
			super(offset, "LDC_W", LDC_W);
			this.index = index;
		}

		public LDC_W(long offset, String name, byte opcode, int index) {
			super(offset, name, opcode);
			this.index = index;
		}
		
		private int index = 0;

		public void index(int index) {
			this.index = index;
		}

		public int index() {
			return index;
		}
	}

	public static class LDC2_W extends LDC_W {

		public LDC2_W(long offset, int index) {
			super(offset, "LDC2_W", LDC2_W, index);
		}	   		
	}

	public static class LDIV extends Bytecode {

		public LDIV(long offset) {
			super(offset, "LDIV", LDIV);
		}
	}

	public static class LLOAD extends Bytecode {

		public LLOAD(long offset, short index) {
			super(offset, "LLOAD", LLOAD);
			this.index = index;
		}

		private short index = 0;

		public void index(short index) {
			this.index = index;
		}

		public short index() {
			return index;
		}
	}

	public static class LLOAD_0 extends Bytecode {

		public LLOAD_0(long offset) {
			super(offset, "LLOAD_0", LLOAD_0);
		}
	}

	public static class LLOAD_1 extends Bytecode {

		public LLOAD_1(long offset) {
			super(offset, "LLOAD_1", LLOAD_1);
		}
	}

	public static class LLOAD_2 extends Bytecode {

		public LLOAD_2(long offset) {
			super(offset, "LLOAD_2", LLOAD_2);
		}
	}

	public static class LLOAD_3 extends Bytecode {

		public LLOAD_3(long offset) {
			super(offset, "LLOAD_3", LLOAD_3);
		}
	}

	public static class LMUL extends Bytecode {

		public LMUL(long offset) {
			super(offset, "LMUL", LMUL);
		}
	}

	public static class LNEG extends Bytecode {

		public LNEG(long offset) {
			super(offset, "LNEG", LNEG);
		}
	}

	public static class SWITCH extends Bytecode {

		public SWITCH(long offset, String name, byte opcode, int defaultOffset) {
			super(offset, name, opcode);
			this.defaultOffset = defaultOffset;
		}

		private int defaultOffset = 0;

		public void defaultOffset(int defaultOffset) {
			this.defaultOffset = defaultOffset;
		}

		public int defaultOffset() {
			return defaultOffset;
		}
	}
	
	public static class LOOKUPSWITCH extends SWITCH {

		public LOOKUPSWITCH(long offset, int defaultOffset, Map<Integer, Integer> matches) {
			super(offset, "LOOKUPSWITCH", LOOKUPSWITCH, defaultOffset);
			this.matches = matches;
		}
	   
		private Map<Integer, Integer> matches = null;

		public void matches(Map<Integer, Integer> matches) {
			this.matches = matches;
		}

		public Map<Integer, Integer> matches() {
			return matches;
		}
	}

	public static class LOR extends Bytecode {

		public LOR(long offset) {
			super(offset, "LOR", LOR);
		}
	}

	public static class LREM extends Bytecode {

		public LREM(long offset) {
			super(offset, "LREM", LREM);
		}
	}

	public static class LRETURN extends Bytecode {

		public LRETURN(long offset) {
			super(offset, "LRETURN", LRETURN);
		}
	}

	public static class LSHL extends Bytecode {

		public LSHL(long offset) {
			super(offset, "LSHL", LSHL);
		}
	}

	public static class LSHR extends Bytecode {

		public LSHR(long offset) {
			super(offset, "LSHR", LSHR);
		}
	}

	public static class LSTORE extends Bytecode {

		public LSTORE(long offset, short index) {
			super(offset, "LSTORE", LSTORE);
			this.index = index;
		}

		private short index = 0;

		public void index(short index) {
			this.index = index;
		}

		public short index() {
			return index;
		}
	}

	public static class LSTORE_0 extends Bytecode {

		public LSTORE_0(long offset) {
			super(offset, "LSTORE_0", LSTORE_0);
		}
	}

	public static class LSTORE_1 extends Bytecode {

		public LSTORE_1(long offset) {
			super(offset, "LSTORE_1", LSTORE_1);
		}
	}

	public static class LSTORE_2 extends Bytecode {

		public LSTORE_2(long offset) {
			super(offset, "LSTORE_2", LSTORE_2);
		}
	}

	public static class LSTORE_3 extends Bytecode {

		public LSTORE_3(long offset) {
			super(offset, "LSTORE_3", LSTORE_3);
		}
	}

	public static class LSUB extends Bytecode {

		public LSUB(long offset) {
			super(offset, "LSUB", LSUB);
		}
	}

	public static class LUSHR extends Bytecode {

		public LUSHR(long offset) {
			super(offset, "LUSHR", LUSHR);
		}
	}

	public static class LXOR extends Bytecode {

		public LXOR(long offset) {
			super(offset, "LXOR", LXOR);
		}
	}

	public static class MONITORENTER extends Bytecode {

		public MONITORENTER(long offset) {
			super(offset, "MONITORENTER", MONITORENTER);
		}
	}

	public static class MONITOREXIT extends Bytecode {

		public MONITOREXIT(long offset) {
			super(offset, "MONITOREXIT", MONITOREXIT);
		}
	}

	public static class MULTIANEWARRAY extends Bytecode {

		public MULTIANEWARRAY(long offset, int index, short dimensions, String type) {
			super(offset, "MULTIANEWARRAY", MULTIANEWARRAY);			
			this.index = index;
			this.dimensions = dimensions;
			this.type = type;
		}

		private int index = 0;

		public void index(int index) {
			this.index = index;
		}

		public int index() {
			return index;
		}

		private short dimensions = 0;

		public void dimensions(short dimensions) {
			this.dimensions = dimensions;
		}

		public short dimensions() {
			return dimensions;
		}

		private String type = null;

		public void type(String type) {
			this.type = type;
		}

		public String type() {
			return type;
		}
	}

	public static class NEW extends Bytecode {

		public NEW(long offset, int index, String type) {
			super(offset, "NEW", NEW);
			this.index = index;
			this.type = type;
		}

		private int index = 0;

		public void index(int index) {
			this.index = index;
		}

		public int index() {
			return index;
		}

		private String type = null;

		public void type(String type) {
			this.type = type;
		}

		public String type() {
			return type;
		}
	}

	public static class NEWARRAY extends Bytecode {

		public static final byte BOOLEAN = (byte)4;
		public static final byte CHAR = (byte)5;
		public static final byte FLOAT = (byte)6;
		public static final byte DOUBLE = (byte)7;
		public static final byte BYTE = (byte)8;
		public static final byte SHORT = (byte)9;
		public static final byte INT = (byte)10;
		public static final byte LONG = (byte)11;

		public NEWARRAY(long offset, byte type) {
			super(offset, "NEWARRAY", NEWARRAY);
			this.type = type;
		}

		private byte type = 0;

		public void type(byte type) {
			this.type = type;
		}

		public byte type() {
			return type;
		}
	}

	public static class NOP extends Bytecode {

		public NOP(long offset) {
			super(offset, "NOP", NOP);
		}
	}

	public static class POP extends Bytecode {

		public POP(long offset) {
			super(offset, "POP", POP);
		}

		public POP(long offset, String name, byte opcode) {
			super(offset, name, opcode);
		}
	}

	public static class POP2 extends POP {

		public POP2(long offset) {
			super(offset, "POP2", POP2);
		}
	}

	public static class PUT extends Bytecode {

		public PUT(long offset, String name, byte opcode, int index, String clazz, String field, String type) {
			super(offset, name, opcode);
			this.index = index;
			this.clazz = clazz;
			this.field = field;
			this.type = type;
		}

		private int index = 0;

		public void index(int index) {
			this.index = index;
		}

		public int index() {
			return index;
		}

		private String clazz = null;

		public void clazz(String clazz) {
			this.clazz = clazz;
		}

		public String clazz() {
			return clazz;
		}

		private String field = null;

		public void field(String field) {
			this.field = field;
		}

		public String field() {
			return field;
		}

		private String type = null;

		public void type(String type) {
			this.type = type;
		}

		public String type() {
			return type;
		}
	}

	public static class PUTFIELD extends PUT {

		public PUTFIELD(long offset, int index, String clazz, String field, String type) {
			super(offset, "PUTFIELD", PUTFIELD, index, clazz, field, type);
		}
	}

	public static class PUTSTATIC extends PUT {

		public PUTSTATIC(long offset, int index, String clazz, String field, String type) {
			super(offset, "PUTSTATIC", PUTSTATIC, index, clazz, field, type);
		}
	}

	public static class RET extends Bytecode {

		public RET(long offset, short index) {
			super(offset, "RET", RET);
			this.index = index;
		}

		private short index = 0;

		public void index(short index) {
			this.index = index;
		}

		public short index() {
			return index;
		}
	}

	public static class RETURN extends Bytecode {

		public RETURN(long offset) {
			super(offset, "RETURN", RETURN);
		}
	}

	public static class SALOAD extends Bytecode {

		public SALOAD(long offset) {
			super(offset, "SALOAD", SALOAD);
		}
	}

	public static class SASTORE extends Bytecode {

		public SASTORE(long offset) {
			super(offset, "SASTORE", SASTORE);
		}
	}

	public static class SIPUSH extends Bytecode {

		public SIPUSH(long offset, short value) {
			super(offset, "SIPUSH", SIPUSH);
			this.value = value;
		}

		private short value = 0;

		public void value(short value) {
			this.value = value;
		}

		public short value() {
			return value;
		}
	}

	public static class SWAP extends Bytecode {

		public SWAP(long offset) {
			super(offset, "SWAP", SWAP);
		}
	}

	public static class TABLESWITCH extends SWITCH {

		public TABLESWITCH(long offset, int defaultOffset, List<Integer> jumps) {
			super(offset, "TABLESWITCH", TABLESWITCH, defaultOffset);
			this.jumps = jumps;
		}

		private List<Integer> jumps = null;

		public void jumps(List<Integer> jumps) {
			this.jumps = jumps;
		}

		public List<Integer> jumps() {
			return jumps;
		}
	}

	public static class WIDE extends Bytecode {

		public WIDE(long offset, byte minor, int index) {
			super(offset, "WIDE", WIDE);
			this.minor = minor;
			this.index = index;
		}

		public WIDE(long offset, byte minor, int index, short value) {
			super(offset, "WIDE", WIDE);
			this.minor = minor;
			this.index = index;
			this.value = value;
		}

		private byte minor = 0;

		public void minor(byte minor) {
			this.minor = minor;
		}

		public byte minor() {
			return minor;
		}

		private int index = 0;

		public void index(int index) {
			this.index = index;
		}

		public int index() {
			return index;
		}

		private short value = 0;

		public void value(short value) {
			this.value = value;
		}

		public short value() {
			return value;
		}
	}

	public static final byte AALOAD = (byte) 0x32;
    public static final byte AASTORE = (byte) 0x53;
    public static final byte ACONST_NULL = (byte) 0x01;
    public static final byte ALOAD = (byte) 0x19;
    public static final byte ALOAD_0 = (byte) 0x2a;
    public static final byte ALOAD_1 = (byte) 0x2b;
    public static final byte ALOAD_2 = (byte) 0x2c;
    public static final byte ALOAD_3 = (byte) 0x2d;
    public static final byte ANEWARRAY = (byte) 0xbd;
    public static final byte ARETURN = (byte) 0xb0;
    public static final byte ARRAYLENGTH = (byte) 0xbe;
    public static final byte ASTORE = (byte) 0x3a;
    public static final byte ASTORE_0 = (byte) 0x4b;
    public static final byte ASTORE_1 = (byte) 0x4c;
    public static final byte ASTORE_2 = (byte) 0x4d;
    public static final byte ASTORE_3 = (byte) 0x4e;
    public static final byte ATHROW = (byte) 0xbf;
    public static final byte BALOAD = (byte) 0x33;
    public static final byte BASTORE = (byte) 0x54;
    public static final byte BIPUSH = (byte) 0x10;
    public static final byte CALOAD = (byte) 0x34;
    public static final byte CASTORE = (byte) 0x55;
    public static final byte CHECKCAST = (byte) 0xc0;
    public static final byte D2F = (byte) 0x90;
    public static final byte D2I = (byte) 0x8e;
    public static final byte D2L = (byte) 0x8f;
    public static final byte DADD = (byte) 0x63;
    public static final byte DALOAD = (byte) 0x31;
    public static final byte DASTORE = (byte) 0x52;
    public static final byte DCMPG = (byte) 0x98;
    public static final byte DCMPL = (byte) 0x97;
    public static final byte DCONST_0 = (byte) 0x0e;
    public static final byte DCONST_1 = (byte) 0x0f;
    public static final byte DDIV = (byte) 0x6f;
    public static final byte DLOAD = (byte) 0x18;
    public static final byte DLOAD_0 = (byte) 0x26;
    public static final byte DLOAD_1 = (byte) 0x27;
    public static final byte DLOAD_2 = (byte) 0x28;
    public static final byte DLOAD_3 = (byte) 0x29;
    public static final byte DMUL = (byte) 0x6b;
    public static final byte DNEG = (byte) 0x77;
    public static final byte DREM = (byte) 0x73;
    public static final byte DRETURN = (byte) 0xaf;
    public static final byte DSTORE = (byte) 0x39;
    public static final byte DSTORE_0 = (byte) 0x47;
    public static final byte DSTORE_1 = (byte) 0x48;
    public static final byte DSTORE_2 = (byte) 0x49;
    public static final byte DSTORE_3 = (byte) 0x4a;    
    public static final byte DSUB = (byte) 0x67;
    public static final byte DUP = (byte) 0x59;
    public static final byte DUP_X1 = (byte) 0x5a;
    public static final byte DUP_X2 = (byte) 0x5b;
    public static final byte DUP2 = (byte) 0x5c;
    public static final byte DUP2_X1 = (byte) 0x5d;
    public static final byte DUP2_X2 = (byte) 0x5e;
    public static final byte F2D = (byte) 0x8d;
    public static final byte F2I = (byte) 0x8b;
    public static final byte F2L = (byte) 0x8c;
    public static final byte FADD = (byte) 0x62;
    public static final byte FALOAD = (byte) 0x30;
    public static final byte FASTORE = (byte) 0x51;
    public static final byte FCMPG = (byte) 0x96;
    public static final byte FCMPL = (byte) 0x95;
    public static final byte FCONST_0 = (byte) 0x0b;
    public static final byte FCONST_1 = (byte) 0x0c;
    public static final byte FCONST_2 = (byte) 0x0d;
    public static final byte FDIV = (byte) 0x6e;    
    public static final byte FLOAD = (byte) 0x17;
    public static final byte FLOAD_0 = (byte) 0x22;
    public static final byte FLOAD_1 = (byte) 0x23;
    public static final byte FLOAD_2 = (byte) 0x24;
    public static final byte FLOAD_3 = (byte) 0x25;
    public static final byte FMUL = (byte) 0x6a;
    public static final byte FNEG = (byte) 0x76;
    public static final byte FREM = (byte) 0x72;
    public static final byte FRETURN = (byte) 0xae;
    public static final byte FSTORE = (byte) 0x38;
    public static final byte FSTORE_0 = (byte) 0x43;
    public static final byte FSTORE_1 = (byte) 0x44;
    public static final byte FSTORE_2 = (byte) 0x45;
    public static final byte FSTORE_3 = (byte) 0x46;
    public static final byte FSUB = (byte) 0x66;
    public static final byte GETFIELD = (byte) 0xb4;
    public static final byte GETSTATIC = (byte) 0xb2;
    public static final byte GOTO = (byte) 0xa7;
    public static final byte GOTO_W = (byte) 0xc8;
    public static final byte I2B = (byte) 0x91;
    public static final byte I2C = (byte) 0x92;
    public static final byte I2D = (byte) 0x87;
    public static final byte I2F = (byte) 0x86;
    public static final byte I2L = (byte) 0x85;
    public static final byte I2S = (byte) 0x93;
    public static final byte IADD = (byte) 0x60;
    public static final byte IALOAD = (byte) 0x2e;
    public static final byte IAND = (byte) 0x7e;
    public static final byte IASTORE = (byte) 0x4f;
    public static final byte ICONST_M1 = (byte) 0x02;
    public static final byte ICONST_0 = (byte) 0x03;
    public static final byte ICONST_1 = (byte) 0x04;
    public static final byte ICONST_2 = (byte) 0x05;
    public static final byte ICONST_3 = (byte) 0x06;
    public static final byte ICONST_4 = (byte) 0x07;
    public static final byte ICONST_5 = (byte) 0x08;
    public static final byte IDIV = (byte) 0x6c;
    public static final byte IF_ACMPEQ = (byte) 0xa5;
    public static final byte IF_ACMPNE = (byte) 0xa6;
    public static final byte IF_ICMPEQ = (byte) 0x9f;
    public static final byte IF_ICMPNE = (byte) 0xa0;
    public static final byte IF_ICMPLT = (byte) 0xa1;
    public static final byte IF_ICMPGE = (byte) 0xa2;
    public static final byte IF_ICMPGT = (byte) 0xa3;
    public static final byte IF_ICMPLE = (byte) 0xa4;
    public static final byte IFEQ = (byte) 0x99;
    public static final byte IFNE = (byte) 0x9a;
    public static final byte IFLT = (byte) 0x9b;
    public static final byte IFGE = (byte) 0x9c;
    public static final byte IFGT = (byte) 0x9d;
    public static final byte IFLE = (byte) 0x9e;
    public static final byte IFNONNULL = (byte) 0xc7;
    public static final byte IFNULL = (byte) 0xc6;
    public static final byte IINC = (byte) 0x84;
    public static final byte ILOAD = (byte) 0x15;
    public static final byte ILOAD_0 = (byte) 0x1a;
    public static final byte ILOAD_1 = (byte) 0x1b;
    public static final byte ILOAD_2 = (byte) 0x1c;
    public static final byte ILOAD_3 = (byte) 0x1d;
    public static final byte IMUL = (byte) 0x68;
    public static final byte INEG = (byte) 0x74;
    public static final byte INSTANCEOF = (byte) 0xc1;
    public static final byte INVOKEDYNAMIC = (byte) 0xba;
    public static final byte INVOKEINTERFACE = (byte) 0xb9;
    public static final byte INVOKESPECIAL = (byte) 0xb7;
    public static final byte INVOKESTATIC = (byte) 0xb8;
    public static final byte INVOKEVIRTUAL = (byte) 0xb6;
    public static final byte IOR = (byte) 0x80;
    public static final byte IREM = (byte) 0x70;
    public static final byte IRETURN = (byte) 0xac;
    public static final byte ISHL = (byte) 0x78;
    public static final byte ISHR = (byte) 0x7a;
    public static final byte ISTORE = (byte) 0x36;
    public static final byte ISTORE_0 = (byte) 0x3b;
    public static final byte ISTORE_1 = (byte) 0x3c;
    public static final byte ISTORE_2 = (byte) 0x3d;
    public static final byte ISTORE_3 = (byte) 0x3e;
    public static final byte ISUB = (byte) 0x64;
    public static final byte IUSHR = (byte) 0x7c;
    public static final byte IXOR = (byte) 0x82;
    public static final byte JSR = (byte) 0xa8;
    public static final byte JSR_W = (byte) 0xc9;
    public static final byte L2D = (byte) 0x8a;
    public static final byte L2F = (byte) 0x89;
    public static final byte L2I = (byte) 0x88;
    public static final byte LADD = (byte) 0x61;
    public static final byte LALOAD = (byte) 0x2f;
    public static final byte LAND = (byte) 0x7f;
    public static final byte LASTORE = (byte) 0x50;
    public static final byte LCMP = (byte) 0x94;
    public static final byte LCONST_0 = (byte) 0x09;
    public static final byte LCONST_1 = (byte) 0x0a;
    public static final byte LDC = (byte) 0x12;
    public static final byte LDC_W = (byte) 0x13;
    public static final byte LDC2_W = (byte) 0x14;
    public static final byte LDIV = (byte) 0x6d;
    public static final byte LLOAD = (byte) 0x16;
    public static final byte LLOAD_0 = (byte) 0x1e;
    public static final byte LLOAD_1 = (byte) 0x1f;
    public static final byte LLOAD_2 = (byte) 0x20;
    public static final byte LLOAD_3 = (byte) 0x21;
    public static final byte LMUL = (byte) 0x69;
    public static final byte LNEG = (byte) 0x75;
    public static final byte LOOKUPSWITCH = (byte) 0xab;
    public static final byte LOR = (byte) 0x81;
    public static final byte LREM = (byte) 0x71;
    public static final byte LRETURN = (byte) 0xad;
    public static final byte LSHL = (byte) 0x79;
    public static final byte LSHR = (byte) 0x7b;
    public static final byte LSTORE = (byte) 0x37;
    public static final byte LSTORE_0 = (byte) 0x3f;
    public static final byte LSTORE_1 = (byte) 0x40;
    public static final byte LSTORE_2 = (byte) 0x41;
    public static final byte LSTORE_3 = (byte) 0x42;
    public static final byte LSUB = (byte) 0x65;
    public static final byte LUSHR = (byte) 0x7d;
    public static final byte LXOR = (byte) 0x83;
    public static final byte MONITORENTER = (byte) 0xc2;
    public static final byte MONITOREXIT = (byte) 0xc3;
    public static final byte MULTIANEWARRAY = (byte) 0xc5;
    public static final byte NEW = (byte) 0xbb;
    public static final byte NEWARRAY = (byte) 0xbc;
    public static final byte NOP = (byte) 0x00;
    public static final byte POP = (byte) 0x57;
    public static final byte POP2 = (byte) 0x58;
    public static final byte PUTFIELD = (byte) 0xb5;
    public static final byte PUTSTATIC = (byte) 0xb3;
    public static final byte RET = (byte) 0xa9;
    public static final byte RETURN = (byte) 0xb1;
    public static final byte SALOAD = (byte) 0x35;
    public static final byte SASTORE = (byte) 0x56;
    public static final byte SIPUSH = (byte) 0x11;
    public static final byte SWAP = (byte) 0x5f;
    public static final byte TABLESWITCH = (byte) 0xaa;
    public static final byte WIDE = (byte) 0xc4;
}
