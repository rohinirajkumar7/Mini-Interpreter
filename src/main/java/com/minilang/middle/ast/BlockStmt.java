package com.minilang.middle.ast;

import java.util.List;
public class BlockStmt extends ASTNode{
    public final List<ASTNode> statements;
    public BlockStmt(List<ASTNode> statements,int line,int col){super(line,col); this.statements=statements;}
}
