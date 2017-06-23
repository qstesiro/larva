package com.runbox.debug.manager;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.StackFrame;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.ThreadDeathEvent;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.StepRequest;
import com.sun.jdi.request.ThreadDeathRequest;
import com.sun.jdi.request.MethodExitRequest;

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

    public void delete(ThreadReference thread) {
        if (null != thread) {
            for (ThreadReference key : map.keySet()) {
                if (key == thread) {
                    RequestManager.instance().deleteEventRequest(map.get(key));
                    map.remove(thread); break;
                }
            }
        }
    }

	public StepRequest get(ThreadReference thread) throws Exception {
		if (null != thread) {
			return map.get(thread);            
        }
        throw new Exception("invalid thread context");
	}    

	public static class ReturnEntry {

		public ReturnEntry(ThreadReference thread, ReferenceType type, String clazz, int frames, RoutineNode routine) {
			this.thread = thread;
			this.type = type;
			this.frames = frames;
			this.routine = routine;
		}
		
		// public ReturnEntry(ThreadReference thread, String clazz, int frames, RoutineNode routine) {
		// 	this.thread = thread;
		// 	this.clazz = clazz;
		// 	this.frames = frames;
		// 	this.routine = routine;
		// }

		private MethodExitRequest request = null;

		public void request(MethodExitRequest request) {
			this.request = request;
		}
		
		public MethodExitRequest request() {
			return request;
		}
		
		private ThreadReference thread = null;

		public ThreadReference thread() {
			return thread;
		}				

		private ReferenceType type = null;

		public ReferenceType type() {
			return type;
		}

		private String clazz = null;

		public String clazz() {
			return clazz;
		}
		
		private int frames = 0;

		public int frames() {
			return frames;
		}
		
		private RoutineNode routine = null;

		public RoutineNode routine() {
			return routine;
		}
	}
	
    private Map<ThreadReference, ReturnEntry> returns = new HashMap<ThreadReference, ReturnEntry>();
	
	public MethodExitRequest create(ThreadReference thread, ReturnEntry entry) throws Exception {
		if (null != thread) {		
            deleteReturn(thread);
			returns.put(thread, entry);
			entry.request(RequestManager.instance().createMethodExitRequest(entry));
            return entry.request();
		}
        throw new Exception("invalid thread context");
	}

	public void deleteReturn(ThreadReference thread) throws Exception {
		if (null != thread) {
			for (ThreadReference key : returns.keySet()) {
                if (key == thread) {
                    RequestManager.instance().deleteEventRequest(returns.get(key).request());
                    returns.remove(thread); break;
                }
            }
		}
	}

	public ReturnEntry getReturn(ThreadReference thread) throws Exception {
		if (null != thread) {
			for (ThreadReference key : returns.keySet()) {
                if (key == thread) {
                    return returns.get(key);                    
                }
            }
		}
		throw new Exception("invalid thread context");
	}

	public void clean(ThreadReference thread) throws Exception {
		if (null != thread) {
            delete(thread);
            deleteReturn(thread);
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
        } else if (event instanceof MethodExitEvent) {
			ReturnEntry entry = getReturn(((MethodExitEvent)event).thread());
			if (event.request() == entry.request()) {
				ThreadReference thread = ContextManager.instance().current();
				if (null != thread) {
					List<StackFrame> frames = thread.frames();
					if (null != frames) {
						if (frames.size() >= entry.frames()) {
							return super.need(event);
						}
					}
				}
			}		   
		}
        return !super.need(event);
    }

    @Override
    public boolean handle(Event event) throws Exception {
		if (event instanceof ThreadDeathEvent) {
			if (event.request() == request) {
				delete(((ThreadDeathEvent)event).thread());
				deleteReturn(((ThreadDeathEvent)event).thread());
			}
        }		
        return super.handle(event);
    }	
}
