package com.runbox.debug.parser;

import com.runbox.debug.command.Command;
import com.runbox.debug.command.CommandFactory;
import com.runbox.debug.parser.expression.Expression;
import com.runbox.debug.parser.expression.token.operand.Operand;
import com.runbox.debug.parser.statement.token.FileParser;
import com.runbox.debug.parser.statement.token.StringParser;
import com.runbox.debug.parser.statement.node.*;
import com.runbox.debug.manager.BlockManager;
import com.sun.jdi.BooleanValue;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangmengmeng01 on 2016/5/18.
 */
public class Parser {

    private Lexer parser = null;

    public Parser(String script) throws Exception {
        if (null != script) {
            if (!script.equals("")) {
                parser = new StringParser(script);
                generate();
                parser.clean();
            }
        }
    }

    public Parser(File file) throws Exception {
        if (null != file) {
            parser = new FileParser(file);
            generate();
            parser.clean();
        }
    }

    private Node root = new Node("root");
    private Node next = null;

    public boolean handle() throws Exception {
            /*while (true) {
                *//*Token token = parser.token();
                System.out.println(token.name());
                if (token.name().equals("#")) {
                    break;
                }*//*
                *//*Token token = parser.token();
                if (token.name().equals("#")) {
                    break;
                }*//*

            }*/
        /*while (null != next) {
            if (next instanceof ConditionNode) {
                Operand operand = new Expression(((ConditionNode)next).condition()).handle();
                if (operand.value() instanceof BooleanValue) {
                    boolean value = ((BooleanValue)operand.value()).value();
                    next = (value ? next.right() : next.left());
                    continue;
                }
            } else if (next instanceof ExpressionNode) {
                String expression = next.name();
                next = next.right();
                new Expression(expression).handle();
            } else if (next instanceof CommandNode) {
                Command command = CommandFactory.build(next.name());
                BlockNode block = ((CommandNode)next).block();
                if (null != block) {
                    command.block(block);
                    next = block;
                } else {
                    next = next.right();
                }
                return command.execute();
            } else {
                next = next.right();
            }
        }
        return true;*/
        while (null != next) {
            if (next instanceof ConditionNode) {
                handleCondition(); continue;
            } else if (next instanceof ExpressionNode) {
                handleExpression();
            } else if (next instanceof CommandNode) {
                return handleCommand();
            } else {
                next = next.right();
            }
        }
        return true;
    }

    private void handleCondition() throws Exception {
        Operand operand = new Expression(((ConditionNode)next).condition()).handle();
        if (operand.value() instanceof BooleanValue) {
            boolean value = ((BooleanValue)operand.value()).value();
            next = (value ? next.right() : next.left());
            return;
        }
        throw new Exception("invalid express");
    }

    private void handleExpression() throws Exception {
        String expression = next.name();
        next = next.right();
        new Expression(expression).handle();
    }

    private boolean handleCommand() throws Exception {
        Command command = CommandFactory.build(next.name());
        BlockNode block = ((CommandNode)next).block();
        if (null != block) {
            command.block(block);
        }
        next = next.right();
        return command.execute();
    }

    public boolean terminal() {
        if (null == next) {
            return true;
        }
        return false;
    }

    public void mount(Node root) {
        if (null != root.left()) {
            root.left().right(root);
        }
    }

    public void mount(BlockNode root) {
        root.left().right(next);
        next = root.right();
    }

    private void generate() throws Exception {
        try {
            root.left(statement(root));
            print(root, "", "");
            next = root.right();
        } catch (Exception e) {
            parser.clean(); throw e;
        }
    }

    private Node statement(Node parent) throws Exception {
        Token token = parser.token();
        if (token.name().equals("#")) {
            return terminate(parent);
        } else if (token.name().equals("if")) {
            return ifStatement(parent, token);
        } else if (token.name().equals("while")) {
            return whileStatement(parent, token);
        } else if (token.name().equals("continue")) {
            return continueStatement(parent);
        } else if (token.name().equals("break")) {
            return breakStatement(parent);
        } else if (Token.block(token.name())) {
            return blockStatement(parent, token);
        } else {
            if (!bound(token)) {
                if (Command.isCommand(token.name())) {
                    return commandStatement(parent, token);
                } else {
                    return expressionStatement(parent, token);
                }
            }
            throw new Exception("invalid statement");
        }
    }

    private Node terminate(Node parent) {
        Node node = new Node("#");
        parent.right(node);
        return node;
    }

    private Node ifStatement(Node parent, Token token) throws Exception {
        Node node = new ConditionNode(token.name(), parser.token().name());
        parent.right(node);
        node = ifStatement(node);
        skip();
        if (!blockEnd(parser.peek())) {
            return statement(node);
        }
        return node;
    }

    private EndNode ifStatement(Node parent) throws Exception {
        Token token = parser.token();
        if (token.name().equals("{")) {
            BeginNode begin = new BeginNode(token.name());
            parent.right(begin);
            Node front = blockStatement(begin);
            token = parser.token();
            if (token.name().equals("}")) {
                EndNode end = new EndNode(token.name());
                parent.left(end);
                front.right(end);
                token = parser.peek();
                if (token.name().equals("else")) {
                    parser.token();
                    end.left(parent);
                    return elseStatement(end);
                }
                return end;
            }
        }
        throw new Exception("invalid if statement");
    }

    private EndNode elseStatement(Node parent) throws Exception {
        Token token = parser.token();
        if (token.name().equals("{")) {
            BeginNode begin = new BeginNode(token.name());
            parent.left(null).left(begin);
            Node front = blockStatement(begin);
            token = parser.token();
            if (token.name().equals("}")) {
                EndNode end = new EndNode(token.name());
                parent.right(end);
                front.right(end);
                return end;
            }
        }
        throw new Exception("invalid else statement");
    }

    private Node whileStatement(Node parent, Token token) throws Exception {
        Node node = new ConditionNode(token.name(), parser.token().name());
        parent.right(node);
        node = whileStatement(node);
        skip();
        if (!blockEnd(parser.peek())) {
            return statement(node);
        }
        return node;
    }

    private EndNode whileStatement(Node parent) throws Exception {
        Token token = parser.token();
        if (token.name().equals("{")) {
            BeginNode begin = new BeginNode(token.name());
            parent.right(begin);
            Node front = blockStatement(begin);
            token = parser.token();
            if (token.name().equals("}")) {
                EndNode end = new EndNode(token.name());
                parent.left(end);
                front.right(parent);
                continues(parent);
                breaks(end);
                return end;
            }
        }
        throw new Exception("invalid while statement");
    }

    private Node continueStatement(Node parent) throws Exception {
        ContinueNode node = new ContinueNode();
        parent.right(node);
        Token token = parser.token();
        if (token.name().equals(";")) {
            skip();
            if (!blockEnd(parser.peek())) {
                Node front = statement(node);
                continues.add(continues.size(), node);
                return front;
            }
            continues.add(continues.size(), node);
            return node;
        }
        throw new Exception("invalid continue statement");
    }

    private Node breakStatement(Node parent) throws Exception {
        BreakNode node = new BreakNode();
        parent.right(node);
        Token token = parser.token();
        if (token.name().equals(";")) {
            skip();
            if (!blockEnd(parser.peek())) {
                Node front = statement(node);
                breaks.add(breaks.size(), node);
                return front;
            }
            breaks.add(breaks.size(), node);
            return node;
        }
        throw new Exception("invalid break statement");
    }

    private Node blockStatement(Node parent) throws Exception {
        if (!blockEnd(parser.peek())) {
            return statement(parent);
        }
        return parent;
    }

    private Node blockStatement(Node parent, Token token) throws Exception {
        if (parser.peek().name().equals("{")) {
            BlockManager.instance().append(token.name(), blockStatement(token.name()));
        } else if (parser.peek().name().equals(";")) {
            parent = blockStatement(parent, token.name());
        }
        skip();
        if (!blockEnd(parser.peek())) {
            return statement(parent);
        }
        return parent;
    }

    private BlockNode blockStatement(String name) throws Exception {
        BlockNode root = new BlockNode(name);
        Token token = parser.token();
        if (token.name().equals("{")) {
            BeginNode node = new BeginNode("{");
            root.right(node);
            Node front = blockStatement(node);
            token = parser.token();
            if (token.name().equals("}")) {
                Node end = new EndNode("}");
                front.right(end);
                root.left(end);
                return root;
            }
        }
        throw new Exception("invalid macro statement");
    }

    private Node blockStatement(Node parent, String name) throws Exception {
        parser.token(); // skip semicolon
        BlockNode block = BlockManager.instance().get(name);
        if (null != block) {
            parent.right(block.right());
            return block.left();
        }
        throw new Exception("invalid block use statement");
    }

    private Node commandStatement(Node parent, Token token) throws Exception {
        CommandNode node = new CommandNode(token.name());
        parent.right(node);
        if (parser.peek().name().equals("{")) {
            BlockNode block = CommandStatement();
            node.block(block);
        }
        token = parser.token();
        if (token.name().equals(";")) {
            skip();
            if (!blockEnd(parser.peek())) {
                return statement(node);
            }
            return node;
        }
        throw new Exception("invalid command statement");
    }

    private BlockNode CommandStatement() throws Exception {
        BlockNode root = new BlockNode("$root");
        Token token = parser.token();
        if (token.name().equals("{")) {
            BeginNode begin = new BeginNode(token.name());
            root.right(begin);
            Node front = blockStatement(begin);
            token = parser.token();
            if (token.name().equals("}")) {
                Node end = new EndNode("}");
                front.right(end);
                root.left(end);
            }
        }
        return root;
    }

    private Node expressionStatement(Node parent, Token token) throws Exception {
        Node node = new ExpressionNode(token.name());
        parent.right(node);
        token = parser.token();
        if (token.name().equals(";")) {
            skip();
            if (!blockEnd(parser.peek())) {
                return statement(node);
            }
            return node;
        }
        throw new Exception("invalid command statement");
    }

    private void skip() throws Exception {
        while (parser.peek().name().equals(";")) {
            parser.token();
        }
    }

    private boolean blockEnd(Token token) {
        if (token.name().equals("}")) {
            return true;
        }
        return false;
    }

    private boolean bound(Token token) {
        if (null != token) {
            if (1 == token.name().length()) {
                if (Token.bound(token.name().charAt(0))) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<Node> nodes = new LinkedList<>();

    private void print(Node node, String tag, String indent) {
        if (null != node) {
            if (!nodes.contains(node)) {
                nodes.add(node);
                System.out.print(indent);
                if (node instanceof ConditionNode) {
                    ConditionNode condition = (ConditionNode)node;
                    System.out.println(condition.name() + "/" + condition.condition() + tag);
                } else {
                    System.out.println(node.name() + tag);
                }
                indent += "  ";
                print(node.right(), "[R]", indent);
                print(node.left(), "[L]", indent);
            }
        }
    }

    private List<Node> continues = new LinkedList<>();

    private void continues(Node node) {
        for (Node entry : continues) {
            entry.left(entry.right());
            entry.right(node);
        }
        continues.clear();
    }

    private List<Node> breaks = new LinkedList<>();

    private void breaks(Node node) {
        for (Node entry : breaks) {
            entry.left(entry.right());
            entry.right(node);
        }
        breaks.clear();
    }
}
