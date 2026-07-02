package com.minilang.middle.ast;

import java.util.List;
public class FunctionCall extends Expr{
    public final String name;
    public final List<Expr> args;
    public FunctionCall(String name,List<Expr> args,int line,int col){super(line,col); this.name=name; this.args=args;}
}
