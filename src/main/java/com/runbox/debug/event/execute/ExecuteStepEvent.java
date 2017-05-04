package com.runbox.debug.event.execute;

import com.sun.jdi.event.StepEvent;

public class ExecuteStepEvent extends ExecuteEvent<StepEvent> {

    public ExecuteStepEvent(StepEvent event) {
        super(event);  
    }	
}
