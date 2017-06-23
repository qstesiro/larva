package com.runbox.debug.event.method;

import com.sun.jdi.event.MethodExitEvent;

public class MethodReturnEvent extends MethodEvent<MethodExitEvent> {

    public MethodReturnEvent(MethodExitEvent event) throws Exception {
        super(event);
    }

    @Override
	public boolean handle() throws Exception {
		return super.handle();
	}
}
