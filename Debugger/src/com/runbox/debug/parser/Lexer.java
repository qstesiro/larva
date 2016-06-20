package com.runbox.debug.parser;

/**
 * Created by qstesiro
 */
public interface Lexer {

    Token token() throws Exception;
    Token peek() throws Exception;
    void clean() throws Exception;
}
