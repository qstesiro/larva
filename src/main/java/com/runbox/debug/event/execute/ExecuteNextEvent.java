package com.runbox.debug.event.execute;

import com.sun.jdi.event.StepEvent;

public class ExecuteNextEvent extends ExecuteEvent<StepEvent> {

    public ExecuteNextEvent(StepEvent event) {
        super(event);		
    }	
}
