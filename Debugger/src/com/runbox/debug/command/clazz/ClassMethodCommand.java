package com.runbox.debug.command.clazz;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.MachineManager;
import com.sun.jdi.Method;
import com.sun.jdi.ReferenceType;

import java.util.List;

/**
 * Created by qstesiro
 */
public class ClassMethodCommand extends Command {

    public ClassMethodCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid argument");
        }
    }

    @Override
    public boolean execute() throws Exception {
        print(clazz(), method());
        return super.execute();
    }

    private void print(String clazz, String method) {
        int index = 0;
        List<ReferenceType> types = MachineManager.instance().allClasses();
        System.out.println("index\tclass.method");
        for (ReferenceType type : types) {
            if (Command.match(clazz, type.name())) {
                List<Method> methods = type.allMethods();
                for (Method entry : methods) {
                    if (Command.match(method, entry.name()))
                        System.out.println(index++ + "\t" + type.name() + "." + entry.name());
                }
            }
        }
    }

    private String clazz() throws Exception {
        int index = argument.lastIndexOf(".");
        if (-1 != index) {
            return Command.convert(argument.substring(0, index).trim());
        }
        throw new Exception("invalid argument");
    }

    private String method() throws Exception {
        int index = argument.lastIndexOf(".");
        if (-1 != index) {
            return Command.convert(argument.substring(index + 1).trim());
        }
        throw new Exception("invalid argument");
    }
}
