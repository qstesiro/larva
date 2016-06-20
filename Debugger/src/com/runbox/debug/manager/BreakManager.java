package com.runbox.debug.manager;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.breakpoint.*;
import com.sun.jdi.*;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.request.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangmengmeng01 on 2016/5/25.
 */
public class BreakManager extends Manager {

    private BreakManager() {

    }

    private static BreakManager manager = new BreakManager();

    public static BreakManager instance() {
        return manager;
    }

    @Override
    public void clean() {
        if (0 < map.size()) {
            for (Integer key : map.keySet()) {
                if (null != map.get(key).request()) {
                    RequestManager.instance().deleteEventRequest(map.get(key).request());
                }
            }
            map.clear();
        }
    }

    private int number = 1;

    public int number() {
        return number++;
    }

    private Map<Integer, BreakPoint> map = new HashMap<>();

    public boolean append(MethodBreakPoint point) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof MethodBreakPoint) {
                if (((MethodBreakPoint)map.get(key)).equals(point)) {
                    return true;
                }
            }
        }
        map.put(number(), point);
        return true;
    }

    public boolean append(LineBreakPoint point) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof LineBreakPoint) {
                if (((LineBreakPoint)map.get(key)).equals(point)) {
                    return true;
                }
            }
        }
        map.put(number(), point);
        return true;
    }

    public boolean append(AccessBreakPoint point) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof AccessBreakPoint) {
                if (((AccessBreakPoint)map.get(key)).equals(point)) {
                    return true;
                }
            }
        }
        map.put(number(), point);
        return true;
    }

    public boolean append(ModifyBreakPoint point) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof ModifyBreakPoint) {
                if (((ModifyBreakPoint)map.get(key)).equals(point)) {
                    return true;
                }
            }
        }
        map.put(number(), point);
        return true;
    }

    public boolean delete(int number) {
        for (Integer key : map.keySet()) {
            if (key == number) {
                BreakPoint point = map.remove(key);
                if (null != point.request()) {
                    RequestManager.instance().deleteEventRequest(point.request());
                }
                return true;
            }
        }
        return false;
    }

    public boolean enable(int number) {
        for (Integer key : map.keySet()) {
            if (key == number && BreakPoint.STATUS_DISABLE == map.get(key).status()) {
                map.get(key).status(BreakPoint.STATUS_ENABLE);
                map.get(key).request().enable();
                return true;
            }
        }
        return false;
    }

    public boolean disable(int number) {
        for (Integer key : map.keySet()) {
            if (key == number && BreakPoint.STATUS_ENABLE == map.get(key).status()) {
                map.get(key).status(BreakPoint.STATUS_DISABLE);
                map.get(key).request().enable();
                return true;
            }
        }
        return false;
    }

    public boolean contain(MethodBreakPoint point) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof MethodBreakPoint) {
                if (((MethodBreakPoint)map.get(key)).equals(point)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contain(LineBreakPoint point) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof LineBreakPoint) {
                if (((LineBreakPoint)map.get(key)).equals(point)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contain(AccessBreakPoint point) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof AccessBreakPoint) {
                if (((AccessBreakPoint)map.get(key)).equals(point)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contain(ModifyBreakPoint point) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof ModifyBreakPoint) {
                if (((ModifyBreakPoint)map.get(key)).equals(point)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean match(MethodBreakPoint point, ReferenceType type) {
        if (match(type.name(), point.clazz())) {
            List<Method> methods = type.methods();
            for (Method method : methods) {
                if (method.name().equals(point.method())) {
                    if (match(point.list(), method.argumentTypeNames())) {
                        BreakpointRequest request = RequestManager.instance().createBreakpointRequest(method.location());
                        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
                        request.putProperty(BreakPoint.OBJECT, point);
                        if (null != point.block()) {
                            request.putProperty(Command.BLOCK, point.block());
                        }
                        request.enable();
                        point.status(BreakPoint.STATUS_ENABLE);
                        point.request(request);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean match(List<String> list1, List<String> list2) {
        if (list1.size() == list2.size()) {
            for (int i = 0; i < list1.size(); ++i) {
                if (!list1.get(i).equals(list2.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean match(LineBreakPoint point, ReferenceType type) {
        if (match(type.name(), point.clazz())) {
            try {
                List<Location> locations = type.locationsOfLine(point.line());
                for (Location location : locations) {
                    if (location.lineNumber() == point.line()) {
                        BreakpointRequest request = RequestManager.instance().createBreakpointRequest(location);
                        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
                        request.putProperty(BreakPoint.OBJECT, point);
                        if (null != point.block()) {
                            request.putProperty(Command.BLOCK, point.block());
                        }
                        request.enable();
                        point.status(BreakPoint.STATUS_ENABLE);
                        point.request(request);
                        return true;
                    }
                }
            } catch (AbsentInformationException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean match(AccessBreakPoint point, ReferenceType type) {
        if (match(type.name(), point.clazz())) {
            List<Field> fields = type.allFields();
            for (Field field : fields) {
                if (field.name().equals(point.field())) {
                    AccessWatchpointRequest request = RequestManager.instance().createAccessWatchpointRequest(field);
                    request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
                    if (null != point.block()) {
                        request.putProperty(Command.BLOCK, point.block());
                    }
                    request.enable();
                    point.status(BreakPoint.STATUS_ENABLE);
                    point.request(request);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean match(ModifyBreakPoint point, ReferenceType type) {
        if (match(type.name(), point.clazz())) {
            List<Field> fields = type.allFields();
            for (Field field : fields) {
                if (field.name().equals(point.field())) {
                    ModificationWatchpointRequest request = RequestManager.instance().createModificationWatchpointRequest(field);
                    request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
                    if (null != point.block()) {
                        request.putProperty(Command.BLOCK, point.block());
                    }
                    request.enable();
                    point.status(BreakPoint.STATUS_ENABLE);
                    point.request(request);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean match(String source, String target) {
        int index = source.indexOf("$");
        if (-1 != index) {
            source = source.substring(0, index);
        }
        if (source.equals(target)) {
            return true;
        }
        return false;
    }

    public Map<Integer, BreakPoint> points() {
        return map;
    }

    private ClassPrepareRequest request = null;

    @Override
    public void monitor(boolean flag) {
        if (flag) {
            request = RequestManager.instance().createClassPrepareRequest();
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
        if (event instanceof ClassPrepareEvent) {
            if (request == event.request()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handle(Event event) throws Exception {
        for (Integer number : map.keySet()) {
            if (BreakPoint.STATUS_PENDING == map.get(number).status()) {
                if (map.get(number) instanceof MethodBreakPoint) {
                    BreakManager.instance().match((MethodBreakPoint)map.get(number), ((ClassPrepareEvent)event).referenceType());
                } else if (map.get(number) instanceof LineBreakPoint) {
                    BreakManager.instance().match((LineBreakPoint)map.get(number), ((ClassPrepareEvent)event).referenceType());
                } else if (map.get(number) instanceof AccessBreakPoint) {
                    BreakManager.instance().match((AccessBreakPoint)map.get(number), ((ClassPrepareEvent)event).referenceType());
                } else if (map.get(number) instanceof ModifyBreakPoint) {
                    BreakManager.instance().match((ModifyBreakPoint)map.get(number), ((ClassPrepareEvent)event).referenceType());
                }
            }
        }
        return super.handle(event);
    }
}
