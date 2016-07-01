package com.runbox.debug.command.machine;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.MachineManager;
import com.sun.jdi.VirtualMachine;

/**
 * Created by qstesiro
 */
public class MachineAbilityCommand extends Command {

    public MachineAbilityCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        int index = 0;
        VirtualMachine machine = MachineManager.instance();
        System.out.println("index\tability");
        System.out.println(index++ + "\taddMethod: " + machine.canAddMethod());
        System.out.println(index++ + "\tbeModified: " + machine.canBeModified());
        System.out.println(index++ + "\tgetBytecode: " + machine.canGetBytecodes());
        System.out.println(index++ + "\tgetCurrentContendedMonitor: " + machine.canGetCurrentContendedMonitor());
        System.out.println(index++ + "\tgetMonitorInfo: " + machine.canGetMonitorFrameInfo());
        System.out.println(index++ + "\tgetOwnedMonitorInfo: " + machine.canGetOwnedMonitorInfo());
        System.out.println(index++ + "\tgetSourceDebugExExtension: " + machine.canGetSourceDebugExtension());
        System.out.println(index++ + "\tgetSyntheticAttribute: " + machine.canGetSyntheticAttribute());
        System.out.println(index++ + "\tpopFrames: " + machine.canPopFrames());
        System.out.println(index++ + "\tredefineClasses: " + machine.canRedefineClasses());
        System.out.println(index++ + "\trequestVMDeathEvent: " + machine.canRequestVMDeathEvent());
        System.out.println(index++ + "\tunrestrictedlyRedefineClasses: " + machine.canUnrestrictedlyRedefineClasses());
        System.out.println(index++ + "\tuseInstanceFilters: " + machine.canUseInstanceFilters());
        System.out.println(index++ + "\twatchFieldAccess: " + machine.canWatchFieldAccess());
        System.out.println(index++ + "\twatchFieldModification: " + machine.canWatchFieldModification());
        return super.execute();
    }

    @Override
    public void help() {
        String help = "\r\n";
        help += "description\r\n";
        help += "";
        help += "note";
        help += "";
        help += "example";
        help += "";
        System.out.println(help);
    }
}
