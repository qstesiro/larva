package com.runbox.debug.command.breakpoint;

import java.util.Map;

import com.runbox.debug.manager.BreakpointManager;
import com.runbox.debug.command.breakpoint.BreakpointCommand;

public class BreakpointQueryCommand extends BreakpointCommand {

    public BreakpointQueryCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        print(BreakpointManager.instance().breakpoints());
        return super.execute();
    }

    private void print(Map<Integer, BreakpointManager.Breakpoint> breakpoints) throws Exception {
		System.out.printf("%-5s%-5s%-8s%-8s%-8s%s\n",
						  "#", "id", "type", "solve", "status", "location");
        int i = 0; for (Integer id : breakpoints.keySet()) {
			BreakpointManager.Breakpoint breakpoint = breakpoints.get(id);
			System.out.printf("%-5d%-5d%-8s%-8b%-8b%s\n",
							  i++, id,
							  type(breakpoint),
							  breakpoint.solve(),
							  breakpoint.status(),
							  breakpoint.location());
        }
    }

    private String type(BreakpointManager.Breakpoint breakpoint) throws Exception {
        if (breakpoint instanceof BreakpointManager.MethodBreakpoint) {
			return "method";
		} else if (breakpoint instanceof BreakpointManager.LineBreakpoint) {
			return "line";
		} else if (breakpoint instanceof BreakpointManager.AccessBreakpoint) {
			return "access";
		} else if (breakpoint instanceof BreakpointManager.ModifyBreakpoint) {
			return "modify";
		} else {
			throw new Exception("invalid breakpoint type");
		}
    }
}
