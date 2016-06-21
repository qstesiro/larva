package com.runbox.debug.manager;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.ClassUnloadEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.ClassUnloadRequest;
import com.sun.jdi.request.EventRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangmengmeng01 on 2016/6/21.
 */
public class ClassManager extends Manager {

    private ClassManager() {

    }

    private static ClassManager manager = new ClassManager();

    public static ClassManager instance() {
        return manager;
    }

    private ClassPrepareRequest prepare = null;
    private ClassUnloadRequest unload = null;

    @Override
    public void monitor(boolean flag) {
        if (flag) {
            initialize();
            prepare = RequestManager.instance().createClassPrepareRequest();
            prepare.setSuspendPolicy(EventRequest.SUSPEND_ALL);
            prepare.enable();
            unload = RequestManager.instance().createClassUnloadRequest();
            unload.setSuspendPolicy(EventRequest.SUSPEND_ALL);
            unload.enable();
        } else {
            prepare.disable();
            RequestManager.instance().deleteEventRequest(prepare);
            prepare = null;
            unload.disable();
            RequestManager.instance().deleteEventRequest(unload);
            unload = null;
        }
    }

    private void initialize() {
        List<ReferenceType> types = MachineManager.instance().allClasses();
        for (ReferenceType type : types) {
            map.put(type.name(), type);
        }
    }

    @Override
    public boolean need(Event event) {
        if (event instanceof ClassPrepareEvent || event instanceof ClassUnloadEvent) {
            if (prepare == event.request() || unload == event.request()) {
                return true;
            }
        }
        return false;
    }

    private Map<String, ReferenceType> map = new HashMap<>();

    @Override
    public boolean handle(Event event) throws Exception {
        if (null != event) {
            if (event instanceof ClassPrepareEvent) {
                map.put(((ClassPrepareEvent)event).referenceType().name(), ((ClassPrepareEvent)event).referenceType());
            } else if (event instanceof ClassUnloadEvent) {
                map.remove(((ClassPrepareEvent)event).referenceType().name());
            }
        }
        return super.handle(event);
    }

    public ReferenceType match(String name) {
        return map.get(name);
    }

    /**
     * I don`t know if VirtualMachine.allClasses was optimized
     * I suppose that it was optimized
     * @return
     */
    public List<ReferenceType> allClasses() {
        return MachineManager.instance().allClasses();
    }
}
