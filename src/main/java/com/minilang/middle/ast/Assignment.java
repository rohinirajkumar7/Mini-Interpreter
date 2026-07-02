package com.minilang.middle.ast;

public class Assignment extends ASTNode {
    public final String name;
    public final Expr value;
    public Assignment(String name,Expr value,int line,int col){super(line,col); this.name=name; this.value=value;}
}
