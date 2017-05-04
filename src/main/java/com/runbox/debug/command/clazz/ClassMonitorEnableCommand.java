package com.runbox.debug.command.clazz;

import java.util.List;

import com.sun.jdi.ReferenceType;

import com.runbox.debug.command.clazz.ClassCommand;
import com.runbox.debug.manager.ClassManager;

public class ClassMonitorEnableCommand extends ClassCommand {

    public ClassMonitorEnableCommand(String command) throws Exception {
        super(command);        
    }

    @Override
    public boolean execute() throws Exception {
		List<Integer> ids = ids();
        if (null != ids) {
			for (int id : ids) {
				ClassManager.instance().enable(id);
			}
        } else {
			ClassManager.instance().enable();
		}                
        return super.execute();
    }
}
