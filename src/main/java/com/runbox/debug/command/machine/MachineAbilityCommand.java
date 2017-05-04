package com.runbox.debug.command.machine;

import com.sun.jdi.VirtualMachine;

import com.runbox.debug.manager.MachineManager;

public class MachineAbilityCommand extends MachineCommand {

    public MachineAbilityCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        int index = 0; VirtualMachine machine = MachineManager.instance().get();
        System.out.printf("%-5s%-32s%s\n", "#", "name", "status");
        System.out.printf("%-5d%-32s%b\n", index++, "addMethod", machine.canAddMethod());
        System.out.printf("%-5d%-32s%b\n", index++, "beModified", machine.canBeModified());
        System.out.printf("%-5d%-32s%b\n", index++, "getBytecode", machine.canGetBytecodes());
        System.out.printf("%-5d%-32s%b\n", index++, "getCurrentContendedMonitor", machine.canGetCurrentContendedMonitor());
        System.out.printf("%-5d%-32s%b\n", index++, "getMonitorInfo", machine.canGetMonitorFrameInfo());
        System.out.printf("%-5d%-32s%b\n", index++, "getOwnedMonitorInfo", machine.canGetOwnedMonitorInfo());
        System.out.printf("%-5d%-32s%b\n", index++, "getSourceDebugExExtension", machine.canGetSourceDebugExtension());
        System.out.printf("%-5d%-32s%b\n", index++, "getSyntheticAttribute", machine.canGetSyntheticAttribute());
        System.out.printf("%-5d%-32s%b\n", index++, "popFrames", machine.canPopFrames());
        System.out.printf("%-5d%-32s%b\n", index++, "redefineClasses", machine.canRedefineClasses());
        System.out.printf("%-5d%-32s%b\n", index++, "requestVMDeathEvent", machine.canRequestVMDeathEvent());
        System.out.printf("%-5d%-32s%b\n", index++, "unrestrictedlyRedefineClasses", machine.canUnrestrictedlyRedefineClasses());
        System.out.printf("%-5d%-32s%b\n", index++, "useInstanceFilters", machine.canUseInstanceFilters());
        System.out.printf("%-5d%-32s%b\n", index++, "watchFieldAccess", machine.canWatchFieldAccess());
        System.out.printf("%-5d%-32s%b\n", index++, "watchFieldModification", machine.canWatchFieldModification());
        return super.execute();
    }
}
