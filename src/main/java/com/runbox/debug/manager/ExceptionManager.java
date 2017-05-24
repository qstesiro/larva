package com.runbox.debug.manager;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sun.jdi.ClassType;
import com.sun.jdi.Field;
import com.sun.jdi.Location;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.StackFrame;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.ExceptionRequest;

import com.runbox.debug.script.expression.token.operand.FieldOperand;
import com.runbox.debug.script.expression.token.operand.Operand;

public class ExceptionManager extends Manager {

    private ExceptionManager() {

    }

    private static ExceptionManager instance = new ExceptionManager();

    public static ExceptionManager instance() {
        return instance;
    }

	public void clean() throws Exception {		
		delete();		
	}

	private Map<Integer, ExceptionRequest> requests = new HashMap<Integer, ExceptionRequest>();

	public Map<Integer, ExceptionRequest> requests() {
		return requests;
	}

	public void append(ReferenceType type, boolean caught, boolean uncaught) {
		for (int id : requests.keySet()) {
			if (requests.get(id).exception().name().equals(type.name())) {
				return;
			}
		}
		requests.put(id(), RequestManager.instance().createExceptionRequest(type, caught, uncaught));
	}

	public void delete(int id) {
		if (requests.containsKey(id)) {
			RequestManager.instance().deleteEventRequest(requests.get(id));
			requests.remove(id);
		}
	}

	public void delete() {
		for (int id : requests.keySet()) {
			RequestManager.instance().deleteEventRequest(requests.get(id));
		}
		requests.clear();
	}
	
    private ExceptionRequest request = null;

    @Override
    public void monitor(boolean flag) {
        if (flag && null == request) {
            request = RequestManager.instance().createExceptionRequest(null, false, true);
        } else if (!flag && null != request) {
            RequestManager.instance().deleteEventRequest(request); request = null;
        }
    }

    @Override
    public boolean need(Event event) {
        if (event instanceof ExceptionEvent) {
            if (request == event.request()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handle(Event event) throws Exception {
		ExceptionEvent exception = (ExceptionEvent)event;        		
        String line = SourceManager.instance().line(exception.catchLocation());
		if (null != line) System.out.println(line);
        print(exception.exception());
        return !super.handle(event);
    }

    private void print(ObjectReference object) throws Exception {
        ClassType type = (ClassType)object.referenceType();
        System.out.println("catch exception -> " + type.name());
		ThreadReference thread = ContextManager.instance().thread();
        if (null != thread) {
			if (0 < thread.frameCount()) {
			    for (StackFrame frame : thread.frames()) {
					Location location = frame.location();
					System.out.printf("   at %s.%s :%d\n", location.declaringType().name(), location.method().name(), location.lineNumber());
				}
			}
        }        
    }    
}
