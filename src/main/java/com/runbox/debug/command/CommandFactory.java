package com.runbox.debug.command;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.runbox.debug.command.breakpoint.*;
import com.runbox.debug.command.clazz.*;
import com.runbox.debug.command.method.*;
import com.runbox.debug.command.execute.*;
import com.runbox.debug.command.machine.*;
import com.runbox.debug.command.source.*;
import com.runbox.debug.command.template.*;
import com.runbox.debug.command.thread.*;
import com.runbox.debug.command.variant.*;
import com.runbox.debug.command.exception.*;
import com.runbox.debug.command.quit.*;

public class CommandFactory extends com.runbox.command.CommandFactory {

    public static Command build(String command) throws Exception {
		String key = new Command(command).key();            
		if (key.equals(MACHINE_NAME)) {
			return new MachineNameCommand(command);
		} else if (key.equals(MACHINE_VERSION)) {
			return new MachineVersionCommand(command);
		} else if (key.equals(MACHINE_ABILITY)) {
			return new MachineAbilityCommand(command);
		} else if (key.equals(MACHINE_SUSPEND)) {
			return new MachineSuspendCommand(command);
		} else if (key.equals(MACHINE_RESUME)) {
			return new MachineResumeCommand(command);
		} else if (key.equals(MACHINE_STATUS)) {
			return new MachineStatusCommand(command);
		} else if (key.equals(CLASS_QUERY)) {
			return new ClassQueryCommand(command);
		} else if (key.equals(CLASS_FIELD)) {
			return new ClassFieldCommand(command);
		} else if (key.equals(CLASS_METHOD)) {
			return new ClassMethodCommand(command);
		} else if (key.equals(CLASS_MONITOR_QUERY)) {
			return new ClassMonitorQueryCommand(command);
		} else if (key.equals(CLASS_MONITOR_PREPARE)) {
			return new ClassMonitorPrepareCommand(command);
		} else if (key.equals(CLASS_MONITOR_UNLOAD)) {
			return new ClassMonitorUnloadCommand(command);
		} else if (key.equals(CLASS_MONITOR_ENABLE)) {
			return new ClassMonitorEnableCommand(command);
		} else if (key.equals(CLASS_MONITOR_DISABLE)) {
			return new ClassMonitorDisableCommand(command);
		} else if (key.equals(CLASS_MONITOR_DELETE)) {
			return new ClassMonitorDeleteCommand(command);
		} else if (key.equals(CLASS_CONSTANT)) {
			return new ClassConstantCommand(command);
		} else if (key.equals(METHOD_ARGUMENT)) {
			return new MethodArgumentCommand(command);
		} else if (key.equals(METHOD_LOCAL)) {
			return new MethodLocalCommand(command);
		} else if (key.equals(METHOD_BYTECODE)) {
			return new MethodBytecodeCommand(command);
		} else if (key.equals(METHOD_MONITOR_ENTRY)) {
			return new MethodMonitorEntryCommand(command);
		} else if (key.equals(METHOD_MONITOR_RETURN)) {
			return new MethodMonitorReturnCommand(command);
		} else if (key.equals(THREAD_QUERY)) {
			return new ThreadQueryCommand(command);
		} else if (key.equals(THREAD_SWITCH)) {
			return new ThreadSwitchCommand(command);
		} else if (key.equals(THREAD_SUSPEND)) {
			return new ThreadSuspendCommand(command);
		} else if (key.equals(THREAD_RESUME)) {
			return new ThreadResumeCommand(command);
		} else if (key.equals(THREAD_INTERRUPT)) {
			return new ThreadInterruptCommand(command);
		} else if (key.equals(THREAD_STACK)) {
			return new ThreadStackCommand(command);
		} else if (key.equals(THREAD_HOLD)) {
			return new ThreadHoldCommand(command);
		} else if (key.equals(THREAD_WAIT)) {
			return new ThreadWaitCommand(command);
		} else if (key.equals(THREAD_MONITOR_START)) {
			return new ThreadMonitorStartCommand(command);
		} else if (key.equals(THREAD_MONITOR_DEATH)) {
			return new ThreadMonitorDeathCommand(command);
		} else if (key.equals(BREAKPOINT_METHOD)) {
			return new BreakpointMethodCommand(command);
		} else if (key.equals(BREAKPOINT_LINE)) {
			return new BreakpointLineCommand(command);
		} else if (key.equals(BREAKPOINT_ACCESS)) {
			return new BreakpointAccessCommand(command);
		} else if (key.equals(BREAKPOINT_MODIFY)) {
			return new BreakpointModifyCommand(command);
		} else if (key.equals(BREAKPOINT_QUERY)) {
			return new BreakpointQueryCommand(command);
		} else if (key.equals(BREAKPOINT_DELETE)) {
			return new BreakpointDeleteCommand(command);
		} else if (key.equals(BREAKPOINT_ENABLE)) {
			return new BreakpointEnableCommand(command);
		} else if (key.equals(BREAKPOINT_DISABLE)) {
			return new BreakpointDisableCommand(command);
		} else if (key.equals(EXECUTE_RUN)) {
			return new ExecuteRunCommand(command);
		} else if (key.equals(EXECUTE_NEXT_OVER)) {
			return new ExecuteNextOverCommand(command);
		} else if (key.equals(EXECUTE_NEXT_INTO)) {
			return new ExecuteNextIntoCommand(command);
		} else if (key.equals(EXECUTE_STEP_OVER)) {
			return new ExecuteStepOverCommand(command);
		} else if (key.equals(EXECUTE_STEP_INTO)) {
			return new ExecuteStepIntoCommand(command);
		} else if (key.equals(EXECUTE_FILE)) {
			return new ExecuteFileCommand(command);
		} else if (key.equals(VARIANT_PRINT)) {
			return new VariantPrintCommand(command);
		} else if (key.equals(VARIANT_FIELD)) {
			return new VariantFieldCommand(command);
		} else if (key.equals(VARIANT_LOCAL)) {
			return new VariantLocalCommand(command);
		} else if (key.equals(VARIANT_ARRAY)) {
			return new VariantArrayCommand(command);
		} else if (key.equals(VARIANT_STRING)) {
			return new VariantStringCommand(command);
		} else if (key.equals(TEMPLATE_LIST)) {
			return new TemplateListCommand(command);
		} else if (key.equals(TEMPLATE_MAP)) {
			return new TemplateMapCommand(command);
		} else if (key.equals(TEMPLATE_VECTOR)) {
			return new TemplateVectorCommand(command);
		} else if (key.equals(TEMPLATE_QUEUE)) {
			return new TemplateQueueCommand(command);
		} else if (key.equals(TEMPLATE_STACK)) {
			return new TemplateStackCommand(command);
		} else if (key.equals(SOURCE_APPEND)) {
			return new SourceAppendCommand(command);
		} else if (key.equals(SOURCE_DELETE)) {
			return new SourceDeleteCommand(command);
		} else if (key.equals(SOURCE_QUERY)) {
			return new SourceQueryCommand(command);
		} else if (key.equals(EXCEPTION_MONITOR)) {
			return new ExceptionMonitorCommand(command);
		} else if (key.equals(EXCEPTION_DELETE)) {
			return new ExceptionDeleteCommand(command);
		} else if (key.equals(EXCEPTION_QUERY)) {
			return new ExceptionQueryCommand(command);
		} else if (key.equals(DETACH)) {			
			return new DetachCommand(command);
		} else if (key.equals(QUIT)) {
			return new QuitCommand(command);
		}        
        return null;
    }

    public final static int COMMAND_MACHINE = 1;
    public final static String MACHINE_NAME = "machine.name";
    public final static String MACHINE_VERSION = "machine.version";
    public final static String MACHINE_ABILITY = "machine.ability";
	public final static String MACHINE_SUSPEND = "machine.suspend";
	public final static String MACHINE_RESUME = "machine.resume";
	public final static String MACHINE_STATUS = "machine.status";

    public final static int COMMAND_CLASS = COMMAND_MACHINE + 1;
    public final static String CLASS_QUERY = "class.query";    
    public final static String CLASS_FIELD = "class.field";
    public final static String CLASS_METHOD = "class.method";	
    public final static String CLASS_MONITOR_QUERY = "class.monitor.query";
    public final static String CLASS_MONITOR_PREPARE = "class.monitor.prepare";
    public final static String CLASS_MONITOR_UNLOAD = "class.monitor.unload";
    public final static String CLASS_MONITOR_ENABLE = "class.monitor.enable";
    public final static String CLASS_MONITOR_DISABLE = "class.monitor.disable";
    public final static String CLASS_MONITOR_DELETE = "class.monitor.delete";
	public final static String CLASS_CONSTANT = "class.constant";

    public final static int COMMAND_METHOD = COMMAND_CLASS + 1;
    public final static String METHOD_ARGUMENT = "method.argument";
    public final static String METHOD_LOCAL = "method.local";
    public final static String METHOD_BYTECODE = "method.bytecode";
    public final static String METHOD_MONITOR_ENTRY = "method.monitor.entry";
    public final static String METHOD_MONITOR_RETURN = "method.monitor.return";    

    public final static int COMMAND_THREAD = COMMAND_METHOD + 1;
    public final static String THREAD_QUERY = "thread.query";	
    public final static String THREAD_SWITCH = "thread.switch";	
    public final static String THREAD_SUSPEND = "thread.suspend";
    public final static String THREAD_RESUME = "thread.resume";
	public final static String THREAD_INTERRUPT = "thread.interrupt";
	public final static String THREAD_STACK = "thread.stack";
	public final static String THREAD_HOLD = "thread.hold";
	public final static String THREAD_WAIT = "thread.wait";
    public final static String THREAD_MONITOR_START = "thread.monitor.start";
    public final static String THREAD_MONITOR_DEATH = "thread.monitor.death";     

    public final static int COMMAND_BREAKPOINT = COMMAND_THREAD + 1;
    public final static String BREAKPOINT_METHOD = "breakpoint.method";
    public final static String BREAKPOINT_LINE = "breakpoint.line";
    public final static String BREAKPOINT_ACCESS = "breakpoint.access";
    public final static String BREAKPOINT_MODIFY = "breakpoint.modify";
    public final static String BREAKPOINT_QUERY = "breakpoint.query";
    public final static String BREAKPOINT_DELETE = "breakpoint.delete";
    public final static String BREAKPOINT_ENABLE = "breakpoint.enable";
    public final static String BREAKPOINT_DISABLE = "breakpoint.disable"; 

    public final static int COMMAND_EXECUTE = COMMAND_BREAKPOINT + 1;
    public final static String EXECUTE_RUN = "execute.run";
    public final static String EXECUTE_NEXT_OVER = "execute.next.over";
    public final static String EXECUTE_NEXT_INTO = "execute.next.into";
    public final static String EXECUTE_STEP_OVER = "execute.step.over";	
	public final static String EXECUTE_STEP_INTO = "execute.step.into";
    public final static String EXECUTE_FILE = "execute.file";    

    public final static int COMMAND_VARIANT = COMMAND_EXECUTE + 1;
    public final static String VARIANT_PRINT = "variant.print";
    public final static String VARIANT_FIELD = "variant.field";
    public final static String VARIANT_LOCAL = "variant.local";
    public final static String VARIANT_ARRAY = "variant.array";
    public final static String VARIANT_STRING = "variant.string";

    public final static int COMMAND_TEMPLATE = COMMAND_VARIANT + 1;
    public final static String TEMPLATE_LIST = "template.list";
    public final static String TEMPLATE_MAP = "template.map";
    public final static String TEMPLATE_VECTOR = "template.vector";
    public final static String TEMPLATE_QUEUE = "template.queue";
    public final static String TEMPLATE_STACK = "template.stack";

    public final static int COMMAND_SOURCE = COMMAND_TEMPLATE + 1;
    public final static String SOURCE_APPEND = "source.append";
    public final static String SOURCE_DELETE = "source.delete";
    public final static String SOURCE_QUERY = "source.query";

    public final static int COMMAND_EXCEPTION = COMMAND_SOURCE + 1;
	public final static String EXCEPTION_MONITOR = "exception.monitor";
	public final static String EXCEPTION_DELETE = "exception.delete";
	public final static String EXCEPTION_QUERY = "exception.query";
	
    public final static int COMMAND_QUIT = COMMAND_EXCEPTION + 1;
    public final static String QUIT = "quit";
    public final static String DETACH = "detach";

    public final static int COMMAND_HELP = COMMAND_QUIT + 1;
    public final static String HELP = "help";
    
    private static List<String> commands = new LinkedList<String>() {{
        // machine command
        add(MACHINE_NAME);
        add(MACHINE_VERSION);
        add(MACHINE_ABILITY);
		add(MACHINE_SUSPEND);
		add(MACHINE_RESUME);
		add(MACHINE_STATUS);
        // class command
        add(CLASS_QUERY);
        add(CLASS_FIELD);
        add(CLASS_METHOD);
        add(CLASS_MONITOR_QUERY);
        add(CLASS_MONITOR_PREPARE);
        add(CLASS_MONITOR_UNLOAD);
        add(CLASS_MONITOR_ENABLE);
        add(CLASS_MONITOR_DISABLE);
        add(CLASS_MONITOR_DELETE);
		add(CLASS_CONSTANT);
        // method command 
        add(METHOD_ARGUMENT);
        add(METHOD_LOCAL);
        add(METHOD_BYTECODE);
        add(METHOD_MONITOR_ENTRY);
        add(METHOD_MONITOR_RETURN);        
        // thread command
        add(THREAD_QUERY);
        add(THREAD_SWITCH);
        add(THREAD_SUSPEND);
        add(THREAD_RESUME);
		add(THREAD_INTERRUPT);
		add(THREAD_STACK);
		add(THREAD_HOLD);
		add(THREAD_WAIT);
        add(THREAD_MONITOR_START);
        add(THREAD_MONITOR_DEATH);
        // breakpoint command
        add(BREAKPOINT_METHOD);
        add(BREAKPOINT_LINE);
        add(BREAKPOINT_ACCESS);
        add(BREAKPOINT_MODIFY);
        add(BREAKPOINT_QUERY);
        add(BREAKPOINT_DELETE);
        add(BREAKPOINT_ENABLE);
        add(BREAKPOINT_DISABLE);
        // execute command
        add(EXECUTE_RUN);
        add(EXECUTE_NEXT_OVER);
        add(EXECUTE_NEXT_INTO);
        add(EXECUTE_STEP_OVER);		
        add(EXECUTE_STEP_INTO);
        add(EXECUTE_FILE);
        // variant command
        add(VARIANT_PRINT);
        add(VARIANT_FIELD);
        add(VARIANT_LOCAL);
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
		// exception command
		add(EXCEPTION_MONITOR);
		add(EXCEPTION_DELETE);
		add(EXCEPTION_QUERY);
        // quit 
        add(QUIT);     
        add(DETACH);
        // help command
        add(HELP);
    }};

    public static boolean command(String command) throws Exception {
		String key = key(command);
        for (String entry : commands) {
            if (entry.equals(key)) {
                return true;
            }
        }
        return false;
    }
}
