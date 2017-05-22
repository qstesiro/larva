package com.runbox.debug.event.breakpoint;

import com.sun.jdi.event.ModificationWatchpointEvent;

public class BreakpointModifyEvent extends BreakpointEvent<ModificationWatchpointEvent> {

    public BreakpointModifyEvent(ModificationWatchpointEvent event) {
        super(event);
    }
}
