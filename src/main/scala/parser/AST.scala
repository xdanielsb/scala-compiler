package parser

class AST

import lexer._

sealed class Exp extends AST

case class Var(id: ID) extends Exp {
  def getID: ID = id
}

case class IntLit(value: Int) extends Exp {
  def toInt: Int = value
}

case class MinExp(op: MINUS, e1: Exp) extends Exp {
  def getExp: Exp = e1

}

case class BinExp(op: OP, e1: Exp, e2: Exp) extends Exp {

  def getOp: OP = op

  def getE1: Exp = e1

  def getE2: Exp = e2
}

case class IfExp(op: IF, cond: Exp, rtrue: Exp, rfalse: Exp) extends Exp {

  def getE1: Exp = cond

  def getE2: Exp = rtrue

  def getE3: Exp = rfalse

}

case class LinkedExp(ex1: Exp, ex2: Exp) extends Exp {
  def getE1: Exp = ex1

  def getE2: Exp = ex2
}

case class VarDef(op: ASSIGN, id: ID, e2: Exp) extends Exp {
  def getID: ID = id

  def getEx: Exp = e2
}

case class Body(ex: Exp) extends Exp {
  def getEx: Exp = ex
}

case class Head(name: ID, parameters: List[ID]) {
  def getID: ID = name

  def getParams: List[ID] = parameters
}

case class FunDef(head: Head, body: Body) extends Exp {
  def getHead: Head = head

  def getBody: Body = body
}

case class Call(name: ID, arguments: List[Exp]) extends Exp {
  def getID: ID = name

  def getArgs: List[Exp] = arguments
}