package com.runbox.debug.command.clazz;

import java.util.List;
import java.util.LinkedList;
import java.util.regex.Pattern;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.ArrayType;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;

import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.manager.RequestManager;
import com.runbox.debug.manager.ClassManager;

public class ClassMonitorPrepareCommand extends ClassMonitorCommand {

    public ClassMonitorPrepareCommand(String command) throws Exception {
        super(command); 
    }

    @Override
    public boolean execute() throws Exception {
		String clazz = clazz();		
		if (null != clazz) {
			print(classes(clazz));
			ClassManager.instance().append(new ClassManager.PrepareEntry(clazz, true, routine()));
            return super.execute();
		} 
		throw new Exception("invalid arguement");				    
    }

    private List<String> classes(String clazz) throws Exception {
        List<String> classes = new LinkedList<String>();
        List<ReferenceType> types = MachineManager.instance().allClasses();
        for (ReferenceType type : types) {
			if (!(type instanceof ArrayType)) {
				if (Pattern.compile(clazz).matcher(type.name()).matches()) {
					classes.add(type.name());
				}
			}
        }
        return classes;
    }

    private void print(List<String> classes) {
        if (0 < classes.size()) {			
            System.out.println("#\tclass");
            for (int i = 0; i < classes.size(); ++i) {				
                System.out.printf("%d\t%s\n", i, classes.get(i));
            }
        }
    }
}
