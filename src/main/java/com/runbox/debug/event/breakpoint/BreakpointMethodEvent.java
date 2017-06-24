package com.runbox.debug.event.breakpoint;

import com.sun.jdi.request.EventRequest;

import com.runbox.debug.manager.BreakpointManager;
import com.runbox.debug.manager.ExecuteManager;

public class BreakpointMethodEvent extends BreakpointEvent<com.sun.jdi.event.BreakpointEvent> {

    public BreakpointMethodEvent(com.sun.jdi.event.BreakpointEvent event) {
        super(event);
    }

	@Override
	public boolean handle() throws Exception {
		System.out.println("hit breakpoint -> " +
						   ((BreakpointManager.Breakpoint)
							event().request().getProperty(BreakpointManager.Breakpoint.OBJECT))
						   .location());	
		return super.handle();
	}
}
