package com.runbox.debug.event.breakpoint;

public class BreakpointMethodEvent extends BreakpointEvent<com.sun.jdi.event.BreakpointEvent> {

    public BreakpointMethodEvent(com.sun.jdi.event.BreakpointEvent event) {
        super(event);
    }
}
