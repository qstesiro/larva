package com.runbox.debug.event.machine;

import com.sun.jdi.event.VMStartEvent;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.event.Event;

public class MachineStartEvent extends Event<VMStartEvent> {

    public MachineStartEvent(VMStartEvent event) {
        super(event);        
    }

    @Override
    public boolean handle() throws Exception {
        String log = event().toString();
        log += "/" + event().thread().name();
        log += "/" + event().thread().threadGroup().toString();
        System.out.println(log);
        return !super.handle();
    }
}
