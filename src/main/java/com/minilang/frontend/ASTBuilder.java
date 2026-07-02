package com.minilang.frontend;

import com.minilang.middle.ast.*;
import com.minilang.parser.MiniLangBaseVisitor;
import com.minilang.parser.MiniLangParser;

import java.util.*;
import java.util.stream.Collectors;

public class ASTBuilder extends MiniLangBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitProgram(MiniLangParser.ProgramContext ctx) {
        List<ASTNode> statements = ctx.statement().stream()
                .map(this::visit)
                .collect(Collectors.toList());
        return new BlockStmt(statements, 0, 0);
    }

    @Override
    public ASTNode visitVarDecl(MiniLangParser.VarDeclContext ctx) {
        String name = ctx.Identifier().getText();
        Expr init = ctx.expression() != null ? (Expr) visit(ctx.expression()) : null;
        return new VarDecl(name, init, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public ASTNode visitAssignment(MiniLangParser.AssignmentContext ctx) {
        String name = ctx.Identifier().getText();
        Expr value = (Expr) visit(ctx.expression());
        return new Assignment(name, value, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public ASTNode visitBlock(MiniLangParser.BlockContext ctx) {
        List<ASTNode> statements = ctx.statement().stream()
                .map(this::visit)
                .collect(Collectors.toList());
        return new BlockStmt(statements, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public ASTNode visitIfStmt(MiniLangParser.IfStmtContext ctx) {
        Expr condition = (Expr) visit(ctx.expression());
        ASTNode thenBranch = visit(ctx.statement(0));
        ASTNode elseBranch = ctx.statement().size() > 1 ? visit(ctx.statement(1)) : null;
        return new IfStmt(condition, thenBranch, elseBranch, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public ASTNode visitWhileStmt(MiniLangParser.WhileStmtContext ctx) {
        Expr condition = (Expr) visit(ctx.expression());
        ASTNode body = visit(ctx.statement());
        return new WhileStmt(condition, body, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public ASTNode visitFunctionDecl(MiniLangParser.FunctionDeclContext ctx) {
        String name = ctx.Identifier().getText();
        List<String> params = new ArrayList<>();
        if (ctx.paramList() != null) {
            params = ctx.paramList().Identifier().stream()
                    .map(t -> t.getText())
                    .collect(Collectors.toList());
        }
        BlockStmt body = (BlockStmt) visit(ctx.block());
        return new FunctionDecl(name, params, body, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public ASTNode visitReturnStmt(MiniLangParser.ReturnStmtContext ctx) {
        Expr value = ctx.expression() != null ? (Expr) visit(ctx.expression()) : null;
        return new ReturnStmt(value, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public ASTNode visitExprStmt(MiniLangParser.ExprStmtContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public ASTNode visitFunctionCall(MiniLangParser.FunctionCallContext ctx) {
        String name = ctx.Identifier().getText();
        List<Expr> args = new ArrayList<>();
        if (ctx.argList() != null) {
            args = ctx.argList().expression().stream()
                    .map(e -> (Expr) visit(e))
                    .collect(Collectors.toList());
        }
        return new FunctionCall(name, args, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
public ASTNode visitExpression(MiniLangParser.ExpressionContext ctx) {
    if (ctx.IntegerLiteral() != null) {
        return new IntegerLiteral(Long.parseLong(ctx.IntegerLiteral().getText()), 
            ctx.start.getLine(), ctx.start.getCharPositionInLine());
    } else if (ctx.BooleanLiteral() != null) {
        boolean val = ctx.BooleanLiteral().getText().equals("true");
        return new BooleanLiteral(val, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    } else if (ctx.StringLiteral() != null) {
        String str = ctx.StringLiteral().getText();
        str = str.substring(1, str.length() - 1); // remove quotes
        return new StringLiteral(str, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    } 
    // FIXED: Check if it's a standalone identifier (not a function call)
    else if (ctx.Identifier() != null && ctx.functionCall() == null) {
        return new VarRef(ctx.Identifier().getText(), ctx.start.getLine(), ctx.start.getCharPositionInLine());
    } 
    else if (ctx.functionCall() != null) {
        return visit(ctx.functionCall());
    } 
    else if (ctx.op != null && ctx.expression().size() == 2) { // binary
        Expr left = (Expr) visit(ctx.expression(0));
        Expr right = (Expr) visit(ctx.expression(1));
        BinaryExpr.Op op = switch (ctx.op.getText()) {
            case "+" -> BinaryExpr.Op.ADD;
            case "-" -> BinaryExpr.Op.SUB;
            case "*" -> BinaryExpr.Op.MUL;
            case "/" -> BinaryExpr.Op.DIV;
            case "==" -> BinaryExpr.Op.EQ;
            case "!=" -> BinaryExpr.Op.NEQ;
            case "<" -> BinaryExpr.Op.LT;
            case "<=" -> BinaryExpr.Op.LE;
            case ">" -> BinaryExpr.Op.GT;
            case ">=" -> BinaryExpr.Op.GE;
            case "&&" -> BinaryExpr.Op.AND;
            case "||" -> BinaryExpr.Op.OR;
            default -> throw new RuntimeException("Unknown operator: " + ctx.op.getText());
        };
        return new BinaryExpr(op, left, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    } 
    else if (ctx.op != null && ctx.expression().size() == 1) { // unary
        Expr expr = (Expr) visit(ctx.expression(0));
        UnaryExpr.Op op = switch (ctx.op.getText()) {
            case "-" -> UnaryExpr.Op.NEG;
            case "!" -> UnaryExpr.Op.NOT;
            default -> throw new RuntimeException("Unknown unary operator: " + ctx.op.getText());
        };
        return new UnaryExpr(op, expr, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    } 
    else if (ctx.expression().size() == 1) { // parentheses
        return visit(ctx.expression(0));
    }
    throw new RuntimeException("Unsupported expression at line " + ctx.start.getLine());
}
}
