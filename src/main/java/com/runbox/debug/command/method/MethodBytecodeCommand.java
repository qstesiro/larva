package com.runbox.debug.command.method;

import java.util.Map;
import java.util.List;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.Method;

import com.runbox.debug.manager.MachineManager;

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
		if (null != method) {
			System.out.printf("%s", access(method));		
			if (method.isNative()) System.out.printf(" native");
			else if (method.isAbstract()) System.out.printf(" abstract");
			if (method.isStatic()) System.out.printf(" static");
			if (method.isFinal()) System.out.printf(" final");
			System.out.printf(" %s", method.returnTypeName());
			System.out.printf(" %s%s {\n", method.name(), arguments(method));
			if (MachineManager.instance().hotspot()) {
				printHotspot(method);
			} else if (MachineManager.instance().dalvik()) {
				printDalvik(method);
			} else {
				throw new Exception("invalid vm");
			}		
			System.out.println("}");
		}
	}

	private void printHotspot(Method method) throws Exception {
		if (null != method) {
			com.runbox.clazz.reader.BytecodeReader reader = com.runbox.clazz.reader.ReaderFactory.create(
				method.bytecodes(), 
				com.runbox.clazz.reader.ReaderFactory.create(method.declaringType().constantPool(),
															 method.declaringType().constantPoolCount()));
			reader.printer().prefix("  ");		
			for (com.runbox.clazz.entry.bytecode.Bytecode code : reader.get()) {
				reader.printer().print(code);
			}
		}
	}

	private void printDalvik(Method method) throws Exception {
		if (null != method) {			
			// com.runbox.dex.reader.BytecodeReader reader = com.runbox.dex.reader.ReaderFactory.create(
			// 	method.bytecodes(),
			// 	com.runbox.dex.reader.ReaderFactory.create("d:\\program\\maven\\larva\\classes.dex"));
			com.runbox.dex.reader.BytecodeReader reader = com.runbox.dex.reader.ReaderFactory.create(method.bytecodes(), null);
			reader.printer().prefix("  ");
			for (com.runbox.dex.entry.bytecode.Bytecode code : reader.get()) {
				reader.printer().print(code);
			}
		}
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
