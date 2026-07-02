package com.minilang.middle.ast;

public class BooleanLiteral extends Expr {
    public final boolean value;
    public BooleanLiteral(boolean value,int line,int col){super(line,col); this.value=value;}
}
