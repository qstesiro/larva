package com.runbox.debug.event.machine;

import com.runbox.debug.event.Event;

/**
 * Created by qstesiro
 */
public class MachineDeathEvent extends Event<com.sun.jdi.event.VMDeathEvent> {

    public MachineDeathEvent(com.sun.jdi.event.VMDeathEvent event) {
        super(event);
    }

    @Override
    public boolean handle() throws Exception {
        System.out.println(event.toString());
        return true;
    }
}
