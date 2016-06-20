package com.runbox.debug.manager;

import com.sun.jdi.VirtualMachine;

/**
 * Created by qstesiro
 */
public class MachineManager extends Manager {

    private MachineManager() {

    }

    private static MachineManager manager = new MachineManager();

    public static VirtualMachine instance() {
        return manager.machine;
    }

    private VirtualMachine machine = null;

    public static void set(VirtualMachine machine) {
        manager.machine = machine;
    }
}
