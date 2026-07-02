package com.minilang.middle.ast;

public class WhileStmt extends ASTNode{
    public final Expr condition;
    public final ASTNode body;
    public WhileStmt(Expr condition, ASTNode body,int line,int col){super(line,col); this.condition=condition; this.body=body;}
}
