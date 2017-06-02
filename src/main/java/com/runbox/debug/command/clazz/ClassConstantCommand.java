package com.runbox.debug.command.clazz;

import java.util.List;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.ArrayType;

import com.runbox.debug.manager.MachineManager;

import com.runbox.clazz.reader.ReaderFactory;
import com.runbox.clazz.reader.ConstantReader;
import com.runbox.clazz.entry.constant.*;
import com.runbox.clazz.entry.bytecode.Bytecode;

import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.operand.Operand;

public class ClassConstantCommand extends ClassCommand {

	public ClassConstantCommand(String command) throws Exception {
		super(command);
		if (null != argument()) {
			clazz = new Expression(argument()).execute().getString(0);
			return;
		}
		throw new Exception("invalid operand");
	}

	private String clazz = null;;
	
	@Override
    public boolean execute() throws Exception {
        if (null != clazz) {
			List<ReferenceType> classes = MachineManager.instance().allClasses();
            for (ReferenceType type : classes) {
				if (!(type instanceof ArrayType)) {
					if (type.name().equals(type)) {
						print(type); break;
					}
				}
            }
        }
        return super.execute();
    }

	private void print(ReferenceType type) throws Exception {
		System.out.println(type.name());
		ConstantReader reader = ReaderFactory.create(type.constantPool(), type.constantPoolCount());
		Constant[] constants = reader.get();
		for (int i = 1; i < constants.length; ++i) {
			print(constants[i]);
			if (constants[i] instanceof LongConstant || constants[i] instanceof DoubleConstant) ++i;
		}
	}	   

	private void print(Constant constant) throws Exception {
		System.out.printf("%-20s", constant.name());
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
}
