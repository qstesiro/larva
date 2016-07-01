package com.runbox.debug.command;

import com.runbox.debug.parser.statement.node.BlockNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by qstesiro
 */
public class Command {

    public final static int COMMAND_MACHINE = 1;
    public final static String MACHINE_NAME = "machine.name";
    public final static String MACHINE_VERSION = "machine.version";
    public final static String MACHINE_ABILITY = "machine.ability";
    public final static String MACHINE_HELP = "machine.help";

    public final static int COMMAND_CLASS = COMMAND_MACHINE + 1;
    public final static String CLASS_QUERY = "class.query";
    public final static String CLASS_FIELD = "class.field";
    public final static String CLASS_METHOD = "class.method";
    public final static String CLASS_LOAD = "class.load";
    public final static String CLASS_UNLOAD = "class.unload";
    public final static String CLASS_HELP = "class.help";

    public final static int COMMAND_THREAD = COMMAND_CLASS + 1;
    public final static String THREAD_QUERY = "thread.query";
    public final static String THREAD_SWITCH = "thread.switch";
    public final static String THREAD_SUSPEND = "thread.suspend";
    public final static String THREAD_RESUME = "thread.resume";
    public final static String THREAD_HELP = "thread.help";

    public final static int COMMAND_STACK = COMMAND_THREAD + 1;
    public final static String STACK_FRAME = "stack.frame";
    public final static String STACK_SWITCH = "stack.switch";
    public final static String STACK_HELP = "stack.help";

    public final static int COMMAND_MONITOR = COMMAND_STACK + 1;
    public final static String MONITOR_THREAD = "monitor.thread";
    public final static String MONITOR_OBJECT = "monitor.object";
    public final static String MONITOR_HELP = "monitor.help";

    public final static int COMMAND_BREAK = COMMAND_MONITOR + 1;
    public final static String BREAK_METHOD = "break.method";
    public final static String BREAK_LINE = "break.line";
    public final static String BREAK_ACCESS = "break.access";
    public final static String BREAK_MODIFY = "break.modify";
    public final static String BREAK_QUERY = "break.query";
    public final static String BREAK_DELETE = "break.delete";
    public final static String BREAK_ENABLE = "break.enable";
    public final static String BREAK_DISABLE = "break.disable";
    public final static String BREAK_HELP = "break.help";

    public final static int COMMAND_EXECUTE = COMMAND_BREAK + 1;
    public final static String EXECUTE_RUN = "execute.run";
    public final static String EXECUTE_NEXT = "execute.next";
    public final static String EXECUTE_STEP = "execute.step";
    public final static String EXECUTE_DETACH = "execute.detach";
    public final static String EXECUTE_FILE = "execute.file";
    public final static String EXECUTE_QUIT = "execute.quit";
    public final static String EXECUTE_HELP = "execute.help";

    public final static int COMMAND_VARIANT = COMMAND_EXECUTE + 1;
    public final static String VARIANT_PRINT = "variant.print";
    public final static String VARIANT_FIELD = "variant.field";
    public final static String VARIANT_LOCAL = "variant.local";
    public final static String VARIANT_AUTO = "variant.auto";
    public final static String VARIANT_ARRAY = "variant.array";
    public final static String VARIANT_STRING = "variant.string";
    public final static String VARIANT_HELP = "variant.help";

    public final static int COMMAND_TEMPLATE = COMMAND_VARIANT + 1;
    public final static String TEMPLATE_LIST = "template.list";
    public final static String TEMPLATE_MAP = "template.map";
    public final static String TEMPLATE_VECTOR = "template.vector";
    public final static String TEMPLATE_QUEUE = "template.queue";
    public final static String TEMPLATE_STACK = "template.stack";
    public final static String TEMPLATE_HELP = "template.help";

    public final static int COMMAND_SOURCE = COMMAND_TEMPLATE + 1;
    public final static String SOURCE_APPEND = "source.append";
    public final static String SOURCE_DELETE = "source.delete";
    public final static String SOURCE_QUERY = "source.query";
    public final static String SOURCE_HELP = "source.help";

    public final static int COMMAND_BLOCK = COMMAND_SOURCE + 1;
    public final static String BLOCK_QUERY = "block.query";
    public final static String BLOCK_FORMAT = "block.format";
    public final static String BLOCK_HELP = "block.help";

    public final static int COMMAND_EXCEPTION = COMMAND_BLOCK + 1;

    public final static int COMMAND_HELP = COMMAND_EXCEPTION + 1;
    public final static String HELP = "help";

    private String command = null;
    protected String key = null;
    protected String argument = null;

    public Command(String command) throws Exception {
        if (null != command) {
            this.command = command;
            int index = this.command.indexOf(' ');
            if (-1 != index) {
                key = command.substring(0, index).toLowerCase();
                argument = command.substring(index + 1).toLowerCase().trim();
            } else {
                key = this.command;
            }
        }
    }

    public boolean execute() throws Exception {
        return true;
    }

    public String command() {
        return command;
    }

    public String key() {
        return key;
    }

    public String argument() {
        return argument;
    }

    protected static List<String> commands = new LinkedList<String>() {{
        // machine command
        add(MACHINE_NAME);
        add(MACHINE_VERSION);
        add(MACHINE_ABILITY);
        // class command
        add(CLASS_QUERY);
        add(CLASS_FIELD);
        add(CLASS_METHOD);
        add(CLASS_LOAD);
        add(CLASS_UNLOAD);
        // thread command
        add(THREAD_QUERY);
        add(THREAD_SWITCH);
        add(THREAD_SUSPEND);
        add(THREAD_RESUME);
        // stack command
        add(STACK_FRAME);
        add(STACK_SWITCH);
        // monitor command
        add(MONITOR_THREAD);
        add(MONITOR_OBJECT);
        // break command
        add(BREAK_METHOD);
        add(BREAK_LINE);
        add(BREAK_ACCESS);
        add(BREAK_MODIFY);
        add(BREAK_QUERY);
        add(BREAK_DELETE);
        add(BREAK_ENABLE);
        add(BREAK_DISABLE);
        // execute command
        add(EXECUTE_RUN);
        add(EXECUTE_NEXT);
        add(EXECUTE_STEP);
        add(EXECUTE_DETACH);
        add(EXECUTE_FILE);
        add(EXECUTE_QUIT);
        // variant command
        add(VARIANT_PRINT);
        add(VARIANT_FIELD);
        add(VARIANT_LOCAL);
        add(VARIANT_AUTO);
        add(VARIANT_ARRAY);
        add(VARIANT_STRING);
        // template command
        add(TEMPLATE_LIST);
        add(TEMPLATE_MAP);
        add(TEMPLATE_VECTOR);
        add(TEMPLATE_QUEUE);
        add(TEMPLATE_STACK);
        // source command
        add(SOURCE_APPEND);
        add(SOURCE_DELETE);
        add(SOURCE_QUERY);
        // block command
        add(BLOCK_QUERY);
        add(BLOCK_FORMAT);
        // help command
        add(HELP);
    }};

    public static boolean isCommand(String command) {
        String lookup = lookup(command);
        for (String entry : commands) {
            if (entry.equals(lookup)) {
                return true;
            }
        }
        return false;
    }

    public static String lookup(String command) {
        int index = command.indexOf(' ');
        String key = (-1 != index) ? command.substring(0, index) : command;
        return key.toLowerCase();
    }

    public static boolean match(String regex, String source) {
        return Pattern.compile(regex).matcher(source).matches();
    }

    public static String convert(String string) {
        return string.replace("*", ".*").replace("?", ".?");
    }

    public final static String BLOCK = "block";

    BlockNode block = null;

    public BlockNode block(BlockNode block) {
        BlockNode previous = this.block;
        this.block = block;
        return previous;
    }

    public BlockNode block() {
        return block;
    }
}
