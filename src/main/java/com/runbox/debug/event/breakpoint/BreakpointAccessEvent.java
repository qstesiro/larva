package com.runbox.debug.event.breakpoint;

import com.sun.jdi.event.AccessWatchpointEvent;

public class BreakpointAccessEvent extends BreakpointEvent<AccessWatchpointEvent> {

    public BreakpointAccessEvent(AccessWatchpointEvent event) {
        super(event);
    }    
}
