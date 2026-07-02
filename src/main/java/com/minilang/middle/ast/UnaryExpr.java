package com.minilang.middle.ast;

public class UnaryExpr extends Expr {
    public enum Op{NEG,NOT}
    public final Op op;
    public final Expr expr;
    public UnaryExpr(Op op,Expr expr,int line,int col){super(line,col); this.op=op; this.expr=expr;}
}
