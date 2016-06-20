package com.runbox.debug.event;

import com.runbox.debug.manager.breakpoint.BreakPoint;
import com.runbox.debug.manager.breakpoint.LineBreakPoint;
import com.runbox.debug.manager.breakpoint.MethodBreakPoint;
import com.runbox.debug.event.breakpoint.BreakAccessEvent;
import com.runbox.debug.event.breakpoint.BreakLineEvent;
import com.runbox.debug.event.breakpoint.BreakMethodEvent;
import com.runbox.debug.event.breakpoint.BreakModifyEvent;
import com.runbox.debug.event.clazz.ClassLoadEvent;
import com.runbox.debug.event.clazz.ClassUnloadEvent;
import com.runbox.debug.event.execute.ExecuteNextEvent;
import com.runbox.debug.event.execute.ExecuteStepEvent;
import com.runbox.debug.event.machine.MachineDeathEvent;
import com.runbox.debug.event.machine.MachineDisconnectEvent;
import com.runbox.debug.event.machine.MachineStartEvent;
import com.sun.jdi.event.*;
import com.sun.jdi.request.StepRequest;


/**
 * Created by huangmengmeng01 on 2015/9/30.
 */
public class EventFactory {

    public static Event build(com.sun.jdi.event.Event event) {
        if (event instanceof com.sun.jdi.event.VMStartEvent) {
            return new MachineStartEvent((VMStartEvent) event);
        } else if (event instanceof com.sun.jdi.event.VMDeathEvent) {
            return new MachineDeathEvent((VMDeathEvent) event);
        } else if (event instanceof com.sun.jdi.event.VMDisconnectEvent) {
            return new MachineDisconnectEvent((VMDisconnectEvent) event);
        } else if (event instanceof ClassPrepareEvent) {
            return new ClassLoadEvent((ClassPrepareEvent) event);
        } else if (event instanceof com.sun.jdi.event.ClassUnloadEvent) {
            return new ClassUnloadEvent((com.sun.jdi.event.ClassUnloadEvent)event);
        } else if (event instanceof com.sun.jdi.event.MethodEntryEvent) {
        } else if (event instanceof com.sun.jdi.event.MethodExitEvent) {
        } else if (event instanceof com.sun.jdi.event.ThreadStartEvent) {
        } else if (event instanceof com.sun.jdi.event.ThreadDeathEvent) {
        } else if (event instanceof BreakpointEvent) {
            return buildBreak(event);
        } else if (event instanceof AccessWatchpointEvent) {
            return new BreakAccessEvent((AccessWatchpointEvent) event);
        } else if (event instanceof ModificationWatchpointEvent) {
            return new BreakModifyEvent((ModificationWatchpointEvent) event);
        } else if (event instanceof StepEvent) {
            return buildStep(event);
        } else if (event instanceof com.sun.jdi.event.ExceptionEvent) {
        }
        return new Event();
    }

    private static Event buildBreak(com.sun.jdi.event.Event event) {
        Object object = event.request().getProperty(BreakPoint.OBJECT);
        if (null != object) {
            if (object instanceof MethodBreakPoint) {
                return new BreakMethodEvent((BreakpointEvent)event);
            } else if (object instanceof LineBreakPoint) {
                return new BreakLineEvent((BreakpointEvent)event);
            }
        }
        return new Event();
    }

    private static Event buildStep(com.sun.jdi.event.Event event) {
        StepRequest request = (StepRequest)event.request();
        if (StepRequest.STEP_OVER == request.depth()) {
            return new ExecuteNextEvent((StepEvent)event);
        } else if (StepRequest.STEP_INTO == request.depth()) {
            return new ExecuteStepEvent((StepEvent)event);
        }
        return new Event();
    }
}
