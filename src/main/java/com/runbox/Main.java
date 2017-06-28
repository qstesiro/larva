package com.runbox;

import com.runbox.manager.ConfigManager;
import com.runbox.debug.Debugger;
import com.runbox.trace.Tracer;

import com.runbox.dex.reader.ReaderFactory;
import com.runbox.dex.reader.DexReader;

public class Main {

    public static void main(String[] args) {
        try {
            ConfigManager.instance().set(args);
			String mode = ConfigManager.instance().mode();
			if (null != mode) {
				if (mode.equals(ConfigManager.instance().MODE_DEBUG)) {
					Debugger.instance().debug();
				} else if (mode.equals(ConfigManager.instance().MODE_TRACE)) {
					Tracer.instance().trace();
				}
			}			
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
