package com.runbox.debug.event.machine;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.event.Event;

/**
 * Created by qstesiro
 */
public class MachineStartEvent extends Event<com.sun.jdi.event.VMStartEvent> {

    public MachineStartEvent(com.sun.jdi.event.VMStartEvent event) {
        super(event);
        ContextManager.instance().thread(event.thread());
    }

    @Override
    public boolean handle() throws Exception {
        String log = event.toString();
        log += "/" + event.thread().name();
        log += "/" + event.thread().threadGroup().toString();
        System.out.println(log);
        return super.handle();
    }
}
