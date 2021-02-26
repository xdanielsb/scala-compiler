package parser

import lexer.{IF, MINUS, OP, Token}
sealed trait Exp extends AST
case class IntLit(value: String) extends Exp
case class MinExp(op: MINUS, e1: Exp) extends Exp
case class BinExp(op: OP, e1: Exp, e2: Exp) extends Exp
case class ifExp(op: IF, e1: Exp, e2: Exp, e3: Exp) extends Exp