package com.minilang.middle.ast;

public class VarRef extends Expr {
    public final String name;
    public VarRef(String name, int line, int col) {
        super(line, col);
        this.name = name;
    }
}
