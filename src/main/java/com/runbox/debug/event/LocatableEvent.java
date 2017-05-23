package com.runbox.debug.event;

import java.util.List;
import java.util.Map;

import com.sun.jdi.Location;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.Method;

import com.runbox.debug.event.Event;
import com.runbox.debug.manager.ContextManager;
import com.runbox.debug.manager.SourceManager;
import com.runbox.debug.manager.MachineManager;

import com.runbox.clazz.reader.ReaderFactory;
import com.runbox.clazz.reader.BytecodeReader;
import com.runbox.clazz.entry.constant.*;
import com.runbox.clazz.entry.bytecode.Bytecode;

public class LocatableEvent<T extends com.sun.jdi.event.LocatableEvent> extends Event<T> {

    public LocatableEvent(T event) {
        super(event);
    }

    @Override
    public boolean handle() throws Exception {		
		printCode(event().location());
		printLine(event().location());		
        return super.handle();
    }	
	
	private void printLine(Location location) throws Exception {
		String line = SourceManager.instance().line(location);
		if (null != line) {
			System.out.printf("%-4d%s\n", location.lineNumber(), line);
		}
	}

	private void printCode(Location location) throws Exception {
		Method method = location.method();
		BytecodeReader reader = ReaderFactory.create(method.bytecodes(), 
													 ReaderFactory.create(location.declaringType().constantPool(),
																		  location.declaringType().constantPoolCount()));
		reader.printer().print(reader.get(location.codeIndex()));
	}	
}
