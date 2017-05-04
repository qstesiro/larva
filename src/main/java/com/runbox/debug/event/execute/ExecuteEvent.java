package com.runbox.debug.event.execute;

import java.io.File;
import java.util.Map;

import com.sun.jdi.Location;
import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.event.StepEvent;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.StepRequest;

import com.runbox.debug.command.execute.ExecuteCommand;
import com.runbox.debug.event.Event;
import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.SourceManager;

public class ExecuteEvent<T extends StepEvent> extends Event<T> {

	public ExecuteEvent(T event) {
		super(event);
		ContextManager.instance().thread(event.thread());
	}

	@Override
	public boolean handle() throws Exception {		
		if (0 == count()) {
			print();
			File file = SourceManager.instance().find(event.location());
			if (null != file) {
				Map<Integer, String> lines = SourceManager.instance().lines(event.location(), 0, 0);
				if (null != lines) {
					for (int key : lines.keySet()) {
						System.out.println(lines.get(key) + "\t" + file.getName() + ":" + key);
					}
				}
			}
		}
        return !super.handle();
	}

	private int count() {
		EventRequest request = event.request();
		int count = (int)request.getProperty(ExecuteCommand.COUNT);
		request.putProperty(ExecuteCommand.COUNT, --count);
		return count;
	}

	private void print() {
		StepRequest request = (StepRequest)event.request();
		String string = (StepRequest.STEP_LINE == request.size() ? "next" : "step");
		string += " " + (StepRequest.STEP_OVER == request.depth() ? "over" : "into");
		string += " " + location(event.location());
		System.out.println(string);
	}

	private String location(Location location) {
		String string = "";
		try {
			string += location.sourcePath();			
		} catch (AbsentInformationException e) {
			string += "none";
		}
		string += ":" + location.lineNumber();
		string += "#" + location.codeIndex();
		return string;
	}
}
