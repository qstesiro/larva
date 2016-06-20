package com.runbox.debug.command.help;

import com.runbox.debug.command.Command;

/**
 * Created by huangmengmeng01 on 2016/6/1.
 */
public class HelpCommand extends Command {

    public HelpCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        int type = ((null != argument) ? Integer.valueOf(argument.trim()) : 0);
        if (0 == type) {
            print();
        } else if (types.size() >= type) {
            print(type);
        }
        return super.execute();
    }

    private void print() {
        for (Integer key : types.keySet()) {
            System.out.println(key + ". " + types.get(key));
        }
    }

    private void print(int type) {
        switch (type) {
            case COMMAND_MACHINE: {
                System.out.println(Command.MACHINE_ABILITY);
                System.out.println(Command.MACHINE_NAME);
                System.out.println(Command.MACHINE_VERSION);
                break;
            }
            case COMMAND_CLASS:{
                System.out.println(Command.CLASS_QUERY);
                System.out.println(Command.CLASS_FIELD);
                System.out.println(Command.CLASS_METHOD);
                System.out.println(Command.CLASS_LOAD);
                System.out.println(Command.CLASS_UNLOAD);
                break;
            }
            case COMMAND_THREAD:{
                System.out.println(Command.THREAD_QUERY);
                System.out.println(Command.THREAD_SWITCH);
                System.out.println(Command.THREAD_SUSPEND);
                System.out.println(Command.THREAD_RESUME);
                break;
            }
            case COMMAND_STACK: {
                System.out.println(Command.STACK_FRAME);
                System.out.println(Command.STACK_SWITCH);
                break;
            }
            case COMMAND_MONITOR: {
                System.out.println(Command.MONITOR_THREAD);
                System.out.println(Command.MONITOR_OBJECT);
                break;
            }
            case COMMAND_BREAK: {
                System.out.println(Command.BREAK_METHOD);
                System.out.println(Command.BREAK_LINE);
                System.out.println(Command.BREAK_ACCESS);
                System.out.println(Command.BREAK_MODIFY);
                System.out.println(Command.BREAK_ENABLE);
                System.out.println(Command.BREAK_DISABLE);
                System.out.println(Command.BREAK_DELETE);
                break;
            }
            case COMMAND_EXECUTE: {
                System.out.println(Command.EXECUTE_NEXT);
                System.out.println(Command.EXECUTE_STEP);
                System.out.println(Command.EXECUTE_RUN);
                System.out.println(Command.EXECUTE_FILE);
                System.out.println(Command.EXECUTE_QUIT);
                System.out.println(Command.EXECUTE_DETACH);
                break;
            }
            case COMMAND_VARIANT: {
                System.out.println(Command.VARIANT_PRINT);
                System.out.println(Command.VARIANT_FIELD);
                System.out.println(Command.VARIANT_LOCAL);
                System.out.println(Command.VARIANT_AUTO);
                break;
            }
            case COMMAND_TEMPLATE: {
                System.out.println(Command.TEMPLATE_LIST);
                System.out.println(Command.TEMPLATE_MAP);
                System.out.println(Command.TEMPLATE_VECTOR);
                System.out.println(Command.TEMPLATE_QUEUE);
                System.out.println(Command.TEMPLATE_STACK);
                break;
            }
            case COMMAND_SOURCE: {
                System.out.println(Command.SOURCE_QUERY);
                System.out.println(Command.SOURCE_APPEND);
                System.out.println(Command.SOURCE_DELETE);
                break;
            }
            case COMMAND_BLOCK: {
                System.out.println(Command.BLOCK_QUERY);
                System.out.println(Command.BLOCK_FORMAT);
                break;
            }
            case COMMAND_EXCEPTION: {
                break;
            }
            case COMMAND_HELP: {
                System.out.println(Command.HELP);
            }
        }
    }
}
