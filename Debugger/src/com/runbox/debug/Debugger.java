package com.runbox.debug;

import com.runbox.debug.event.*;
import com.runbox.debug.manager.*;
import com.sun.jdi.Bootstrap;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.connect.*;
import com.sun.jdi.event.*;
import com.sun.jdi.event.Event;
import com.sun.tools.javac.util.Pair;
import com.sun.tools.jdi.SocketAttachingConnector;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by qstesiro
 */

public class Debugger implements SignalHandler {

    public static void main(String[] arguments) {
        try {
            ArgumentManager.instance().set(arguments);
            Debugger.instance().debug();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Debugger() {

    }

    private static Debugger debugger = new Debugger();

    public static Debugger instance() {
        return debugger;
    }

    private void debug() {
        Signal.handle(new Signal("INT"), this);
        //launchVirtualMachine();
        attach();
        script();
        monitor(true);
        input();
        loop();
        monitor(false);
        exit();
    }

    private void script() {
        try {
            if (null != ArgumentManager.instance().get(ArgumentManager.SCRIPT)) {
                ContextManager.instance().parser(new File(ArgumentManager.instance().get(ArgumentManager.SCRIPT)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // adb shell am start -D -n "com.example.administrator.datareport/com.example.administrator.datareport.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
    /*java -classpath ".\out\production\Debugger;D:\Program Files\Java\jdk1.8.0_45\lib\tools.jar" com.runbox.debug.Debugger*/
    /*"D:\Program Files\Java\jdk1.8.0_45\bin\java"
            -agentlib:jdwp=transport=dt_socket,address=localhost:5005,server=y,suspend=y
           -classpath ".\out\production\Debugger;D:\Program Files\Java\jdk1.8.0_45\lib\tools.jar" com.runbox.debug.Debugger*/
    private void launchVirtualMachine() {
        LaunchingConnector connector = Bootstrap.virtualMachineManager().defaultConnector();
        Map<String, com.sun.jdi.connect.Connector.Argument> map = connector.defaultArguments();
        map.get("main").setValue("Demo");
        map.get("options").setValue("-classpath D:\\JetBrains\\IdeaProjects\\Demo\\out\\production\\Demo");
        try {
            MachineManager.set(connector.launch(map));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalConnectorArgumentsException e) {
            e.printStackTrace();
        } catch (VMStartException e) {
            e.printStackTrace();
        }
    }

    /*"D:\Program Files\Java\jdk1.8.0_45\bin\java" -agentlib:jdwp=transport=dt_socket,address=localhost:1025,server=y,suspend=y -classpath ".\out\production\Demo" Demo*/
    /*"D:\Program Files\Java\jdk1.8.0_45\bin\java" -agentlib:jdwp=transport=dt_socket,address=localhost:5005,server=y,suspend=y -classpath ".\out\production\Debugger;D:\Program Files\Java\jdk1.8.0_45\lib\tools.jar" com.runbox.debug.Debugger -address localhost:1025 -script D:\JetBrains\IdeaProjects\Demo\debug.jdb*/
    private void attach() {
        List<AttachingConnector> list = Bootstrap.virtualMachineManager().attachingConnectors();
        for (AttachingConnector connector : list) {
            if (connector instanceof SocketAttachingConnector) {
                try {
                    Pair<String, String> pair = ArgumentManager.instance().getAddress();
                    Map<String, com.sun.jdi.connect.Connector.Argument> map = connector.defaultArguments();
                    map.get("hostname").setValue(pair.fst);
                    map.get("port").setValue(pair.snd);
                    MachineManager.set(connector.attach(map));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalConnectorArgumentsException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private final static int TIMEOUT = 1000;

    private void loop() {
        EventQueue queue = MachineManager.instance().eventQueue();
        while (CONTINUE == Debugger.instance().flag()) {
            EventSet set = null;
            try {
                set = queue.remove(TIMEOUT);
                if (null != set) {
                    EventIterator iterator = set.eventIterator();
                    while (iterator.hasNext()) {
                        handle(iterator);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != set) {
                    set.resume();
                }
            }
        }
    }

    private void handle(EventIterator iterator) throws Exception {
        synchronized (this) {
            boolean flag = false;
            Event event = iterator.nextEvent();
            if (ClassManager.instance().need(event)) {
                flag = ClassManager.instance().handle(event);
            } else if (BreakManager.instance().need(event)) {
                flag = BreakManager.instance().handle(event);
            } else if (ExecuteManager.instance().need(event)) {
                flag = ExecuteManager.instance().handle(event);
            } else if (ExceptionManager.instance().need(event)) {
                flag = ExceptionManager.instance().handle(event);
            } else {
                flag = EventFactory.build(event).handle();
            }
            if (!flag) {
                ContextManager.instance().mount(event);
                input();
            }
        }
    }

    private void input() {
        while (true) {
            try {
                if (null != ContextManager.instance().parser() && !ContextManager.instance().parser().terminal()) {
                    if (!ContextManager.instance().parser().handle()) {
                        return;
                    }
                } else {
                    manual(ContextManager.instance().thread());
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void manual(ThreadReference thread) {
        synchronized (this) {
            ContextManager.instance().store();
            ContextManager.instance().thread(thread);
            while (true) {
                try {
                    printTip();
                    String command = new Scanner(System.in).nextLine();
                    ContextManager.instance().parser(command + ";");
                    while (!ContextManager.instance().parser().terminal()) {
                        if (!ContextManager.instance().parser().handle()) {
                            ContextManager.instance().restore();
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void printTip() {
        if (null == ContextManager.instance().thread()) {
            System.out.print("0> ");
        } else {
            System.out.print(ContextManager.instance().thread().uniqueID() + "> ");
        }
    }

    private void monitor(boolean flag) {
        if (DISCONNECT != this.flag) {
            if (!flag) {
                ClassManager.instance().clean();
                BreakManager.instance().clean();
                ExecuteManager.instance().clean();
                ExceptionManager.instance().clean();
            }
            ClassManager.instance().monitor(flag);
            BreakManager.instance().monitor(flag);
            ExecuteManager.instance().monitor(flag);
            ExceptionManager.instance().monitor(flag);
        }
    }

    private void exit() {
        if (QUIT == flag) {
            MachineManager.instance().exit(0);
            Process process = MachineManager.instance().process();
            if (null != process) {
                process.destroy();
            }
        }
    }

    @Override
    public void handle(Signal signal) {
        MachineManager.instance().suspend();
        manual(null);
        MachineManager.instance().resume();
    }

    public final static int CONTINUE = 0;
    public final static int QUIT = 1;
    public final static int DETACH = 2;
    public final static int DISCONNECT = 3;

    private int flag = CONTINUE;

    public void flag(int flag) {
        this.flag = flag;
    }

    public int flag() {
        return flag;
    }
}