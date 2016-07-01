package com.runbox.debug.command.breakpoint;

import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.parser.command.breakpoint.Number;
import com.runbox.debug.parser.command.breakpoint.Lexer;
import com.runbox.debug.parser.command.breakpoint.Token;
import com.runbox.debug.manager.breakpoint.ModifyBreakPoint;
import com.runbox.debug.command.Command;
import com.runbox.debug.manager.BreakManager;
import com.sun.jdi.ReferenceType;

import java.util.List;

/**
 * Created by qstesiro
 */
public class BreakModifyCommand extends Command {

    public BreakModifyCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid break command");
        }
    }

    @Override
    public boolean execute() throws Exception {
        ModifyBreakPoint point = parse();
        if (!BreakManager.instance().contain(point)) {
            BreakManager.instance().append(point);
            search(point);
        }
        return super.execute();
    }

    private void search(ModifyBreakPoint point) {
        List<ReferenceType> types = MachineManager.instance().allClasses();
        for (ReferenceType type : types) {
            BreakManager.instance().match(point, type);
        }
    }

    private ModifyBreakPoint parse() throws Exception {
        Lexer parser = new Lexer(argument);
        Token token = parser.token();
        if (!parser.bound(token) && !(token instanceof Number)) {
            int index = token.name().lastIndexOf(".");
            if (-1 != index) {
                String clazz = token.name().substring(0, index);
                String field = token.name().substring(index + 1);
                if (parser.peek().name().equals("#")) {
                    ModifyBreakPoint point = new ModifyBreakPoint(clazz, field);
                    point.block(block());
                    return point;
                }
            }
        }
        throw new Exception("invalid method break argument");
    }

    @Override
    public void help() {
        String help = "break.modify variable \r\n";
        help += "description \r\n";
        help += "set a modified breakpoint, if the class which owns the field variable which is watched has been " +
                "loaded by virtual machine, the access breakpoint will be enabled, otherwise, the breakpoint will was" +
                "pending until class is loaded.";
        help += "note";
        help += "breakpoint is triggered after the variable is modified.";
        help += "example";
        help += "";
        System.out.println(help);
    }
}
