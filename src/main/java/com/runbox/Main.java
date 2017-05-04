package com.runbox;

import com.runbox.debug.Debugger;
import com.runbox.manager.ConfigManager;

public class Main {

    public static void main(String[] args) {
        try {
            ConfigManager.instance().set(args);
            Debugger.instance().debug();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}