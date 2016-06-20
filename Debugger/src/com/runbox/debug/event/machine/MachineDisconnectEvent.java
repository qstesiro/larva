package com.runbox.debug.event.machine;

import com.runbox.debug.Debugger;
import com.runbox.debug.event.Event;

/**
 * Created by huangmengmeng01 on 2016/4/27.
 */
public class MachineDisconnectEvent extends Event<com.sun.jdi.event.VMDisconnectEvent> {

    public MachineDisconnectEvent(com.sun.jdi.event.VMDisconnectEvent event) {
        super(event);
    }

    @Override
    public boolean handle() throws Exception {
        Debugger.instance().flag(Debugger.DISCONNECT);
        System.out.println(event.toString());
        return true;
    }
}
