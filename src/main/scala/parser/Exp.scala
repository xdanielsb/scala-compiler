package parser

import lexer.{IF, MINUS, OP, Token}
sealed class Exp extends AST{
  override def toString: String = super.toString
}
case class IntLit(value: String) extends Exp{
  override def toString: String = value
}
case class MinExp(op: MINUS, e1: Exp) extends Exp{
  override def toString: String = "( - "+ e1.toString + " )"
}
case class BinExp(op: OP, e1: Exp, e2: Exp) extends Exp{
  override def toString: String = "( OP "+ e1.toString + " " + e2.toString + " )"
}
case class ifExp(op: IF, e1: Exp, e2: Exp, e3: Exp) extends Exp{
  override def toString: String = "( if "+ e1.toString + " " + e2.toString + " "+e3.toString + " )"
}