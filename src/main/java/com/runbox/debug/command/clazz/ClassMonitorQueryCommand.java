package com.runbox.debug.command.clazz;

import java.util.Map;

import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.ClassUnloadRequest;

import com.runbox.debug.command.clazz.ClassCommand;
import com.runbox.debug.manager.ClassManager;

public class ClassMonitorQueryCommand extends ClassCommand {

    public ClassMonitorQueryCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
		String type = type();
        if (null != type) {
            if (type.equals("prepare")) {
                print(ClassManager.instance().prepares());
            } else if (type.equals("unload")) {
                print(ClassManager.instance().unloads());
            } else {
                throw new Exception("invalid argument -> " + argument());
            }
        } else {
            print(ClassManager.instance().get());
        }
        return super.execute();
    }

    private void print(Map<Integer, EventRequest> map) throws Exception {
        if (0 < map.size()) {			
            System.out.printf("%-5s%-5s%-10s%-8s%s\n", "#", "id", "type", "status", "clazz");
            int i = 0; for (int id : map.keySet()) {
                EventRequest request = map.get(id);
				System.out.printf("%-5s%-5d%-10s%-8b%s\n",
								  i++, id,
								  ((request instanceof ClassPrepareRequest) ? "prepare" : "unload"),
								  request.isEnabled(),
								  request.getProperty(ClassCommand.CLASS));
            }            
        }
    }
}
