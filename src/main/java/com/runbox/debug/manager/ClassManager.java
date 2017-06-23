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
                Entry entry = map.remove(key);
				RequestManager.instance().deleteEventRequest(entry.request());
				return;
            }
        }
    }

	public void delete() {		
		for (int key : map.keySet()) {
			Entry entry = map.get(key);
			RequestManager.instance().deleteEventRequest(entry.request());
        }
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
                map.put(key, entry);
            }
        }        
        return map;        
    }
	
	@Override
    public boolean need(Event event) throws Exception {
        if (event instanceof ClassPrepareEvent) {
			for (int key : map.keySet()) {
				Entry entry = map.get(key);
				if (entry instanceof PrepareEntry) {
					if (event.request() == entry.request()) {
						return super.need(event);
					}
				}
			}
		} else if (event instanceof ClassUnloadEvent) {
			for (int key : map.keySet()) {
				Entry entry = map.get(key);
				if (entry instanceof UnloadEntry) {
					if (event.request() == entry.request()) {
						return super.need(event);
					}
				}
			}			
		}
		return !super.need(event);
    }

	@Override
	public boolean handle(Event event) throws Exception {
		if (event instanceof ClassPrepareEvent) {
			String name = ((ClassPrepareEvent)event).referenceType().name();
			for (Integer key : map.keySet()) {
				Entry entry = map.get(key);
				if (entry instanceof PrepareEntry && entry.status()) {
					if (entry.clazz().equals(name)) {
						return !super.need(event);
					}
				}
			}
		} else if (event instanceof ClassUnloadEvent) {
			String name = ((ClassUnloadEvent)event).className();
			for (Integer key : map.keySet()) {
				Entry entry = map.get(key);
				if (entry instanceof UnloadEntry && entry.status()) {
					if (Pattern.compile(entry.clazz()).matcher(name).matches()) {
						return !super.need(event);
					}
				}
			}
		}
		return super.need(event);
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
			if (null != request) {
				request.setEnabled(status);
			}
		}

		public boolean status() {
			return status;
		}

		private EventRequest request = null;

		public void request(EventRequest request) {
			this.request = request;
		}

		public EventRequest request() {
			return request;
		}
	}

	public static class PrepareEntry extends Entry {
		
		public PrepareEntry(String clazz, boolean status, RoutineNode routine) {
			super(clazz, status, routine);
			request(RequestManager.instance().createClassPrepareRequest(clazz, routine));
		}		
	}

	public static class UnloadEntry extends Entry {

		public UnloadEntry(String clazz, boolean status, RoutineNode routine) {
			super(clazz, status, routine);
			request(RequestManager.instance().createClassUnloadRequest(clazz, routine));
		}
	}
}
