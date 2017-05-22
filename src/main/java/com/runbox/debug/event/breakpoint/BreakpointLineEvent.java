package com.runbox.debug.event.breakpoint;

public class BreakpointLineEvent extends BreakpointEvent<com.sun.jdi.event.BreakpointEvent> {

    public BreakpointLineEvent(com.sun.jdi.event.BreakpointEvent event) {
        super(event);
    }    
}
