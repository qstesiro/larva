package com.runbox.debug.command.thread;

import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.ThreadStartRequest;

import com.runbox.debug.command.thread.ThreadCommand;
import com.runbox.debug.manager.RequestManager;
import com.runbox.debug.manager.ThreadManager;

public class ThreadMonitorStartupCommand extends ThreadCommand {

    public ThreadMonitorStartupCommand(String command) throws Exception {
        super(command);
    }

     @Override
     public boolean execute() throws Exception {
		 String status = status();
         if (null != status) {
             if (status.equals("enable")) {
                 ThreadStartRequest request = ThreadManager.instance().startup();
                 if (null == request) {
                     ThreadManager.instance().startup(RequestManager.instance().createThreadStartRequest());
                 }
             } else if (status.equals("disable")) {
                 ThreadStartRequest request = ThreadManager.instance().startup();
                 if (null != request) {
                     RequestManager.instance().deleteEventRequest(request);
                     ThreadManager.instance().startup(null);
                 }
             } else {
                 throw new Exception("invalid command");
             }
         } else {
             if (null != ThreadManager.instance().death()) {
                 System.out.println("enable");
             } else {
                 System.out.println("disable");
             }
         }        
         return super.execute();
     }
}
