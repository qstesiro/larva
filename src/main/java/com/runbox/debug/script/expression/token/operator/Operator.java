package com.runbox.debug.script.expression.token.operator;

import java.util.HashMap;
import java.util.Map;

import com.runbox.debug.script.expression.token.Token;

public class Operator extends Token {

	public final static int LEVEL_I = 0x12;
    public final static int LEVEL_H = 0x11;
	public final static int LEVEL_G = 0x10;
    public final static int LEVEL_F = 0xf;
    public final static int LEVEL_E = 0xe;
    public final static int LEVEL_D = 0xd;
    public final static int LEVEL_C = 0xc;
    public final static int LEVEL_B = 0xb;
    public final static int LEVEL_A = 0xa;
    public final static int LEVEL_9 = 9;
    public final static int LEVEL_8 = 8;
    public final static int LEVEL_7 = 7;
    public final static int LEVEL_6 = 6;
    public final static int LEVEL_5 = 5;
    public final static int LEVEL_4 = 4;
    public final static int LEVEL_3 = 3;
    public final static int LEVEL_2 = 2;
    public final static int LEVEL_1 = 1;
    public final static int LEVEL_0 = 0;

    private final static Map<String, Integer> operators = new HashMap<String, Integer>() {{
        put("(", LEVEL_I); put("[", LEVEL_I);
		put("->", LEVEL_H);
		put(".", LEVEL_G);
        put("<-", LEVEL_F);
        put("!", LEVEL_E); put("~", LEVEL_E);
        put("+++", LEVEL_E); put("---", LEVEL_E); put("++", LEVEL_E); put("--", LEVEL_E);
        put("0+", LEVEL_E); put("0-", LEVEL_E);
        put("*", LEVEL_D); put("/", LEVEL_D); put("%", LEVEL_D);
        put("+", LEVEL_C); put("-", LEVEL_C);
        put("<<", LEVEL_B); put(">>", LEVEL_B); put(">>>", LEVEL_B);
        put("<", LEVEL_A); put("<=", LEVEL_A); put(">", LEVEL_A); put(">=", LEVEL_A); put("instanceof", LEVEL_A);
        put("==", LEVEL_9); put("!=", LEVEL_9);
        put("&", LEVEL_8);
        put("^", LEVEL_7);
        put("|", LEVEL_6);
        put("&&", LEVEL_5);
        put("||", LEVEL_4);
        put("?", LEVEL_3); put(":", LEVEL_3);
        put("=", LEVEL_2);
        put("+=", LEVEL_2); put("-=", LEVEL_2);
        put("*=", LEVEL_2); put("/=", LEVEL_2); put("%=", LEVEL_2);
        put("&=", LEVEL_2); put("^=", LEVEL_2); put("|=", LEVEL_2);
        put("<<=", LEVEL_2); put(">>=", LEVEL_2); put(">>>=", LEVEL_2);
        put(")", LEVEL_1); put("]", LEVEL_1); put(",", LEVEL_1);
        put("#", LEVEL_0);
    }};

    public Operator(String name) throws Exception {
        super(name);
        if (null != name) {
            if (operators.containsKey(name)) {
                this.level = operators.get(name);
            } else {
                throw new Exception("invalid operator");
            }
        }
    }

    private int level = 0;

    public void level(int level) {
        this.level = level;
    }

    public int level() {
        return level;
    }
}
