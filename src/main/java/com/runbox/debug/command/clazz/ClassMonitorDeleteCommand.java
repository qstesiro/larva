package com.runbox.debug.command.clazz;

import java.util.Collection;

import com.sun.jdi.ReferenceType;

import com.runbox.debug.command.clazz.ClassCommand;
import com.runbox.debug.manager.ClassManager;

public class ClassMonitorDeleteCommand extends ClassCommand {

    public ClassMonitorDeleteCommand(String command) throws Exception {
        super(command);        
    }

    @Override
    public boolean execute() throws Exception {
        Collection<Integer> ids = ids();
        if (null != ids) {
			for (int id : ids) {
				ClassManager.instance().delete(id);
			}
        } else {
			ClassManager.instance().delete();
		}
        return super.execute();
    }
}
