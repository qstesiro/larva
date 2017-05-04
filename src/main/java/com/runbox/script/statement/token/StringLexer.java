package com.runbox.script.statement.token;

import com.runbox.script.statement.Lexer;

public class StringLexer extends Lexer {

    private String statement = null;

    public StringLexer(String statement) {
        this.statement = statement; preprocess();
    }

    @Override
    public void clean() throws Exception {

    }

    @Override
    public Token token() throws Exception {
        if (null != statement) {
            if (null == peek) {
                skip(); StringBuffer buffer = new StringBuffer();
                while (true) {
					if (null != front) {
						if (front.name().equals("def")) {
							while ('(' != lookup()) {
								buffer.append(next());
							}
							return token(buffer);
						}
					}
					if (Token.bound(lookup())) {
                        if (0 == buffer.length()) {                            
							return token(buffer.append(next()));
                        } else {							
							return token(buffer);
                        }
                    } else {
                        buffer.append(next());
                        if (key(buffer)) {                            
							return token(buffer);
                        }
                    }                    
                }
            } else {
                Token token = peek;
                peek = null;
                return token;
            }
        }
        throw new Exception("invalid statement");
    }

	private Token front = null;

	public Token token(StringBuffer buffer) {
		front = new Token(buffer.toString());		
		return front;
	}
	
    private Token peek = null;

    @Override
    public Token peek(boolean flag) throws Exception {
        if (null == peek && flag) {
            peek = token();
        }
        return peek;
    }

	@Override
	protected void preprocess() {
		StringBuffer buffer = new StringBuffer();
		while ('#' != lookup()) {
			try {
				if ('/' == skipComment()) buffer.append('/');
			} catch (Exception e) {
				e.printStackTrace();
			}
			buffer.append(next());
		}
		statement = buffer.toString();
		index = 0;
	}
	
    private int index = 0;

    @Override
    protected char lookup() {
        if (index < statement.length()) {
            return statement.charAt(index);
        }
        return '#';
    }
	
    @Override
    protected char next() {
        if (index < statement.length()) {
            return statement.charAt(index++);
        }
        return '#';
    }

    @Override
    protected void skip() {
        while ((index < statement.length())) {
            if (!space(statement.charAt(index))) {
                return;
            }
            index++;
        }
    }    

    private boolean key(StringBuffer buffer) throws Exception {
        if (Token.key(buffer.toString()) && space(lookup())) {
            return true;
        }
        return false;
    }
}
