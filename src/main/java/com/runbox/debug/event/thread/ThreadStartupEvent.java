package com.runbox.debug.event.thread;

import com.sun.jdi.event.ThreadStartEvent;

import com.runbox.debug.event.Event;

public class ThreadStartupEvent extends Event<ThreadStartEvent> {

    public ThreadStartupEvent(ThreadStartEvent event) {
        super(event);
    }

    @Override
    public boolean handle() throws Exception {       
        System.out.println(((ThreadStartEvent)event()).thread().name()); 
        return !super.handle();
    }
}
