package com.runbox.script;

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

public class Engine {

    private Engine() {

    }    

    private static Engine engine = new Engine();

    public static Engine instance() {
        return engine;
    }

    private RootNode root = new RootNode("$root");
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
			Node node = statement(begin);
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
                if (CommandFactory.command(token.name())) {
                    return commandStatement(parent, token);
                } else {
                    return expressionStatement(parent, token);
                }
            }
            throw new Exception("invalid statement -> " + token.name());
        }
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
                return end;
            }
        }
        throw new Exception("invalid else statement -> " + token.name());
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
		List<ContinueNode> continues = this.continues;
        List<BreakNode> breaks = this.breaks;
        this.continues = new LinkedList<ContinueNode>();
        this.breaks = new LinkedList<BreakNode>();
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
                continues(parent); this.continues = continues;
				breaks(parent); this.breaks = breaks;             
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
                return statement(node);
            }
            return node;
        }
        throw new Exception("invalid command statement -> " + token.name());
    }

    private RoutineNode commandStatement() throws Exception {
        RoutineNode routine = new RoutineNode("$routine", null);
        Token token = lexer.token();
        if (token.name().equals("{")) {            
            BeginNode begin = new BeginNode();
            routine.right(begin);
            BlockNode block = this.block; this.block = routine;
            Node front = blockStatement(begin);
            this.block = block;
            token = lexer.token();
            if (token.name().equals("}")) {
                Node end = new EndNode(routine);
                routine.left(end); front.next(end);
            }
        }
        return routine;
    }

    private Node expressionStatement(Node parent, Token token) throws Exception {
        Node node = new ExpressionNode(token.name());
        parent.next(node);
        token = lexer.token();
        if (token.name().equals(";")) {
            skip();
            if (!blockEnd(lexer.peek(true))) {
                return statement(node);
            }
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

	private List<ReturnNode> returns = null;

    private void returns(RoutineNode node) {
        for (ReturnNode entry : returns) {
            entry.step(entry.next());
            entry.next(node.left());
            entry.routine(node);
        }
        returns.clear();
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

    private Stack<Frame> frames = new Stack<Frame>() {{push(new Frame(root, 0));}};

	public BlockNode push(BlockNode block) {
		if (null != block) {
			Frame frame = frames.push(new Frame(block, autos.size()));
			if (null != frame) {
				return frame.block();
			}
		}
		return null;
	}

	public void pop(BlockNode block) {
		if (null != block) {
			while (!frames.empty()) {
				if (pop() == block) {
					break;
				}								
			}
		}
	}
	
	private BlockNode pop() {
		if (!frames.empty()) {
			Frame frame = frames.pop();
			autos.setSize(frame.index());
			if (frame.block() instanceof RoutineNode) {
				autos.setSize(autos.size() - ((RoutineNode)frame.block()).count());
			}			
			return frame.block();
		}
		return null;
	}   	

    public RoutineNode findRoutine(String name) {
        if (null != name) {
			for (int i = frames.size() - 1; i >= 0; --i) {
				RoutineNode routine = frames.get(i).block().find(name);
                if (null != routine) {
                    return routine;
                }
			}
		}
		return null;
    }	
	
	private Stack<Token> autos = new Stack<Token>();

	public Token append(Token auto) {
		if (null != auto) {
			return autos.push(auto);
		}
		return null;
	}
	
	public Token findAuto(String name) {
		if (null != name) {
			for (int i = autos.size() - 1; i >= 0; --i) {
				Token auto = autos.get(i);
				if (auto.name().equals(name)) {
					return auto;
				}
			}
		}
		return null;
	} 

    private static class Frame {

		public Frame (BlockNode block, int index) {
			this.block = block;
			this.index = index;
		}
		
		BlockNode block = null;

		public void block(BlockNode block) {
			this.block = block;
		}

		public BlockNode block() {
			return block;
		}
		
		int index = 0;

		public void index(int index) {
			this.index = index;
		}

		public int index() {
			return index;
		}
	}       
}