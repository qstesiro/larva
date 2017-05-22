package com.runbox.debug.command.machine;

import com.sun.jdi.VirtualMachine;

import com.runbox.debug.manager.MachineManager;

public class MachineAbilityCommand extends MachineCommand {

    public MachineAbilityCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        int i = 0; VirtualMachine machine = MachineManager.instance().get();
        System.out.printf("%-5s%-32s%s\n", "#", "name", "status");
        System.out.printf("%-5d%-32s%b\n", i++, "addMethod", machine.canAddMethod());
        System.out.printf("%-5d%-32s%b\n", i++, "beModified", machine.canBeModified());
		System.out.printf("%-5d%-32s%b\n", i++, "forceEarlyReturn", machine.canForceEarlyReturn());
        System.out.printf("%-5d%-32s%b\n", i++, "getBytecode", machine.canGetBytecodes());
		System.out.printf("%-5d%-32s%b\n", i++, "getClassFileVersion", machine.canGetClassFileVersion());
		System.out.printf("%-5d%-32s%b\n", i++, "getConstantPool", machine.canGetConstantPool());
        System.out.printf("%-5d%-32s%b\n", i++, "getCurrentContendedMonitor", machine.canGetCurrentContendedMonitor());
		System.out.printf("%-5d%-32s%b\n", i++, "getInstanceInfo", machine.canGetInstanceInfo());
		System.out.printf("%-5d%-32s%b\n", i++, "getMethodReturnValues", machine.canGetMethodReturnValues());			
        System.out.printf("%-5d%-32s%b\n", i++, "getMonitorInfo", machine.canGetMonitorFrameInfo());
		System.out.printf("%-5d%-32s%b\n", i++, "getMonitorInfo", machine.canGetMonitorInfo());
        System.out.printf("%-5d%-32s%b\n", i++, "getOwnedMonitorInfo", machine.canGetOwnedMonitorInfo());
        System.out.printf("%-5d%-32s%b\n", i++, "getSourceDebugExExtension", machine.canGetSourceDebugExtension());
        System.out.printf("%-5d%-32s%b\n", i++, "getSyntheticAttribute", machine.canGetSyntheticAttribute());
        System.out.printf("%-5d%-32s%b\n", i++, "popFrames", machine.canPopFrames());
        System.out.printf("%-5d%-32s%b\n", i++, "redefineClasses", machine.canRedefineClasses());
		System.out.printf("%-5d%-32s%b\n", i++, "requestMonitorEvents", machine.canRequestMonitorEvents());
        System.out.printf("%-5d%-32s%b\n", i++, "requestVMDeathEvent", machine.canRequestVMDeathEvent());
        System.out.printf("%-5d%-32s%b\n", i++, "unrestrictedlyRedefineClasses", machine.canUnrestrictedlyRedefineClasses());
        System.out.printf("%-5d%-32s%b\n", i++, "useInstanceFilters", machine.canUseInstanceFilters());
		System.out.printf("%-5d%-32s%b\n", i++, "useSourceNameFilters", machine.canUseSourceNameFilters());
        System.out.printf("%-5d%-32s%b\n", i++, "watchFieldAccess", machine.canWatchFieldAccess());
        System.out.printf("%-5d%-32s%b\n", i++, "watchFieldModification", machine.canWatchFieldModification());
        return super.execute();
    }
}
