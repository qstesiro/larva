package com.runbox.debug.event.breakpoint;

import java.util.Map;

import com.sun.jdi.event.BreakpointEvent;

import com.runbox.debug.event.Event;
import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.SourceManager;
import com.runbox.debug.manager.BreakpointManager;

public class BreakpointLineEvent extends Event<BreakpointEvent> {

    public BreakpointLineEvent(BreakpointEvent event) {
        super(event);
    }

    @Override
    public boolean handle() throws Exception {
		System.out.println("hit breakpoint -> " +
						   ((BreakpointManager.Breakpoint)event.request().getProperty(BreakpointManager.Breakpoint.OBJECT)).location());
		Map<Integer, String> lines = SourceManager.instance().lines(event.location(), 0, 0);
		if (null != lines) {
			for (Integer key : lines.keySet()) {
				System.out.println(lines.get(key) + "\t" + event.location().sourceName() + ":" + key);
			}
		}
        return false;
    }
}
