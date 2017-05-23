package com.runbox.debug.event.breakpoint;

import com.runbox.debug.event.LocatableEvent;

import com.runbox.debug.manager.BreakpointManager;

public class BreakpointEvent<T extends com.sun.jdi.event.LocatableEvent> extends LocatableEvent<T> {

	public BreakpointEvent(T event) {
		super(event);
	}

	@Override
	public boolean handle() throws Exception {
		System.out.println("hit breakpoint -> " +
						   ((BreakpointManager.Breakpoint)event().request().getProperty(BreakpointManager.Breakpoint.OBJECT)).location());
		return !super.handle();
	}
}
