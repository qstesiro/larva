package com.runbox.script.statement.token;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import com.runbox.script.statement.Lexer;

public class FileLexer extends Lexer {

    private BufferedReader reader = null;

    public FileLexer(File file) throws Exception {
        if (null != file) {
            if (file.exists()) {
                reader = new BufferedReader(new FileReader(file));
				preprocess(); return;
            }
        }
        throw new Exception("invalid file");
    }
	
    @Override
    public Token token() throws Exception {
        if (null != reader) {
            if (null == peek) {
                skip();
                StringBuffer buffer = new StringBuffer();
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
    public void clean() throws Exception {
        if (null != reader) {
            reader.close();
        }
    }

	@Override
	protected void preprocess() {		
		BufferedWriter writer = null;
		try {
			File file = File.createTempFile(String.valueOf(System.currentTimeMillis()), null);
			writer = new BufferedWriter(new FileWriter(file));
			while ('#' != lookup()) {
				if ('/' == skipComment()) writer.write('/');
				writer.write(next());
			}
			writer.flush();
			clean();
			reader = new BufferedReader(new FileReader(file));		   
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
	}	
	
    @Override
    protected char lookup() {
        try {
            reader.mark(1);
            char letter = (char)reader.read();
            if (0xffff != letter) {
                reader.reset();
                return letter;
            }
            reader.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return '#';
    }

    @Override
    protected char next() {
        try {
            char letter = (char)reader.read();
            if (0xffff != letter) {
                return letter;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return '#';
    }	
	
    @Override
    protected void skip() {        
        while (true) {
            try {
                reader.mark(1);
                char letter = (char)reader.read();            
                if (!space(letter) || 0xffff == letter) {
                    reader.reset();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }    

    private boolean key(StringBuffer buffer) throws Exception {
        if (Token.key(buffer.toString()) && space(lookup())) {
            return true;
        }
        return false;
    }
}
