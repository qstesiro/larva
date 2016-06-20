package com.runbox.debug.manager;

import com.runbox.debug.parser.statement.node.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by qstesiro
 */
public class BlockManager extends Manager {

    private BlockManager() {

    }

    private static BlockManager manager = new BlockManager();

    public static BlockManager instance() {
        return manager;
    }

    private static Map<String, BlockNode> map = new HashMap<>();

    public static BlockNode append(String name, BlockNode node) {
        BlockNode previous = map.get(name);
        map.put(name, node);
        return previous;
    }

    public BlockNode get(String name) {
        return map.get(name);
    }

    public Map<String, BlockNode> get() {
        return map;
    }

    public String string(String name) throws Exception {
        if (null != map.get(name)) {
            BlockNode root = map.get(name);
            String block = name;
            List<Node> nodes = new LinkedList<>();
            Node previous = root;
            Node current = root.right();
            while (null != current) {
                if (current instanceof ConditionNode) {
                    if (!(previous instanceof BeginNode)) {
                        block += " ";
                    }
                    block += current.name() + " " + ((ConditionNode)current).condition();
                    if (current.name().equals("while")) {
                        nodes.add(current);
                    }
                } else if (current instanceof ExpressionNode
                        || current instanceof CommandNode
                        || current instanceof BlockNode
                        || current instanceof ContinueNode
                        || current instanceof BreakNode) {
                    if (!(previous instanceof BeginNode)) {
                        block += " ";
                    }
                    block += current.name() + ";";
                } else if (current.name().equals("{")) {
                    block += " " + current.name();
                } else if (current.name().equals("}")) {
                    block += current.name();
                }
                previous = current;
                if (current instanceof ContinueNode || current instanceof BreakNode) {
                    current = current.left();
                } else {
                    current = current.right();
                }
                if (nodes.contains(current)) {
                    current = current.left();
                }
            }
            return block;
        }
        throw new Exception("invalid block name");
    }

    public String format(String name) throws Exception {
        if (null != map.get(name)) {
            BlockNode root = map.get(name);
            String block = name;
            List<Node> nodes = new LinkedList<>();
            int count = 0;
            Node current = root.right();
            while (null != current) {
                if (current instanceof ConditionNode) {
                    block += "\n" + indent(count, '\t') + current.name() + " " + ((ConditionNode)current).condition();
                    if (current.name().equals("while")) {
                        nodes.add(current);
                    }
                } else if (current instanceof ExpressionNode
                        || current instanceof CommandNode
                        || current instanceof BlockNode
                        || current instanceof ContinueNode
                        || current instanceof BreakNode) {
                    block += "\n" + indent(count, '\t') + current.name() + ";";
                } else if (current.name().equals("{")) {
                    block += " " + current.name();
                    ++count;
                } else if (current.name().equals("}")) {
                    block += "\n" + indent(--count, '\t') + current.name();
                }
                if (current instanceof ContinueNode || current instanceof BreakNode) {
                    current = current.left();
                } else {
                    current = current.right();
                }
                if (nodes.contains(current)) {
                    current = current.left();
                }
            }
            return block;
        }
        throw new Exception("invalid block name");
    }

    private String indent(int count, char letter) {
        String indent = "";
        for (int i = 0; i < count; ++i) {
            indent += letter;
        }
        return indent;
    }
}
