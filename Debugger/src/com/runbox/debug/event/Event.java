package com.runbox.debug.event;

/**
 * Created by huangmengmeng01 on 2016/4/27.
 */
public class Event<T> {

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
