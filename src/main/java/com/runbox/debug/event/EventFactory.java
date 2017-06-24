package com.runbox.debug.event;

import com.sun.jdi.event.VMStartEvent;
import com.sun.jdi.event.VMDeathEvent;
import com.sun.jdi.event.VMDisconnectEvent;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.AccessWatchpointEvent;
import com.sun.jdi.event.ModificationWatchpointEvent;
import com.sun.jdi.event.StepEvent;

import com.sun.jdi.request.StepRequest;

import com.runbox.debug.manager.BreakpointManager;
import com.runbox.debug.manager.ExecuteManager;

import com.runbox.debug.event.breakpoint.BreakpointAccessEvent;
import com.runbox.debug.event.breakpoint.BreakpointLineEvent;
import com.runbox.debug.event.breakpoint.BreakpointMethodEvent;
import com.runbox.debug.event.breakpoint.BreakpointModifyEvent;
import com.runbox.debug.event.clazz.ClassPrepareEvent;
import com.runbox.debug.event.clazz.ClassUnloadEvent;
import com.runbox.debug.event.method.MethodEntryEvent;
import com.runbox.debug.event.method.MethodReturnEvent;
import com.runbox.debug.event.execute.ExecuteNextEvent;
import com.runbox.debug.event.execute.ExecuteStepEvent;
import com.runbox.debug.event.machine.MachineDeathEvent;
import com.runbox.debug.event.machine.MachineDisconnectEvent;
import com.runbox.debug.event.machine.MachineStartEvent;
import com.runbox.debug.event.thread.ThreadStartEvent;
import com.runbox.debug.event.thread.ThreadDeathEvent;
import com.runbox.debug.event.exception.ExceptionEvent;

public class EventFactory {

    public static Event build(com.sun.jdi.event.Event event) throws Exception {
        if (event instanceof VMStartEvent) {
            return new MachineStartEvent((VMStartEvent)event);
        } else if (event instanceof VMDeathEvent) {
            return new MachineDeathEvent((VMDeathEvent)event);
        } else if (event instanceof VMDisconnectEvent) {
            return new MachineDisconnectEvent((VMDisconnectEvent)event);
        } else if (event instanceof com.sun.jdi.event.ClassPrepareEvent) {
            return new ClassPrepareEvent((com.sun.jdi.event.ClassPrepareEvent)event);
        } else if (event instanceof com.sun.jdi.event.ClassUnloadEvent) {
            return new ClassUnloadEvent((com.sun.jdi.event.ClassUnloadEvent)event);
        } else if (event instanceof com.sun.jdi.event.MethodEntryEvent) {
            return new MethodEntryEvent((com.sun.jdi.event.MethodEntryEvent)event);
        } else if (event instanceof MethodExitEvent) {
            return new MethodReturnEvent((MethodExitEvent)event);
        } else if (event instanceof com.sun.jdi.event.ThreadStartEvent) {
            return new ThreadStartEvent((com.sun.jdi.event.ThreadStartEvent)event);
        } else if (event instanceof com.sun.jdi.event.ThreadDeathEvent) {
            return new ThreadDeathEvent((com.sun.jdi.event.ThreadDeathEvent)event);
        } else if (event instanceof BreakpointEvent) {
            return build((BreakpointEvent)event);
        } else if (event instanceof AccessWatchpointEvent) {
            return new BreakpointAccessEvent((AccessWatchpointEvent)event);
        } else if (event instanceof ModificationWatchpointEvent) {
            return new BreakpointModifyEvent((ModificationWatchpointEvent)event);
        } else if (event instanceof StepEvent) {
            return build((StepEvent)event);
        } else if (event instanceof com.sun.jdi.event.ExceptionEvent) {
			return new ExceptionEvent((com.sun.jdi.event.ExceptionEvent)event);
        }
        return new Event();
    }

    private static Event build(BreakpointEvent event) throws Exception {
        Object object = event.request().getProperty(BreakpointManager.Breakpoint.OBJECT);
        if (null != object) {
            if (object instanceof BreakpointManager.MethodBreakpoint) {				
                return new BreakpointMethodEvent(event);
            } else if (object instanceof BreakpointManager.LineBreakpoint) {
                return new BreakpointLineEvent(event);
            }			
        } else {			
			object = event.request().getProperty(ExecuteManager.Breakpoint.OBJECT);
			if (null != object) {				
				if (object instanceof ExecuteManager.Breakpoint) {
					return new BreakpointLineEvent(event);
				}
			}
		}		
        throw new Exception("invalid breakpoint event -> " + event.toString());
    }

    private static Event build(StepEvent event) throws Exception {
        StepRequest request = (StepRequest)event.request();
        if (StepRequest.STEP_OVER == request.depth()) {
            return new ExecuteNextEvent((StepEvent)event);
        } else if (StepRequest.STEP_INTO == request.depth()) {
            return new ExecuteStepEvent((StepEvent)event);
        }
        throw new Exception("invalid step event -> " + event.toString());
    }
}
