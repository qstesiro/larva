package com.runbox.debug.event;

import java.util.List;
import java.util.Map;

import com.sun.jdi.Location;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.Method;

import com.runbox.manager.ConfigManager;

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
		int count = Integer.valueOf(ConfigManager.instance().get(ConfigManager.LINE));
		if (0 < count) {
			Map<Integer, String> lines = SourceManager.instance().lines(location);
			if (null != lines && 0 < location.lineNumber()) {
				int start = location.lineNumber() - count / 2;
				for (int i = 0; i < count; ++i) {
					int number = start + i;
					if (null != lines.get(number)) {
						if (location.lineNumber() == number)
							System.out.printf("> %-4d%s\n", number, lines.get(number));
						else
							System.out.printf("  %-4d%s\n", number, lines.get(number));
					}
				}
			}
		}
	}

	private void printCode(Location location) throws Exception {
		int count = Integer.valueOf(ConfigManager.instance().get(ConfigManager.BYTECODE));
		if (0 < count) {
			// MachineManager.instance().get().canGetBytecodes()
			// this is a bug in davlik
			// method.bytecode() will throw UnsupportedOperationException
			// though canGetBytecode() return true		
			Method method = location.method();
			try {
				BytecodeReader reader = ReaderFactory.create(method.bytecodes(), 
															 ReaderFactory.create(location.declaringType().constantPool(),
																				  location.declaringType().constantPoolCount()));
				reader.printer().print(reader.get(location.codeIndex()));
			} catch (UnsupportedOperationException e) {}
		}
	}
}
