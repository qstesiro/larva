package com.runbox.debug.manager;

import java.io.File;
import java.util.List;

import com.sun.jdi.*;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventIterator;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.event.VMStartEvent;
import com.sun.jdi.event.ThreadStartEvent;
import com.sun.jdi.event.ThreadDeathEvent;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.request.EventRequest;

public class ContextManager extends Manager {

    private ContextManager() {

    }

    private static ContextManager instance = new ContextManager();

    public static ContextManager instance() {
        return instance;
    }    	

	private Event event = null;

	public void event(Event event) {		
		this.event = event;		
		request = request(event);
		thread = current = thread(event);
		frame = frame(current);					
	}

	@Override
	public void clean() throws Exception {
		this.event = null;
		request = null;
		thread = null;
		frame = null;
	}
	
	public Event event() {
		return event;
	}

	private EventRequest request = null;

	public EventRequest request() {
		return request;
	}
	
	private ThreadReference thread = null;
	
    public ThreadReference thread() {
        return thread;
    }

	private ThreadReference current = null;

	public void current(ThreadReference current) {
		this.current = current; 
	}
	
	public ThreadReference current() {
		return current;
	}

    private StackFrame frame = null;

    public void frame(StackFrame frame) {
        this.frame = frame;
    }

    public StackFrame frame() {
        return frame;
    }

	private EventRequest request(Event event) {
		if (null != event) {
			return event.request();
		}
		return null;
	}
	
	private ThreadReference thread(Event event) {
		if (null != event) {			
			if (event instanceof LocatableEvent) {
				return ((LocatableEvent)event).thread();
			} else if (event instanceof VMStartEvent) {
				return ((VMStartEvent)event).thread();
			} else if (event instanceof ThreadStartEvent) {								
				return ((ThreadStartEvent)event).thread();
			} else if (event instanceof ThreadDeathEvent) {
				if (MachineManager.instance().name().toLowerCase().equals("dalvik")) {
					// we must return null
					// because it will throw (com.sun.jdi.InternalException: Unexpected JDWP Error: 15)
					// when you call ThreadReference.frameCount()
					return null;
				}
				return ((ThreadDeathEvent)event).thread();
			} else if (event instanceof ClassPrepareEvent) { // ClassUnloadEvent to my surprise
				return ((ClassPrepareEvent)event).thread();
			}
		}
		return null;
	}
	
	private StackFrame frame(ThreadReference thread) {
		if (null != thread) {
            try {
				if (0 < thread.frameCount()) return thread.frames().get(0);
			} catch (IncompatibleThreadStateException e) {}
        }
		return null;
	}
	
	private Event saveEvent = null;
	private EventRequest saveRequest = null;
	private ThreadReference saveThread = null;	
	private ThreadReference saveCurrent = null;
	private StackFrame saveFrame = null;
	
	public void store() {
		saveEvent = event;
		saveRequest = request;
		saveThread = thread;
		saveCurrent = current;
		saveFrame = frame;
	}
	
    public void restore() {
		event = saveEvent;
		request = saveRequest;
        thread = saveThread;
		current = saveCurrent;
		frame = saveFrame;
    }       
}
