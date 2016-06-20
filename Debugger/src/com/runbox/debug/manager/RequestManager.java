package com.runbox.debug.manager;

import com.sun.jdi.request.EventRequestManager;

/**
 * Created by qstesiro
 */
public class RequestManager extends Manager {

    private RequestManager() {

    }

    public static EventRequestManager instance() {
        return MachineManager.instance().eventRequestManager();
    }
}
