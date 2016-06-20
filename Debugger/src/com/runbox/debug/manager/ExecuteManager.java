package com.runbox.debug.manager;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.ThreadDeathEvent;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.StepRequest;
import com.sun.jdi.request.ThreadDeathRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qstesiro on 2016/5/29.
 */
public class ExecuteManager extends Manager {

    private ExecuteManager() {

    }

    private static ExecuteManager manager = new ExecuteManager();

    public static ExecuteManager instance() {
        return manager;
    }

    @Override
    public void clean() {
        if (0 < map.size()) {
            for (ThreadReference key : map.keySet()) {
                RequestManager.instance().deleteEventRequest(map.get(key));
            }
            map.clear();
        }
    }

    private Map<ThreadReference, StepRequest> map = new HashMap<>();

    public StepRequest next(ThreadReference thread) throws Exception {
        if (null != thread) {
            delete(thread);
            StepRequest request = RequestManager.instance().createStepRequest(thread, StepRequest.STEP_LINE, StepRequest.STEP_OVER);
            map.put(thread, configRequest(request));
            return request;
        }
        throw new Exception("invalid thread context");
    }

    public StepRequest step(ThreadReference thread) throws Exception {
        if (null != thread) {
            delete(thread);
            StepRequest request = RequestManager.instance().createStepRequest(thread, StepRequest.STEP_LINE, StepRequest.STEP_INTO);
            map.put(thread, configRequest(request));
            return request;
        }
        throw new Exception("invalid thread context");
    }

    public void delete(ThreadReference thread) {
        if (null != thread) {
            for (ThreadReference key : map.keySet()) {
                if (key == thread) {
                    RequestManager.instance().deleteEventRequest(map.get(key));
                    map.remove(thread);
                    break;
                }
            }
        }
    }

    private StepRequest configRequest(StepRequest request) {

        return request;
    }

    private ThreadDeathRequest request = null;

    @Override
    public void monitor(boolean flag) {
        if (flag) {
            request = RequestManager.instance().createThreadDeathRequest();
            request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
            request.enable();
        } else {
            request.disable();
            RequestManager.instance().deleteEventRequest(request);
            request = null;
        }
    }

    @Override
    public boolean need(Event event) {
        if (event instanceof ThreadDeathEvent) {
            if (request == event.request()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handle(Event event) throws Exception {
        for (ThreadReference key : map.keySet()) {
            if (key == ((ThreadDeathEvent)event).thread()) {
                RequestManager.instance().deleteEventRequest(map.get(key));
                map.remove(key);
                break;
            }
        }
        return super.handle(event);
    }
}
