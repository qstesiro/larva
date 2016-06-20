package com.runbox.debug.parser.statement.token;

import com.runbox.debug.parser.Lexer;

import java.io.*;

/**
 * Created by qstesiro
 */
public class FileParser implements Lexer {

    private BufferedReader reader = null;

    public FileParser(File file) throws Exception {
        if (null != file) {
            if (file.exists()) {
                reader = new BufferedReader(new FileReader(file));
                return;
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
                    if (Token.bound(lookup())) {
                        if (0 == buffer.length()) {
                            return new Token(buffer.append(next()).toString());
                        } else {
                            return new Token(buffer.toString());
                        }
                    } else {
                        buffer.append(next());
                        if (key(buffer)) {
                            return new Token(buffer.toString());
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

    private Token peek = null;

    @Override
    public Token peek() throws Exception {
        if (null == peek) {
            peek = token();
        }
        return peek;
    }

    public void clean() throws IOException {
        if (null != reader) {
            reader.close();
        }
    }

    private char lookup() throws IOException {
        reader.mark(1);
        char letter = (char)reader.read();
        if (0xffff != letter) {
            reader.reset();
            return letter;
        }
        reader.reset();
        return '#';
    }

    private char next() throws IOException {
        char letter = (char)reader.read();
        if (0xffff != letter) {
            return letter;
        }
        return '#';
    }

    private void skip() throws IOException {
        while (true) {
            reader.mark(1);
            char letter = (char)reader.read();
            if (!space(letter) || 0xffff == letter) {
                reader.reset();
                break;
            }
        }
    }

    private boolean space(char letter) {
        boolean condition = ' ' == letter;
        condition = condition || '\t' == letter;
        condition = condition || '\r' == letter;
        condition = condition || '\n' == letter;
        return condition;
    }

    private boolean key(StringBuffer buffer) throws Exception {
        if (Token.key(buffer.toString()) && space(lookup())) {
            return true;
        }
        return false;
    }
}
