package com.runbox.debug.command.thread;

import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.ThreadDeathRequest;

import com.runbox.debug.command.thread.ThreadCommand;
import com.runbox.debug.manager.RequestManager;
import com.runbox.debug.manager.ThreadManager;

public class ThreadMonitorDeathCommand extends ThreadCommand {

    public ThreadMonitorDeathCommand(String command) throws Exception {
        super(command);
    }

     @Override
     public boolean execute() throws Exception {
		 String status = status();
         if (null != status) {
             if (status.equals("enable")) {
                 ThreadDeathRequest request = ThreadManager.instance().death();
                 if (null == request) {
                     ThreadManager.instance().death(RequestManager.instance().createThreadDeathRequest());
                 }
             } else if (status.equals("disable")) {
                 ThreadDeathRequest request = ThreadManager.instance().death();
                 if (null != request) {
                     RequestManager.instance().deleteEventRequest(request);
                     ThreadManager.instance().death(null);
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
