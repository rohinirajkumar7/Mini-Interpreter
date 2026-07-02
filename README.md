# MiniLang Interpreter

A fully-functional interpreter for MiniLang - a custom programming language built with Java, ANTLR4, and Maven. This project demonstrates compiler design principles including lexical analysis, parsing, AST construction, semantic analysis, and runtime execution.

## 🚀 Features

- **Variables & Assignments**: Declare and modify variables with `let` keyword
- **Arithmetic Operations**: Support for `+`, `-`, `*`, `/` with proper operator precedence
- **Comparison Operators**: `==`, `!=`, `<`, `>`, `<=`, `>=`
- **Boolean Logic**: `&&`, `||`, `!` with short-circuit evaluation
- **Control Flow**: `if-else` statements and `while` loops
- **Functions**: Named functions with parameters and return values
- **Recursion**: Full support for recursive function calls
- **Built-in Functions**: `print()` for console output
- **Interactive REPL**: Read-Eval-Print Loop for interactive programming
- **Error Handling**: Division by zero protection, undefined variable detection, argument count validation

## 📋 Prerequisites

- **Java 17** or higher
- **Apache Maven 3.6+**
- **ANTLR 4.13.0** (automatically managed by Maven)

## 🛠️ Installation

### 1. Clone the Repository

git clone https://github.com/yourusername/mini-interpreter.git
cd mini-interpreter



### 2. Build the Project

mvn clean compile


This will:
- Download all dependencies
- Generate ANTLR parser files from the grammar
- Compile all Java sources

## 🎯 Usage

### Running MiniLang Files

Execute a `.mini` file:

mvn exec:java "-Dexec.args=examples/advanced_test.mini"


### Interactive REPL Mode

Start the interactive interpreter:

mvn exec:java

Then type MiniLang code directly:

MiniLang REPL. Type 'exit' to quit.

let x = 100;
let y = 28;
print(x + y);
128
exit


## 📝 Language Syntax

### Variable Declaration

let x = 10;
let name = "MiniLang";
let flag = true;


### Functions

fun factorial(n) {
if (n == 0) return 1;
return n * factorial(n - 1);
}

print(factorial(5)); // Output: 120


### Control Flow

if (x > 10) {
print("Greater");
} else {
print("Smaller");
}

let i = 0;
while (i < 5) {
print(i);
i = i + 1;
}


### Expressions

let result = (5 + 3) * 2 - 8 / 4;
let comparison = (x >= 10) && (y < 20);
let negation = !flag;


## 📂 Project Structure
```
MiniLang/
├─ pom.xml
├─ src/main/antlr4/com/minilang/MiniLang.g4
├─ src/main/java/com/minilang/
│   ├─ cli/
│   │   └─ Main.java
│   ├─ frontend/
│   │   └─ ASTBuilder.java
│   ├─ middle/
│   │   ├─ ast/
│   │   │   ├─ ASTNode.java
│   │   │   ├─ Expr.java
│   │   │   ├─ IntegerLiteral.java
│   │   │   ├─ BooleanLiteral.java
│   │   │   ├─ StringLiteral.java
│   │   │   ├─ BinaryExpr.java
│   │   │   ├─ UnaryExpr.java
│   │   │   ├─ VarDecl.java
│   │   │   ├─ Assignment.java
│   │   │   ├─ BlockStmt.java
│   │   │   ├─ IfStmt.java
│   │   │   ├─ WhileStmt.java
│   │   │   ├─ FunctionDecl.java
│   │   │   ├─ FunctionCall.java
│   │   │   └─ ReturnStmt.java
│   │   └─ semantic/
│   │       └─ Environment.java
│   ├─ backend/
│   │   └─ Interpreter.java
│   └─ stdlib/
│       └─ StdLib.java
├─ examples/
│   └─ advanced_test.mini
└─ README.md
```


## 🏗️ Architecture

### 1. **Lexical Analysis (ANTLR Lexer)**
Tokenizes input source code into meaningful symbols.

### 2. **Parsing (ANTLR Parser)**
Constructs a parse tree following the MiniLang grammar rules.

### 3. **AST Construction (ASTBuilder)**
Transforms the parse tree into a simplified Abstract Syntax Tree.

### 4. **Semantic Analysis (Environment)**
Manages variable scopes, symbol tables, and type information.

### 5. **Interpretation (Interpreter)**
Executes the AST using a tree-walking interpreter with dynamic type evaluation.

## 🔧 Key Implementation Details

### Variable Reference Resolution
- **VarRef** class handles variable lookups in expressions
- Environment chain supports nested scopes (global → local)

### Function Call Execution
- Parameters bound to local environment before execution
- **ReturnException** enables early exit from functions
- Argument count validated before invocation

### Control Flow
- Boolean expressions automatically converted to truthy values
- Block statements create new scopes except at REPL top-level
- While loops evaluated with condition at each iteration

## 🎓 Example Programs

### Recursive Fibonacci

fun fib(n) {
if (n <= 1) return n;
return fib(n - 1) + fib(n - 2);
}

print(fib(10)); // Output: 55


### Sum Loop

let sum = 0;
let i = 1;
while (i <= 100) {
sum = sum + i;
i = i + 1;
}
print(sum); // Output: 5050


## 🐛 Debugging

### Enable Maven Debug Output

mvn clean compile -X


### Check Generated Parser Files

ls target/generated-sources/antlr4/com/minilang/parser/


Should contain: `MiniLangLexer.java`, `MiniLangParser.java`, `MiniLangBaseVisitor.java`

## 🤝 Contributing

Contributions are welcome! To contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- **ANTLR4** by Terence Parr for the powerful parser generator
- **Apache Maven** for dependency management
- Compiler design principles from *Crafting Interpreters* by Robert Nystrom

## 📧 Contact

Email- rohini.rajkumar7@gmail.com

## 🗺️ Future Roadmap

- [ ] Add string manipulation functions
- [ ] Implement arrays and data structures
- [ ] Support for lambda/anonymous functions
- [ ] Add more built-in functions (input, file I/O)
- [ ] Implement a bytecode compiler
- [ ] Add static type checking
- [ ] Create VS Code syntax highlighting extension

---

**Built with ❤️ using Java 17, ANTLR4, and Maven**
