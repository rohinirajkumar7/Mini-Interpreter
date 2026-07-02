package com.minilang.middle.ast;

public abstract class ASTNode {
    public final int line, col;
    protected ASTNode(int line, int col) { this.line=line; this.col=col; }
}
