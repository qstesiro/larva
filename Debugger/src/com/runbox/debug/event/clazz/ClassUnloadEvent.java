package com.runbox.debug.event.clazz;

import com.runbox.debug.event.Event;

/**
 * Created by qstesiro on 2016/4/30.
 */
public class ClassUnloadEvent extends Event<com.sun.jdi.event.ClassUnloadEvent> {

    public ClassUnloadEvent(com.sun.jdi.event.ClassUnloadEvent event) {
        super(event);
    }

    @Override
    public boolean handle() throws Exception {
        return super.handle();
    }
}
