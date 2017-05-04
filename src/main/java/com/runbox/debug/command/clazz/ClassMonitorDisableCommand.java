package com.runbox.debug.command.clazz;

import java.util.List;

import com.runbox.debug.command.clazz.ClassCommand;
import com.runbox.debug.manager.ClassManager;

public class ClassMonitorDisableCommand extends ClassCommand {

    public ClassMonitorDisableCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
		List<Integer> ids = ids();
        if (null != ids) {
			for (int id : ids) {
				ClassManager.instance().disable(id);
			}
        } else {
			ClassManager.instance().disable();
		}
        return super.execute();
    }
}
