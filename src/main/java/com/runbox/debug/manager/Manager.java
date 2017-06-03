package com.runbox.debug.manager;

import com.sun.jdi.event.Event;

public class Manager extends com.runbox.manager.Manager {    

    public void monitor(boolean flag) {

    }

    public boolean need(Event event) {
        return true;
    }

    public boolean handle(Event event) throws Exception {
        return true;
    }    
    
    private int id = 1;

    protected int id() {
        return id++;
    }
}
