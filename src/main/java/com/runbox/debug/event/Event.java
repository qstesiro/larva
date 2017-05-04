package com.runbox.debug.event;

public class Event<T extends com.sun.jdi.event.Event> {

    public Event() {
        event = null;
    }

    protected T event;

    public Event(T event) {
        this.event = event;
    }

    public T event() {
        return event;
    }

    public boolean handle() throws Exception {
        return true;
    }
}
