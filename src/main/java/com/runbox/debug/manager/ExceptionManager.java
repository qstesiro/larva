package com.runbox.debug.manager;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.sun.jdi.ClassType;
import com.sun.jdi.Field;
import com.sun.jdi.Location;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.ExceptionRequest;

import com.runbox.debug.script.expression.token.operand.FieldOperand;
import com.runbox.debug.script.expression.token.operand.Operand;

public class ExceptionManager extends Manager {

    private ExceptionManager() {

    }

    private static ExceptionManager instance = new ExceptionManager();

    public static ExceptionManager instance() {
        return instance;
    }

    private ExceptionRequest request = null;

    @Override
    public void monitor(boolean flag) {
        if (flag) {
            request = RequestManager.instance().createExceptionRequest(null, false, true);            
        } else {
            RequestManager.instance().deleteEventRequest(request); request = null;
        }
    }

    @Override
    public boolean need(Event event) {
        if (event instanceof ExceptionEvent) {
            if (request == event.request()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handle(Event event) throws Exception {
        Location location = ((ExceptionEvent)event).location();
        File file = SourceManager.instance().find(location);
        if (null != file) {
            Map<Integer, String> lines = SourceManager.instance().lines(location, -10, 10);
            if (null != lines) {
                for (Integer key : lines.keySet()) {
                    System.out.println(key + " " + lines.get(key));
                }
            }
        }
        print(((ExceptionEvent)event).exception());
        return false;
    }

    private void print(ObjectReference object) throws Exception {
        ClassType type = (ClassType)object.referenceType();
        System.out.println(type.name());
        List<Field> fields = type.allFields();
        for (Field field : fields) {
            System.out.println(format(new FieldOperand(object, field.name())));
        }
    }

    private String format(Operand operand) throws Exception {
        String print = "";
        if (null != operand.value()) {
            if (null != operand.name()) {
                print += operand.name();
                print += " :" + operand.type().name();
                print += " = ";
            }
            print += operand.value();
            if (operand.value() instanceof ObjectReference) {
                print += " :" + operand.value().type().name();
            }
        } else {
            if (null != operand.name()) {
                print = operand.name();
            }
            print += " = " + "null";
        }
        return print;
    }
}
