package com.runbox.debug.manager;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;

import com.sun.jdi.event.Event;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.ClassUnloadEvent;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.ClassUnloadRequest;

import com.runbox.debug.command.Command;

import com.runbox.script.statement.node.RoutineNode;

public class ClassManager extends Manager {

    private ClassManager() {

    }

    private static ClassManager instance = new ClassManager();

    public static ClassManager instance() {
        return instance;
    }

	public void clean() throws Exception {		
		map.clear();		
	}

	private Map<Integer, Entry> map = new HashMap<Integer, Entry>();   	
    
    public void append(Entry entry) {
        map.put(id(), entry);
    }

    public void enable(int id) throws Exception {
        for (int key : map.keySet()) {
            if (key == id) {
                if (!map.get(key).status()) {
                    map.get(key).status(true);
                }
            }
        }            
    }

	public void enable() {
		for (int key : map.keySet()) {
			if (!map.get(key).status()) {
				map.get(key).status(true);
			}
		} 
	}

    public void disable(int id) throws Exception {       
        for (int key : map.keySet()) {
            if (key == id) {
                if (map.get(key).status()) {
                    map.get(key).status(false);
                }
            }
        }        
    }

	public void disable() {
		for (int key : map.keySet()) {         
			if (map.get(key).status()) {
				map.get(key).status(false);
			}
        }
	}

    public void delete(int id) throws Exception {       
        for (int key : map.keySet()) {
            if (key == id) {                
                map.remove(key); break;
            }
        }        
    }

	public void delete() {		
		map.clear();
	}

    public Map<Integer, Entry> get() {
        return map;
    }

    public Map<Integer, Entry> prepares() {        
        Map<Integer, Entry> map = new HashMap<Integer, Entry>();
        for (int key : this.map.keySet()) {
			Entry entry = this.map.get(key);
            if (entry instanceof PrepareEntry) {
                map.put(key, entry);
            }
        }
        return map;
    }

    public Map<Integer, Entry> unloads() {
		Map<Integer, Entry> map = new HashMap<Integer, Entry>();
        for (int key : this.map.keySet()) {
			Entry entry = this.map.get(key);
            if (entry instanceof UnloadEntry) {
                map.put(key, (Entry)entry);
            }
        }        
        return map;        
    }	
	
	public static class Entry {

		public Entry(String clazz, boolean status, RoutineNode routine) {			
			this.clazz = clazz;			
			this.status = status;
			this.routine = routine;
		}		
		
		private String clazz = null;

		public void clazz(String clazz) {
			this.clazz = clazz;
		}

		public String clazz() {
			return clazz;
		}

		private RoutineNode routine = null;

		public void routine(RoutineNode routine) {
			this.routine = routine;
		}

		public RoutineNode routine() {
			return routine;
		}

		private boolean status = false;

		public void status(boolean status) {
			this.status = status;
		}

		public boolean status() {
			return status;
		}
	}

	public static class PrepareEntry extends Entry {

		public PrepareEntry(String clazz, boolean status, RoutineNode routine) {
			super(clazz, status, routine);
		}
	}

	public static class UnloadEntry extends Entry {

		public UnloadEntry(String clazz, boolean status, RoutineNode routine) {
			super(clazz, status, routine);
		}
	}
	
	private ClassPrepareRequest prepare = null;
	private ClassUnloadRequest unload = null;

	@Override
	public void monitor(boolean flag) {
		if (flag && (null == prepare && null == unload)) {
			prepare = RequestManager.instance().createClassPrepareRequest(null, EventRequest.SUSPEND_EVENT_THREAD);
			unload = RequestManager.instance().createClassUnloadRequest(null, EventRequest.SUSPEND_EVENT_THREAD);
		} else if (!flag && (null != prepare && null != unload)) {
			RequestManager.instance().deleteEventRequest(prepare); prepare = null;
			RequestManager.instance().deleteEventRequest(unload); unload = null;
		}
    }
	
	@Override
    public boolean need(Event event) {
        if (prepare == event.request()) {
			String name = ((ClassPrepareEvent)event).referenceType().name();
			for (Integer key : map.keySet()) {
				Entry entry = map.get(key);
				if (entry instanceof PrepareEntry) {
					if (Pattern.compile(entry.clazz()).matcher(name).matches()) {						
						if (null != entry.routine()) {
							event.request().putProperty(Command.ROUTINE, entry.routine());
						}
						return super.need(event);
					}
				}
			}
			return !super.need(event);
		} else if (unload == event.request()) {
			String name = ((ClassUnloadEvent)event).className();
			for (Integer key : map.keySet()) {
				Entry entry = map.get(key);
				if (entry instanceof UnloadEntry) {
					if (Pattern.compile(entry.clazz()).matcher(name).matches()) {
						if (null != entry.routine()) {
							event.request().putProperty(Command.ROUTINE, entry.routine());
						}
						return super.need(event);
					}
				}
			}
			return !super.need(event);
		}
		return super.need(event);
    }	
}
