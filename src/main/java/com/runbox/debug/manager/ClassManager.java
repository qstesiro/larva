package com.runbox.debug.manager;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sun.jdi.event.Event;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.ClassUnloadEvent;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.ClassUnloadRequest;

public class ClassManager extends Manager {

    private ClassManager() {

    }

    private static ClassManager instance = new ClassManager();

    public static ClassManager instance() {
        return instance;
    }

    @Override
    public void clean() throws Exception {
        if (null != prepare && null != unload) {
            RequestManager.instance().deleteEventRequest(prepare); prepare = null;
            RequestManager.instance().deleteEventRequest(unload); unload = null;
        }
        for (int key : map.keySet()) {
            RequestManager.instance().deleteEventRequest(map.get(key));
        }		
        map.clear();
    }
    
    private ClassPrepareRequest prepare = null;
    private ClassUnloadRequest unload = null;

    @Override
    public void monitor(boolean flag) {
        if (flag) {
            if (null == prepare && null == unload) {
                prepare = RequestManager.instance().createClassPrepareRequest(null);                
                unload = RequestManager.instance().createClassUnloadRequest(null);
                unload.setSuspendPolicy(EventRequest.SUSPEND_ALL);
                unload.enable();
            }
        } else {
            if (null != prepare && null != unload) {
                RequestManager.instance().deleteEventRequest(prepare); prepare = null;
                RequestManager.instance().deleteEventRequest(unload); unload = null;
            }
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

    /**
     * I don`t know if VirtualMachine.allClasses was optimized
     * I suppose that it was optimized
     */
    public List<ReferenceType> allClasses() {
        return MachineManager.instance().allClasses();
    }

	private Map<Integer, EventRequest> map = new HashMap<Integer, EventRequest>();       
    
    public void append(EventRequest request) {
        map.put(id(), request);
    }

    public void enable(int id) throws Exception {
        for (int key : map.keySet()) {
            if (key == id) {
                if (!map.get(key).isEnabled()) {
                    map.get(key).enable();
                }
            }
        }            
    }

	public void enable() {
		for (int key : map.keySet()) {
			if (!map.get(key).isEnabled()) {
				map.get(key).enable();
			}
		} 
	}

    public void disable(int id) throws Exception {       
        for (int key : map.keySet()) {
            if (key == id) {
                if (map.get(key).isEnabled()) {
                    map.get(key).disable();
                }
            }
        }        
    }

	public void disable() {
		for (int key : map.keySet()) {         
			if (map.get(key).isEnabled()) {
				map.get(key).disable();
			}
        }
	}

    public void delete(int id) throws Exception {       
        for (int key : map.keySet()) {
            if (key == id) {
                RequestManager.instance().deleteEventRequest(map.get(key));
                map.remove(key); break;
            }
        }        
    }

	public void delete() {
		for (int key : map.keySet()) {
			RequestManager.instance().deleteEventRequest(map.get(key));
        }
		map.clear();
	}

    public Map<Integer, EventRequest> get() {
        return map;
    }

    public Map<Integer, EventRequest> prepares() {        
        Map<Integer, EventRequest> prepares = new HashMap<Integer, EventRequest>();
        for (int key : map.keySet()) {
            if (map.get(key) instanceof ClassPrepareRequest) {
                prepares.put(key, map.get(key));
            }
        }
        return prepares;
    }

    public Map<Integer, EventRequest> unloads() {
		Map<Integer, EventRequest> unloads = new HashMap<Integer, EventRequest>();
        for (int key : map.keySet()) {
            if (map.get(key) instanceof ClassUnloadRequest) {
                unloads.put(key, map.get(key));
            }
        }        
        return unloads;        
    }
}
