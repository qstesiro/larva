package com.runbox.debug.command;

import com.runbox.debug.command.block.BlockFormatCommand;
import com.runbox.debug.command.block.BlockQueryCommand;
import com.runbox.debug.command.breakpoint.*;
import com.runbox.debug.command.clazz.*;
import com.runbox.debug.command.execute.*;
import com.runbox.debug.command.help.HelpCommand;
import com.runbox.debug.command.machine.MachineAbilityCommand;
import com.runbox.debug.command.machine.MachineNameCommand;
import com.runbox.debug.command.machine.MachineVersionCommand;
import com.runbox.debug.command.monitor.MonitorObjectCommand;
import com.runbox.debug.command.monitor.MonitorThreadCommand;
import com.runbox.debug.command.source.SourceAppendCommand;
import com.runbox.debug.command.source.SourceDeleteCommand;
import com.runbox.debug.command.source.SourceQueryCommand;
import com.runbox.debug.command.stack.StackFrameCommand;
import com.runbox.debug.command.stack.StackSwitchCommand;
import com.runbox.debug.command.template.*;
import com.runbox.debug.command.thread.*;
import com.runbox.debug.command.variant.VariantAutoCommand;
import com.runbox.debug.command.variant.VariantFieldCommand;
import com.runbox.debug.command.variant.VariantLocalCommand;
import com.runbox.debug.command.variant.VariantPrintCommand;

/**
 * Created by huangmengmeng01 on 2016/4/26.
 */
public class CommandFactory {

    public static Command build(String command) throws Exception {
        String key = Command.lookup(command);
        if (key.equals(Command.MACHINE_NAME)) {
            return new MachineNameCommand(command);
        } else if (key.equals(Command.MACHINE_VERSION)) {
            return new MachineVersionCommand(command);
        } else if (key.equals(Command.MACHINE_ABILITY)) {
            return new MachineAbilityCommand(command);
        } else if (key.equals(Command.CLASS_QUERY)) {
            return new ClassQueryCommand(command);
        } else if (key.equals(Command.CLASS_FIELD)) {
            return new ClassFieldCommand(command);
        } else if (key.equals(Command.CLASS_METHOD)) {
            return new ClassMethodCommand(command);
        } else if (key.equals(Command.CLASS_LOAD)) {
            return new ClassLoadCommand(command);
        } else if (key.equals(Command.CLASS_UNLOAD)) {
            return new ClassUnloadCommand(command);
        } else if (key.equals(Command.THREAD_QUERY)) {
            return new ThreadQueryCommand(command);
        } else if (key.equals(Command.THREAD_SWITCH)) {
            return new ThreadSwitchCommand(command);
        } else if (key.equals(Command.THREAD_SUSPEND)) {
            return new ThreadSuspendCommand(command);
        } else if (key.equals(Command.THREAD_RESUME)) {
            return new ThreadResumeCommand(command);
        } else if (key.equals(Command.STACK_FRAME)) {
            return new StackFrameCommand(command);
        } else if (key.equals(Command.STACK_SWITCH)) {
            return new StackSwitchCommand(command);
        } else if (key.equals(Command.MONITOR_THREAD)) {
            return new MonitorThreadCommand(command);
        } else if (key.equals(Command.MONITOR_OBJECT)) {
            return new MonitorObjectCommand(command);
        } else if (key.equals(Command.BREAK_METHOD)) {
            return new BreakMethodCommand(command);
        } else if (key.equals(Command.BREAK_LINE)) {
            return new BreakLineCommand(command);
        } else if (key.equals(Command.BREAK_ACCESS)) {
            return new BreakAccessCommand(command);
        } else if (key.equals(Command.BREAK_MODIFY)) {
            return new BreakModifyCommand(command);
        } else if (key.equals(Command.BREAK_QUERY)) {
            return new BreakQueryCommand(command);
        } else if (key.equals(Command.BREAK_DELETE)) {
            return new BreakDeleteCommand(command);
        } else if (key.equals(Command.BREAK_ENABLE)) {
            return new BreakEnableCommand(command);
        } else if (key.equals(Command.BREAK_DISABLE)) {
            return new BreakDisableCommand(command);
        } else if (key.equals(Command.EXECUTE_RUN)) {
            return new ExecuteRunCommand(command);
        } else if (key.equals(Command.EXECUTE_NEXT)) {
            return new ExecuteNextCommand(command);
        } else if (key.equals(Command.EXECUTE_STEP)) {
            return new ExecuteStepCommand(command);
        } else if (key.equals(Command.EXECUTE_FILE)) {
            return new ExecuteFileCommand(command);
        } else if (key.equals(Command.EXECUTE_DETACH)) {
            return new ExecuteDetachCommand(command);
        } else if (key.equals(Command.EXECUTE_QUIT)) {
            return new ExecuteQuitCommand(command);
        } else if (key.equals(Command.VARIANT_PRINT)) {
            return new VariantPrintCommand(command);
        } else if (key.equals(Command.VARIANT_FIELD)) {
            return new VariantFieldCommand(command);
        } else if (key.equals(Command.VARIANT_LOCAL)) {
            return new VariantLocalCommand(command);
        } else if (key.equals(Command.VARIANT_AUTO)) {
            return new VariantAutoCommand(command);
        } else if (key.equals(Command.TEMPLATE_LIST)) {
            return new TemplateListCommand(command);
        } else if (key.equals(Command.TEMPLATE_MAP)) {
            return new TemplateMapCommand(command);
        } else if (key.equals(Command.TEMPLATE_VECTOR)) {
            return new TemplateVectorCommand(command);
        } else if (key.equals(Command.TEMPLATE_QUEUE)) {
            return new TemplateQueueCommand(command);
        } else if (key.equals(Command.TEMPLATE_STACK)) {
            return new TemplateStackCommand(command);
        } else if (key.equals(Command.SOURCE_APPEND)) {
            return new SourceAppendCommand(command);
        } else if (key.equals(Command.SOURCE_DELETE)) {
            return new SourceDeleteCommand(command);
        } else if (key.equals(Command.SOURCE_QUERY)) {
            return new SourceQueryCommand(command);
        } else if (key.equals(Command.BLOCK_QUERY)) {
            return new BlockQueryCommand(command);
        } else if (key.equals(Command.BLOCK_FORMAT)) {
            return new BlockFormatCommand(command);
        } else if (key.equals(Command.HELP)) {
            return new HelpCommand(command);
        }
        return new Command(null);
    }
}
