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
            if (type.name().equals(clazz)) {
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
		reader.printer().prefix("    ");		
		for (Bytecode code : reader.get()) {
			reader.printer().print(code);
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
}
