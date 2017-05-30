package com.runbox.script.statement;

import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Stack;

import com.runbox.script.statement.Lexer;
import com.runbox.script.statement.token.*;
import com.runbox.script.statement.node.*;

import com.runbox.script.expression.Expression;
import com.runbox.script.expression.ExpressionFactory;

import com.runbox.command.Command;
import com.runbox.command.CommandFactory;

public class Script {		
	
    private Script() {
        
    }

    private static Script instance = new Script();

	public static Script instance() {
		return instance;
	}

	private RootNode root = new RootNode("$root");

	public RootNode root() {
		return root;
	}   	
		
	private Lexer lexer = null;	
	private BlockNode block = null;
	
    public RootNode generate(String script) throws Exception {
        if (null != script && !script.equals("")) {
			lexer = new StringLexer(script);
			block = root; generate();			
			lexer.clean(); lexer = null;
            return root;
        }
        throw new Exception("invalid script");
    }

    public RootNode generate(File file) throws Exception {
        if (null != file && 0 < file.length()) {
            lexer = new FileLexer(file);
			block = root; generate();
            lexer.clean(); lexer = null;
            return root;
        }
        throw new Exception("invalid file path");        
    }

    public void generate() throws Exception {
        try {
			BeginNode begin = new BeginNode(); root.right(begin);
			EndNode end = new EndNode(root); root.left(end);
			List<CommandNode> commands = this.commands;
			this.commands = new LinkedList<CommandNode>();
			List<UnsolvedNode> unsolveds = this.unsolveds;
			this.unsolveds = new LinkedList<UnsolvedNode>();		
			Node node = statement(begin); 
			commands(root); this.commands = commands;
			unsolveds(root); this.unsolveds = unsolveds;
			node.next(null).next(end); end.next(node);
            // print(root, "", "");
        } catch (Exception e) {
            lexer.clean(); throw e;
        }
    }

    private Node statement(Node parent) throws Exception {
        Token token = lexer.token();
        if (token.name().equals("#")) {
            return terminate(parent);
        } else if (token.name().equals("def")) {
			return defStatement(parent);
		} else if (token.name().equals("if")) {
            return ifStatement(parent);
        } else if (token.name().equals("while")) {
            return whileStatement(parent);
        } else if (token.name().equals("continue")) {
            return continueStatement(parent);
        } else if (token.name().equals("break")) {
            return breakStatement(parent);
        } else if (token.name().equals("return")) {
			return returnStatement(parent);
		} else {
            if (!bound(token)) {
                if (command(token)) {
                    return commandStatement(parent, token);
                } else {
                    return unsolvedStatement(parent, token);
                }
            }
            throw new Exception("invalid statement -> " + token.name());
        }
    }

	private boolean command(Token token) throws Exception {
		return (CommandFactory.command(token.name()) || lexer.peek(true).name().equals("{"));
	}
	
    private Node blockStatement(Node parent) throws Exception {
        if (!blockEnd(lexer.peek(true))) {
            return statement(parent);
        }
        return parent;
    }

    private Node terminate(Node parent) {
        Node node = new Node("#");
		node.next(parent);
        return node;
    }

    private Node defStatement(Node parent) throws Exception {
		Token token = lexer.token();
		if (com.runbox.debug.script.expression.token.Token.routine(token.name())) {
			RoutineNode routine = new RoutineNode(token.name(), block);		
			routine.arguments(convert(lexer.token().name()));			
			EndNode end = defStatement(routine);
			// print(routine, "", "");
			skip();
			if (!blockEnd(lexer.peek(true))) {
				return statement(parent);
			}
			return parent;
		} 
		throw new Exception("invalid routine name -> " + token.name());				
	}    

    private EndNode defStatement(RoutineNode parent) throws Exception {
		List<ReturnNode> returns = this.returns;
        this.returns = new LinkedList<ReturnNode>();
		List<CommandNode> commands = this.commands;
		this.commands = new LinkedList<CommandNode>();
		List<UnsolvedNode> unsolveds = this.unsolveds;
		this.unsolveds = new LinkedList<UnsolvedNode>();		
        Token token = lexer.token();
        if (token.name().equals("{")) {
            BeginNode begin = new BeginNode();
			parent.right(begin);
            BlockNode block = this.block; this.block = parent;
            Node front = blockStatement(begin);
            this.block = block;
            token = lexer.token();
            if (token.name().equals("}")) {
                EndNode end = new EndNode(parent);
                parent.left(end); front.next(end);
                returns(parent); this.returns = returns;
				commands(parent); this.commands = commands;
				unsolveds(parent); this.unsolveds = unsolveds;
                return end;
            }
        }
        throw new Exception("invalid routine statement -> " + token.name());
    }

    private Node ifStatement(Node parent) throws Exception {
        IfNode node = new IfNode(lexer.token().name());
        parent.next(node);
        EndNode end = ifStatement(node);
        skip();
        if (!blockEnd(lexer.peek(true))) {
            return statement(end);
        }
        return end;
    }

    private EndNode ifStatement(IfNode parent) throws Exception {
        Token token = lexer.token();
        if (token.name().equals("{")) {
            BeginNode begin = new BeginNode();
            parent.right(begin);
            BlockNode block = this.block; this.block = parent;
            Node front = blockStatement(begin);
            this.block = block;
            token = lexer.token();
            if (token.name().equals("}")) {
                EndNode end = new EndNode(parent);
				parent.left(end); front.next(end);
                token = lexer.peek(true);
                if (token.name().equals("else")) {
					return elseStatement(parent);
                }
                return end;
            }
        }
        throw new Exception("invalid if statement -> " + token.name());
    }

    private EndNode elseStatement(IfNode parent) throws Exception {
        Token token = lexer.token();
        ElseNode node = new ElseNode();        
        EndNode end = elseStatement(node);
        parent.left().next(end);
		parent.left(node);
        return end;
    }	

    private EndNode elseStatement(ElseNode parent) throws Exception {
        Token token = lexer.token();
        if (token.name().equals("{")) {
            BeginNode begin = new BeginNode();
			parent.right(begin);
            BlockNode block = this.block; this.block = parent;
            Node front = blockStatement(begin);
            this.block = block;
            token = lexer.token();
            if (token.name().equals("}")) {
                EndNode end = new EndNode(parent);
				parent.left(end); front.next(end);
                return end;
            }
        }
        throw new Exception("invalid else statement -> " + token.name());
    }

    private Node whileStatement(Node parent) throws Exception {		
        WhileNode node = new WhileNode(lexer.token().name());
        parent.next(node);
        EndNode end = whileStatement(node);
        skip();
        if (!blockEnd(lexer.peek(true))) {
            return statement(end);
        }
        return end;
    }

    private EndNode whileStatement(WhileNode parent) throws Exception {
		List<BreakNode> breaks = this.breaks;        
        this.breaks = new LinkedList<BreakNode>();
		List<ContinueNode> continues = this.continues;
		this.continues = new LinkedList<ContinueNode>();		
        Token token = lexer.token();        
        if (token.name().equals("{")) {
            BeginNode begin = new BeginNode();
            parent.right(begin);
            BlockNode block = this.block; this.block = parent;
            Node front = blockStatement(begin);
            this.block = block;
            token = lexer.token();
            if (token.name().equals("}")) {
                EndNode end = new EndNode(parent);
                parent.left(end); front.next(parent);
				breaks(parent); this.breaks = breaks;
                continues(parent); this.continues = continues;				
                return end;
            }
        }
        throw new Exception("invalid while statement -> " + token.name());
    }

    private Node commandStatement(Node parent, Token token) throws Exception {		
        CommandNode node = new CommandNode(token.name());
        parent.next(node);		
        if (lexer.peek(true).name().equals("{")) {			
            RoutineNode routine = commandStatement();
            node.routine(routine);
        }
        token = lexer.token();
        if (token.name().equals(";")) {
            skip();
            if (!blockEnd(lexer.peek(true))) {
				commands.add(commands.size(), node);
                return statement(node);
            }
			commands.add(commands.size(), node);
			return node;
        }
        throw new Exception("invalid command statement -> " + token.name());
    }

    private RoutineNode commandStatement() throws Exception {
        RoutineNode routine = new RoutineNode("$routine", null);
		defStatement(routine); return routine;
    }

    private Node unsolvedStatement(Node parent, Token token) throws Exception {
        UnsolvedNode node = new UnsolvedNode(token.name());
        parent.next(node);
        token = lexer.token();
        if (token.name().equals(";")) {
            skip();
            if (!blockEnd(lexer.peek(true))) {
				unsolveds.add(unsolveds.size(), node);
                return statement(node);
            }
			unsolveds.add(unsolveds.size(), node);
            return node;
        }
        throw new Exception("invalid expression statement -> " + token.name());
    }
    
    private Node continueStatement(Node parent) throws Exception {
        ContinueNode node = new ContinueNode();
        parent.next(node);
        Token token = lexer.token();
        if (token.name().equals(";")) {
            skip();
            if (!blockEnd(lexer.peek(true))) {
                Node front = statement(node);
                continues.add(continues.size(), node);
                return front;
            }
            continues.add(continues.size(), node);
            return node;
        }
        throw new Exception("invalid continue statement -> " + token.name());
    }

    private Node breakStatement(Node parent) throws Exception {
        BreakNode node = new BreakNode();
        parent.next(node);
        Token token = lexer.token();
        if (token.name().equals(";")) {
            skip();
            if (!blockEnd(lexer.peek(true))) {
                Node front = statement(node);
                breaks.add(breaks.size(), node);
                return front;
            }
            breaks.add(breaks.size(), node);
            return node;
        }
        throw new Exception("invalid break statement -> " + token.name());
    }

	private Node returnStatement(Node parent) throws Exception {
		ReturnNode node = (lexer.peek(true).name().equals(";") ?
						   new ReturnNode(null) :
						   new ReturnNode(lexer.token().name()));		
		parent.next(node);
		Token token = lexer.token();
        if (token.name().equals(";")) {
            skip();
            if (!blockEnd(lexer.peek(true))) {
                Node front = statement(node);
                returns.add(returns.size(), node);
                return front;
            }
            returns.add(returns.size(), node);
            return node;
        }
        throw new Exception("invalid return statement -> " + token.name());
	}

    private void skip() throws Exception {
        while (lexer.peek(true).name().equals(";")) {
            lexer.token();
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

	public List<String> convert(String str) throws Exception {
		List<String> names = new LinkedList<String>();
        if (str.equals("()")) {
            return names;
        } else if ('(' == str.charAt(0) && ')' == str.charAt(str.length() - 1)) {
            str = str.substring(1, str.length() - 1);
            for (String name : str.split(",", -1)) {
                name = name.trim();
                if (com.runbox.debug.script.expression.token.Token.auto(name)) {
                    names.add(name);
                } else {
                    throw new Exception("invalid routine argument -> " + name);
                }
            }
            return names;
        } 
        throw new Exception("invalid routine argument -> " + str);
	}       

    private List<ContinueNode> continues = null;

    private void continues(WhileNode node) {
        for (ContinueNode entry : continues) {     
            entry.step(entry.next());
            entry.next(node);
        }
        continues.clear();
    }

    private List<BreakNode> breaks = null;

    private void breaks(WhileNode node) {
        for (BreakNode entry : breaks) {
            entry.step(entry.next());
            entry.next(node.left());
        }
        breaks.clear();
    }

	private List<CommandNode> commands = null;

	private void commands(BlockNode node) {
		for (CommandNode entry : commands) {			
			entry.end((EndNode)node.left());			
		}
		commands.clear();
	}
	
	private List<ReturnNode> returns = null;

    private void returns(RoutineNode node) {
        for (ReturnNode entry : returns) {
            entry.step(entry.next());
            entry.next(node.left());
            entry.routine(node);
        }
        returns.clear();
    }

	private List<UnsolvedNode> unsolveds = null;

	private void unsolveds(BlockNode node) {
		for (UnsolvedNode entry : unsolveds) {
			entry.end((EndNode)node.left());			
		}
		unsolveds.clear();
	}

	private List<Node> nodes = new LinkedList<Node>();

    public void print(Node node, String tag, String indent) {
        if (null != node) {
            if (!nodes.contains(node)) {
                nodes.add(node);				
                if (node instanceof RoutineNode) {
                    RoutineNode routine = (RoutineNode)node;
                    System.out.print(indent + "def " + routine.name());
                    print(routine.right(), "[R]", indent);
                    print(routine.left(), "[L]", indent);
                } else if (node instanceof ConditionNode) {
                    ConditionNode condition = (ConditionNode)node;
                    System.out.print(indent + condition.name() + " " + condition.condition());
                    print(condition.right(), "[R]", indent);
					if (node instanceof WhileNode) {
						indent += "    ";
					}
                    print(condition.left(), "[L]", indent);
                } else if (node instanceof ElseNode) {
					ElseNode block = (ElseNode)node;
					System.out.print(node.name());
					print(block.right(), "[R]", indent);
                    print(block.left(), "[L]", indent);
				} else if (node instanceof BeginNode) {
					System.out.println(" " + node.name());
					print(node.next(), "", indent + "    ");
				} else if (node instanceof EndNode) {
					if (4 <= indent.length()) {
						indent = indent.substring(0, indent.length() - 4);
					}
					System.out.println(indent + node.name());
					print(node.next(), "", indent);
				} else if (node instanceof GotoNode) {				   
                    if (node instanceof ReturnNode) {                    
                        System.out.println(indent + node.name() + " " + ((ReturnNode)node).expression());                        
                    } else {   
                        System.out.println(indent + node.name());                        
                    }
                    print(((GotoNode)node).step(), "", indent);
                } else {
                    System.out.println(indent + node.name() + tag);
					print(node.next(), "", indent);
                }                                
            }
        }
    }
}
