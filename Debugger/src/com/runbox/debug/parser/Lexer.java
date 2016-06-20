package com.runbox.debug.parser;

/**
 * Created by huangmengmeng01 on 2016/5/24.
 */
public interface Lexer {

    Token token() throws Exception;
    Token peek() throws Exception;
    void clean() throws Exception;
}
