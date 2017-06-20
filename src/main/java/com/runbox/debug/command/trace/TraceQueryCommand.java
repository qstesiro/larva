package com.runbox.debug.command.trace;

import com.runbox.debug.manager.TraceManager;

public class TraceQueryCommand extends TraceCommand {

	public TraceQueryCommand(String command) throws Exception {
		super(command);
	}

	@Override
	public boolean execute() throws Exception {
		System.out.printf("mode: 0x%02x\n", TraceManager.instance().mode());
		System.out.printf("path: %s\n", (null != TraceManager.instance().path() ?
										 TraceManager.instance().path() :
										 "n/a"));
		return super.execute();
	}	
}
