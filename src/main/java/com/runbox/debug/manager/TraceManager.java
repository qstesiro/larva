package com.runbox.debug.manager;

import java.io.File;
import java.io.PrintStream;
import java.io.FileOutputStream;

import com.sun.jdi.VirtualMachine;

public class TraceManager extends Manager {

	private TraceManager() {
		error = System.err;
	}

	private static TraceManager instance = new TraceManager();

	public static TraceManager instance() {
		return instance;
	}	

	private PrintStream error = null;
	
	public void clean() throws Exception {
		if (null != error) {
			System.setErr(error);
		}
		if (null != redirect) {
			redirect.close();
			redirect = null;
		}
    }
	
	// VirtualMachine.TRACE_ALL      16777215 -> 0x1f
	// VirtualMachine.TRACE_NONE     0x00
	// VirtualMachine.TRACE_SENDS    0x01
	// VirtualMachine.TRACE_RECEIVES 0x02	
	// VirtualMachine.TRACE_EVENTS   0x04
	// VirtualMachine.TRACE_REFTYPES 0x08
	// VirtualMachine.TRACE_OBJREFS  0x10

	public int mode = VirtualMachine.TRACE_NONE;	
	
	public void mode(int mode) {				
		if (0x1f <= mode) mode = VirtualMachine.TRACE_ALL;
		MachineManager.get().setDebugTraceMode(mode);
		this.mode = mode;
	}

	public int mode() {
		if (VirtualMachine.TRACE_ALL == mode) mode = 0x1f;
		return mode;
	}

	private File file = null;

	public void redirect(String path) throws Exception {
		if (null != path) {			
			file = new File(path);
			if (file.exists()) file.delete();			
			if (file.createNewFile()) {
				if (file.isFile()) {
					if (null != redirect) redirect.close();					
					redirect = new RedirectStream(file);
					if (null != redirect) {					
						System.setErr(redirect); return;
					}
				}
			}
		}
		throw new Exception("invalid path");
	}

	public String path() {
		return file.getAbsolutePath();
	}
	
	private RedirectStream redirect = null;
	
	private class RedirectStream extends PrintStream {

		public RedirectStream(File file) throws Exception {
			super(file);
			if (null != file) {				
				stream = new PrintStream(new FileOutputStream(file));
				return;
			}
			throw new Exception("invalid file");
		}

		private PrintStream stream = null;
		
		public void write(byte[] data, int offset, int size) {
			if (null != stream) {
				stream.write(data, offset, size);
			}
		}

		public void flush() {
			if (null != stream) {
				stream.flush();
			}
		}
	}
}
