package com.runbox.debug.parser.command.breakpoint;


/**
 * Created by huangmengmeng01 on 2016/5/27.
 */
public class Number extends Token {

    public Number(int number) {
        super(null);
        this.number = number;
    }

    private int number = 0;

    public int number(int number) {
        int previous = this.number;
        this.number = number;
        return previous;
    }

    public int number() {
        return number;
    }
}
