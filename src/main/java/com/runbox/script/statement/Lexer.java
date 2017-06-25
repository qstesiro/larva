package com.runbox.script.statement;

import com.runbox.script.statement.token.Token;

public abstract class Lexer {

    public abstract Token token() throws Exception;
    public abstract Token peek(boolean flag) throws Exception;
    public abstract void clean() throws Exception;

	protected abstract void preprocess();
    protected abstract char lookup();
    protected abstract char next();
    protected abstract void skip();    
    
    protected static boolean alphabet(char lookup) {
        if (('A' <= lookup && 'Z' >= lookup) ||
            ('a' <= lookup && 'z' >= lookup)) {
            return true;
        }
        return false;
    }

	private static final int BIN = 2;
	private static final int OCT = 8;
	private static final int DEC = 10;
	private static final int HEX = 16;
	
    protected static boolean number(char lookup, int radix) {
        if (BIN == radix) {
			if ('0' == lookup || '1' == lookup) {
				return true;
			}
		} else if (OCT == radix) {
            if ('0' <= lookup && '7' >= lookup) {
                return true;
            }
        } else if (DEC == radix) {
            if ('0' <= lookup && '9' >= lookup) {
                return true;
            }
        } else if (HEX == radix) {
            if (('0' <= lookup && '9' >= lookup) ||
                ('A' <= lookup && 'F' >= lookup) ||
                ('a' <= lookup && 'f' >= lookup)) {
                return true;
            }
        }
        return false;
    }

    protected boolean space(char letter) {
        boolean condition = ' ' == letter;
        condition = condition || '\t' == letter;
        condition = condition || '\r' == letter;
        condition = condition || '\n' == letter;
        return condition;
    }

	protected char skipComment() throws Exception {
		char letter = '\0';
		if ('/' == lookup()) {
			next();
			if ('/' == lookup()) {
				next();
				while (true) {								
					if ('\r' == lookup() || '\n' == lookup() || '#' == lookup()) {
						break;
					}
					next();
				}
			} else if ('*' == lookup()) {
				next();
				while (true) {
					if ('*' == lookup()) {
						next();
						if ('/' == lookup()) {
							next(); break;
						}
					} else if ('#' == lookup()) {
						throw new Exception("invalid comment");
					}
					next();
				}							
			} else {
				letter = '/';
			}
		}
		return letter;
	}
}
