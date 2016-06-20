package com.runbox.debug.event.clazz;

import com.runbox.debug.event.Event;
import com.sun.jdi.event.ClassPrepareEvent;

/**
 * Created by huangmengmeng01 on 2016/4/29.
 */
public class ClassLoadEvent extends Event<ClassPrepareEvent> {

    public ClassLoadEvent(ClassPrepareEvent event) {
        super(event);
    }

    @Override
    public boolean handle() throws Exception {
        return super.handle();
    }

}
