package com.runbox.debug.command.breakpoint;

import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.parser.command.breakpoint.Number;
import com.runbox.debug.parser.command.breakpoint.Lexer;
import com.runbox.debug.parser.command.breakpoint.Token;
import com.runbox.debug.manager.breakpoint.AccessBreakPoint;
import com.runbox.debug.command.Command;
import com.runbox.debug.manager.BreakManager;
import com.sun.jdi.ReferenceType;

import java.util.List;

/**
 * Created by qstesiro
 */
public class BreakAccessCommand extends Command {

    public BreakAccessCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid break command");
        }
    }

    @Override
    public boolean execute() throws Exception {
        AccessBreakPoint point = parse();
        if (!BreakManager.instance().contain(point)) {
            BreakManager.instance().append(point);
            search(point);
        }
        return super.execute();
    }

    private void search(AccessBreakPoint point) {
        List<ReferenceType> types = MachineManager.instance().allClasses();
        for (ReferenceType type : types) {
            BreakManager.instance().match(point, type);
        }
    }

    private AccessBreakPoint parse() throws Exception {
        Lexer parser = new Lexer(argument);
        Token token = parser.token();
        if (!parser.bound(token) && !(token instanceof Number)) {
            int index = token.name().lastIndexOf(".");
            if (-1 != index) {
                String clazz = token.name().substring(0, index);
                String field = token.name().substring(index + 1);
                if (parser.peek().name().equals("#")) {
                    AccessBreakPoint point = new AccessBreakPoint(clazz, field);
                    point.block(block());
                    return point;
                }
            }
        }
        throw new Exception("invalid method break argument");
    }
}
