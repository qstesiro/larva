package com.runbox.debug.event.thread;

import com.runbox.debug.event.Event;

public class ThreadDeathEvent extends Event<com.sun.jdi.event.ThreadDeathEvent> {

    public ThreadDeathEvent(com.sun.jdi.event.ThreadDeathEvent event) {
        super(event);
    }

    @Override
    public boolean handle() throws Exception {       
        System.out.println(((com.sun.jdi.event.ThreadDeathEvent)event()).thread().name()); 
        return !super.handle();
    }
}
