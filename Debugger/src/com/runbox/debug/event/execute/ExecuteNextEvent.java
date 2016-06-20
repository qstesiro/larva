package com.runbox.debug.event.execute;

import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.event.Event;
import com.runbox.debug.manager.SourceManager;
import com.sun.jdi.event.StepEvent;

import java.io.File;
import java.util.Map;

/**
 * Created by huangmengmeng01 on 2016/4/27.
 */
public class ExecuteNextEvent extends Event<StepEvent> {

    public ExecuteNextEvent(StepEvent event) {
        super(event);
        ContextManager.instance().thread(event.thread());
    }

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
