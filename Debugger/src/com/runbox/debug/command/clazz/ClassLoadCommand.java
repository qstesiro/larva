package com.runbox.debug.command.clazz;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.ClassManager;
import com.runbox.debug.manager.RequestManager;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by qstesiro
 */
public class ClassLoadCommand extends Command {

    public ClassLoadCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid argument");
        }
    }

    @Override
    public boolean execute() throws Exception {
        for (String clazz : classes()) {
            ClassPrepareRequest request = RequestManager.instance().createClassPrepareRequest();
            request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
            request.addClassFilter(clazz);
            request.enable();
        }
        return super.execute();
    }

    private List<String> classes() {
        List<String> classes = new LinkedList<>();
        List<ReferenceType> types = ClassManager.instance().allClasses();
        for (ReferenceType type : types) {
            if (Command.match(argument, type.name())) {
                classes.add(type.name());
            }
        }
        return classes;
    }
}
