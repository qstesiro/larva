package com.runbox.debug.command.breakpoint;

import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.parser.command.breakpoint.Number;
import com.runbox.debug.parser.command.breakpoint.Lexer;
import com.runbox.debug.parser.command.breakpoint.Token;
import com.runbox.debug.manager.breakpoint.LineBreakPoint;
import com.runbox.debug.command.Command;
import com.runbox.debug.manager.BreakManager;
import com.sun.jdi.ReferenceType;

import java.util.List;

/**
 * Created by qstesiro
 */
public class BreakLineCommand extends Command {

    public BreakLineCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid break command");
        }
    }

    @Override
    public boolean execute() throws Exception {
        LineBreakPoint point = parse();
        if (!BreakManager.instance().contain(point)) {
            BreakManager.instance().append(point);
            search(point);
        }
        return super.execute();
    }

    private void search(LineBreakPoint point) {
        if (null != point) {
            List<ReferenceType> types = MachineManager.instance().allClasses();
            for (ReferenceType type : types) {
                BreakManager.instance().match(point, type);
            }
        }
    }

    private LineBreakPoint parse() throws Exception {
        Lexer parser = new Lexer(argument);
        Token token = parser.token();
        if (!parser.bound(token) && !(token instanceof Number)) {
            String clazz = token.name();
            token = parser.token();
            if (token.name().equals(":")) {
                token = parser.token();
                if (token instanceof Number) {
                    if (parser.peek().name().equals("#")) {
                        LineBreakPoint point = new LineBreakPoint(clazz, ((Number)token).number());
                        point.block(block());
                    }
                }
            }
        }
        throw new Exception("invalid method break argument");
    }

    @Override
    public void help() {
        String help = "break.method class:line\r\n";
        help += "description\r\n";
        help += "set a line breakpoint, if the class which owns the field variable which is watched has been " +
                "loaded by virtual machine, the access breakpoint will be enabled, otherwise, the breakpoint will was" +
                "pending until class is loaded.";
        help += "arguments";
        help += "class:line is a full method signature, class is name of the class which must be a short class name;" +
                "line is line number of the class file.";
        help += "example";
        help += "";
        System.out.println(help);
    }
}
