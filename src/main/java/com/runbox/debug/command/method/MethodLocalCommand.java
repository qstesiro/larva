package com.runbox.debug.command.method;

import java.util.List;

import com.sun.jdi.Method;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.AbsentInformationException;

import com.runbox.debug.manager.MachineManager;

public class MethodLocalCommand extends MethodCommand {

    public MethodLocalCommand(String command) throws Exception {
        super(command);
    }

    @Override 
    public boolean execute() throws Exception {
        int index = 0; String clazz = clazz(); String method = method();    
        List<ReferenceType> types = MachineManager.instance().allClasses();		        
        for (ReferenceType type : types) {
            if (type.name().equals(clazz)) {
                List<Method> methods = type.allMethods();
                for (Method element : methods) {
                    if (element.name().equals(method)) {
                        print(index++, element);
					}
                }
            }
        }
        return super.execute();
    }

    private void print(int index, Method method) {
		System.out.printf("#%-4d%s\n", index, method.name());		        
        try {				
            printLocals(method.variables());
        } catch (AbsentInformationException e) {
            printTypes(method.argumentTypeNames());
        }
	}
	
	private void printLocals(List<LocalVariable> variables) {
		if (0 < variables.size()) {
			for (LocalVariable variable : variables) {
				System.out.printf("%-5s%s :%s\n", "", variable.name(), variable.typeName());
			}
		}
	}

	private void printTypes(List<String> arguments) {
		if (0 < arguments.size()) {
			for (String type : arguments) {
				System.out.printf("%-5s%s :%s\n", "", "n/a", type);
			}
		}
	}
}
