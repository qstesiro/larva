package com.runbox.debug.manager;

import com.runbox.debug.Debugger;
import com.sun.jdi.request.EventRequestManager;
import com.sun.org.apache.xml.internal.resolver.helpers.Debug;

/**
 * Created by huangmengmeng01 on 2016/5/26.
 */
public class RequestManager extends Manager {

    private RequestManager() {

    }

    public static EventRequestManager instance() {
        return MachineManager.instance().eventRequestManager();
    }
}
