package com.runbox.debug.command.clazz;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.MachineManager;
import com.sun.jdi.Field;
import com.sun.jdi.ReferenceType;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/4/26.
 */
public class ClassFieldCommand extends Command {

    public ClassFieldCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid argument");
        }
    }

    @Override
    public boolean execute() throws Exception {
        print(clazz(), field());
        return super.execute();
    }

    private void print(String clazz, String field) {
        int index = 0;
        List<ReferenceType> types = MachineManager.instance().allClasses();
        System.out.println("index\tclass.field");
        for (ReferenceType type : types) {
            if (Command.match(clazz, type.name())) {
                List<Field> fields = type.allFields();
                for (Field entry : fields) {
                    if (Command.match(field, entry.name())) {
                        System.out.println(index++ + "\t" + type.name() + "." + entry.name());
                    }
                }
            }
        }
    }

    private String clazz() throws Exception {
        int index = argument.lastIndexOf('.');
        if (-1 != index) {
            return Command.convert(argument.substring(0, index).trim());
        }
        throw new Exception("invalid argument");
    }

    private String field() throws Exception {
        int index = argument.lastIndexOf('.');
        if (-1 != index) {
            return Command.convert(argument.substring(index + 1).trim());
        }
        throw new Exception("invalid argument");
    }
}
