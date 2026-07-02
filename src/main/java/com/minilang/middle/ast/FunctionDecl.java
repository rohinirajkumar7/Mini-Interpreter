package com.minilang.middle.ast;

import java.util.List;
public class FunctionDecl extends ASTNode{
    public final String name;
    public final List<String> params;
    public final BlockStmt body;
    public FunctionDecl(String name,List<String> params,BlockStmt body,int line,int col){super(line,col); this.name=name; this.params=params; this.body=body;}
}
