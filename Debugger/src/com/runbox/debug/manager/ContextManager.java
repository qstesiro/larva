package com.runbox.debug.manager;

import com.runbox.debug.command.Command;
import com.runbox.debug.parser.Parser;
import com.runbox.debug.parser.statement.node.BlockNode;
import com.sun.jdi.*;
import com.sun.jdi.event.Event;
import com.sun.jdi.request.EventRequest;

import java.io.File;
import java.util.List;


/**
 * Created by huangmengmeng01 on 2016/4/20.
 */
public class ContextManager extends Manager {

    private ContextManager() {

    }

    private static ContextManager manager = new ContextManager();

    public static ContextManager instance() {
        return manager;
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

    private Parser parser = null;

    public void parser(File file) throws Exception {
        parser = new Parser(file);
    }

    public void parser(String commands) throws Exception {
        parser = new Parser(commands);
    }

    public void mount(Event event) {
        EventRequest request = event.request();
        if (null != request.getProperty(Command.BLOCK)) {
            parser.mount((BlockNode)request.getProperty(Command.BLOCK));
        }
    }

    public Parser parser() {
        return parser;
    }

    private class Context {
        private ThreadReference thread = null;
        private StackFrame frame = null;
        private Parser parser = null;
    }

    private Context context = new Context();

    public void store() {
        context.thread = thread;
        context.frame = frame;
        context.parser = parser;
    }

    public void restore() {
        thread = context.thread;
        frame = context.frame;
        parser = context.parser;
    }
}
