package com.runbox.debug;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.ListeningConnector;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by huangmengmeng01 on 2015/9/29.
 */
public class Connector {

    private Connector() {

    }

    public static void printAllConnector() {
        System.out.println("allConnector");
        List<com.sun.jdi.connect.Connector> list = Bootstrap.virtualMachineManager().allConnectors();
        for (com.sun.jdi.connect.Connector connector : list) {
            System.out.println(connector.name() + "/" + connector.description());
        }
    }

    public static void printDefaultConnector() {
        System.out.println("defaultConnector");
        com.sun.jdi.connect.LaunchingConnector connector = Bootstrap.virtualMachineManager().defaultConnector();
        System.out.println(connector.name() + "/" + connector.description());
        printDefaultArgument(connector.defaultArguments());
    }

    public static void printLaunchingConnector() {
        System.out.println("launchingConnector");
        List<com.sun.jdi.connect.LaunchingConnector> list = Bootstrap.virtualMachineManager().launchingConnectors();
        for (LaunchingConnector connector : list) {
            System.out.println(connector.name() + "/" + connector.description());
        }
    }

    public static void printAttachingConnector() {
        System.out.println("attachingConnector");
        List<AttachingConnector> list = Bootstrap.virtualMachineManager().attachingConnectors();
        for (AttachingConnector connector : list) {
            System.out.println(connector.name() + "/" + connector.description());
            printDefaultArgument(connector.defaultArguments());
        }
    }

    public static void printListeningConnector() {
        System.out.println("listeningConnector");
        List<ListeningConnector> list = Bootstrap.virtualMachineManager().listeningConnectors();
        for (ListeningConnector connector : list) {
            System.out.println(connector.name() + "/" + connector.description());
        }
    }

    public static void printDefaultArgument(Map<String, com.sun.jdi.connect.Connector.Argument> map) {
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            com.sun.jdi.connect.Connector.Argument argument = (com.sun.jdi.connect.Connector.Argument)entry.getValue();
            System.out.println(entry.getKey() + "/" + argument.description() + "/" + argument.value());
        }
    }
}
