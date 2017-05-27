package com.runbox.debug.event.thread;

public class ThreadDeathEvent extends ThreadEvent<com.sun.jdi.event.ThreadDeathEvent> {

    public ThreadDeathEvent(com.sun.jdi.event.ThreadDeathEvent event) {
        super(event);
    }

    @Override
    public boolean handle() throws Exception {       
        System.out.println(((com.sun.jdi.event.ThreadDeathEvent)event()).thread().name() + " -> death"); 
        return super.handle();
    }
}
