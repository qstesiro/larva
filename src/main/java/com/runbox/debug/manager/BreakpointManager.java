package com.runbox.debug.manager;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import com.sun.jdi.*;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.ClassUnloadEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.request.*;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.RequestManager;
import com.runbox.script.statement.node.RoutineNode;

public class BreakpointManager extends Manager {

    private BreakpointManager() {

    }

    private static BreakpointManager instance = new BreakpointManager();

    public static BreakpointManager instance() {
        return instance;
    }

    @Override
    public void clean() throws Exception {
        cleanBreakpoints();
        cleanRequests();
    }

    public void cleanBreakpoints() {
        if (0 < map.size()) {
            for (Integer key : map.keySet()) {
                if (null != map.get(key).request()) {
                    RequestManager.instance().deleteEventRequest(map.get(key).request());
                }
            }
            map.clear();
        }
    }

    public void cleanRequests() {
        if (0 < requests.size()) {
            for (String key : requests.keySet()) {
                Entry entry = requests.get(key);
                RequestManager.instance().deleteEventRequest(entry.prepare());
                RequestManager.instance().deleteEventRequest(entry.unload());
            }
            requests.clear();
        }
    }

    private Map<Integer, Breakpoint> map = new HashMap<Integer, Breakpoint>();    
    
    public boolean append(MethodBreakpoint breakpoint) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof MethodBreakpoint) {
                if (((MethodBreakpoint)map.get(key)).equals(breakpoint)) {
                    return true;
                }
            }
        }
        map.put(id(), breakpoint);
        append(breakpoint.clazz());
        return true;
    }

    public boolean append(LineBreakpoint breakpoint) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof LineBreakpoint) {
                if (((LineBreakpoint)map.get(key)).equals(breakpoint)) {
                    return true;
                }
            }
        }
        map.put(id(), breakpoint);
        append(breakpoint.clazz());
        return true;
    }

    public boolean append(AccessBreakpoint breakpoint) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof AccessBreakpoint) {
                if (((AccessBreakpoint)map.get(key)).equals(breakpoint)) {
                    return true;
                }
            }
        }
        map.put(id(), breakpoint);
        append(breakpoint.clazz());
        return true;
    }

    public boolean append(ModifyBreakpoint breakpoint) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof ModifyBreakpoint) {
                if (((ModifyBreakpoint)map.get(key)).equals(breakpoint)) {
                    return true;
                }
            }
        }
        map.put(id(), breakpoint);
        append(breakpoint.clazz());
        return true;
    }

    public boolean delete(int id) {
        for (Integer key : map.keySet()) {
            if (key == id) {                
                delete(map.get(key).clazz());
				erase(map.remove(key));                                 
                return true;
            }
        }
        return false;
    }

	public void delete() {
		for (Integer key : map.keySet()) {
            delete(map.get(key).clazz());
			erase(map.get(key));			
        }
		map.clear();
	}

    public void enable(int id) {
        for (Integer key : map.keySet()) {			
			if (key == id) {
				Breakpoint breakpoint = map.get(key);
				breakpoint.status(true); break;				
			}
        }
    }

    public void enable() {
        for (Integer key : map.keySet()) {
			Breakpoint breakpoint = map.get(key);
			breakpoint.status(true);			
        }
    }

    public void disable(int id) {
        for (Integer key : map.keySet()) {
			if (key == id) {
				Breakpoint breakpoint = map.get(key);
				breakpoint.status(false); break;				
			}
        }
    }

    public void disable() {
        for (Integer key : map.keySet()) {		
			Breakpoint breakpoint = map.get(key);
			breakpoint.status(false);			
        }
    }

    public boolean contain(MethodBreakpoint breakpoint) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof MethodBreakpoint) {
                if (((MethodBreakpoint)map.get(key)).equals(breakpoint)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contain(LineBreakpoint breakpoint) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof LineBreakpoint) {
                if (((LineBreakpoint)map.get(key)).equals(breakpoint)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contain(AccessBreakpoint breakpoint) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof AccessBreakpoint) {
                if (((AccessBreakpoint)map.get(key)).equals(breakpoint)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contain(ModifyBreakpoint breakpoint) {
        for (Integer key : map.keySet()) {
            if (map.get(key) instanceof ModifyBreakpoint) {
                if (((ModifyBreakpoint)map.get(key)).equals(breakpoint)) {
                    return true;
                }
            }
        }
        return false;
    }    
    
    public Location find(MethodBreakpoint breakpoint, ReferenceType type) {
		if (type.name().equals(breakpoint.clazz())) {            
            List<Method> methods = type.methods();
            for (Method method : methods) {                
                if (method.name().equals(breakpoint.method())) {                    
                    if (breakpoint.equals(method.argumentTypeNames())) {						
                        return method.location();
                    }
                }
            }
        }
        return null;
    }

    public Location find(LineBreakpoint breakpoint, ReferenceType type) {
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

    public Field find(AccessBreakpoint breakpoint, ReferenceType type) {        
		if (type.name().equals(breakpoint.clazz())) {
            List<Field> fields = type.allFields();
            for (Field field : fields) {
                if (field.name().equals(breakpoint.field())) {
                    return field;
                }
            }
        }
        return null;
    }

    public Field find(ModifyBreakpoint breakpoint, ReferenceType type) {
		if (type.name().equals(breakpoint.clazz())) {
            List<Field> fields = type.allFields();
            for (Field field : fields) {
                if (field.name().equals(breakpoint.field())) {
                    return field;
                }
            }
        }
        return null;
    }
    
    public void erase(Breakpoint breakpoint) {
        if (null != breakpoint && null != breakpoint.request()) {
            breakpoint.solve(false);            
            RequestManager.instance().deleteEventRequest(breakpoint.request());            
        }
    }

    public Map<Integer, Breakpoint> breakpoints() {
        return map;
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
	
    @Override
    public boolean need(Event event) {        
        if (event instanceof ClassPrepareEvent) {
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
        for (Integer id : map.keySet()) {
			Breakpoint breakpoint = map.get(id);
            if (!breakpoint.solve()) {
                if (breakpoint instanceof MethodBreakpoint) {
                    if (event instanceof ClassPrepareEvent) {
						ClassPrepareEvent prepare = (ClassPrepareEvent)event;
						if (!(prepare.referenceType() instanceof ArrayType)) {
							Location location = find((MethodBreakpoint)breakpoint, prepare.referenceType());
							if (null != location) {
								RequestManager.instance().createBreakpointRequest(location, breakpoint);
							}
						}
                    } else if (event instanceof ClassUnloadEvent) {
                        if (breakpoint.clazz().equals(((ClassUnloadEvent)event).className())) {
                            erase(breakpoint);
                        }
                    }
                } else if (breakpoint instanceof LineBreakpoint) {
                    if (event instanceof ClassPrepareEvent) {
						ClassPrepareEvent prepare = (ClassPrepareEvent)event;
						if (!(prepare.referenceType() instanceof ArrayType)) {
							Location location = find((LineBreakpoint)breakpoint, prepare.referenceType());
							if (null != location) {
								RequestManager.instance().createBreakpointRequest(location, breakpoint);								
							}
						}
                    } else if (event instanceof ClassUnloadEvent) {
                        if (breakpoint.clazz().equals(((ClassUnloadEvent)event).className())) {
                            erase(breakpoint);
                        }
                    }                    
                } else if (breakpoint instanceof AccessBreakpoint) {
                    if (event instanceof ClassPrepareEvent) {
						ClassPrepareEvent prepare = (ClassPrepareEvent)event;
						if (!(prepare.referenceType() instanceof ArrayType)) {
							Field field = find((AccessBreakpoint)breakpoint, prepare.referenceType());
							if (null != field) {
								RequestManager.instance().createBreakpointRequest(field, breakpoint);								
							}
						}
                    } else if (event instanceof ClassUnloadEvent) {
                        if (breakpoint.clazz().equals(((ClassUnloadEvent)event).className())) {
                            erase(breakpoint);
                        }
                    }                    
                } else if (breakpoint instanceof ModifyBreakpoint) {
                    if (event instanceof ClassPrepareEvent) {
						ClassPrepareEvent prepare = (ClassPrepareEvent)event;
						if (!(prepare.referenceType() instanceof ArrayType)) {
							Field field = find((ModifyBreakpoint)breakpoint, prepare.referenceType());
							if (null != field) {
								RequestManager.instance().createBreakpointRequest(field, breakpoint);								
							}
						}
                    } else if (event instanceof ClassUnloadEvent) {
                        if (breakpoint.clazz().equals(((ClassUnloadEvent)event).className())) {
                            erase(breakpoint);
                        }
                    }                    
                }
            }
        }
        return super.handle(event);
    }

    public static class Breakpoint {

        public static String OBJECT = "object";

        public Breakpoint(String clazz, RoutineNode routine) {
            this.clazz = clazz;        
            this.routine = routine;
        }        

        private boolean solve = false;

        public void solve(boolean solve) {
            this.solve = solve;
        }

        public boolean solve() {
            return solve;
        }        

		private boolean status = true;

		public void status(boolean status) {			
			this.status = status;
			if (solve && null != request) {
				request.setEnabled(status);
			}
		}

		public boolean status() {
			return status;			
		}		
		
        private String clazz = null;

        public void clazz(String clazz) {
            this.clazz = clazz;
        }

        public String clazz() {
            return clazz;
        }		

        private EventRequest request = null;

        public void request(EventRequest request) {
            this.request = request;
        }

        public EventRequest request() {
            return request;
        }

        public String location() {
            return null;
        }

        public boolean equals(Breakpoint breakpoint) {
            if (clazz.equals(breakpoint.clazz)) {
                return true;
            }
            return false;
        }

        RoutineNode routine = null;

        public RoutineNode routine(RoutineNode routine) {
            RoutineNode prev = this.routine;
            this.routine = routine;
            return prev;
        }

        public RoutineNode routine() {
            return routine;
        }
    }

    public static class MethodBreakpoint extends Breakpoint {

        public MethodBreakpoint(String clazz, String method, List<String> arguments, RoutineNode routine) {
            super(clazz, routine);
            this.method = method;
            this.arguments = arguments;
        }

        private String method = null;

        public void method(String method) {
            this.method = method;
        }

        public String method() {
            return method;
        }    

		@Override
        public String location() {        
            String str = clazz() + "." + method;
            str += "(";
            for (int i = 0; i < arguments.size(); ++i) {
                if (0 < i) str += ", ";
                str += arguments.get(i);            
            }
            str += ")";
            return str;
        }
        
        private List<String> arguments = new LinkedList<String>();

        public void arguments(List<String> arguments) {
            this.arguments = arguments;
        }

        public List<String> arguments() {
            return arguments;
        }    

        public boolean equals(MethodBreakpoint breakpoint) {
            if (super.equals(breakpoint) && method.equals(breakpoint.method)) {
                return equals(breakpoint.arguments());
            }
            return false;
        }

        public boolean equals(List<String> arguments) {
            if (this.arguments.size() == arguments.size()) {
                for (int i = 0; i < arguments.size(); ++i) {
                    if (!this.arguments.get(i).equals(arguments.get(i))) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    }

    public static class LineBreakpoint extends Breakpoint {

        public LineBreakpoint(String clazz, int line, RoutineNode routine) {
            super(clazz, routine);
            this.line = line;
        }

        private int line = 0;

        public void line(int line) {
            this.line = line;
        }

        public int line() {
            return line;
        }

		@Override
        public String location() {
            return clazz() + ":" + line;
        }

        public boolean equals(LineBreakpoint breakpoint) {
            if (super.equals(breakpoint) && line == breakpoint.line) {
                return true;
            }
            return false;
        }
    }

    public static class AccessBreakpoint extends Breakpoint {

        public AccessBreakpoint(String clazz, String field, RoutineNode routine) {
            super(clazz, routine);
            this.field = field;
        }

        private String field = null;

        public void field(String field) {
            this.field = field;
        }

        public String field() {
            return field;
        }

		@Override
        public String location() {
            return clazz() + "." + field;
        }

        public boolean equals(AccessBreakpoint breakpoint) {
            if (super.equals(breakpoint) && field.equals(breakpoint.field)) {
                return true;
            }
            return false;
        }
    }

    public static class ModifyBreakpoint extends Breakpoint {

        public ModifyBreakpoint(String clazz, String field, RoutineNode routine) {
            super(clazz, routine);
            this.field = field;
        }

        private String field = null;

        public void field(String field) {
            this.field = field;
        }

        public String field() {
            return field;
        }

		@Override
        public String location() {
            return clazz() + "." + field;
        }

        public boolean equals(ModifyBreakpoint breakpoint) {
            if (super.equals(breakpoint) && field.equals(breakpoint.field)) {
                return true;
            }
            return false;
        }
    }
}
