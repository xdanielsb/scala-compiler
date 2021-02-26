package parser

import lexer.{IF, MINUS, OP, Token}
sealed class Exp extends AST{
  //override def toString: String = super.toString
}
case class IntLit(value: String) extends Exp{
 // override def toString: String = value
  override def eval: Int = value.toInt
}
case class MinExp(op: MINUS, e1: Exp) extends Exp{
 // override def toString: String = "( - "+ e1.toString + " )"
  override def eval: Int = -e1.eval
}
case class BinExp(op: OP, e1: Exp, e2: Exp) extends Exp{
 // override def toString: String = "( OP "+ e1.toString + " " + e2.toString + " )"
  override def eval: Int = op.eval(e1.eval, e2.eval)
}
case class ifExp(op: IF, e1: Exp, e2: Exp, e3: Exp) extends Exp{
  //override def toString: String = "( if "+ e1.toString + " " + e2.toString + " "+e3.toString + " )"
  override def eval: Int = if (e1.eval != 0) e2.eval else e3.eval
}