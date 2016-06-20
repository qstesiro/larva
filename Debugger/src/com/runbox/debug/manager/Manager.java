package com.runbox.debug.manager;

import com.sun.jdi.event.Event;

/**
 * Created by huangmengmeng01 on 2016/5/26.
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
