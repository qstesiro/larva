package com.runbox.debug.command.exception;

import java.util.Map;

import com.sun.jdi.ArrayType;
import com.sun.jdi.request.ExceptionRequest;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.ExceptionManager;

public class ExceptionQueryCommand extends Command {

	public ExceptionQueryCommand(String command) throws Exception {
		super(command);
	}

	@Override
	public boolean execute() throws Exception {
		Map<Integer, ExceptionRequest> requests = ExceptionManager.instance().requests();
		System.out.printf("%-5s%-5s%-8s%-10s%s\n", "#", "id", "caught", "uncaught", "class");
		int i = 0; for (int id : requests.keySet()) {
			ExceptionRequest request = requests.get(id);
			System.out.printf("%-5d%-5d%-8b%-10b%s\n", i++, id, request.notifyCaught(), request.notifyUncaught(), request.exception().name());
		}
		return super.execute();
	}
}
