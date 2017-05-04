package com.runbox.debug.manager;

import java.io.File;
import java.util.List;

import com.sun.jdi.*;
import com.sun.jdi.event.Event;
import com.sun.jdi.request.EventRequest;

public class ContextManager extends Manager {

    private ContextManager() {

    }

    private static ContextManager instance = new ContextManager();

    public static ContextManager instance() {
        return instance;
    }

    private ThreadReference thread = null;

    public void thread(ThreadReference thread) {
        this.thread = thread;
        if (null != thread) {            
            try {
                List<StackFrame> frames = thread.frames();
                if (0 < frames.size()) {                    
                    frame = frames.get(0);
                }
            } catch (IncompatibleThreadStateException e) {
                e.printStackTrace();
            }
        } else {
            frame = null;
        }
    }

    public ThreadReference thread() {
        return thread;
    }

    private StackFrame frame = null;

    public void frame(StackFrame frame) {
        this.frame = frame;
    }

    public StackFrame frame() {
        return frame;
    }

    private class Context {
        private ThreadReference thread = null;
        private StackFrame frame = null;
    }

    private Context context = new Context();

    public void store() {
        context.thread = thread;
        context.frame = frame;
    }

    public void restore() {
        thread = context.thread;
        frame = context.frame;
    }
}
