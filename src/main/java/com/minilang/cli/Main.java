package com.minilang.cli;

import com.minilang.frontend.ASTBuilder;
import com.minilang.backend.Interpreter;
import com.minilang.middle.ast.ASTNode;
import com.minilang.parser.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        if(args.length >= 1) {
            runFile(args[0]);
        } else {
            runREPL();
        }
    }

    private static void runFile(String path) throws Exception {
        InputStream is = new FileInputStream(path);
        run(is);
    }

    private static void runREPL() {
        try (Scanner sc = new Scanner(System.in)) {
            Interpreter interp = new Interpreter();
            System.out.println("MiniLang REPL. Type 'exit' to quit.");
            while(true) {
                System.out.print("> ");
                String line = sc.nextLine();
                if(line.equals("exit")) break;
                runString(line, interp);
            }
        }
    }

    private static void run(InputStream is) throws Exception {
        CharStream input = CharStreams.fromStream(is);
        MiniLangLexer lexer = new MiniLangLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniLangParser parser = new MiniLangParser(tokens);
        ParseTree tree = parser.program();

        ASTBuilder builder = new ASTBuilder();
        ASTNode node = (ASTNode) builder.visit(tree);

        Interpreter interp = new Interpreter();
        interp.interpret(node);
    }

    private static void runString(String code, Interpreter interp) {
        try {
            CharStream input = CharStreams.fromString(code);
            MiniLangLexer lexer = new MiniLangLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MiniLangParser parser = new MiniLangParser(tokens);
            ParseTree tree = parser.program();

            ASTBuilder builder = new ASTBuilder();
            ASTNode node = (ASTNode) builder.visit(tree);

            interp.interpret(node);
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
