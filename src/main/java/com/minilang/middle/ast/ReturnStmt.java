package com.minilang.middle.ast;

public class ReturnStmt extends ASTNode{
    public final Expr value;
    public ReturnStmt(Expr value,int line,int col){super(line,col); this.value=value;}
}
