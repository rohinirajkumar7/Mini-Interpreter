package com.minilang.middle.ast;

public class VarDecl extends ASTNode {
    public final String name;
    public final Expr init;
    public VarDecl(String name,Expr init,int line,int col){super(line,col); this.name=name; this.init=init;}
}
