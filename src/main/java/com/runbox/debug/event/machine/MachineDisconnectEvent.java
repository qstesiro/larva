package com.runbox.debug.event.machine;

import com.sun.jdi.event.VMDisconnectEvent;

import com.runbox.debug.Debugger;
import com.runbox.debug.event.Event;

public class MachineDisconnectEvent extends Event<VMDisconnectEvent> {

    public MachineDisconnectEvent(VMDisconnectEvent event) {
        super(event);
    }

    @Override
    public boolean handle() throws Exception {
        Debugger.instance().flag(Debugger.DISCONNECT);
        System.out.println(event().toString());
        return super.handle();
    }
}
