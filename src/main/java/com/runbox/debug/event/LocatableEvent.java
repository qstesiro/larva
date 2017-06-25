package com.runbox.debug.event;

import java.util.List;
import java.util.Map;

import com.sun.jdi.Location;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.Method;
import com.sun.jdi.event.StepEvent;

import com.runbox.clazz.entry.bytecode.Bytecode;

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
		if (!(event() instanceof StepEvent)) {
			printCode(event().location());
			printLine(event().location());
		}
        return super.handle();
    }  

	protected void printCode(Location location) throws Exception {
		int count = ConfigManager.instance().bytecode();
		if (0 < count && 0 < location.lineNumber()) {
			// MachineManager.instance().get().canGetBytecodes()
			// this is a bug in davlik
			// method.bytecode() will throw UnsupportedOperationException
			// though canGetBytecode() return true		
			Method method = location.method();
			try {
				BytecodeReader reader = ReaderFactory.create(method.bytecodes(), 
															 ReaderFactory.create(location.declaringType().constantPool(),
																				  location.declaringType().constantPoolCount()));
				BytecodeReader.Printer printer = reader.printer();
				List<Bytecode> codes = reader.get();
				if (null != codes && 0 < codes.size()) {
					int current = codes.indexOf(reader.get(location.codeIndex()));					
					int start = current - count / 2;
					for (int i = 0; i < count; ++i) {
						int index = start + i;
						if (0 <= index && codes.size() > index) {
							if (null != codes.get(index)) {
								if (index == current) printer.prefix("> ");
								else printer.prefix("  ");													
								printer.print(codes.get(index));
							}
						}
					}
				}
			} catch (UnsupportedOperationException e) {}
		}
	}
	
	protected void printLine(Location location) throws Exception {
		int count = ConfigManager.instance().line();
		if (0 < count && 0 < location.lineNumber()) {
			Map<Integer, String> lines = SourceManager.instance().lines(location);
			if (null != lines && 0 < lines.size()) {
				int start = location.lineNumber() - count / 2;
				for (int i = 0; i < count; ++i) {
					int number = start + i;
					if (0 < number && lines.size() > number) {
						if (null != lines.get(number)) {
							if (location.lineNumber() == number) System.out.printf("> %-4d%s\n", number, lines.get(number));
							else System.out.printf("  %-4d%s\n", number, lines.get(number));
						}
					}
				}
			}
		}
	}	
}
