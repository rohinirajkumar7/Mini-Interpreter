package com.minilang.backend;

import com.minilang.middle.ast.*;
import com.minilang.middle.semantic.Environment;
import com.minilang.stdlib.StdLib;
import java.util.*;

// Helper exception for return control flow
class ReturnException extends RuntimeException {
    public final Object value;
    public ReturnException(Object value) { 
        this.value = value; 
    }
}

public class Interpreter {

    private final Environment global = new Environment(null);

    public Interpreter() {
        StdLib.load(global);
    }

    public Object interpret(ASTNode node) { 
        return execute(node, global); 
    }

    private Object execute(ASTNode node, Environment env) {
    if (node instanceof VarDecl vd) {
        Object val = vd.init != null ? evaluate(vd.init, env) : null;
        env.define(vd.name, val);
        return val;
    } else if (node instanceof Assignment as) {
        Object val = evaluate(as.value, env);
        env.assign(as.name, val);
        return val;
    } else if (node instanceof BlockStmt blk) {
        Object last = null;
        // FIX: Use parent environment for top-level blocks (REPL)
        Environment local = (env == global) ? global : new Environment(env);
        for (ASTNode s : blk.statements) {
            last = execute(s, local);
        }
        return last;
        } else if (node instanceof IfStmt ifs) {
            boolean cond = truthy(evaluate(ifs.condition, env));
            if (cond) {
                return execute(ifs.thenBranch, env);
            } else if (ifs.elseBranch != null) {
                return execute(ifs.elseBranch, env);
            }
        } else if (node instanceof WhileStmt ws) {
            while (truthy(evaluate(ws.condition, env))) {
                execute(ws.body, env);
            }
        } else if (node instanceof FunctionDecl fd) {
            env.define(fd.name, fd);
            return fd;
        } else if (node instanceof ReturnStmt rs) {
            // Fixed: Throw exception for proper control flow
            Object val = rs.value != null ? evaluate(rs.value, env) : null;
            throw new ReturnException(val);
        } else if (node instanceof Expr e) {
            return evaluate(e, env);
        }
        return null;
    }

    private Object evaluate(Expr expr, Environment env) {
        if (expr instanceof IntegerLiteral il) {
            return il.value;
        }
        if (expr instanceof BooleanLiteral bl) {
            // Fixed: Keep as boolean instead of converting to Long
            return bl.value;
        }
        if (expr instanceof StringLiteral sl) {
            return sl.value;
        }
        if (expr instanceof BinaryExpr be) {
            Object leftObj = evaluate(be.left, env);
            Object rightObj = evaluate(be.right, env);
            
            // Handle numeric operations
            if (leftObj instanceof Long && rightObj instanceof Long) {
                long l = (long) leftObj;
                long r = (long) rightObj;
                
                return switch (be.op) {
                    case ADD -> l + r;
                    case SUB -> l - r;
                    case MUL -> l * r;
                    case DIV -> {
                        // Fixed: Division by zero check
                        if (r == 0) {
                            throw new RuntimeException("Division by zero at line " + be.line);
                        }
                        yield l / r;
                    }
                    case EQ -> l == r;  // Return boolean
                    case NEQ -> l != r;
                    case LT -> l < r;
                    case LE -> l <= r;
                    case GT -> l > r;
                    case GE -> l >= r;
                    case AND -> truthy(l) && truthy(r);
                    case OR -> truthy(l) || truthy(r);
                };
            }
            
            // Handle boolean operations
            return switch (be.op) {
                case AND -> truthy(leftObj) && truthy(rightObj);
                case OR -> truthy(leftObj) || truthy(rightObj);
                case EQ -> Objects.equals(leftObj, rightObj);
                case NEQ -> !Objects.equals(leftObj, rightObj);
                default -> throw new RuntimeException("Invalid operation on non-numeric types at line " + be.line);
            };
            
        } else if (expr instanceof UnaryExpr ue) {
            Object val = evaluate(ue.expr, env);
            return switch (ue.op) {
                case NEG -> {
                    if (val instanceof Long l) yield -l;
                    throw new RuntimeException("Cannot negate non-numeric value at line " + ue.line);
                }
                case NOT -> !truthy(val);
            };
        } else if (expr instanceof FunctionCall fc) {
            Object obj = env.get(fc.name);
            
            if (obj instanceof FunctionDecl fdecl) {
                // Fixed: Validate argument count
                if (fc.args.size() != fdecl.params.size()) {
                    throw new RuntimeException(
                        "Function '" + fc.name + "' expects " + fdecl.params.size() + 
                        " arguments, got " + fc.args.size() + " at line " + fc.line
                    );
                }
                
                Environment local = new Environment(env);
                List<String> params = fdecl.params;
                List<Expr> args = fc.args;
                
                // Bind parameters to arguments
                for (int i = 0; i < params.size(); i++) {
                    local.define(params.get(i), evaluate(args.get(i), env));
                }
                
                // Fixed: Catch return exception for proper control flow
                try {
                    execute(fdecl.body, local);
                    return null;  // No explicit return
                } catch (ReturnException re) {
                    return re.value;  // Return the value
                }
                
            } else if (obj instanceof StdLib.BuiltinFunction bf) {
                Object[] args = fc.args.stream()
                    .map(a -> evaluate(a, env))
                    .toArray();
                return bf.call(args);
            } else {
                throw new RuntimeException("Not a function: " + fc.name + " at line " + fc.line);
            }
        } else if (expr instanceof VarRef vr) {
            // Fixed: Use VarRef instead of VarDecl
            return env.get(vr.name);
        }
        return null;
    }

    private boolean truthy(Object val) {
        if (val instanceof Long l) return l != 0;
        if (val instanceof Boolean b) return b;
        if (val instanceof Integer i) return i != 0;
        return val != null;
    }

    public Environment getGlobal() {
        return global;
    }
}
