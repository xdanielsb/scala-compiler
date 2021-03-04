# Compiler in scala 

This project implements the following language description


### Language definition

```
Program    ::= FunDef* Body
FunDef     ::= ’(’ ’defun’ Head Body ’)’
Head       ::= ’(’ FUN_ID VAR_ID* ’)’
Body       ::= VarDef* Expression
VarDef     ::= ’(’ ’=’ VAR_ID Expression ’)’
Expression ::= INTEGER | 
               VAR_ID  | 
               ’(’ ’-’ Expression ’)’ |
               ’(’ OP Expression Expression ’)’| 
               ’(’ ’if’ Expression Expression  Expression ’)’| 
               ’(’ FUN_ID Expression* ’)’
OP         ::= ’+’ | ’-’ | ’*’ | ’/’ | ’==’ | ’<’
INTEGER    ::= ’0’|[’1’-’9’][’0’-’9’]*
VAR_ID     ::= [’a’-’z’][’a’-’z’’0’-’9’]*
FUN_ID     ::= [’a’-’z’][’a’-’z’’0’-’9’]*
```

### How to test 

| Test                 | Description                                  |
|----------------------|----------------------------------------------|
| TestBlue.scala       | Test for Body::, Vardef:: & VAR\_ID          |
| TestErrors.scala     | Test errors that should arrise               |
| TestGreen.scala      | Test Expression::, OP:: and Integer::        |
| TestRed.scala        | Test Program::, FunDef::, Head:: and FunID   |
| TestSingleFile.scala | Test a single file of the files in src/tests |


#### Packages description

| Package Name | Usage                                                  |
|--------------|--------------------------------------------------------|
| lexer        | In charge of lexical analysis, lexing or tokenization. |
| parser       | Build a parser tree (T), analyze the syntax.           |
| evaluator    | Receive T, and evaluate it. Check semantics.           |
| test         | Package with 68 tests.                                 |

#### Example

In TestSingleFile.java
```scala
  def main(args: Array[String]) {
    // am I asleep?
    val nameFile = "src/test/redFib3.calc"
    try {
      interpret(nameFile, verbose = true)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
```

Input
```sh
(defun (fib n)
  (if (== n 0) 0
      (if (== n 1) 1
          (fibaux n))))
(defun (fibaux n)
  (= fib1 (fib (- n 1)))
  (= fib2 (fib (- n 2)))
  (+ fib1 fib2))
(fib 4)
```

Output
```sh

Tokens: 
List( 
  LPAR((), FUNDEF(defun), LPAR((), ID(fib,true),
  ID(n,true), RPAR()), LPAR((), IF(if), LPAR((), 
  EQUA(==), ID(n,true), INT(0), RPAR()), INT(0), 
  LPAR((), IF(if), LPAR((), EQUA(==), ID(n,true), 
  INT(1), RPAR()), INT(1), LPAR((), ID(fibaux,true), 
  ID(n,true), RPAR()), RPAR()), RPAR()), RPAR()), 
  LPAR((), FUNDEF(defun), LPAR((), ID(fibaux,true), 
  ID(n,true), RPAR()), LPAR((), ASSIGN(=), 
  ID(fib1,true), LPAR((), ID(fib,true), LPAR((),
  MINUS(-), ID(n,true), INT(1), RPAR()), RPAR()), 
  RPAR()), LPAR((), ASSIGN(=), ID(fib2,true), 
  LPAR((), ID(fib,true), LPAR((), MINUS(-), 
  ID(n,true), INT(2), RPAR()), RPAR()), RPAR()),
  LPAR((), PLUS(+), ID(fib1,true), ID(fib2,true), 
  RPAR()), RPAR()), LPAR((), ID(fib,true), 
  INT(4), RPAR())).


Syntax tree: 
  LinkedExp(
    FunDef(
      Head(ID(fib,true),List(ID(n,false))),
      Body(
       IfExp(
        IF(if),BinExp(EQUA(==),Var(ID(n,true)),IntLit(0)),
          IntLit(0),
          IfExp(
            IF(if),BinExp(EQUA(==),Var(ID(n,true)),IntLit(1)),
              IntLit(1),
              Call(ID(fibaux,true),List(Var(ID(n,true))))
          )
        )
      )
    ),
    LinkedExp(
      FunDef(
        Head(ID(fibaux,true),List(ID(n,false))),
        Body(
          LinkedExp(
            VarDef(ASSIGN(=),ID(fib1,true),
             Call(ID(fib,true),
               List(BinExp(MINUS(-),Var(ID(n,true)),IntLit(1)))
              )
            ),
            LinkedExp(
              VarDef(ASSIGN(=),ID(fib2,true),
                Call(ID(fib,true),
                  List(BinExp(MINUS(-),Var(ID(n,true)),IntLit(2)))
                )
              ),
              BinExp(PLUS(+),Var(ID(fib1,true)),Var(ID(fib2,true)))
            )
          )
        )
      ),
      Call(ID(fib,true),List(IntLit(4)))
    )
  )

Evaluation of the tree= 3

  ans = 3 
  # testing by hand, f(4)
  # f(4) = f(3) + f(2) 
  #      = f(2) + f(1) + f(2) 
  #      = f(0) + f(1) + f(1) + f(1) + f(0) 
  #      =   0  +   1  +   1  +   1  +   0
  #      =   3 
  
```



It is my first time in scala :3, I will be happy to receive your comments/suggestion. 



