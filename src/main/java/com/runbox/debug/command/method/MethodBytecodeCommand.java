package com.runbox.debug.command.method;

import java.util.List;
import java.util.Map;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.Method;

import com.runbox.debug.manager.MachineManager;

import com.runbox.clazz.reader.ReaderFactory;
import com.runbox.clazz.reader.BytecodeReader;
import com.runbox.clazz.entry.constant.*;
import com.runbox.clazz.entry.bytecode.Bytecode;

public class MethodBytecodeCommand extends MethodCommand {

    public MethodBytecodeCommand(String command) throws Exception {
        super(command);
    }

	@Override 
    public boolean execute() throws Exception {
        String clazz = clazz(); String method = method();    
        List<ReferenceType> types = MachineManager.instance().allClasses();		        
        for (ReferenceType type : types) {
            String name = type.name().replace("$", ".");
            if (name.equals(clazz)) {
                List<Method> methods = type.allMethods();
                for (Method item : methods) {
                    if (item.name().equals(method)) {
                        print(item);
					}
                }
            }
        }
        return super.execute();
    }

	public void print(Method method) throws Exception {
		System.out.printf("%s", access(method));		
		if (method.isNative()) System.out.printf(" native");
		else if (method.isAbstract()) System.out.printf(" abstract");
		if (method.isStatic()) System.out.printf(" static");
		if (method.isFinal()) System.out.printf(" final");
		System.out.printf(" %s", method.returnTypeName());
		System.out.printf(" %s%s {\n", method.name(), arguments(method));
		BytecodeReader reader = ReaderFactory.create(method.bytecodes(), 
													 ReaderFactory.create(method.declaringType().constantPool(),
																		  method.declaringType().constantPoolCount()));
		for (Bytecode code : reader.get()) {
			print(code);
		}
		System.out.println("}");
	}

	public String access(Method method) {
		if (method.isPublic()) return "public";
		else if (method.isProtected()) return "protected";
		else if (method.isPrivate()) return "private";
		return "none";
	}

	public String arguments(Method method) {
		String arguments = "(";
		int i = 0; for (String argument : method.argumentTypeNames()) {
			if (0 < i++) arguments += ", ";
			arguments += argument;
		}
		return arguments + ")";
	}

	private static final String FORMAT1 = "    %04x    ";
	private static final String FORMAT2 = "[%02x %04x]\t";
	private static final String FORMAT3 = "[%02x %4s]\t";
	private static final String FORMAT4 = "    %4s    %2s %4s \t%16s";
	
    private void print(Bytecode bytecode) throws Exception {		
        switch (bytecode.opcode()) {
		case Bytecode.ALOAD: {
            Bytecode.ALOAD code = (Bytecode.ALOAD)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.index()); 
            return; 		
        }
		case Bytecode.ANEWARRAY: {
            Bytecode.ANEWARRAY code = (Bytecode.ANEWARRAY)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT2, code.opcode(), code.index());
            System.out.printf("%-16s%s\n", code.name(), code.type()); 
            return;        
        }
		case Bytecode.ASTORE: {
            Bytecode.ASTORE code = (Bytecode.ASTORE)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.index()); 
            return; 
        }
		case Bytecode.BIPUSH: {
            Bytecode.BIPUSH code = (Bytecode.BIPUSH)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.value());
            return;
        }
		case Bytecode.CHECKCAST: {
            Bytecode.CHECKCAST code = (Bytecode.CHECKCAST)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT2, code.opcode(), code.index());
            System.out.printf("%-16s%s\n", code.name(), code.type()); 
            return; 		
        }
		case Bytecode.DLOAD: {
            Bytecode.DLOAD code = (Bytecode.DLOAD)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");			
            System.out.printf("%-16s%04x\n", code.name(), code.index()); 
            return;		
        }
		case Bytecode.DSTORE: {
            Bytecode.DSTORE code = (Bytecode.DSTORE)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.index()); 
            return;	
        }
		case Bytecode.FLOAD: {
            Bytecode.FLOAD code = (Bytecode.FLOAD)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.index()); 
            return;		
        }
		case Bytecode.FSTORE: {
            Bytecode.FSTORE code = (Bytecode.FSTORE)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.index()); 
            return;			
        }
		case Bytecode.GETFIELD: {
            Bytecode.GETFIELD code = (Bytecode.GETFIELD)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT2, code.opcode(), code.index());
            System.out.printf("%-16s%s.%s:%s\n", code.name(), code.clazz(), code.field(), code.type()); 
            return;
        }	 
		case Bytecode.GETSTATIC: {
            Bytecode.GETSTATIC code = (Bytecode.GETSTATIC)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT2, code.opcode(), code.index());
            System.out.printf("%-16s%s.%s:%s\n", code.name(), code.clazz(), code.field(), code.type()); 
            return;
        }
		case Bytecode.GOTO: {
            Bytecode.GOTO code = (Bytecode.GOTO)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.target()); 
            return;	
        }
		case Bytecode.GOTO_W: {
            Bytecode.GOTO_W code = (Bytecode.GOTO_W)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.target()); 
            return;	
        }
		case Bytecode.IF_ACMPEQ: {
            Bytecode.IF_ACMPEQ code = (Bytecode.IF_ACMPEQ)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IF_ACMPNE: {
            Bytecode.IF_ACMPNE code = (Bytecode.IF_ACMPNE)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IF_ICMPEQ: {
            Bytecode.IF_ICMPEQ code = (Bytecode.IF_ICMPEQ)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IF_ICMPNE: {
            Bytecode.IF_ICMPNE code = (Bytecode.IF_ICMPNE)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IF_ICMPLT: {
            Bytecode.IF_ICMPLT code = (Bytecode.IF_ICMPLT)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IF_ICMPGE: {
            Bytecode.IF_ICMPGE code = (Bytecode.IF_ICMPGE)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IF_ICMPGT: {
            Bytecode.IF_ICMPGT code = (Bytecode.IF_ICMPGT)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IF_ICMPLE: {
            Bytecode.IF_ICMPLE code = (Bytecode.IF_ICMPLE)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IFEQ: {
            Bytecode.IFEQ code = (Bytecode.IFEQ)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IFNE: {
            Bytecode.IFNE code = (Bytecode.IFNE)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IFLT: {
            Bytecode.IFLT code = (Bytecode.IFLT)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IFGE: {
            Bytecode.IFGE code = (Bytecode.IFGE)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IFGT: {
            Bytecode.IFGT code = (Bytecode.IFGT)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IFLE: {
            Bytecode.IFLE code = (Bytecode.IFLE)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IFNONNULL: {
            Bytecode.IFNONNULL code = (Bytecode.IFNONNULL)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IFNULL: {
            Bytecode.IFNULL code = (Bytecode.IFNULL)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return;
        }
		case Bytecode.IINC: {
            Bytecode.IINC code = (Bytecode.IINC)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x, %04x\n",  code.name(), code.index(), code.value()); 
            return;	
        }
		case Bytecode.ILOAD: {
            Bytecode.ILOAD code = (Bytecode.ILOAD)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.index()); 
            return; 	
        }
		case Bytecode.INSTANCEOF: {
            Bytecode.INSTANCEOF code = (Bytecode.INSTANCEOF)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT2, code.opcode(), code.index());
            System.out.printf("%-16s%s\n", code.name(), code.type()); 
            return; 
        }
		case Bytecode.INVOKEDYNAMIC: {            
            Bytecode.INVOKEDYNAMIC code = (Bytecode.INVOKEDYNAMIC)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT2, code.opcode(), code.index());
			System.out.printf("%-16s%04x, %s%s:%s\n", code.name(), code.bootstrap(), code.method(), code.arguments(), code.type());
            return; 
        }
		case Bytecode.INVOKEINTERFACE: {
            Bytecode.INVOKEINTERFACE code = (Bytecode.INVOKEINTERFACE)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT2, code.opcode(), code.index());
            System.out.printf("%-16s%s.%s%s:%s, %04x\n", code.name(), code.clazz(), code.method(), code.arguments(), code.type(), code.count());
            return; 
        }
        case Bytecode.INVOKESPECIAL: {
            Bytecode.INVOKESPECIAL code = (Bytecode.INVOKESPECIAL)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT2, code.opcode(), code.index());
            System.out.printf("%-16s%s.%s%s:%s\n", code.name(), code.clazz(), code.method(), code.arguments(), code.type());
            return; 
        }
		case Bytecode.INVOKESTATIC: {
            Bytecode.INVOKESTATIC code = (Bytecode.INVOKESTATIC)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT2, code.opcode(), code.index());
            System.out.printf("%-16s%s.%s%s:%s\n", code.name(), code.clazz(), code.method(), code.arguments(), code.type());
            return; 
        }
		case Bytecode.INVOKEVIRTUAL: {
            Bytecode.INVOKEVIRTUAL code = (Bytecode.INVOKEVIRTUAL)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT2, code.opcode(), code.index());
            System.out.printf("%-16s%s.%s%s:%s\n", code.name(), code.clazz(), code.method(), code.arguments(), code.type());
            return; 	
        }
		case Bytecode.ISTORE: {
            Bytecode.ISTORE code = (Bytecode.ISTORE)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s0x%04x\n", code.name(), code.index()); 
            return; 	
        }
		case Bytecode.JSR: {
            Bytecode.JSR code = (Bytecode.JSR)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return; 
        }
		case Bytecode.JSR_W: {
            Bytecode.JSR_W code = (Bytecode.JSR_W)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.branch()); 
            return; 		
        }
		case Bytecode.LDC: print((Bytecode.LDC)bytecode, ((Bytecode.LDC)bytecode).index()); return;        
		case Bytecode.LDC_W: print((Bytecode.LDC_W)bytecode, ((Bytecode.LDC_W)bytecode).index()); return;        
		case Bytecode.LDC2_W: print((Bytecode.LDC2_W)bytecode); return;
		case Bytecode.LLOAD: {
            Bytecode.LLOAD code = (Bytecode.LLOAD)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.index()); 
            return;	
        }
		case Bytecode.LOOKUPSWITCH: print((Bytecode.LOOKUPSWITCH)bytecode);	return;        
		case Bytecode.LSTORE: {
            Bytecode.LSTORE code = (Bytecode.LSTORE)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.index()); 
            return;	
        }
		case Bytecode.MULTIANEWARRAY: {
            Bytecode.MULTIANEWARRAY code = (Bytecode.MULTIANEWARRAY)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT2, code.opcode(), code.index());
            System.out.printf("%-16s%s[%04x]\n", code.name(), code.type(), code.dimensions()); 
            return;	
        }
		case Bytecode.NEW: {
            Bytecode.NEW code = (Bytecode.NEW)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT2, code.opcode(), code.index());
            System.out.printf("%-16s%s\n", code.name(), code.type()); 
            return;	
        }
		case Bytecode.NEWARRAY: {
            Bytecode.NEWARRAY code = (Bytecode.NEWARRAY)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.type()); 
            return;		
        }
		case Bytecode.PUTFIELD: {
            Bytecode.PUTFIELD code = (Bytecode.PUTFIELD)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT2, code.opcode(), code.index());
            System.out.printf("%-16s%s.%s:%s\n", code.name(), code.clazz(), code.field(), code.type()); 
            return;	 
        }
		case Bytecode.PUTSTATIC: {
            Bytecode.PUTSTATIC code = (Bytecode.PUTSTATIC)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT2, code.opcode(), code.index());
            System.out.printf("%-16s%s.%s:%s\n", code.name(), code.clazz(), code.field(), code.type()); 
            return;	 
        }
		case Bytecode.RET: {
            Bytecode.RET code = (Bytecode.RET)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.index()); 
            return;		
        }
		case Bytecode.SIPUSH: {
            Bytecode.SIPUSH code = (Bytecode.SIPUSH)bytecode;
			System.out.printf(FORMAT1, code.offset());
			System.out.printf(FORMAT3, code.opcode(), "");
            System.out.printf("%-16s%04x\n", code.name(), code.value()); 
            return;
        }
		case Bytecode.TABLESWITCH: print((Bytecode.TABLESWITCH)bytecode); return;        
		case Bytecode.WIDE: print((Bytecode.WIDE)bytecode); return;
        default: {
			System.out.printf(FORMAT1, bytecode.offset());
			System.out.printf(FORMAT3, bytecode.opcode(), "");
            System.out.printf("%-16s\n", bytecode.name());
        }}        
    }

	private void print(Bytecode.LDC_B code, int index) throws Exception {
		System.out.printf(FORMAT1, code.offset());
		if (code instanceof Bytecode.LDC) {
			System.out.printf(FORMAT2, code.opcode(), index);
		} else if (code instanceof Bytecode.LDC_W) {
			System.out.printf(FORMAT2, code.opcode(), index);
		}
		switch (code.flag()) {
		case Bytecode.LDC_B.FLAG_INTEGER: System.out.printf("%-16s%08x\n", code.name(), code.intValue()); return;
		case Bytecode.LDC_B.FLAG_FLOAT: System.out.printf("%-16s%08x\n", code.name(), code.floatValue()); return;
		case Bytecode.LDC_B.FLAG_STRING: System.out.printf("%-16s%s\n", code.name(), "\"" + code.string() + "\""); return;
		case Bytecode.LDC_B.FLAG_CLASS: System.out.printf("%-16s%s\n", code.name(), code.clazz()); return;
		case Bytecode.LDC_B.FLAG_METHODTYPE: System.out.printf("%s:%s\n", code.name(), code.arguments(), code.returnType()); return;
		case Bytecode.LDC_B.FLAG_METHODHANDLE: {				
			if (MethodHandleConstant.REF_GETFIELD == code.kind() ||
				MethodHandleConstant.REF_GETSTATIC == code.kind() ||
				MethodHandleConstant.REF_PUTFIELD == code.kind() ||
				MethodHandleConstant.REF_PUTSTATIC == code.kind()) {			
				System.out.printf("%-16s%s.%s:%s\n", code.name(), code.clazz(), code.field(), code.type());
				return;
			} else if (MethodHandleConstant.REF_INVOKEVIRTUAL == code.kind() ||
					   MethodHandleConstant.REF_INVOKESTATIC == code.kind() ||
					   MethodHandleConstant.REF_INVOKESPECIAL == code.kind() ||
					   MethodHandleConstant.REF_NEWINVOKESPECIAL == code.kind() ||
					   MethodHandleConstant.REF_INVOKEINTERFACE == code.kind()) {
				System.out.printf("%-16s%s.%s%s:%s\n", code.name(), code.clazz(), code.method(), code.arguments(), code.returnType());
				return;
			}
		}
		default: throw new Exception("invalid LDC flag #" + code.flag()); 		            
		}
	}

	private void print(Bytecode.LDC2_W code) throws Exception {
		System.out.printf(FORMAT1, code.offset());
		System.out.printf(FORMAT2, code.opcode(), code.index());		
		switch (code.flag()) {
		case Bytecode.LDC_B.FLAG_LONG: System.out.printf("%-16s%016x\n", code.name(), code.longValue()); return;
		case Bytecode.LDC_B.FLAG_DOUBLE: System.out.printf("%-16s%f\n", code.name(), code.doubleValue()); return;
		default: throw new Exception("invalid LDC_W flag #" + code.flag());
		}
	}

	private void print(Bytecode.LOOKUPSWITCH code)  {
		System.out.printf(FORMAT1, code.offset());
		System.out.printf(FORMAT3, code.opcode(), "");
		System.out.printf("%-16s", code.name());
		Map<Integer, Integer> matches = code.matches();                        
		int i = 0; for (Integer key : matches.keySet()) {
			if (0 < i++) System.out.printf(FORMAT4, "", "", "", "");
			System.out.printf("%08x: %08x\n", key.intValue(), matches.get(key).intValue());
		}
		System.out.printf(FORMAT4, "", "", "", "");
		System.out.printf("%8s: %08x\n", "default", code.defaultOffset());
	}

	private void print(Bytecode.TABLESWITCH code) {		
		System.out.printf(FORMAT1, code.offset());
		System.out.printf(FORMAT3, code.opcode(), "");
		System.out.printf("%-16s", code.name());
		List<Integer> jumps = code.jumps();            
		int i = 0; for (Integer jump : jumps) {				
			if (0 < i++) System.out.printf(FORMAT4, "", "", "", "");
			System.out.printf("%08x\n", jump.intValue());
		}
		System.out.printf(FORMAT4, "", "", "", "");
		System.out.printf("%08x\n", code.defaultOffset());
	}

	private void print(Bytecode.WIDE code) {
		System.out.printf(FORMAT1, code.offset());
		System.out.printf(FORMAT3, code.opcode(), "");
		switch (code.minor()) {
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
		case Bytecode.RET: System.out.printf("%-16s%04x\n", code.name(), code.index()); return;		
		case Bytecode.IINC: System.out.printf("%-16s%04x, %04x\n", code.name(), code.index(), code.value()); return;            
		}
	}
}
