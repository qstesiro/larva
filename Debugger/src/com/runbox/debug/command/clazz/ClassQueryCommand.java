package com.runbox.debug.command.clazz;

import com.runbox.debug.Debugger;
import com.runbox.debug.command.Command;
import com.runbox.debug.manager.MachineManager;
import com.sun.jdi.ReferenceType;

import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/4/26.
 */
public class ClassQueryCommand extends Command {

    public ClassQueryCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid argument");
        }
    }

    @Override
    public boolean execute() throws Exception {
        String clazz = Command.convert(argument.trim());
        int index = 0;
        List<ReferenceType> list = MachineManager.instance().allClasses();
        System.out.println("index\tclass");
        for (ReferenceType type : list) {
            if (null != clazz) {
                if (Command.match(clazz, type.name())) {
                    System.out.println(index++ + "\t" + type.name());
                }
            } else {
                System.out.println(index++ + "\t" + type.name());
            }
        }
        return super.execute();
    }
}
