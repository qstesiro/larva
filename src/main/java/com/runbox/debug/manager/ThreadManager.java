package com.runbox.debug.manager;

import com.sun.jdi.request.ThreadStartRequest;
import com.sun.jdi.request.ThreadDeathRequest;

public class ThreadManager extends Manager {

    private ThreadManager() {
        
    }

    private static ThreadManager instance = new ThreadManager();

    public static ThreadManager instance() {
        return instance;
    }
    
    private ThreadStartRequest startup = null;

    public void startup(ThreadStartRequest request) {
        startup = request;
    }

    public ThreadStartRequest startup() {
        return startup;
    }
    
    private ThreadDeathRequest death = null;

    public void death(ThreadDeathRequest request) {
        death = request;
    }

    public ThreadDeathRequest death() {
        return death;
    }
}
