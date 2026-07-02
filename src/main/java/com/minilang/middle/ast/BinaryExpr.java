package com.minilang.middle.ast;

public class BinaryExpr extends Expr {
    public enum Op{ADD,SUB,MUL,DIV,EQ,NEQ,LT,GT,LE,GE,AND,OR}
    public final Op op;
    public final Expr left,right;
    public BinaryExpr(Op op,Expr left,Expr right,int line,int col){super(line,col); this.op=op; this.left=left; this.right=right;}
}
