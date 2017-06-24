package com.runbox.debug.event.breakpoint;

import com.sun.jdi.request.EventRequest;

import com.runbox.debug.manager.BreakpointManager;
import com.runbox.debug.manager.ExecuteManager;

public class BreakpointLineEvent extends BreakpointEvent<com.sun.jdi.event.BreakpointEvent> {

    public BreakpointLineEvent(com.sun.jdi.event.BreakpointEvent event) {
        super(event);
    }

	@Override
	public boolean handle() throws Exception {
		EventRequest request = event().request();
		if (null != request.getProperty(BreakpointManager.Breakpoint.OBJECT)) {
			System.out.println("hit breakpoint -> " +
							   ((BreakpointManager.Breakpoint)
								request.getProperty(BreakpointManager.Breakpoint.OBJECT))
							   .location());	
		}
		return super.handle();
	}
}
