package com.runbox.debug.manager;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.StackFrame;
import com.sun.jdi.Location;
import com.sun.jdi.AbsentInformationException;

import com.sun.jdi.event.Event;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.ThreadDeathEvent;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.ClassUnloadEvent;

import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.StepRequest;
import com.sun.jdi.request.ThreadDeathRequest;
import com.sun.jdi.request.MethodExitRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.ClassUnloadRequest;

import com.runbox.script.statement.node.RoutineNode;

public class ExecuteManager extends Manager {

    private ExecuteManager() {

    }

    private static ExecuteManager instance = new ExecuteManager();

    public static ExecuteManager instance() {
        return instance;
    }

    @Override
    public void clean() throws Exception {        
		for (ThreadReference key : map.keySet()) {
			RequestManager.instance().deleteEventRequest(map.get(key));
		} map.clear();        
		for (ThreadReference key : returns.keySet()) {			
			RequestManager.instance().deleteEventRequest(returns.get(key).request());							
		} returns.clear();
		for (ThreadReference key : gotos.keySet()) {			
			RequestManager.instance().deleteEventRequest(gotos.get(key).request());							
		} gotos.clear();
    }

    private Map<ThreadReference, StepRequest> map = new HashMap<ThreadReference, StepRequest>();	
	
    public StepRequest create(ThreadReference thread, int size, int depth, RoutineNode routine) throws Exception {
        if (null != thread) {			
			StepRequest request = RequestManager.instance().createStepRequest(thread, size, depth, routine);
            map.put(thread, request);
            return request;
        }
        throw new Exception("invalid thread context");
    }
	
    private Map<ThreadReference, ReturnBreakpoint> returns = new HashMap<ThreadReference, ReturnBreakpoint>();	
	
	public boolean append(ReturnBreakpoint breakpoint) throws Exception {
		if (!contain(breakpoint)) {		            
			returns.put(breakpoint.thread(), breakpoint);
		}
        return true;
	}

	public boolean contain(ReturnBreakpoint breakpoint) {
        for (ThreadReference key : returns.keySet()) {
			if (returns.get(key).equals(breakpoint)) {
				return true;
            }
        }
        return false;
    }
	
	private Map<ThreadReference, GotoBreakpoint> gotos = new HashMap<ThreadReference, GotoBreakpoint>();
	
	public boolean append(GotoBreakpoint breakpoint) {
        if (!contain(breakpoint)) {
			gotos.put(breakpoint.thread(), breakpoint);
			if (!breakpoint.solve()) {
				append(breakpoint.clazz());
			}
		}
        return true;
    }    
	
	public boolean contain(GotoBreakpoint breakpoint) {
        for (ThreadReference key : gotos.keySet()) {
			if (gotos.get(key).equals(breakpoint)) {
				return true;
            }
        }
        return false;
    }
	
	public Location find(Breakpoint breakpoint, ReferenceType type) {
		if (type.name().equals(breakpoint.clazz())) {
            try {
                List<Location> locations = type.locationsOfLine(breakpoint.line());
                for (Location location : locations) {
                    if (location.lineNumber() == breakpoint.line()) {                        
                        return location;
                    }
                }
            } catch (AbsentInformationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }	

	private Map<String, Entry> requests = new HashMap<String, Entry>();

    private void append(String clazz) {
        if (!requests.containsKey(clazz)) {
            requests.put(clazz, new Entry(RequestManager.instance().createClassPrepareRequest(clazz, null),
                                          RequestManager.instance().createClassUnloadRequest(clazz, null)));
        }
        requests.get(clazz).increase();
    }

    private void delete(String clazz) {
        if (requests.containsKey(clazz)) {
            Entry entry = requests.get(clazz);
            if (0 == entry.decrease()) {
                RequestManager.instance().deleteEventRequest(entry.prepare());
                RequestManager.instance().deleteEventRequest(entry.unload());
                requests.remove(entry);
            }
        }
    }   	
	
	public void clean(ThreadReference thread) throws Exception {
		if (null != thread) {
            StepRequest request = map.get(thread);
			if (null != request) {
				RequestManager.instance().deleteEventRequest(request);
				map.remove(thread);
			}
			Breakpoint breakpoint = returns.get(thread);
            if (null != breakpoint) {
				erase(breakpoint);
				returns.remove(breakpoint.thread());
			}			
			breakpoint = gotos.get(thread);
			if (null != breakpoint) {
				erase(breakpoint);
				gotos.remove(breakpoint.thread());
			}			
		}
	}

	private void erase(Breakpoint breakpoint) {
		if (null != breakpoint) {
			delete(breakpoint.clazz());
			RequestManager.instance().deleteEventRequest(breakpoint.request());
		}
	}

	private void clean(String clazz) throws Exception {		
		if (null != clazz) {
			if (0 < returns.size()) {
				Iterator<Map.Entry<ThreadReference, ReturnBreakpoint>> iterator = returns.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<ThreadReference, ReturnBreakpoint> entry = iterator.next();
					Breakpoint breakpoint = entry.getValue();
					if (breakpoint.clazz().equals(clazz)) {
						RequestManager.instance().deleteEventRequest(breakpoint.request());
						iterator.remove();
					}
				}
			}
			if (0 < gotos.size()) {
				Iterator<Map.Entry<ThreadReference, GotoBreakpoint>> iterator = gotos.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<ThreadReference, GotoBreakpoint> entry = iterator.next();
					Breakpoint breakpoint = entry.getValue();
					if (breakpoint.clazz().equals(clazz)) {
						erase(breakpoint);
						iterator.remove();
					}
				}
			}
		}
	}
	
    private ThreadDeathRequest request = null;

    @Override
    public void monitor(boolean flag) {
        if (flag && null == request) {
            request = RequestManager.instance().createThreadDeathRequest(null);
        } else if (!flag && null != request) {
            RequestManager.instance().deleteEventRequest(request); request = null;
        }
    }

    @Override
    public boolean need(Event event) throws Exception {
        if (event instanceof ThreadDeathEvent) {
			if (event.request() == request) {
				return super.need(event);
			}
        } else if (event instanceof ClassPrepareEvent) {
			String clazz = ((ClassPrepareEvent)event).referenceType().name();
			for (String key : requests.keySet()) {
				Entry entry = requests.get(key);
				if (event.request() == entry.prepare() && key.equals(clazz)) {
                    return super.need(event);
				}
            }            
        } else if (event instanceof ClassUnloadEvent) {
            String clazz = ((ClassUnloadEvent)event).className();
			for (String key : requests.keySet()) {
				Entry entry = requests.get(key);
				if (event.request() == entry.unload() && key.equals(clazz)) {
                    return super.need(event);
				}                
            }
        }        
        return !super.need(event);
    }

    @Override
    public boolean handle(Event event) throws Exception {
		if (event instanceof ThreadDeathEvent) {
			if (event.request() == request) {
			    clean(((ThreadDeathEvent)event).thread());
			}
        } else if (event instanceof ClassPrepareEvent) {
			ClassPrepareEvent prepare = (ClassPrepareEvent)event;
			for (ThreadReference key : gotos.keySet()) {
				GotoBreakpoint breakpoint = gotos.get(key);
				if (!breakpoint.solve()) {
					Location location = find(breakpoint, prepare.referenceType());
					if (null != location) {
						RequestManager.instance().createBreakpointRequest(location, breakpoint);
						breakpoint.solve(true);
					}
				}
			}			
		} else if (event instanceof ClassUnloadEvent) {
			clean(((ClassUnloadEvent)event).className());
		}		
        return super.handle(event);
    }

	public static class Breakpoint {

		public static String OBJECT = "3d7b627b-3251-40ef-86dd-55bde69dfca6";
		
		public Breakpoint(ThreadReference thread, String clazz, int line, RoutineNode routine) {
			this.thread = thread;
			this.clazz = clazz;
			this.line = line;
            this.routine = routine;
		}

		private ThreadReference thread = null;

		public ThreadReference thread() {
			return thread;
		}	   		

		private String clazz = null;

        public void clazz(String clazz) {
            this.clazz = clazz;
        }

        public String clazz() {
            return clazz;
        }

		private int line = 0;

        public void line(int line) {
            this.line = line;
        }

        public int line() {
            return line;
        }

		private EventRequest request = null;

        public void request(EventRequest request) {
            this.request = request;
        }

        public EventRequest request() {
            return request;
        }
		
		RoutineNode routine = null;

        public void routine(RoutineNode routine) {            
            this.routine = routine;			
        }

        public RoutineNode routine() {
            return routine;
        }

		public String location() {
            return clazz + ":" + line;
        }

        public boolean equals(Breakpoint breakpoint) {		    
            if (thread.uniqueID() == breakpoint.thread().uniqueID() &&
				clazz.equals(breakpoint.clazz) &&
				line == breakpoint.line) {
                return true;
            }
            return false;
        }
	}

	public static class ReturnBreakpoint extends Breakpoint {

		public ReturnBreakpoint(ThreadReference thread, String clazz, int line, RoutineNode routine) {
			super(thread, clazz, line, routine);
		}
	}

	public static class GotoBreakpoint extends Breakpoint {
		
		public GotoBreakpoint(ThreadReference thread, String clazz, int line, RoutineNode routine) {
			super(thread, clazz, line, routine);
		}

		private boolean solve = false;

        public void solve(boolean solve) {
            this.solve = solve;
        }

        public boolean solve() {
            return solve;
        }
	}

	private static class Entry {

        public Entry(ClassPrepareRequest prepare, ClassUnloadRequest unload) {
            this.prepare = prepare;
            this.unload = unload;
        }

        private int count = 0;

        public int increase() {
            return ++count;
        }

        public int decrease() {
            return --count;
        }   

        private ClassPrepareRequest prepare = null;

        public ClassPrepareRequest prepare() {
            return prepare;
        }

        private ClassUnloadRequest unload = null;

        public ClassUnloadRequest unload() {
            return unload;
        }
    }
}
