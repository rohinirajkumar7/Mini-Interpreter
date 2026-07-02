grammar MiniLang;

program: statement* EOF;

statement
    : varDecl
    | assignment
    | functionDecl
    | ifStmt
    | whileStmt
    | returnStmt
    | exprStmt
    | block
    ;

block: '{' statement* '}';

varDecl: 'let' Identifier ('=' expression)?  ';';

assignment: Identifier '=' expression ';';

exprStmt: expression ';';

functionDecl: 'fun' Identifier '(' paramList? ')' block;

paramList: Identifier (',' Identifier)*;

ifStmt: 'if' '(' expression ')' statement ('else' statement)?;

whileStmt: 'while' '(' expression ')' statement;

returnStmt: 'return' expression? ';';

expression
    : functionCall
    | Identifier
    | IntegerLiteral
    | BooleanLiteral
    | StringLiteral
    | '(' expression ')'
    | '-' expression
    | '!' expression
    | expression op=('*'|'/') expression
    | expression op=('+'|'-') expression
    | expression op=('=='|'!='|'<'|'>'|'<='|'>=') expression
    | expression op='&&' expression
    | expression op='||' expression
    ;

functionCall: Identifier '(' argList? ')';

argList: expression (',' expression)*;

IntegerLiteral: [0-9]+;

BooleanLiteral: 'true' | 'false';

StringLiteral: '"' .*? '"';

Identifier: [a-zA-Z][a-zA-Z0-9_]*;

WS: [ \t\r\n]+ -> skip;

COMMENT: '//' ~[\r\n]* -> skip;
