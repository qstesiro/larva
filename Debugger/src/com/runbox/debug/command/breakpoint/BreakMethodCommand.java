package com.runbox.debug.command.breakpoint;

import com.runbox.debug.manager.ClassManager;
import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.parser.command.breakpoint.Number;
import com.runbox.debug.parser.command.breakpoint.Lexer;
import com.runbox.debug.parser.command.breakpoint.Token;
import com.runbox.debug.manager.breakpoint.MethodBreakPoint;
import com.runbox.debug.command.Command;
import com.runbox.debug.manager.BreakManager;
import com.sun.jdi.ReferenceType;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by qstesiro
 */
public class BreakMethodCommand extends Command {

    public BreakMethodCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid break command");
        }
    }

    @Override
    public boolean execute() throws Exception {
        MethodBreakPoint point = parse();
        if (!BreakManager.instance().contain(point)) {
            BreakManager.instance().append(point);
            search(point);
        }
        return super.execute();
    }

    private void search(MethodBreakPoint point) {
        if (null != point) {
            List<ReferenceType> types = ClassManager.instance().allClasses();
            for (ReferenceType type : types) {
                BreakManager.instance().match(point, type);
            }
        }
    }

    private MethodBreakPoint parse() throws Exception {
        Lexer parser = new Lexer(argument);
        Token token = parser.token();
        if (!parser.bound(token) && !(token instanceof Number)) {
            int index = token.name().lastIndexOf(".");
            if (-1 != index) {
                String clazz = token.name().substring(0, index);
                String method = token.name().substring(index + 1);
                MethodBreakPoint point = new MethodBreakPoint(clazz, method);
                if (parser.peek().name().equals("(")) {
                    point = parse(point, parser);
                    point.block(block());
                    return point;
                }
            }
        }
        throw new Exception("invalid method break argument");
    }

    private MethodBreakPoint parse(MethodBreakPoint point, Lexer parser) throws Exception {
        if (null != parser) {
            Token token = parser.token();
            if (token.name().equals("(")) {
                List<String> list = new LinkedList<>();
                point.list(list);
                token = parser.token();
                if (!parser.bound(token) && !(token instanceof Number)) {
                    while (!token.name().equals("#")) {
                        list.add(token.name());
                        token = parser.token();
                        if (token.name().equals(",")) {
                            token = parser.token();
                            continue;
                        } else if (token.name().equals(")")) {
                            if (parser.peek().name().equals("#")) {
                                return point;
                            }
                            break;
                        }
                    }
                } else if (token.name().equals(")")) {
                    if (parser.peek().name().equals("#")) {
                        return point;
                    }
                }
            }
        }
        throw new Exception("invalid method break argument");
    }
}
