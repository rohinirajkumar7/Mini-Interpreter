package com.minilang.middle.ast;

public class IfStmt extends ASTNode{
    public final Expr condition;
    public final ASTNode thenBranch, elseBranch;
    public IfStmt(Expr condition, ASTNode thenBranch, ASTNode elseBranch,int line,int col){super(line,col); this.condition=condition; this.thenBranch=thenBranch; this.elseBranch=elseBranch;}
}
