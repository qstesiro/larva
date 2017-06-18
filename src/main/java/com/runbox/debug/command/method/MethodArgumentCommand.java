package com.runbox.debug.command.method;

import java.util.List;

import com.sun.jdi.Method;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.AbsentInformationException;

import com.runbox.debug.manager.MachineManager;

public class MethodArgumentCommand extends MethodCommand {

    public MethodArgumentCommand(String command) throws Exception {
        super(command); 
    }

    @Override 
    public boolean execute() throws Exception {
        String clazz = clazz(); String method = method();    
        List<ReferenceType> types = MachineManager.instance().allClasses();
        int i = 0; for (ReferenceType type : types) {
            if (type.name().equals(clazz)) {
                List<Method> methods = type.allMethods();
                for (Method element : methods) {
                    if (element.name().equals(method)) {
                        print(i++, element);
					}
                }
            }
        }
        return super.execute();
    }

    private void print(int index, Method method) {
		System.out.printf("#%-4d%s", index, method.name());		
        try {
            printLocals(method.arguments());				            
        } catch (AbsentInformationException e) {
            printTypes(method.argumentTypeNames());
        }
	}
	
	private void printLocals(List<LocalVariable> variables) {
		System.out.printf("(");
		if (0 < variables.size()) {
			int i = 0; for (LocalVariable variable : variables) {
				if (0 < i++) System.out.printf("%s", ", ");
				System.out.printf("%s %s", variable.typeName(), variable.name());				
			}
		}
		System.out.printf(")\n");
	}

	private void printTypes(List<String> arguments) {
		System.out.printf("(");
		if (0 < arguments.size()) {
			int i = 0; for (String type : arguments) {
				if (0 < i++) System.out.printf("%s", ", ");
				System.out.printf("%s %s", type, "n/a");
			}
		}
		System.out.printf(")\n");
	}
}
