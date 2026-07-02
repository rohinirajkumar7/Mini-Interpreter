package com.minilang.middle.ast;

public class StringLiteral extends Expr {
    public final String value;
    public StringLiteral(String value,int line,int col){super(line,col); this.value=value;}
}
