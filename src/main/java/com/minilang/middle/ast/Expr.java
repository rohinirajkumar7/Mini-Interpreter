package com.minilang.middle.ast;

public abstract class Expr extends ASTNode {
    protected Expr(int line, int col) { super(line,col); }
}
