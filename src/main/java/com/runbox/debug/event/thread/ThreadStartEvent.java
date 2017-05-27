package com.runbox.debug.event.thread;

public class ThreadStartEvent extends ThreadEvent<com.sun.jdi.event.ThreadStartEvent> {

    public ThreadStartEvent(com.sun.jdi.event.ThreadStartEvent event) {
        super(event);
    }

    @Override
    public boolean handle() throws Exception {       
        System.out.println(((com.sun.jdi.event.ThreadStartEvent)event()).thread().name() + " -> start");
        return super.handle();
    }
}
