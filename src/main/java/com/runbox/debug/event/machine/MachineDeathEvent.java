package com.runbox.debug.event.machine;

import com.sun.jdi.event.VMDeathEvent;

import com.runbox.debug.event.Event;

public class MachineDeathEvent extends Event<VMDeathEvent> {

    public MachineDeathEvent(VMDeathEvent event) {
        super(event);
    }

    @Override
    public boolean handle() throws Exception {
        System.out.println(event().toString());
        return !super.handle();
    }
}
