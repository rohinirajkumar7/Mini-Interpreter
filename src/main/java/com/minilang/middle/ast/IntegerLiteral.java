package com.minilang.middle.ast;

public class IntegerLiteral extends Expr {
    public final long value;
    public IntegerLiteral(long value,int line,int col){super(line,col); this.value=value;}
}
