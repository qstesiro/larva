package com.runbox.debug.command.method;

import java.util.Map;
import java.util.List;

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
		List<String> arguments = arguments();
        for (ReferenceType type : MachineManager.instance().allClasses()) {
            if (type.name().equals(clazz)) {
                for (Method element : type.allMethods()) {
                    if (element.name().equals(method)) {
						if (null == arguments || arguments(arguments, element)) {
							print(element);
						}
					}
                }
            }
        }
        return super.execute();
    }
	
	private boolean arguments(List<String> arguments, Method method) {
		if (null != arguments) {			
			if (arguments.size() == method.argumentTypeNames().size()) {
				int i = 0; for (String argument : method.argumentTypeNames()) {						
					if (!arguments.get(i).equals(argument)) return false;				
				}
				return true;
			}
		}
		return false;
	}

	private void print(Method method) throws Exception {
		System.out.printf("%s", access(method));		
		if (method.isNative()) System.out.printf(" native");
		else if (method.isAbstract()) System.out.printf(" abstract");
		if (method.isStatic()) System.out.printf(" static");
		if (method.isFinal()) System.out.printf(" final");
		System.out.printf(" %s", method.returnTypeName());
		System.out.printf(" %s%s {\n", method.name(), arguments(method));
		// byte[] codes = method.bytecodes();
		// System.out.println(codes.length);
		// for (int i = 0; i < codes.length; ++i) {
		// 	System.out.printf("%02x", codes[i]);
		// 	if (0 == i % 100 && 0 != i) System.out.println();
		// }		
		BytecodeReader reader = ReaderFactory.create(method.bytecodes(), 
													 ReaderFactory.create(method.declaringType().constantPool(),
																		  method.declaringType().constantPoolCount()));
		reader.printer().prefix("    ");		
		for (Bytecode code : reader.get()) {
			reader.printer().print(code);
		}
		System.out.println("}");
	}

	private String access(Method method) {
		if (method.isPublic()) return "public";
		else if (method.isProtected()) return "protected";
		else if (method.isPrivate()) return "private";
		return "n/a";
	}

	private String arguments(Method method) {
		String arguments = "(";
		int i = 0; for (String argument : method.argumentTypeNames()) {
			if (0 < i++) arguments += ", ";
			arguments += argument;
		}
		return arguments + ")";
	}	
}
