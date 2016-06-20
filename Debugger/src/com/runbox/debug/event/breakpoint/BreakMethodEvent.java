package com.runbox.debug.event.breakpoint;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.SourceManager;
import com.runbox.debug.event.Event;
import com.sun.jdi.event.BreakpointEvent;

import java.io.File;
import java.util.Map;

/**
 * Created by qstesiro
 */
public class BreakMethodEvent extends Event<BreakpointEvent> {

    public BreakMethodEvent(BreakpointEvent event) {
        super(event);
        ContextManager.instance().thread(event.thread());
    }

    /*Dalvik 中对于方法断点中有bug应该是虚拟机的bug*/
    @Override
    public boolean handle() throws Exception {
        File file = SourceManager.instance().match(event.location());
        if (null != file) {
            Map<Integer, String> lines = SourceManager.instance().lines(event.location(), 0, 0);
            if (null != lines) {
                for (Integer key : lines.keySet()) {
                    System.out.println(lines.get(key) + "\t" + file.getName() + ":" + key);
                }
            }
        }
        return false;
    }
}
