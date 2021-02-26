package parser

import lexer.{OP, Token}
trait AST
sealed trait Exp extends AST
case class IntLit(value: Int) extends Exp
case class BinExp(op: OP, e1: Exp, e2: Exp)

object Exp {
  def parse(token: Token): Exp = token match {
    case _ => IntLit(1)
   // case _ => throw new SyntaxError("")
  }
}