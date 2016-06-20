package com.runbox.debug.manager;

import com.sun.jdi.event.Event;

/**
 * Created by qstesiro
 */
public class Manager {

    public void clean() {

    }

    public void monitor(boolean flag) {

    }

    public boolean need(Event event) {
        return false;
    }

    public boolean handle(Event event) throws Exception {
        return true;
    }
}
