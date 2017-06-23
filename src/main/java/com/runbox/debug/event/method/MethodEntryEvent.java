package com.runbox.debug.event.method;

public class MethodEntryEvent extends MethodEvent<com.sun.jdi.event.MethodEntryEvent> {

    public MethodEntryEvent(com.sun.jdi.event.MethodEntryEvent event) {
        super(event);
    }

    @Override
	public boolean handle() throws Exception {
		return super.handle();
	}
}