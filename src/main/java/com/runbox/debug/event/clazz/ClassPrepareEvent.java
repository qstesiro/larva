package com.runbox.debug.event.clazz;

import com.sun.jdi.request.EventRequest;

import com.runbox.debug.event.Event;
import com.runbox.debug.command.clazz.ClassCommand;

public class ClassPrepareEvent extends Event<com.sun.jdi.event.ClassPrepareEvent> {

    public ClassPrepareEvent(com.sun.jdi.event.ClassPrepareEvent event) {
        super(event);
    }

    @Override
    public boolean handle() throws Exception {       
        EventRequest request = ((com.sun.jdi.event.ClassPrepareEvent)event()).request();
        System.out.println("prepare -> " + (String)request.getProperty(ClassCommand.CLASS));
        return !super.handle();
    }
}
