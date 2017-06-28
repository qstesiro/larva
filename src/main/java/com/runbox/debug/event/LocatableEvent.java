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
		if (MachineManager.instance().hotspot()) {
			printHotspotCode(location);
		} else if (MachineManager.instance().dalvik()) {
			printDalvikCode(location);
		} else {
			throw new Exception("invalid vm");
		}
	}	

	private void printHotspotCode(Location location) throws Exception {
		int count = ConfigManager.instance().bytecode();
		if (0 < count && 0 < location.lineNumber()) {	
			Method method = location.method();
			try {
				com.runbox.clazz.reader.BytecodeReader reader = com.runbox.clazz.reader.ReaderFactory.create(
					method.bytecodes(),
					com.runbox.clazz.reader.ReaderFactory.create(
						location.declaringType().constantPool(),
						location.declaringType().constantPoolCount()));
				com.runbox.clazz.reader.BytecodeReader.Printer printer = reader.printer();
				List<com.runbox.clazz.entry.bytecode.Bytecode> codes = reader.get();
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

	private void printDalvikCode(Location location) throws Exception {
		int count = ConfigManager.instance().bytecode();
		if (0 < count && 0 < location.lineNumber()) {
			// MachineManager.instance().get().canGetBytecodes()
			// this is a bug in davlik
			// method.bytecode() will throw UnsupportedOperationException
			// though canGetBytecode() return true		
			Method method = location.method();
			try {				
				// com.runbox.dex.reader.BytecodeReader reader = com.runbox.dex.reader.ReaderFactory.create(
				// 	method.bytecodes(),
				// 	com.runbox.dex.reader.ReaderFactory.create("d:\\program\\maven\\larva\\classes.dex"));
				com.runbox.dex.reader.BytecodeReader reader = com.runbox.dex.reader.ReaderFactory.create(method.bytecodes(), null);
				com.runbox.dex.reader.BytecodeReader.Printer printer = reader.printer();
				List<com.runbox.dex.entry.bytecode.Bytecode> codes = reader.get();
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
