package com.runbox.dex.entry.bytecode;

public class Bytecode {        

    public Bytecode(int offset, String name) {
		this.offset = offset;
        this.name = name.trim().toLowerCase();
    }	
	
    public Bytecode(int offset, String name, byte opcode) {
		this.offset = offset;
		this.name = name.trim().toLowerCase();
		this.opcode = opcode; 		
    }       

	private int offset = 0;

	public Bytecode offset(int offset) {
		this.offset = offset; return this;
	}

	public int offset() {
		return offset;
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

	public static class NOP extends Bytecode {

		public NOP(int offset) {
			super(offset, "NOP");
		}
	}

	public static class MOVE extends Bytecode {

		public MOVE(int offset, byte vA, byte vB) {
			super(offset, "MOVE", MOVE);
			this.vA = vA;
			this.vB = vB;
		}

		private byte vA = 0;		
		
		public byte vA() {
			return vA;
		}

		private byte vB = 0;
		
		public byte vB() {
			return vB;
		}
	}

	public static class MOVE_FROM16 extends Bytecode {

		public MOVE_FROM16(int offset, short vAA, int vBBBB) {
			super(offset, "MOVE/FROM16", MOVE_FROM16);
			this.vAA = vAA;
			this.vBBBB = vBBBB;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}

		private int vBBBB = 0;

		public int vBBBB() {
			return vBBBB;
		}
	}

	public static class MOVE16 extends Bytecode {

		public MOVE16(int offset, int vAAAA, int vBBBB) {
			super(offset, "MOVE16", MOVE16);
			this.vAAAA = vAAAA;
			this.vBBBB = vBBBB;			
		}

		private int vAAAA = 0;

		public int vAAAA() {
			return vAAAA;
		}

		private int vBBBB = 0;

		public int vBBBB() {
			return vBBBB;
		}
	}

	public static class MOVE_WIDE extends Bytecode {

		public MOVE_WIDE(int offset, byte vA, byte vB) {
			super(offset, "MOVE-WIDE", MOVE_WIDE);
			this.vA = vA;
			this.vB = vB;
		}

		private byte vA = 0;

		public byte vA() {
			return vA;
		}

		private int vB = 0;
		
		public int vB() {
			return vB;
		}
	}

	public static class MOVE_WIDE_FROM16 extends Bytecode {

		public MOVE_WIDE_FROM16(int offset, short vAA, int vBBBB) {
			super(offset, "MOVE-WIDE/FROM16", MOVE_WIDE_FROM16);
			this.vAA = vAA;
			this.vBBBB = vBBBB;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}

		private int vBBBB = 0;

		public int vBBBB() {
			return vBBBB;
		}
	}

	public static class MOVE_WIDE16 extends Bytecode {

		public MOVE_WIDE16(int offset, short vAA, int vBBBB) {
			super(offset, "MOVE-WIDE/16", MOVE_WIDE16);
			this.vAA = vAA;
			this.vBBBB = vBBBB;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}

		private int vBBBB = 0;

		public int vBBBB() {
			return vBBBB;
		}
	}

	public static class MOVE_OBJECT extends Bytecode {

		public MOVE_OBJECT(int offset, byte vA, byte vB) {
			super(offset, "MOVE-OBJECT", MOVE_OBJECT);
			this.vA = vA;
			this.vB = vB;
		}

		private byte vA = 0;

		public byte vA() {
			return vA;
		}

		private byte vB = 0;
		
		public byte vB() {
			return vB;
		}
	}

	public static class MOVE_OBJECT_FROM16 extends Bytecode {

		public MOVE_OBJECT_FROM16(int offset, short vAA, int vBBBB) {
			super(offset, "MOVE-OBJECT/FROM16", MOVE_OBJECT_FROM16);
			this.vAA = vAA;
			this.vBBBB = vBBBB;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}

		private int vBBBB = 0;

		public int vBBBB() {
			return vBBBB;
		}
	}

	public static class MOVE_OBJECT16 extends Bytecode {

		public MOVE_OBJECT16(int offset, int vAAAA, int vBBBB) {
			super(offset, "MOVE-OBJECT/16", MOVE_OBJECT16);
			this.vAAAA = vAAAA;
			this.vBBBB = vBBBB;
		}

		private int vAAAA = 0;

		public int vAAAA() {
			return vAAAA;
		}

		private int vBBBB = 0;

		public int vBBBB() {
			return vBBBB;
		}
	}

	public static class MOVE_RESULT extends Bytecode {

		public MOVE_RESULT(int offset, short vAA) {
			super(offset, "MOVE-RESULT", MOVE_RESULT);
			this.vAA = vAA;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
	}

	public static class MOVE_RESULT_WIDE extends Bytecode {

		public MOVE_RESULT_WIDE(int offset, short vAA) {
			super(offset, "MOVE-RESULT-WIDE", MOVE_RESULT_WIDE);
			this.vAA = vAA;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
	}

	public static class MOVE_RESULT_OBJECT extends Bytecode {

		public MOVE_RESULT_OBJECT(int offset, short vAA) {
			super(offset, "MOVE-RESULT-OBJECT", MOVE_RESULT_OBJECT);
			this.vAA = vAA;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
	}

	public static class MOVE_EXCEPTION extends Bytecode {

		public MOVE_EXCEPTION(int offset, short vAA) {
			super(offset, "MOVE-EXCEPTION", MOVE_EXCEPTION);
			this.vAA = vAA;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
	}

	public static class RETURN_VOID extends Bytecode {

		public RETURN_VOID(int offset) {
			super(offset, "RETURN-VOID", RETURN_VOID);
		}
	}

	public static class RETURN extends Bytecode {

		public RETURN(int offset, short vAA) {
			super(offset, "RETURN", RETURN);
			this.vAA = vAA;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
	}

	public static class RETURN_WIDE extends Bytecode {

		public RETURN_WIDE(int offset, short vAA) {
			super(offset, "RETURN-WIDE", RETURN_WIDE);
			this.vAA = vAA;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
	}

	public static class RETURN_OBJECT extends Bytecode {

		public RETURN_OBJECT(int offset, short vAA) {
			super(offset, "RETURN-OBJECT", RETURN_OBJECT);
			this.vAA = vAA;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
	}

	public static class CONST4 extends Bytecode {

		public CONST4(int offset, byte vA, byte value) {
			super(offset, "CONST4", CONST4);
			this.vA = vA;
			this.value = value;
		}

		private byte vA = 0;

		public byte vA() {
			return vA;
		}

		private int value = 0;

		public int value() {
			return value;
		}
	}

	public static class CONST16 extends Bytecode {

		public CONST16(int offset, short vAA, short value) {
			super(offset, "CONST16", CONST16);
			this.vAA = vAA;
			this.value = value;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}

		private int value = 0;

		public int value() {
			return value;
		}
	}

	public static class CONST extends Bytecode {

		public CONST(int offset, short vAA, int value) {
			super(offset, "CONST", CONST);
			this.vAA = vAA;
			this.value = value;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}

		private int value = 0;

		public int value() {
			return value;
		}
	}

	public static class CONST_HIGH16 extends Bytecode {

		public CONST_HIGH16(int offset, short vAA, int value) {
			super(offset, "CONST/HIGH16", CONST_HIGH16);
			this.vAA = vAA;
			this.value = value;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}

		private int value = 0;

		public int value() {
			return value;
		}
	}

	public static class CONST_WIDE16 extends Bytecode {

		public CONST_WIDE16(int offset, short vAA, long value) {
			super(offset, "CONST-WIDE/16", CONST_WIDE16);
			this.vAA = vAA;
			this.value = value;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}

		private long value = 0;

		public long value() {
			return value;
		}
	}

	public static class CONST_WIDE32 extends Bytecode {

		public CONST_WIDE32(int offset, short vAA, long value) {
			super(offset, "CONST-WIDE/32", CONST_WIDE32);
			this.vAA = vAA;
			this.value = value;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}

		private long value = 0;

		public long value() {
			return value;
		}
	}

	public static class CONST_WIDE extends Bytecode {

		public CONST_WIDE(int offset, short vAA, long value) {
			super(offset, "CONST-WIDE", CONST_WIDE);
			this.vAA = vAA;
			this.value = value;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}

		private long value = 0;

		public long value() {
			return value;
		}
	}

	public static class CONST_WIDE_HIGH16 extends Bytecode {

		public CONST_WIDE_HIGH16(int offset, short vAA, long value) {
			super(offset, "CONST-WIDE/HIGH16", CONST_WIDE_HIGH16);
			this.vAA = vAA;
			this.value = value;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}

		private long value = 0;

		public long value() {
			return value;
		}
	}

	public static class CONST_STRING extends Bytecode {

		public CONST_STRING(int offset, short vAA, int index) {
			super(offset, "CONST-STRING", CONST_STRING);
			this.vAA = vAA;
			this.index = index;			
		}
		
		public CONST_STRING(int offset, short vAA, int index, String string) {
			super(offset, "CONST-STRING", CONST_STRING);
			this.vAA = vAA;
			this.index = index;
			this.string = string;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
		
		private int index = 0;

		public int index() {
			return index;
		}

		private String string = null;

		public String string() {
			return string;
		}
	}

	public static class CONST_STRING_JUMBO extends Bytecode {

		public CONST_STRING_JUMBO(int offset, short vAA, int index) {
			super(offset, "CONST-STRING/JUMBO", CONST_STRING_JUMBO);
			this.vAA = vAA;
			this.index = index;		
		}
		
		public CONST_STRING_JUMBO(int offset, short vAA, int index, String string) {
			super(offset, "CONST-STRING/JUMBO", CONST_STRING_JUMBO);
			this.vAA = vAA;
			this.index = index;
			this.string = string;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
		
		private int index = 0;

		public int index() {
			return index;
		}

		private String string = null;

		public String string() {
			return string;
		}
	}

	public static class CONST_CLASS extends Bytecode {

		public CONST_CLASS(int offset, short vAA, int index) {
			super(offset, "CONST-CLASS", CONST_CLASS);
			this.vAA = vAA;
			this.index = index;
		}
		
		public CONST_CLASS(int offset, short vAA, int index, String clazz) {
			super(offset, "CONST-CLASS", CONST_CLASS);
			this.vAA = vAA;
			this.index = index;
			this.clazz = clazz;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
		
		private int index = 0;

		public int index() {
			return index;
		}

		private String clazz = null;

		public String clazz() {
			return clazz;
		}
	}

	public static class MONITOR_ENTER extends Bytecode {

		public MONITOR_ENTER(int offset, short vAA) {
			super(offset, "MONITOR-ENTER", MONITOR_ENTER);
			this.vAA = vAA;			
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}	   		
	}

	public static class MONITOR_EXIT extends Bytecode {

		public MONITOR_EXIT(int offset, short vAA) {
			super(offset, "MONITOR-EXIT", MONITOR_EXIT);
			this.vAA = vAA;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
	}

	public static class CHECK_CAST extends Bytecode {

		public CHECK_CAST(int offset, short vAA, int index) {
			super(offset, "CHECK-CAST", CHECK_CAST);
			this.vAA = vAA;
			this.index = index;
		}
		
		public CHECK_CAST(int offset, short vAA, int index, String type) {
			super(offset, "CHECK-CAST", CHECK_CAST);
			this.vAA = vAA;
			this.index = index;
			this.type = type;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
		
		private int index = 0;

		public int index() {
			return index;
		}

		private String type = null;

		public String type() {
			return type;
		}
	}

	public static class INSTANCE_OF extends Bytecode {

		public INSTANCE_OF(int offset, byte vA, byte vB, int index) {
			super(offset, "INSTANCE-OF", INSTANCE_OF);
			this.vA = vA;
			this.vB = vB;
			this.index = index;
		}
		
		public INSTANCE_OF(int offset, byte vA, byte vB, int index, String clazz) {
			super(offset, "INSTANCE-OF", INSTANCE_OF);
			this.vA = vA;
			this.vB = vB;
			this.index = index;
			this.clazz = clazz;
		}

		private byte vA = 0;

		public byte vA() {
			return vA;
		}
		
		private byte vB = 0;

		public byte vB() {
			return vB;
		}

		private int index = 0;

		public int index() {
			return index;
		}

		private String clazz = null;

		public String clazz() {
			return clazz;
		}
	}

	public static class ARRAY_LENGTH extends Bytecode {

		public ARRAY_LENGTH(int offset, byte vA, byte vB) {
			super(offset, "ARRAY-LENGTH", ARRAY_LENGTH);
			this.vA = vA;
			this.vB = vB;
		}

		private byte vA = 0;

		public byte vA() {
			return vA;
		}
		
		private byte vB = 0;

		public byte vB() {
			return vB;
		}
	}

	public static class NEW_INSTANCE extends Bytecode {

		public NEW_INSTANCE(int offset, short vAA, int index) {
			super(offset, "NEW-INSTANCE", NEW_INSTANCE);
			this.vAA = vAA;
			this.index = index;
		}
		
		public NEW_INSTANCE(int offset, short vAA, int index, String clazz) {
			super(offset, "NEW-INSTANCE", NEW_INSTANCE);
			this.vAA = vAA;
			this.index = index;
			this.clazz = clazz;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
		
		private int index = 0;

		public int index() {
			return index;
		}

		private String clazz = null;

		public String clazz() {
			return clazz;
		}
	}

	public static class NEW_ARRAY extends Bytecode {

		public NEW_ARRAY(int offset, byte vA, byte vB, int index) {
			super(offset, "NEW-ARRAY", NEW_ARRAY);
			this.vA = vA;
			this.vB = vB;
			this.index = index;
		}
		
		public NEW_ARRAY(int offset, byte vA, byte vB, int index, String type) {
			super(offset, "NEW-ARRAY", NEW_ARRAY);
			this.vA = vA;
			this.vB = vB;
			this.index = index;
			this.type = type;
		}

		private byte vA = 0;

		public byte vA() {
			return vA;
		}
		
		private byte vB = 0;

		public byte vB() {
			return vB;
		}
		
		private int index = 0;

		public int index() {
			return index;
		}

		private String type = null;

		public String type() {
			return type;
		}
	}

	public static class FILLED_NEW_ARRAY extends Bytecode {

		public FILLED_NEW_ARRAY(int offset, byte count, int index) {
			super(offset, "FILLED-NEW-ARRAY", FILLED_NEW_ARRAY);
			this.count = count;
			this.index = index;
		}
		
		public FILLED_NEW_ARRAY(int offset, byte count, int index, String type) {
			super(offset, "FILLED-NEW-ARRAY", FILLED_NEW_ARRAY);
			this.count = count;
			this.index = index;
			this.type = type;
		}

		private byte count = 0;

		public byte count() {
			return count;
		}

		private int index = 0;

		public int index() {
			return index;
		}

		private String type = null;

		public String type() {
			return type;
		}

		private byte[] vArray = new byte[5];

		public FILLED_NEW_ARRAY vC(byte vC) {
			vArray[0] = vC; return this;
		}

		public byte vC() {
			return vArray[0];
		}

		public FILLED_NEW_ARRAY vD(byte vD) {
			vArray[1] = vD; return this;
		}

		public byte vD() {
			return vArray[1];
		}

		public FILLED_NEW_ARRAY vE(byte vE) {
			vArray[2] = vE; return this;
		}

		public byte vE() {
			return vArray[2];
		}

		public FILLED_NEW_ARRAY vF(byte vF) {
			vArray[3] = vF; return this;
		}

		public byte vF() {
			return vArray[3];
		}

		public FILLED_NEW_ARRAY vG(byte vG) {
			vArray[4] = vG; return this;
		}

		public byte vG() {
			return vArray[4];
		}
	}

	public static class FILLED_NEW_ARRAY_RANGE extends Bytecode {

		public FILLED_NEW_ARRAY_RANGE(int offset, short count, int index, int vCCCC) {
			super(offset, "FILLED-NEW-ARRAY/RANGE", FILLED_NEW_ARRAY_RANGE);
			this.count = count;						
			this.index = index;
			this.vCCCC = vCCCC;
		}
		
		public FILLED_NEW_ARRAY_RANGE(int offset, short count, int index, int vCCCC, String type) {
			super(offset, "FILLED-NEW-ARRAY/RANGE", FILLED_NEW_ARRAY_RANGE);
			this.count = count;						
			this.index = index;
			this.vCCCC = vCCCC;
			this.type = type;
		}

		private short count = 0;

		public short count() {
			return count;
		}				

		private int index = 0;

		public int index() {
			return index;
		}

		private int vCCCC = 0;

		public int vCCCC() {
			return vCCCC;
		}

		private String type = null;

		public String type() {
			return type;
		}
	}

	public static class FILL_ARRAY_DATA extends Bytecode {

		public FILL_ARRAY_DATA(int offset, short vAA, int value) {
			super(offset, "FILL-ARRAY-DATA", FILL_ARRAY_DATA);
			this.vAA = vAA;
			this.value = value;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}

		private int value = 0;

		public int value() {
			return value;
		}
	}

	public static class THROW extends Bytecode {

		public THROW(int offset, short vAA) {
			super(offset, "THROW", THROW);
			this.vAA = vAA;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
	}

	public static class GOTO extends Bytecode {

		public GOTO(int offset, byte value) {
			super(offset, "GOTO", GOTO);
			this.value = value;
		}

		private byte value = 0;

		public byte value() {
			return value;
		}
	}

	public static class GOTO16 extends Bytecode {

		public GOTO16(int offset, short value) {
			super(offset, "GOTO/16", GOTO16);
			this.value = value;
		}

		private short value = 0;

		public short value() {
			return value;
		}
	}

	public static class GOTO32 extends Bytecode {

		public GOTO32(int offset, int value) {
			super(offset, "GOTO/32", GOTO32);
			this.value = value;
		}

		private int value = 0;

		public int value() {
			return value;
		}
	}

	public static class PACKED_SWITCH extends Bytecode {

		public PACKED_SWITCH(int offset, short vAA, int value) {
			super(offset, "PACKED_SWITCH", PACKED_SWITCH);
			this.vAA = vAA;
			this.value = value;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
		
		private int value = 0;

		public int value() {
			return value;
		}
	}

	public static class SPARSE_SWITCH extends Bytecode {

		public SPARSE_SWITCH(int offset, short vAA, int value) {
			super(offset, "SPARSE-SWITCH", SPARSE_SWITCH);
			this.vAA = vAA;
			this.value = value;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
		
		private int value = 0;

		public int value() {
			return value;
		}
	}

	public static class CMP_KIND extends Bytecode {

		public CMP_KIND(int offset, String name, byte opcode, short vAA, short vBB, short vCC) {
			super(offset, name, opcode);
			this.vAA = vAA;
			this.vBB = vBB;
			this.vCC = vCC;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
		
		private short vBB = 0;

		public short vBB() {
			return vBB;
		}

		private short vCC = 0;	   

		public short vCC() {
			return vCC;
		}
	}

	public static class CMPL_FLOAT extends CMP_KIND {

		public CMPL_FLOAT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "CMPL-FLOAT", CMPL_FLOAT, vAA, vBB, vCC);
		}
	}

	public static class CMPG_FLOAT extends CMP_KIND {

		public CMPG_FLOAT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "CMPG-FLOAT", CMPG_FLOAT, vAA, vBB, vCC);
		}
	}

	public static class CMPL_DOUBLE extends CMP_KIND {

		public CMPL_DOUBLE(int offset, short vAA, short vBB, short vCC) {
			super(offset, "CMPL-DOUBLE", CMPL_DOUBLE, vAA, vBB, vCC);
		}
	}

	public static class CMPG_DOUBLE extends CMP_KIND {

		public CMPG_DOUBLE(int offset, short vAA, short vBB, short vCC) {
			super(offset, "CMPG-DOUBLE", CMPG_DOUBLE, vAA, vBB, vCC);
		}
	}

	public static class CMP_LONG extends CMP_KIND {

		public CMP_LONG(int offset, short vAA, short vBB, short vCC) {
			super(offset, "CMP-LONG", CMP_LONG, vAA, vBB, vCC);
		}
	}

	public static class IF_TEST extends Bytecode {

		public IF_TEST(int offset, String name, byte opcode, byte vA, byte vB, short value) {
			super(offset, name, opcode);
			this.vA = vA;
			this.vB = vB;
			this.value = value;
		}

		private byte vA = 0;

		public byte vA() {
			return vA;
		}
		
		private byte vB = 0;

		public byte vB() {
			return vB;
		}

		private short value = 0;

		public short value() {
			return value;
		}
	}

	public static class IF_EQ extends IF_TEST {

		public IF_EQ(int offset, byte vA, byte vB, short value) {
			super(offset, "IF-EQ", IF_EQ, vA, vB, value);
		}
	}
	
	public static class IF_NE extends IF_TEST {

		public IF_NE(int offset, byte vA, byte vB, short value) {
			super(offset, "IF-NE", IF_NE, vA, vB, value);
		}
	}

	public static class IF_LT extends IF_TEST {

		public IF_LT(int offset, byte vA, byte vB, short value) {
			super(offset, "IF-LT", IF_LT, vA, vB, value);
		}
	}

	public static class IF_GE extends IF_TEST {

		public IF_GE(int offset, byte vA, byte vB, short value) {
			super(offset, "IF-GE", IF_GE, vA, vB, value);
		}
	}

	public static class IF_GT extends IF_TEST {

		public IF_GT(int offset, byte vA, byte vB, short value) {
			super(offset, "IF-GT", IF_GT, vA, vB, value);
		}
	}

	public static class IF_LE extends IF_TEST {

		public IF_LE(int offset, byte vA, byte vB, short value) {
			super(offset, "IF-LE", IF_LE, vA, vB, value);
		}
	}

	public static class IF_TESTZ extends Bytecode {

		public IF_TESTZ(int offset, String name, byte opcode, short vAA, short value) {
			super(offset, name, opcode);
			this.vAA = vAA;
			this.value = value;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
		
		private short value = 0;

		public short value() {
			return value;
		}
	}

	public static class IF_EQZ extends IF_TESTZ {

		public IF_EQZ(int offset, short vAA, short value) {
			super(offset, "IF-EQZ", IF_EQZ, vAA, value);
		}
	}
	
	public static class IF_NEZ extends IF_TESTZ {

		public IF_NEZ(int offset, short vAA, short value) {
			super(offset, "IF-NEZ", IF_NEZ, vAA, value);
		}
	}

	public static class IF_LTZ extends IF_TESTZ {

		public IF_LTZ(int offset, short vAA, short value) {
			super(offset, "IF-LTZ", IF_LTZ, vAA, value);
		}
	}

	public static class IF_GEZ extends IF_TESTZ {

		public IF_GEZ(int offset, short vAA, short value) {
			super(offset, "IF-GEZ", IF_GEZ, vAA, value);
		}
	}

	public static class IF_GTZ extends IF_TESTZ {

		public IF_GTZ(int offset, short vAA, short value) {
			super(offset, "IF-GTZ", IF_GTZ, vAA, value);
		}
	}

	public static class IF_LEZ extends IF_TESTZ {

		public IF_LEZ(int offset, short vAA, short value) {
			super(offset, "IF-LEZ", IF_LEZ, vAA, value);
		}
	}
	
	public static class ARRAY_OP extends Bytecode {
		public ARRAY_OP(int offset, String name, byte opcode, short vAA, short vBB, short vCC) {
			super(offset, name, opcode);
			this.vAA = vAA;
			this.vBB = vBB;
			this.vCC = vCC;			
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
		
		private short vBB = 0;

		public short vBB() {
			return vBB;
		}		
		
		private short vCC = 0;

		public short vCC() {
			return vCC;
		}
	}

	public static class AGET extends ARRAY_OP {

		public AGET(int offset, short vAA, short vBB, short vCC) {
			super(offset, "AGET", AGET, vAA, vBB, vCC);
		}
	}

	public static class AGET_WIDE extends ARRAY_OP {

		public AGET_WIDE(int offset, short vAA, short vBB, short vCC) {
			super(offset, "AGET-WIDE", AGET_WIDE, vAA, vBB, vCC);
		}
	}

	public static class AGET_OBJECT extends ARRAY_OP {

		public AGET_OBJECT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "AGET-OBJECT", AGET_OBJECT, vAA, vBB, vCC);
		}
	}

	public static class AGET_BOOLEAN extends ARRAY_OP {

		public AGET_BOOLEAN(int offset, short vAA, short vBB, short vCC) {
			super(offset, "AGET-BOOLEAN", AGET_BOOLEAN, vAA, vBB, vCC);
		}
	}

	public static class AGET_BYTE extends ARRAY_OP {

		public AGET_BYTE(int offset, short vAA, short vBB, short vCC) {
			super(offset, "AGET-BYTE", AGET_BYTE, vAA, vBB, vCC);
		}
	}

	public static class AGET_CHAR extends ARRAY_OP {

		public AGET_CHAR(int offset, short vAA, short vBB, short vCC) {
			super(offset, "AGET-CHAR", AGET_CHAR, vAA, vBB, vCC);
		}
	}

	public static class AGET_SHORT extends ARRAY_OP {

		public AGET_SHORT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "AGET-SHORT", AGET_SHORT, vAA, vBB, vCC);
		}
	}
	
	public static class APUT extends ARRAY_OP {

		public APUT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "APUT", APUT, vAA, vBB, vCC);
		}
	}

	public static class APUT_WIDE extends ARRAY_OP {

		public APUT_WIDE(int offset, short vAA, short vBB, short vCC) {
			super(offset, "APUT-WIDE", APUT_WIDE, vAA, vBB, vCC);
		}
	}

	public static class APUT_OBJECT extends ARRAY_OP {

		public APUT_OBJECT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "APUT-OBJECT", APUT_OBJECT, vAA, vBB, vCC);
		}
	}

	public static class APUT_BOOLEAN extends ARRAY_OP {

		public APUT_BOOLEAN(int offset, short vAA, short vBB, short vCC) {
			super(offset, "APUT-BOOLEAN", APUT_BOOLEAN, vAA, vBB, vCC);
		}
	}

	public static class APUT_BYTE extends ARRAY_OP {

		public APUT_BYTE(int offset, short vAA, short vBB, short vCC) {
			super(offset, "APUT-BYTE", APUT_BYTE, vAA, vBB, vCC);
		}
	}

	public static class APUT_CHAR extends ARRAY_OP {

		public APUT_CHAR(int offset, short vAA, short vBB, short vCC) {
			super(offset, "APUT-CHAR", APUT_CHAR, vAA, vBB, vCC);
		}
	}

	public static class APUT_SHORT extends ARRAY_OP {

		public APUT_SHORT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "APUT-SHORT", APUT_SHORT, vAA, vBB, vCC);
		}
	}

	public static class IOP extends Bytecode {

		public IOP(int offset, String name, byte opcode, byte vA, byte vB, int index) {
			super(offset, name, opcode);
			this.vA = vA;
			this.vB = vB;
			this.index = index;			
		}
		
		public IOP(int offset, String name, byte opcode, byte vA, byte vB, int index, String clazz, String type, String field) {
			super(offset, name, opcode);
			this.vA = vA;
			this.vB = vB;
			this.index = index;
			this.clazz = clazz;
			this.type = type;
			this.field = field;
		}

		private byte vA = 0;

		public byte vA() {
			return vA;
		}
		
		private byte vB = 0;

		public byte vB() {
			return vB;
		}
		
		private int index = 0;
		
		public int index() {
			return index;
		}

		private String clazz = null;

		public String clazz() {
			return clazz;
		}
		
		private String type = null;

		public String type() {
			return type;
		}

		private String field = null;

		public String field() {
			return field;
		}		
	}

	public static class IGET extends IOP {

		public IGET(int offset, byte vA, byte vB, int index) {
			super(offset, "IGET", IGET, vA, vB, index);
		}
		
		public IGET(int offset, byte vA, byte vB, int index, String clazz, String type, String field) {
			super(offset, "IGET", IGET, vA, vB, index, clazz, type, field);
		}
	}

	public static class IGET_WIDE extends IOP {

		public IGET_WIDE(int offset, byte vA, byte vB, int index) {
			super(offset, "IGET-WIDE", IGET_WIDE, vA, vB, index);
		}
		
		public IGET_WIDE(int offset, byte vA, byte vB, int index, String clazz, String type, String field) {
			super(offset, "IGET-WIDE", IGET_WIDE, vA, vB, index, clazz, type, field);
		}
	}

	public static class IGET_OBJECT extends IOP {

		public IGET_OBJECT(int offset, byte vA, byte vB, int index) {
			super(offset, "IGET-OBJECT", IGET_OBJECT, vA, vB, index);
		}
		
		public IGET_OBJECT(int offset, byte vA, byte vB, int index, String clazz, String type, String field) {
			super(offset, "IGET-OBJECT", IGET_OBJECT, vA, vB, index, clazz, type, field);
		}
	}

	public static class IGET_BOOLEAN extends IOP {

		public IGET_BOOLEAN(int offset, byte vA, byte vB, int index) {
			super(offset, "IGET-BOOLEAN", IGET_BOOLEAN, vA, vB, index);
		}
		
		public IGET_BOOLEAN(int offset, byte vA, byte vB, int index, String clazz, String type, String field) {
			super(offset, "IGET-BOOLEAN", IGET_BOOLEAN, vA, vB, index, clazz, type, field);
		}
	}

	public static class IGET_BYTE extends IOP {

		public IGET_BYTE(int offset, byte vA, byte vB, int index) {
			super(offset, "IGET-BYTE", IGET_BYTE, vA, vB, index);
		}
		
		public IGET_BYTE(int offset, byte vA, byte vB, int index, String clazz, String type, String field) {
			super(offset, "IGET-BYTE", IGET_BYTE, vA, vB, index, clazz, type, field);
		}
	}

	public static class IGET_CHAR extends IOP {

		public IGET_CHAR(int offset, byte vA, byte vB, int index) {
			super(offset, "IGET-CHAR", IGET_CHAR, vA, vB, index);
		}
		
		public IGET_CHAR(int offset, byte vA, byte vB, int index, String clazz, String type, String field) {
			super(offset, "IGET-CHAR", IGET_CHAR, vA, vB, index, clazz, type, field);
		}
	}

	public static class IGET_SHORT extends IOP {

		public IGET_SHORT(int offset, byte vA, byte vB, int index) {
			super(offset, "IGET-SHORT", IGET_SHORT, vA, vB, index);
		}
		
		public IGET_SHORT(int offset, byte vA, byte vB, int index, String clazz, String type, String field) {
			super(offset, "IGET-SHORT", IGET_SHORT, vA, vB, index, clazz, type, field);
		}
	}
	
	public static class IPUT extends IOP {

		public IPUT(int offset, byte vA, byte vB, int index) {
			super(offset, "IPUT", IPUT, vA, vB, index);
		}
		
		public IPUT(int offset, byte vA, byte vB, int index, String clazz, String type, String field) {
			super(offset, "IPUT", IPUT, vA, vB, index, clazz, type, field);
		}
	}

	public static class IPUT_WIDE extends IOP {

		public IPUT_WIDE(int offset, byte vA, byte vB, int index) {
			super(offset, "IPUT-WIDE", IPUT_WIDE, vA, vB, index);
		}
		
		public IPUT_WIDE(int offset, byte vA, byte vB, int index, String clazz, String type, String field) {
			super(offset, "IPUT-WIDE", IPUT_WIDE, vA, vB, index, clazz, type, field);
		}
	}

	public static class IPUT_OBJECT extends IOP {

		public IPUT_OBJECT(int offset, byte vA, byte vB, int index) {
			super(offset, "IPUT-OBJECT", IPUT_OBJECT, vA, vB, index);
		}
		
		public IPUT_OBJECT(int offset, byte vA, byte vB, int index, String clazz, String type, String field) {
			super(offset, "IPUT-OBJECT", IPUT_OBJECT, vA, vB, index, clazz, type, field);
		}
	}

	public static class IPUT_BOOLEAN extends IOP {

		public IPUT_BOOLEAN(int offset, byte vA, byte vB, int index) {
			super(offset, "IPUT-BOOLEAN", IPUT_BOOLEAN, vA, vB, index);
		}
		
		public IPUT_BOOLEAN(int offset, byte vA, byte vB, int index, String clazz, String type, String field) {
			super(offset, "IPUT-BOOLEAN", IPUT_BOOLEAN, vA, vB, index, clazz, type, field);
		}
	}

	public static class IPUT_BYTE extends IOP {

		public IPUT_BYTE(int offset, byte vA, byte vB, int index) {
			super(offset, "IPUT-BYTE", IPUT_BYTE, vA, vB, index);
		}
		
		public IPUT_BYTE(int offset, byte vA, byte vB, int index, String clazz, String type, String field) {
			super(offset, "IPUT-BYTE", IPUT_BYTE, vA, vB, index, clazz, type, field);
		}
	}

	public static class IPUT_CHAR extends IOP {

		public IPUT_CHAR(int offset, byte vA, byte vB, int index) {
			super(offset, "IPUT-CHAR", IPUT_CHAR, vA, vB, index);
		}
		
		public IPUT_CHAR(int offset, byte vA, byte vB, int index, String clazz, String type, String field) {
			super(offset, "IPUT-CHAR", IPUT_CHAR, vA, vB, index, clazz, type, field);
		}
	}

	public static class IPUT_SHORT extends IOP {

		public IPUT_SHORT(int offset, byte vA, byte vB, int index) {
			super(offset, "IPUT-SHORT", IPUT_SHORT, vA, vB, index);
		}
		
		public IPUT_SHORT(int offset, byte vA, byte vB, int index, String clazz, String type, String field) {
			super(offset, "IPUT-SHORT", IPUT_SHORT, vA, vB, index, clazz, type, field);
		}
	}

	public static class SOP extends Bytecode {

		public SOP(int offset, String name, byte opcode, short vAA, int index) {
			super(offset, name, opcode);
			this.vAA = vAA;
			this.index = index;
		}
		
		public SOP(int offset, String name, byte opcode, short vAA, int index, String clazz, String type, String field) {
			super(offset, name, opcode);
			this.vAA = vAA;
			this.index = index;
			this.clazz = clazz;
			this.type = type;
			this.field = field;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
		
		private int index = 0;

		public int index() {
			return index;
		}

		private String clazz = null;

		public String clazz() {
			return clazz;
		}
		
		private String type = null;

		public String type() {
			return type;
		}

		private String field = null;

		public String field() {
			return field;
		}		
	}

	public static class SGET extends SOP {

		public SGET(int offset, short vAA, int index) {
			super(offset, "SGET", SGET, vAA, index);
		}
		
		public SGET(int offset, short vAA, int index, String clazz, String type, String field) {
			super(offset, "SGET", SGET, vAA, index, clazz, type, field);
		}
	}

	public static class SGET_WIDE extends SOP {

		public SGET_WIDE(int offset, short vAA, int index) {
			super(offset, "SGET-WIDE", SGET_WIDE, vAA, index);
		}
		
		public SGET_WIDE(int offset, short vAA, int index, String clazz, String type, String field) {
			super(offset, "SGET-WIDE", SGET_WIDE, vAA, index, clazz, type, field);
		}
	}

	public static class SGET_OBJECT extends SOP {

		public SGET_OBJECT(int offset, short vAA, int index) {
			super(offset, "SGET-OBJECT", SGET_OBJECT, vAA, index);
		}
		
		public SGET_OBJECT(int offset, short vAA, int index, String clazz, String type, String field) {
			super(offset, "SGET-OBJECT", SGET_OBJECT, vAA, index, clazz, type, field);
		}
	}

	public static class SGET_BOOLEAN extends SOP {

		public SGET_BOOLEAN(int offset, short vAA, int index) {
			super(offset, "SGET-BOOLEAN", SGET_BOOLEAN, vAA, index);
		}
		
		public SGET_BOOLEAN(int offset, short vAA, int index, String clazz, String type, String field) {
			super(offset, "SGET-BOOLEAN", SGET_BOOLEAN, vAA, index, clazz, type, field);
		}
	}

	public static class SGET_BYTE extends SOP {

		public SGET_BYTE(int offset, short vAA, int index) {
			super(offset, "SGET-BYTE", SGET_BYTE, vAA, index);
		}
		
		public SGET_BYTE(int offset, short vAA, int index, String clazz, String type, String field) {
			super(offset, "SGET-BYTE", SGET_BYTE, vAA, index, clazz, type, field);
		}
	}

	public static class SGET_CHAR extends SOP {

		public SGET_CHAR(int offset, short vAA, int index) {
			super(offset, "SGET-CHAR", SGET_CHAR, vAA, index);
		}
		
		public SGET_CHAR(int offset, short vAA, int index, String clazz, String type, String field) {
			super(offset, "SGET-CHAR", SGET_CHAR, vAA, index, clazz, type, field);
		}
	}

	public static class SGET_SHORT extends SOP {

		public SGET_SHORT(int offset, short vAA, int index) {
			super(offset, "SGET-SHORT", SGET_SHORT, vAA, index);
		}
		
		public SGET_SHORT(int offset, short vAA, int index, String clazz, String type, String field) {
			super(offset, "SGET-SHORT", SGET_SHORT, vAA, index, clazz, type, field);
		}
	}
	
	public static class SPUT extends SOP {

		public SPUT(int offset, short vAA, int index) {
			super(offset, "SPUT", SPUT, vAA, index);
		}
		
		public SPUT(int offset, short vAA, int index, String clazz, String type, String field) {
			super(offset, "SPUT", SPUT, vAA, index, clazz, type, field);
		}
	}

	public static class SPUT_WIDE extends SOP {

		public SPUT_WIDE(int offset, short vAA, int index) {
			super(offset, "SPUT-WIDE", SPUT_WIDE, vAA, index);
		}
		
		public SPUT_WIDE(int offset, short vAA, int index, String clazz, String type, String field) {
			super(offset, "SPUT-WIDE", SPUT_WIDE, vAA, index, clazz, type, field);
		}
	}

	public static class SPUT_OBJECT extends SOP {

		public SPUT_OBJECT(int offset, short vAA, int index) {
			super(offset, "SPUT-OBJECT", SPUT_OBJECT, vAA, index);
		}
		
		public SPUT_OBJECT(int offset, short vAA, int index, String clazz, String type, String field) {
			super(offset, "SPUT-OBJECT", SPUT_OBJECT, vAA, index, clazz, type, field);
		}
	}

	public static class SPUT_BOOLEAN extends SOP {

		public SPUT_BOOLEAN(int offset, short vAA, int index) {
			super(offset, "SPUT-BOOLEAN", SPUT_BOOLEAN, vAA, index);
		}
		
		public SPUT_BOOLEAN(int offset, short vAA, int index, String clazz, String type, String field) {
			super(offset, "SPUT-BOOLEAN", SPUT_BOOLEAN, vAA, index, clazz, type, field);
		}
	}

	public static class SPUT_BYTE extends SOP {

		public SPUT_BYTE(int offset, short vAA, int index) {
			super(offset, "SPUT-BYTE", SPUT_BYTE, vAA, index);
		}
		
		public SPUT_BYTE(int offset, short vAA, int index, String clazz, String type, String field) {
			super(offset, "SPUT-BYTE", SPUT_BYTE, vAA, index, clazz, type, field);
		}
	}

	public static class SPUT_CHAR extends SOP {

		public SPUT_CHAR(int offset, short vAA, int index) {
			super(offset, "SPUT-CHAR", SPUT_CHAR, vAA, index);
		}
		
		public SPUT_CHAR(int offset, short vAA, int index, String clazz, String type, String field) {
			super(offset, "SPUT-CHAR", SPUT_CHAR, vAA, index, clazz, type, field);
		}
	}

	public static class SPUT_SHORT extends SOP {

		public SPUT_SHORT(int offset, short vAA, int index) {
			super(offset, "SPUT-SHORT", SPUT_SHORT, vAA, index);
		}
		
		public SPUT_SHORT(int offset, short vAA, int index, String clazz, String type, String field) {
			super(offset, "SPUT-SHORT", SPUT_SHORT, vAA, index, clazz, type, field);
		}
	}

	public static class INVOKE_KIND extends Bytecode {

		public INVOKE_KIND(int offset, String name, byte opcode, byte count, int index) {
			super(offset, name, opcode);
			this.count = count;
			this.index = index;			
		}
		
		public INVOKE_KIND(int offset, String name, byte opcode, byte count, int index,
						   String clazz, String method, String returnType, String parameter) {
			super(offset, name, opcode);
			this.count = count;
			this.index = index;
			this.clazz = clazz;
			this.method = method;
			this.returnType = returnType;
			this.parameter = parameter;				
		}

		private byte count = 0;

		public byte count() {
			return count;
		}

		private int index = 0;

		public int index() {
			return index;
		}

		private String clazz = null;

		public String clazz() {
			return clazz;
		}
		
		private String method = null;

		public String method() {
			return method;
		}

		private String returnType = null;

		public String returnType() {
			return returnType;
		}

		private String parameter = null;

		public String parameter() {
			return parameter;
		}

		private byte[] vArray = new byte[5];

		public INVOKE_KIND vC(byte vC) {
			vArray[0] = vC; return this;
		}

		public byte vC() {
			return vArray[0];
		}

		public INVOKE_KIND vD(byte vD) {
			vArray[1] = vD; return this;
		}

		public byte vD() {
			return vArray[1];
		}

		public INVOKE_KIND vE(byte vE) {
			vArray[2] = vE; return this;
		}

		public byte vE() {
			return vArray[2];
		}

		public INVOKE_KIND vF(byte vF) {
			vArray[3] = vF; return this;
		}

		public byte vF() {
			return vArray[3];
		}

		public INVOKE_KIND vG(byte vG) {
			vArray[4] = vG; return this;
		}

		public byte vG() {
			return vArray[4];
		}
	}

	public static class INVOKE_VIRTUAL extends INVOKE_KIND {

		public INVOKE_VIRTUAL(int offset, byte count, int index) {
			super(offset, "INVOKE-VIRTUAL", INVOKE_VIRTUAL, count, index);
		}
		
		public INVOKE_VIRTUAL(int offset, byte count, int index,
							  String clazz, String method, String returnType, String parameter) {
			super(offset, "INVOKE-VIRTUAL", INVOKE_VIRTUAL, count, index, clazz, method, returnType, parameter);
		}
	}

	public static class INVOKE_SUPER extends INVOKE_KIND {

		public INVOKE_SUPER(int offset, byte count, int index) {
			super(offset, "INVOKE-SUPER", INVOKE_SUPER, count, index);
		}
		
		public INVOKE_SUPER(int offset, byte count, int index,
							  String clazz, String method, String returnType, String parameter) {
			super(offset, "INVOKE-SUPER", INVOKE_SUPER, count, index, clazz, method, returnType, parameter);
		}
	}

	public static class INVOKE_DIRECT extends INVOKE_KIND {

		public INVOKE_DIRECT(int offset, byte count, int index) {
			super(offset, "INVOKE-DIRECT", INVOKE_DIRECT, count, index);
		}
		
		public INVOKE_DIRECT(int offset, byte count, int index,
							  String clazz, String method, String returnType, String parameter) {
			super(offset, "INVOKE-DIRECT", INVOKE_DIRECT, count, index, clazz, method, returnType, parameter);
		}
	}

	public static class INVOKE_STATIC extends INVOKE_KIND {

		public INVOKE_STATIC(int offset, byte count, int index) {
			super(offset, "INVOKE-STATIC", INVOKE_STATIC, count, index);
		}
		
		public INVOKE_STATIC(int offset, byte count, int index,
							  String clazz, String method, String returnType, String parameter) {
			super(offset, "INVOKE-STATIC", INVOKE_STATIC, count, index, clazz, method, returnType, parameter);
		}
	}

	public static class INVOKE_INTERFACE extends INVOKE_KIND {

		public INVOKE_INTERFACE(int offset, byte count, int index) {
			super(offset, "INVOKE-INTERFACE", INVOKE_INTERFACE, count, index);
		}
		
		public INVOKE_INTERFACE(int offset, byte count, int index,
							  String clazz, String method, String returnType, String parameter) {
			super(offset, "INVOKE-INTERFACE", INVOKE_INTERFACE, count, index, clazz, method, returnType, parameter);
		}
	}
	
	public static class INVOKE_KIND_RANGE extends Bytecode {

		public INVOKE_KIND_RANGE(int offset, String name, byte opcode, short count, int index, int vCCCC) {
			super(offset, name, opcode);
			this.count = count;
			this.index = index;			
			this.vCCCC = vCCCC;			
		}
		
		public INVOKE_KIND_RANGE(int offset, String name, byte opcode, short count, int index, int vCCCC,			
								 String clazz, String method, String returnType, String parameter) {
			super(offset, name, opcode);
			this.count = count;
			this.index = index;			
			this.vCCCC = vCCCC;
			this.clazz = clazz;
			this.method = method;
			this.returnType = returnType;
			this.parameter = parameter;
		}

		private short count = 0;

		public short count() {
			return count;
		}		

		private int index = 0;

		public int index() {
			return index;
		}
		
		private int vCCCC = 0;

		public int vCCCC() {
			return vCCCC;
		}		

		private String clazz = null;

		public String clazz() {
			return clazz;
		}
		
		private String method = null;

		public String method() {
			return method;
		}

		private String returnType = null;

		public String returnType() {
			return returnType;
		}

		private String parameter = null;

		public String parameter() {
			return parameter;
		}
	}

	public static class INVOKE_VIRTUAL_RANGE extends INVOKE_KIND_RANGE {

		public INVOKE_VIRTUAL_RANGE(int offset, short count, int index, int vCCCC) {
			super(offset, "INVOKE-VIRTUAL/RANGE", INVOKE_VIRTUAL_RANGE, count, index, vCCCC);
		}
		
		public INVOKE_VIRTUAL_RANGE(int offset, short count, int index, int vCCCC,
							  String clazz, String method, String returnType, String parameter) {
			super(offset, "INVOKE-VIRTUAL/RANGE", INVOKE_VIRTUAL_RANGE, count, index, vCCCC,
				  clazz, method, returnType, parameter);
		}
	}

	public static class INVOKE_SUPER_RANGE extends INVOKE_KIND_RANGE {

		public INVOKE_SUPER_RANGE(int offset, short count, int index, int vCCCC) {
			super(offset, "INVOKE-SUPER/RANGE", INVOKE_SUPER_RANGE, count, index, vCCCC);
		}
		
		public INVOKE_SUPER_RANGE(int offset, short count, int index, int vCCCC,
							  String clazz, String method, String returnType, String parameter) {
			super(offset, "INVOKE-SUPER/RANGE", INVOKE_SUPER_RANGE, count, index, vCCCC,
				  clazz, method, returnType, parameter);
		}
	}

	public static class INVOKE_DIRECT_RANGE extends INVOKE_KIND_RANGE {

		public INVOKE_DIRECT_RANGE(int offset, short count, int index, int vCCCC) {
			super(offset, "INVOKE-DIRECT/RANGE", INVOKE_DIRECT_RANGE, count, index, vCCCC);
		}
		
		public INVOKE_DIRECT_RANGE(int offset, short count, int index, int vCCCC,
							  String clazz, String method, String returnType, String parameter) {
			super(offset, "INVOKE-DIRECT/RANGE", INVOKE_DIRECT_RANGE, count, index, vCCCC,
				  clazz, method, returnType, parameter);
		}
	}

	public static class INVOKE_STATIC_RANGE extends INVOKE_KIND_RANGE {

		public INVOKE_STATIC_RANGE(int offset, short count, int index, int vCCCC) {
			super(offset, "INVOKE-STATIC/RANGE", INVOKE_STATIC_RANGE, count, index, vCCCC);
		}
		
		public INVOKE_STATIC_RANGE(int offset, short count, int index, int vCCCC,
							  String clazz, String method, String returnType, String parameter) {
			super(offset, "INVOKE-STATIC/RANGE", INVOKE_STATIC_RANGE, count, index, vCCCC,
				  clazz, method, returnType, parameter);
		}
	}

	public static class INVOKE_INTERFACE_RANGE extends INVOKE_KIND_RANGE {

		public INVOKE_INTERFACE_RANGE(int offset, short count, int index, int vCCCC) {
			super(offset, "INVOKE-INTERFACE/RANGE", INVOKE_INTERFACE_RANGE, count, index, vCCCC);
		}
		
		public INVOKE_INTERFACE_RANGE(int offset, short count, int index, int vCCCC,
							  String clazz, String method, String returnType, String parameter) {
			super(offset, "INVOKE-INTERFACE/RANGE", INVOKE_INTERFACE_RANGE, count, index, vCCCC,
				  clazz, method, returnType, parameter);
		}
	}

	public static class UNOP extends Bytecode {

		public UNOP(int offset, String name, byte opcode, byte vA, byte vB) {
			super(offset, name, opcode);
			this.vA = vA;
			this.vB = vB;
		}

		private byte vA = 0;

		public byte vA() {
			return vA;
		}
		
		private byte vB = 0;

		public byte vB() {
			return vB;
		}
	}

	public static class NEG_INT extends UNOP {

		public NEG_INT(int offset, byte vA, byte vB) {
			super(offset, "NEG-INT", NEG_INT, vA, vB);
		}
	}

	public static class NOT_INT extends UNOP {

		public NOT_INT(int offset, byte vA, byte vB) {
			super(offset, "NOT-INT", NOT_INT, vA, vB);
		}
	}	

	public static class NEG_LONG extends UNOP {

		public NEG_LONG(int offset, byte vA, byte vB) {
			super(offset, "NEG-LONG", NEG_LONG, vA, vB);
		}
	}	

	public static class NOT_LONG extends UNOP {

		public NOT_LONG(int offset, byte vA, byte vB) {
			super(offset, "NOT-LONG", NOT_LONG, vA, vB);
		}
	}	

	public static class NEG_FLOAT extends UNOP {

		public NEG_FLOAT(int offset, byte vA, byte vB) {
			super(offset, "NEG-FLOAT", NEG_FLOAT, vA, vB);
		}
	}	

	public static class NEG_DOUBLE extends UNOP {

		public NEG_DOUBLE(int offset, byte vA, byte vB) {
			super(offset, "NEG-DOUBLE", NEG_DOUBLE, vA, vB);
		}
	}	

	public static class INT_TO_LONG extends UNOP {

		public INT_TO_LONG(int offset, byte vA, byte vB) {
			super(offset, "INT-TO-LONG", INT_TO_LONG, vA, vB);
		}
	}	

	public static class INT_TO_FLOAT extends UNOP {

		public INT_TO_FLOAT(int offset, byte vA, byte vB) {
			super(offset, "INT-TO-FLOAT", INT_TO_FLOAT, vA, vB);
		}
	}	

	public static class INT_TO_DOUBLE extends UNOP {

		public INT_TO_DOUBLE(int offset, byte vA, byte vB) {
			super(offset, "INT-TO-DOUBLE", INT_TO_DOUBLE, vA, vB);
		}
	}	

	public static class LONG_TO_INT extends UNOP {

		public LONG_TO_INT(int offset, byte vA, byte vB) {
			super(offset, "LONG-TO-INT", LONG_TO_INT, vA, vB);
		}
	}	

	public static class LONG_TO_FLOAT extends UNOP {

		public LONG_TO_FLOAT(int offset, byte vA, byte vB) {
			super(offset, "LONG-TO-FLOAT", LONG_TO_FLOAT, vA, vB);
		}
	}	

	public static class LONG_TO_DOUBLE extends UNOP {

		public LONG_TO_DOUBLE(int offset, byte vA, byte vB) {
			super(offset, "LONG-TO-DOUBLE", LONG_TO_DOUBLE, vA, vB);
		}
	}	

	public static class FLOAT_TO_INT extends UNOP {

		public FLOAT_TO_INT(int offset, byte vA, byte vB) {
			super(offset, "FLOAT-TO-INT", FLOAT_TO_INT, vA, vB);
		}
	}	

	public static class FLOAT_TO_LONG extends UNOP {

		public FLOAT_TO_LONG(int offset, byte vA, byte vB) {
			super(offset, "FLOAT-TO-LONG", FLOAT_TO_LONG, vA, vB);
		}
	}	

	public static class FLOAT_TO_DOUBLE extends UNOP {

		public FLOAT_TO_DOUBLE(int offset, byte vA, byte vB) {
			super(offset, "FLOAT-TO-DOUBLE", FLOAT_TO_DOUBLE, vA, vB);
		}
	}

	public static class DOUBLE_TO_INT extends UNOP {

		public DOUBLE_TO_INT(int offset, byte vA, byte vB) {
			super(offset, "DOUBLE-TO-INT", DOUBLE_TO_INT, vA, vB);
		}
	}

	public static class DOUBLE_TO_LONG extends UNOP {

		public DOUBLE_TO_LONG(int offset, byte vA, byte vB) {
			super(offset, "DOUBLE-TO-LONG", DOUBLE_TO_LONG, vA, vB);
		}
	}

	public static class DOUBLE_TO_FLOAT extends UNOP {

		public DOUBLE_TO_FLOAT(int offset, byte vA, byte vB) {
			super(offset, "DOUBLE-TO-FLOAT", DOUBLE_TO_FLOAT, vA, vB);
		}
	}

	public static class INT_TO_BYTE extends UNOP {

		public INT_TO_BYTE(int offset, byte vA, byte vB) {
			super(offset, "INT-TO-BYTE", INT_TO_BYTE, vA, vB);
		}
	}

	public static class INT_TO_CHAR extends UNOP {

		public INT_TO_CHAR(int offset, byte vA, byte vB) {
			super(offset, "INT-TO-CHAR", INT_TO_CHAR, vA, vB);
		}
	}

	public static class INT_TO_SHORT extends UNOP {

		public INT_TO_SHORT(int offset, byte vA, byte vB) {
			super(offset, "INT-TO-SHORT", INT_TO_SHORT, vA, vB);
		}
	}

	public static class BINOP extends Bytecode {

		public BINOP(int offset, String name, byte opcode, short vAA, short vBB, short vCC) {
			super(offset, name, opcode);
			this.vAA = vAA;
			this.vBB = vBB;
			this.vCC = vCC;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
		
		private short vBB = 0;

		public short vBB() {
			return vBB;
		}

		private short vCC = 0;

		public short vCC() {
			return vCC;
		}
	}

	public static class ADD_INT extends BINOP {

		public ADD_INT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "ADD-INT", ADD_INT, vAA, vBB, vCC);
		}
	}

	public static class SUB_INT extends BINOP {

		public SUB_INT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "SUB-INT", SUB_INT, vAA, vBB, vCC);
		}
	}

	public static class MUL_INT extends BINOP {

		public MUL_INT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "MUL-INT", SUB_INT, vAA, vBB, vCC);
		}
	}

	public static class DIV_INT extends BINOP {

		public DIV_INT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "DIV-INT", DIV_INT, vAA, vBB, vCC);
		}
	}

	public static class REM_INT extends BINOP {

		public REM_INT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "REM-INT", REM_INT, vAA, vBB, vCC);
		}
	}

	public static class AND_INT extends BINOP {

		public AND_INT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "AND-INT", AND_INT, vAA, vBB, vCC);
		}
	}

	public static class OR_INT extends BINOP {

		public OR_INT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "OR-INT", OR_INT, vAA, vBB, vCC);
		}
	}

	public static class XOR_INT extends BINOP {

		public XOR_INT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "XOR-INT", XOR_INT, vAA, vBB, vCC);
		}
	}

	public static class SHL_INT extends BINOP {

		public SHL_INT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "SHL-INT", SHL_INT, vAA, vBB, vCC);
		}
	}

	public static class SHR_INT extends BINOP {

		public SHR_INT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "SHR-INT", SHR_INT, vAA, vBB, vCC);
		}
	}

	public static class USHR_INT extends BINOP {

		public USHR_INT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "USHR-INT", USHR_INT, vAA, vBB, vCC);
		}
	}

	public static class ADD_LONG extends BINOP {

		public ADD_LONG(int offset, short vAA, short vBB, short vCC) {
			super(offset, "ADD-LONG", ADD_LONG, vAA, vBB, vCC);
		}
	}

	public static class SUB_LONG extends BINOP {

		public SUB_LONG(int offset, short vAA, short vBB, short vCC) {
			super(offset, "SUB-LONG", SUB_LONG, vAA, vBB, vCC);
		}
	}

	public static class MUL_LONG extends BINOP {

		public MUL_LONG(int offset, short vAA, short vBB, short vCC) {
			super(offset, "MUL-LONG", MUL_LONG, vAA, vBB, vCC);
		}
	}

	public static class DIV_LONG extends BINOP {

		public DIV_LONG(int offset, short vAA, short vBB, short vCC) {
			super(offset, "DIV-LONG", DIV_LONG, vAA, vBB, vCC);
		}
	}

	public static class REM_LONG extends BINOP {

		public REM_LONG(int offset, short vAA, short vBB, short vCC) {
			super(offset, "REM-LONG", REM_LONG, vAA, vBB, vCC);
		}
	}

	public static class AND_LONG extends BINOP {

		public AND_LONG(int offset, short vAA, short vBB, short vCC) {
			super(offset, "AND-LONG", AND_LONG, vAA, vBB, vCC);
		}
	}

	public static class OR_LONG extends BINOP {

		public OR_LONG(int offset, short vAA, short vBB, short vCC) {
			super(offset, "OR-LONG", OR_LONG, vAA, vBB, vCC);
		}
	}

	public static class XOR_LONG extends BINOP {

		public XOR_LONG(int offset, short vAA, short vBB, short vCC) {
			super(offset, "XOR-LONG", XOR_LONG, vAA, vBB, vCC);
		}
	}

	public static class SHL_LONG extends BINOP {

		public SHL_LONG(int offset, short vAA, short vBB, short vCC) {
			super(offset, "SHL-LONG", SHL_LONG, vAA, vBB, vCC);
		}
	}

	public static class SHR_LONG extends BINOP {

		public SHR_LONG(int offset, short vAA, short vBB, short vCC) {
			super(offset, "SHR-LONG", SHR_LONG, vAA, vBB, vCC);
		}
	}	

	public static class USHR_LONG extends BINOP {

		public USHR_LONG(int offset, short vAA, short vBB, short vCC) {
			super(offset, "USHR-LONG", USHR_LONG, vAA, vBB, vCC);
		}
	}
	
	public static class ADD_FLOAT extends BINOP {

		public ADD_FLOAT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "ADD-FLOAT", ADD_FLOAT, vAA, vBB, vCC);
		}
	}

	public static class SUB_FLOAT extends BINOP {

		public SUB_FLOAT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "SUB-FLOAT", SUB_FLOAT, vAA, vBB, vCC);
		}
	}

	public static class MUL_FLOAT extends BINOP {

		public MUL_FLOAT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "MUL-FLOAT", MUL_FLOAT, vAA, vBB, vCC);
		}
	}

	public static class DIV_FLOAT extends BINOP {

		public DIV_FLOAT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "DIV-FLOAT", DIV_FLOAT, vAA, vBB, vCC);
		}
	}

	public static class REM_FLOAT extends BINOP {

		public REM_FLOAT(int offset, short vAA, short vBB, short vCC) {
			super(offset, "REM-FLOAT", REM_FLOAT, vAA, vBB, vCC);
		}
	}

	public static class ADD_DOUBLE extends BINOP {

		public ADD_DOUBLE(int offset, short vAA, short vBB, short vCC) {
			super(offset, "ADD-DOUBLE", ADD_DOUBLE, vAA, vBB, vCC);
		}
	}

	public static class SUB_DOUBLE extends BINOP {

		public SUB_DOUBLE(int offset, short vAA, short vBB, short vCC) {
			super(offset, "SUB-DOUBLE", SUB_DOUBLE, vAA, vBB, vCC);
		}
	}

	public static class MUL_DOUBLE extends BINOP {

		public MUL_DOUBLE(int offset, short vAA, short vBB, short vCC) {
			super(offset, "MUL-DOUBLE", MUL_DOUBLE, vAA, vBB, vCC);
		}
	}

	public static class DIV_DOUBLE extends BINOP {

		public DIV_DOUBLE(int offset, short vAA, short vBB, short vCC) {
			super(offset, "DIV-DOUBLE", DIV_DOUBLE, vAA, vBB, vCC);
		}
	}

	public static class REM_DOUBLE extends BINOP {

		public REM_DOUBLE(int offset, short vAA, short vBB, short vCC) {
			super(offset, "REM-DOUBLE", REM_DOUBLE, vAA, vBB, vCC);
		}
	}

	public static class BINOP_2ADDR extends Bytecode {

		public BINOP_2ADDR(int offset, String name, byte opcode, byte vA, byte vB) {
			super(offset, name, opcode);
			this.vA = vA;
			this.vB = vB;
		}

		private byte vA = 0;

		public byte vA() {
			return vA;
		}
		
		private byte vB = 0;

		public byte vB() {
			return vB;
		}
	}

	public static class ADD_INT_2ADDR extends BINOP_2ADDR {

		public ADD_INT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "ADD-INT/2ADDR", ADD_INT_2ADDR, vA, vB);
		}
	}

	public static class SUB_INT_2ADDR extends BINOP_2ADDR {

		public SUB_INT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "SUB-INT/2ADDR", SUB_INT_2ADDR, vA, vB);
		}
	}

	public static class MUL_INT_2ADDR extends BINOP_2ADDR {

		public MUL_INT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "MUL-INT/2ADDR", MUL_INT_2ADDR, vA, vB);
		}
	}

	public static class DIV_INT_2ADDR extends BINOP_2ADDR {

		public DIV_INT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "DIV-INT/2ADDR", DIV_INT_2ADDR, vA, vB);
		}
	}

	public static class REM_INT_2ADDR extends BINOP_2ADDR {

		public REM_INT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "REM-INT/2ADDR", REM_INT_2ADDR, vA, vB);
		}
	}

	public static class AND_INT_2ADDR extends BINOP_2ADDR {

		public AND_INT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "AND-INT/2ADDR", AND_INT_2ADDR, vA, vB);
		}
	}

	public static class OR_INT_2ADDR extends BINOP_2ADDR {

		public OR_INT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "OR-INT/2ADDR", OR_INT_2ADDR, vA, vB);
		}
	}

	public static class XOR_INT_2ADDR extends BINOP_2ADDR {

		public XOR_INT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "XOR-INT/2ADDR", XOR_INT_2ADDR, vA, vB);
		}
	}

	public static class SHL_INT_2ADDR extends BINOP_2ADDR {

		public SHL_INT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "SHL-INT/2ADDR", SHL_INT_2ADDR, vA, vB);
		}
	}

	public static class SHR_INT_2ADDR extends BINOP_2ADDR {

		public SHR_INT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "SHR-INT/2ADDR", SHR_INT_2ADDR, vA, vB);
		}
	}

	public static class USHR_INT_2ADDR extends BINOP_2ADDR {

		public USHR_INT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "USHR-INT/2ADDR", USHR_INT_2ADDR, vA, vB);
		}
	}

	public static class ADD_LONG_2ADDR extends BINOP_2ADDR {

		public ADD_LONG_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "ADD-LONG/2ADDR", ADD_LONG_2ADDR, vA, vB);
		}
	}

	public static class SUB_LONG_2ADDR extends BINOP_2ADDR {

		public SUB_LONG_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "SUB-LONG/2ADDR", SUB_LONG_2ADDR, vA, vB);
		}
	}

	public static class MUL_LONG_2ADDR extends BINOP_2ADDR {

		public MUL_LONG_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "MUL-LONG/2ADDR", MUL_LONG_2ADDR, vA, vB);
		}
	}

	public static class DIV_LONG_2ADDR extends BINOP_2ADDR {

		public DIV_LONG_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "DIV-LONG/2ADDR", DIV_LONG_2ADDR, vA, vB);
		}
	}

	public static class REM_LONG_2ADDR extends BINOP_2ADDR {

		public REM_LONG_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "REM-LONG/2ADDR", REM_LONG_2ADDR, vA, vB);
		}
	}

	public static class AND_LONG_2ADDR extends BINOP_2ADDR {

		public AND_LONG_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "AND-LONG/2ADDR", AND_LONG_2ADDR, vA, vB);
		}
	}

	public static class OR_LONG_2ADDR extends BINOP_2ADDR {

		public OR_LONG_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "OR-LONG/2ADDR", OR_LONG_2ADDR, vA, vB);
		}
	}

	public static class XOR_LONG_2ADDR extends BINOP_2ADDR {

		public XOR_LONG_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "XOR-LONG/2ADDR", XOR_LONG_2ADDR, vA, vB);
		}
	}

	public static class SHL_LONG_2ADDR extends BINOP_2ADDR {

		public SHL_LONG_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "SHL-LONG/2ADDR", SHL_LONG_2ADDR, vA, vB);
		}
	}

	public static class SHR_LONG_2ADDR extends BINOP_2ADDR {

		public SHR_LONG_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "SHR-LONG/2ADDR", SHR_LONG_2ADDR, vA, vB);
		}
	}	

	public static class USHR_LONG_2ADDR extends BINOP_2ADDR {

		public USHR_LONG_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "USHR-LONG/2ADDR", USHR_LONG_2ADDR, vA, vB);
		}
	}
	
	public static class ADD_FLOAT_2ADDR extends BINOP_2ADDR {

		public ADD_FLOAT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "ADD-FLOAT/2ADDR", ADD_FLOAT_2ADDR, vA, vB);
		}
	}

	public static class SUB_FLOAT_2ADDR extends BINOP_2ADDR {

		public SUB_FLOAT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "SUB-FLOAT/2ADDR", SUB_FLOAT_2ADDR, vA, vB);
		}
	}

	public static class MUL_FLOAT_2ADDR extends BINOP_2ADDR {

		public MUL_FLOAT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "MUL-FLOAT/2ADDR", MUL_FLOAT_2ADDR, vA, vB);
		}
	}

	public static class DIV_FLOAT_2ADDR extends BINOP_2ADDR {

		public DIV_FLOAT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "DIV-FLOAT/2ADDR", DIV_FLOAT_2ADDR, vA, vB);
		}
	}

	public static class REM_FLOAT_2ADDR extends BINOP_2ADDR {

		public REM_FLOAT_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "REM-FLOAT/2ADDR", REM_FLOAT_2ADDR, vA, vB);
		}
	}

	public static class ADD_DOUBLE_2ADDR extends BINOP_2ADDR {

		public ADD_DOUBLE_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "ADD-DOUBLE/2ADDR", ADD_DOUBLE_2ADDR, vA, vB);
		}
	}

	public static class SUB_DOUBLE_2ADDR extends BINOP_2ADDR {

		public SUB_DOUBLE_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "SUB-DOUBLE/2ADDR", SUB_DOUBLE_2ADDR, vA, vB);
		}
	}

	public static class MUL_DOUBLE_2ADDR extends BINOP_2ADDR {

		public MUL_DOUBLE_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "MUL-DOUBLE/2ADDR", MUL_DOUBLE_2ADDR, vA, vB);
		}
	}

	public static class DIV_DOUBLE_2ADDR extends BINOP_2ADDR {

		public DIV_DOUBLE_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "DIV-DOUBLE/2ADDR", DIV_DOUBLE_2ADDR, vA, vB);
		}
	}

	public static class REM_DOUBLE_2ADDR extends BINOP_2ADDR {

		public REM_DOUBLE_2ADDR(int offset, byte vA, byte vB) {
			super(offset, "REM-DOUBLE/2ADDR", REM_DOUBLE_2ADDR, vA, vB);
		}
	}

	public static class BINOP_LIT16 extends Bytecode {

		public BINOP_LIT16(int offset, String name, byte opcode, byte vA, byte vB, short value) {
			super(offset, name, opcode);
			this.vA = vA;
			this.vB = vB;
			this.value = value;
		}

		private byte vA = 0;

		public byte vA() {
			return vA;
		}
		
		private byte vB = 0;

		public byte vB() {
			return vB;
		}
		
		private short value = 0;

		public short value() {
			return value;
		}
	}

	public static class ADD_INT_LIT16 extends BINOP_LIT16 {

		public ADD_INT_LIT16(int offset, byte vA, byte vB, short value) {
			super(offset, "ADD-INT/LIT16", ADD_INT_LIT16, vA, vB, value);
		}
	}

	public static class RSUB_INT_LIT16 extends BINOP_LIT16 {

		public RSUB_INT_LIT16(int offset, byte vA, byte vB, short value) {
			super(offset, "RSUB-INT/LIT16", RSUB_INT_LIT16, vA, vB, value);
		}
	}

	public static class MUL_INT_LIT16 extends BINOP_LIT16 {

		public MUL_INT_LIT16(int offset, byte vA, byte vB, short value) {
			super(offset, "MUL-INT/LIT16", MUL_INT_LIT16, vA, vB, value);
		}
	}

	public static class DIV_INT_LIT16 extends BINOP_LIT16 {

		public DIV_INT_LIT16(int offset, byte vA, byte vB, short value) {
			super(offset, "DIV-INT/LIT16", DIV_INT_LIT16, vA, vB, value);
		}
	}

	public static class REM_INT_LIT16 extends BINOP_LIT16 {

		public REM_INT_LIT16(int offset, byte vA, byte vB, short value) {
			super(offset, "REM-INT/LIT16", REM_INT_LIT16, vA, vB, value);
		}
	}

	public static class AND_INT_LIT16 extends BINOP_LIT16 {

		public AND_INT_LIT16(int offset, byte vA, byte vB, short value) {
			super(offset, "AND-INT/LIT16", AND_INT_LIT16, vA, vB, value);
		}
	}

	public static class OR_INT_LIT16 extends BINOP_LIT16 {

		public OR_INT_LIT16(int offset, byte vA, byte vB, short value) {
			super(offset, "OR-INT/LIT16", OR_INT_LIT16, vA, vB, value);
		}
	}

	public static class XOR_INT_LIT16 extends BINOP_LIT16 {

		public XOR_INT_LIT16(int offset, byte vA, byte vB, short value) {
			super(offset, "XOR-INT/LIT16", XOR_INT_LIT16, vA, vB, value);
		}
	}

	public static class BINOP_LIT8 extends Bytecode {

		public BINOP_LIT8(int offset, String name, byte opcode, short vAA, short vBB, byte value) {
			super(offset, name, opcode);
			this.vAA = vAA;
			this.vBB = vBB;
			this.value = value;
		}

		private short vAA = 0;

		public short vAA() {
			return vAA;
		}
		
		private short vBB = 0;

		public short vBB() {
			return vBB;
		}
		
		private byte value = 0;

		public byte value() {
			return value;
		}
	}

	public static class ADD_INT_LIT8 extends BINOP_LIT8 {

		public ADD_INT_LIT8(int offset, short vAA, short vBB, byte value) {
			super(offset, "ADD-INT/LIT8", ADD_INT_LIT8, vAA, vBB, value);
		}
	}

	public static class RSUB_INT_LIT8 extends BINOP_LIT8 {

		public RSUB_INT_LIT8(int offset, short vAA, short vBB, byte value) {
			super(offset, "RSUB-INT/LIT8", RSUB_INT_LIT8, vAA, vBB, value);
		}
	}

	public static class MUL_INT_LIT8 extends BINOP_LIT8 {

		public MUL_INT_LIT8(int offset, short vAA, short vBB, byte value) {
			super(offset, "MUL-INT/LIT8", MUL_INT_LIT8, vAA, vBB, value);
		}
	}

	public static class DIV_INT_LIT8 extends BINOP_LIT8 {

		public DIV_INT_LIT8(int offset, short vAA, short vBB, byte value) {
			super(offset, "DIV-INT/LIT8", DIV_INT_LIT8, vAA, vBB, value);
		}
	}

	public static class REM_INT_LIT8 extends BINOP_LIT8 {

		public REM_INT_LIT8(int offset, short vAA, short vBB, byte value) {
			super(offset, "REM-INT/LIT8", REM_INT_LIT8, vAA, vBB, value);
		}
	}

	public static class AND_INT_LIT8 extends BINOP_LIT8 {

		public AND_INT_LIT8(int offset, short vAA, short vBB, byte value) {
			super(offset, "AND-INT/LIT8", AND_INT_LIT8, vAA, vBB, value);
		}
	}

	public static class OR_INT_LIT8 extends BINOP_LIT8 {

		public OR_INT_LIT8(int offset, short vAA, short vBB, byte value) {
			super(offset, "OR-INT/LIT8", OR_INT_LIT8, vAA, vBB, value);
		}
	}

	public static class XOR_INT_LIT8 extends BINOP_LIT8 {

		public XOR_INT_LIT8(int offset, short vAA, short vBB, byte value) {
			super(offset, "XOR-INT/LIT8", XOR_INT_LIT8, vAA, vBB, value);
		}
	}

	public static class SHL_INT_LIT8 extends BINOP_LIT8 {

		public SHL_INT_LIT8(int offset, short vAA, short vBB, byte value) {
			super(offset, "SHL-INT/LIT8", SHL_INT_LIT8, vAA, vBB, value);
		}
	}

	public static class SHR_INT_LIT8 extends BINOP_LIT8 {

		public SHR_INT_LIT8(int offset, short vAA, short vBB, byte value) {
			super(offset, "SHR-INT/LIT8", SHR_INT_LIT8, vAA, vBB, value);
		}
	}

	public static class USHR_INT_LIT8 extends BINOP_LIT8 {

		public USHR_INT_LIT8(int offset, short vAA, short vBB, byte value) {
			super(offset, "USHR-INT/LIT8", USHR_INT_LIT8, vAA, vBB, value);			
		}		
	}

	public static class INVOKE_POLYMORPHIC extends Bytecode {

		public INVOKE_POLYMORPHIC(int offset, byte count, int method, int proto) {
			super(offset, "INVOKE-POLYMORPHIC", INVOKE_POLYMORPHIC);
			this.count = count;
			this.method = method;
			this.proto = proto;			
		}
		
		public INVOKE_POLYMORPHIC(int offset, byte count, int method, int proto,
								  String clazz, String methodName, String methodReturn, String methodParameter,
								  String descriptor, String protoReturn, String protoParameter) {
			super(offset, "INVOKE-POLYMORPHIC", INVOKE_POLYMORPHIC);
			this.count = count;
			this.method = method;
			this.proto = proto;
			this.clazz = clazz;
			this.methodName = methodName;
			this.methodReturn = methodReturn;
			this.methodParameter = methodParameter;
			this.descriptor = descriptor;
			this.protoReturn = protoReturn;
			this.protoParameter = protoParameter;
		}

		private byte count = 0;

		public byte count() {
			return count;
		}

		private int method = 0;

		public int method() {
			return method;
		}

		private int proto = 0;

		public int proto() {
			return proto;
		}

		private String clazz = null;

		public String clazz() {
			return clazz;
		}
		
		private String methodName = null;

		public String methodName() {
			return methodName;
		}

		private String methodReturn = null;

		public String methodReturn() {
			return methodReturn;
		}
		
		private String methodParameter = null;

		public String methodParameter() {
			return methodParameter;
		}
		
		private String descriptor = null;

		public String descriptor() {
			return descriptor;
		}
		
		private String protoReturn = null;

		public String protoReturn() {
			return protoReturn;
		}
		
		private String protoParameter = null;

		public String protoParameter() {
			return protoParameter;
		}
		
		private byte[] vArray = new byte[5];

		public INVOKE_POLYMORPHIC vC(byte vC) {
			vArray[0] = vC; return this;
		}

		public byte vC() {
			return vArray[0];
		}

		public INVOKE_POLYMORPHIC vD(byte vD) {
			vArray[1] = vD; return this;
		}

		public byte vD() {
			return vArray[1];
		}

		public INVOKE_POLYMORPHIC vE(byte vE) {
			vArray[2] = vE; return this;
		}

		public byte vE() {
			return vArray[2];
		}

		public INVOKE_POLYMORPHIC vF(byte vF) {
			vArray[3] = vF; return this;
		}

		public byte vF() {
			return vArray[3];
		}

		public INVOKE_POLYMORPHIC vG(byte vG) {
			vArray[4] = vG; return this;
		}

		public byte vG() {
			return vArray[4];
		}
	}

	public static class INVOKE_POLYMORPHIC_RANGE extends Bytecode {

		public INVOKE_POLYMORPHIC_RANGE(int offset, short count, int method, int vCCCC, int proto) {
			super(offset, "INVOKE-POLYMORPHIC/RANGE", INVOKE_POLYMORPHIC_RANGE);
			this.count = count;			
			this.method = method;
			this.vCCCC = vCCCC;
			this.proto = proto;			
		}
		
		public INVOKE_POLYMORPHIC_RANGE(int offset, short count, int method, int vCCCC, int proto,
								  String clazz, String methodName, String methodReturn, String methodParameter,
								  String descriptor, String protoReturn, String protoParameter) {
			super(offset, "INVOKE-POLYMORPHIC/RANGE", INVOKE_POLYMORPHIC_RANGE);
			this.count = count;			
			this.method = method;
			this.vCCCC = vCCCC;
			this.proto = proto;
			this.clazz = clazz;
			this.methodName = methodName;
			this.methodReturn = methodReturn;
			this.methodParameter = methodParameter;
			this.descriptor = descriptor;
			this.protoReturn = protoReturn;
			this.protoParameter = protoParameter;
		}

		private short count = 0;

		public short count() {
			return count;
		}

		private int method = 0;

		public int method() {
			return method;
		}

		private int vCCCC = 0;

		public int vCCCC() {
			return vCCCC;
		}		

		private int proto = 0;

		public int proto() {
			return proto;
		}

		private String clazz = null;

		public String clazz() {
			return clazz;
		}
		
		private String methodName = null;

		public String methodName() {
			return methodName;
		}

		private String methodReturn = null;

		public String methodReturn() {
			return methodReturn;
		}
		
		private String methodParameter = null;

		public String methodParameter() {
			return methodParameter;
		}
		
		private String descriptor = null;

		public String descriptor() {
			return descriptor;
		}
		
		private String protoReturn = null;

		public String protoReturn() {
			return protoReturn;
		}
		
		private String protoParameter = null;

		public String protoParameter() {
			return protoParameter;
		}
	}

	public static class INVOKE_CUSTOM extends Bytecode {
		
		public INVOKE_CUSTOM(int offset) {
			super(offset, "INVOKE-CUSTOM", INVOKE_CUSTOM);
		}
	}

	public static class INVOKE_CUSTOM_RANGE extends Bytecode {

		public INVOKE_CUSTOM_RANGE(int offset) {
			super(offset, "INVOKE-CUSTOM/RANGE", INVOKE_CUSTOM_RANGE);
		}
	}

	public static final byte NOP = (byte) 0x00;
	public static final byte MOVE = (byte) 0x01;
	public static final byte MOVE_FROM16 = (byte) 0x02;
	public static final byte MOVE16 = (byte) 0x03;
	public static final byte MOVE_WIDE = (byte) 0x04;
	public static final byte MOVE_WIDE_FROM16 = (byte) 0x05;
	public static final byte MOVE_WIDE16 = (byte) 0x06;
	public static final byte MOVE_OBJECT = (byte) 0x07;
	public static final byte MOVE_OBJECT_FROM16 = (byte) 0x08;
	public static final byte MOVE_OBJECT16 = (byte) 0x09;
	public static final byte MOVE_RESULT = (byte) 0x0a;
	public static final byte MOVE_RESULT_WIDE = (byte) 0x0b;
	public static final byte MOVE_RESULT_OBJECT = (byte) 0x0c;
	public static final byte MOVE_EXCEPTION = (byte) 0x0d;
	public static final byte RETURN_VOID = (byte) 0x0e;
	public static final byte RETURN = (byte) 0x0f;
	public static final byte RETURN_WIDE = (byte) 0x10;
	public static final byte RETURN_OBJECT = (byte) 0x11;
	public static final byte CONST4 = (byte) 0x12;
	public static final byte CONST16 = (byte) 0x13;
	public static final byte CONST = (byte) 0x14;
	public static final byte CONST_HIGH16 = (byte) 0x15;
	public static final byte CONST_WIDE16 = (byte) 0x16;
	public static final byte CONST_WIDE32 = (byte) 0x17;
	public static final byte CONST_WIDE = (byte) 0x18;
	public static final byte CONST_WIDE_HIGH16 = (byte) 0x19;
	public static final byte CONST_STRING = (byte) 0x1a;
	public static final byte CONST_STRING_JUMBO = (byte) 0x1b;
	public static final byte CONST_CLASS = (byte) 0x1c;
	public static final byte MONITOR_ENTER = (byte) 0x1d;
	public static final byte MONITOR_EXIT = (byte) 0x1e;
	public static final byte CHECK_CAST = (byte) 0x1f;
	public static final byte INSTANCE_OF = (byte) 0x20;
	public static final byte ARRAY_LENGTH = (byte) 0x21;
	public static final byte NEW_INSTANCE = (byte) 0x22;
	public static final byte NEW_ARRAY = (byte) 0x23;
	public static final byte FILLED_NEW_ARRAY = (byte) 0x24;
	public static final byte FILLED_NEW_ARRAY_RANGE = (byte) 0x25;
	public static final byte FILL_ARRAY_DATA = (byte) 0x26;
	public static final byte THROW = (byte) 0x27;
	public static final byte GOTO = (byte) 0x28;
	public static final byte GOTO16 = (byte) 0x29;
	public static final byte GOTO32 = (byte) 0x2a;
	public static final byte PACKED_SWITCH = (byte) 0x2b;
	public static final byte SPARSE_SWITCH = (byte) 0x2c;
	public static final byte CMPL_FLOAT = (byte) 0x2d;
	public static final byte CMPG_FLOAT = (byte) 0x2e;
	public static final byte CMPL_DOUBLE = (byte) 0x2f;
	public static final byte CMPG_DOUBLE = (byte) 0x30;
	public static final byte CMP_LONG = (byte) 0x31;
	public static final byte IF_EQ = (byte) 0x32;
	public static final byte IF_NE = (byte) 0x33;
	public static final byte IF_LT = (byte) 0x34;
	public static final byte IF_GE = (byte) 0x35;
	public static final byte IF_GT = (byte) 0x36;
	public static final byte IF_LE = (byte) 0x37;
	public static final byte IF_EQZ = (byte) 0x38;
	public static final byte IF_NEZ = (byte) 0x39;
	public static final byte IF_LTZ = (byte) 0x3a;
	public static final byte IF_GEZ = (byte) 0x3b;
	public static final byte IF_GTZ = (byte) 0x3c;
	public static final byte IF_LEZ = (byte) 0x3d;
	public static final byte AGET = (byte) 0x44;
	public static final byte AGET_WIDE = (byte) 0x45;
	public static final byte AGET_OBJECT = (byte) 0x46;
	public static final byte AGET_BOOLEAN = (byte) 0x47;
	public static final byte AGET_BYTE = (byte) 0x48;
	public static final byte AGET_CHAR = (byte) 0x49;
	public static final byte AGET_SHORT = (byte) 0x4a;
	public static final byte APUT = (byte) 0x4b;
	public static final byte APUT_WIDE = (byte) 0x4c;
	public static final byte APUT_OBJECT = (byte) 0x4d;
	public static final byte APUT_BOOLEAN = (byte) 0x4e;
	public static final byte APUT_BYTE = (byte) 0x4f;
	public static final byte APUT_CHAR = (byte) 0x50;
	public static final byte APUT_SHORT = (byte) 0x51;
	public static final byte IGET = (byte) 0x52;
	public static final byte IGET_WIDE = (byte) 0x53;
	public static final byte IGET_OBJECT = (byte) 0x54;
	public static final byte IGET_BOOLEAN = (byte) 0x55;
	public static final byte IGET_BYTE = (byte) 0x56;
	public static final byte IGET_CHAR = (byte) 0x57;
	public static final byte IGET_SHORT = (byte) 0x58;
	public static final byte IPUT = (byte) 0x59;
	public static final byte IPUT_WIDE = (byte) 0x5a;
	public static final byte IPUT_OBJECT = (byte) 0x5b;
	public static final byte IPUT_BOOLEAN = (byte) 0x5c;
	public static final byte IPUT_BYTE = (byte) 0x5d;
	public static final byte IPUT_CHAR = (byte) 0x5e;
	public static final byte IPUT_SHORT = (byte) 0x5f;
	public static final byte SGET = (byte) 0x60;
	public static final byte SGET_WIDE = (byte) 0x61;
	public static final byte SGET_OBJECT = (byte) 0x62;
	public static final byte SGET_BOOLEAN = (byte) 0x63;
	public static final byte SGET_BYTE = (byte) 0x64;
	public static final byte SGET_CHAR = (byte) 0x65;
	public static final byte SGET_SHORT = (byte) 0x66;
	public static final byte SPUT = (byte) 0x67;
	public static final byte SPUT_WIDE = (byte) 0x68;
	public static final byte SPUT_OBJECT = (byte) 0x69;
	public static final byte SPUT_BOOLEAN = (byte) 0x6a;
	public static final byte SPUT_BYTE = (byte) 0x6b;
	public static final byte SPUT_CHAR = (byte) 0x6c;
	public static final byte SPUT_SHORT = (byte) 0x6d;
	public static final byte INVOKE_VIRTUAL = (byte) 0x6e;
	public static final byte INVOKE_SUPER = (byte) 0x6f;
	public static final byte INVOKE_DIRECT = (byte) 0x70;
	public static final byte INVOKE_STATIC = (byte) 0x71;
	public static final byte INVOKE_INTERFACE = (byte) 0x72;
	public static final byte INVOKE_VIRTUAL_RANGE = (byte) 0x74;
	public static final byte INVOKE_SUPER_RANGE = (byte) 0x75;
	public static final byte INVOKE_DIRECT_RANGE = (byte) 0x76;
	public static final byte INVOKE_STATIC_RANGE = (byte) 0x77;
	public static final byte INVOKE_INTERFACE_RANGE = (byte) 0x78;
	public static final byte NEG_INT = (byte) 0x7b;
	public static final byte NOT_INT = (byte) 0x7c;
	public static final byte NEG_LONG = (byte) 0x7d;
	public static final byte NOT_LONG = (byte) 0x7e;
	public static final byte NEG_FLOAT = (byte) 0x7f;
	public static final byte NEG_DOUBLE = (byte) 0x80;
	public static final byte INT_TO_LONG = (byte) 0x81;
	public static final byte INT_TO_FLOAT = (byte) 0x82;
	public static final byte INT_TO_DOUBLE = (byte) 0x83;
	public static final byte LONG_TO_INT = (byte) 0x84;
	public static final byte LONG_TO_FLOAT = (byte) 0x85;
	public static final byte LONG_TO_DOUBLE = (byte) 0x86;
	public static final byte FLOAT_TO_INT = (byte) 0x87;
	public static final byte FLOAT_TO_LONG = (byte) 0x88;
	public static final byte FLOAT_TO_DOUBLE = (byte) 0x89;
	public static final byte DOUBLE_TO_INT = (byte) 0x8a;
	public static final byte DOUBLE_TO_LONG = (byte) 0x8b;
	public static final byte DOUBLE_TO_FLOAT = (byte) 0x8c;
	public static final byte INT_TO_BYTE = (byte) 0x8d;
	public static final byte INT_TO_CHAR = (byte) 0x8e;
	public static final byte INT_TO_SHORT = (byte) 0x8f;
	public static final byte ADD_INT = (byte) 0x90;
	public static final byte SUB_INT = (byte) 0x91;
	public static final byte MUL_INT = (byte) 0x92;
	public static final byte DIV_INT = (byte) 0x93;
	public static final byte REM_INT = (byte) 0x94;
	public static final byte AND_INT = (byte) 0x95;
	public static final byte OR_INT = (byte) 0x96;
	public static final byte XOR_INT = (byte) 0x97;
	public static final byte SHL_INT = (byte) 0x98;
	public static final byte SHR_INT = (byte) 0x99;
	public static final byte USHR_INT = (byte) 0x9a;
	public static final byte ADD_LONG = (byte) 0x9b;
	public static final byte SUB_LONG = (byte) 0x9c;
	public static final byte MUL_LONG = (byte) 0x9d;
	public static final byte DIV_LONG = (byte) 0x9e;
	public static final byte REM_LONG = (byte) 0x9f;
	public static final byte AND_LONG = (byte) 0xa0;
	public static final byte OR_LONG = (byte) 0xa1;
	public static final byte XOR_LONG = (byte) 0xa2;
	public static final byte SHL_LONG = (byte) 0xa3;
	public static final byte SHR_LONG = (byte) 0xa4;
	public static final byte USHR_LONG = (byte) 0xa5;
	public static final byte ADD_FLOAT = (byte) 0xa6;
	public static final byte SUB_FLOAT = (byte) 0xa7;
	public static final byte MUL_FLOAT = (byte) 0xa8;
	public static final byte DIV_FLOAT = (byte) 0xa9;
	public static final byte REM_FLOAT = (byte) 0xaa;
	public static final byte ADD_DOUBLE = (byte) 0xab;
	public static final byte SUB_DOUBLE = (byte) 0xac;
	public static final byte MUL_DOUBLE = (byte) 0xae;
	public static final byte DIV_DOUBLE = (byte) 0xad;
	public static final byte REM_DOUBLE = (byte) 0xaf;
	public static final byte ADD_INT_2ADDR = (byte) 0xb0;
	public static final byte SUB_INT_2ADDR = (byte) 0xb1;
	public static final byte MUL_INT_2ADDR = (byte) 0xb2;
	public static final byte DIV_INT_2ADDR = (byte) 0xb3;
	public static final byte REM_INT_2ADDR = (byte) 0xb4;
	public static final byte AND_INT_2ADDR = (byte) 0xb5;
	public static final byte OR_INT_2ADDR = (byte) 0xb6;
	public static final byte XOR_INT_2ADDR = (byte) 0xb7;
	public static final byte SHL_INT_2ADDR = (byte) 0xb8;
	public static final byte SHR_INT_2ADDR = (byte) 0xb9;
	public static final byte USHR_INT_2ADDR = (byte) 0xba;
	public static final byte ADD_LONG_2ADDR = (byte) 0xbb;
	public static final byte SUB_LONG_2ADDR = (byte) 0xbc;
	public static final byte MUL_LONG_2ADDR = (byte) 0xbd;
	public static final byte DIV_LONG_2ADDR = (byte) 0xbe;
	public static final byte REM_LONG_2ADDR = (byte) 0xbf;
	public static final byte AND_LONG_2ADDR = (byte) 0xc0;
	public static final byte OR_LONG_2ADDR = (byte) 0xc1;
	public static final byte XOR_LONG_2ADDR = (byte) 0xc2;
	public static final byte SHL_LONG_2ADDR = (byte) 0xc3;
	public static final byte SHR_LONG_2ADDR = (byte) 0xc4;
	public static final byte USHR_LONG_2ADDR = (byte) 0xc5;
	public static final byte ADD_FLOAT_2ADDR = (byte) 0xc6;
	public static final byte SUB_FLOAT_2ADDR = (byte) 0xc7;
	public static final byte MUL_FLOAT_2ADDR = (byte) 0xc8;
	public static final byte DIV_FLOAT_2ADDR = (byte) 0xc9;
	public static final byte REM_FLOAT_2ADDR = (byte) 0xca;
	public static final byte ADD_DOUBLE_2ADDR = (byte) 0xcb;
	public static final byte SUB_DOUBLE_2ADDR = (byte) 0xcc;
	public static final byte MUL_DOUBLE_2ADDR = (byte) 0xcd;
	public static final byte DIV_DOUBLE_2ADDR = (byte) 0xce;
	public static final byte REM_DOUBLE_2ADDR = (byte) 0xcf;
	public static final byte ADD_INT_LIT16= (byte) 0xd0;
	public static final byte RSUB_INT_LIT16 = (byte) 0xd1;
	public static final byte MUL_INT_LIT16 = (byte) 0xd2;
	public static final byte DIV_INT_LIT16 = (byte) 0xd3;
	public static final byte REM_INT_LIT16 = (byte) 0xd4;
	public static final byte AND_INT_LIT16= (byte) 0xd5;
	public static final byte OR_INT_LIT16 = (byte) 0xd6;
	public static final byte XOR_INT_LIT16 = (byte) 0xd7;
	public static final byte ADD_INT_LIT8= (byte) 0xd8;
	public static final byte RSUB_INT_LIT8 = (byte) 0xd9;
	public static final byte MUL_INT_LIT8 = (byte) 0xda;
	public static final byte DIV_INT_LIT8 = (byte) 0xdb;
	public static final byte REM_INT_LIT8 = (byte) 0xdc;
	public static final byte AND_INT_LIT8= (byte) 0xdd;
	public static final byte OR_INT_LIT8 = (byte) 0xde;
	public static final byte XOR_INT_LIT8 = (byte) 0xdf;
	public static final byte SHL_INT_LIT8 = (byte) 0xe0;
	public static final byte SHR_INT_LIT8 = (byte) 0xe1;
	public static final byte USHR_INT_LIT8 = (byte) 0xe2;	
	public static final byte INVOKE_POLYMORPHIC = (byte) 0xfa;
	public static final byte INVOKE_POLYMORPHIC_RANGE = (byte) 0xfb;
	public static final byte INVOKE_CUSTOM = (byte) 0xfc;
	public static final byte INVOKE_CUSTOM_RANGE = (byte) 0xfd;
}
