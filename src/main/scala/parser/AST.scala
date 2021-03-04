package parser

class AST

import lexer.{ASSIGN, EQUA, FUNDEF, ID, IF, MINUS, OP, Token}

sealed class Exp extends AST {
  //override def toString: String = super.toString
}

case class Var(id: ID) extends Exp {
  // override def toString: String = value
  def getID: ID = id;
}

case class IntLit(value: Int) extends Exp {
  // override def toString: String = value
  def toInt: Int = value
}

case class MinExp(op: MINUS, e1: Exp) extends Exp {
  // override def toString: String = "( - "+ e1.toString + " )"
  def getExp: Exp = e1;

}

case class BinExp(op: OP, e1: Exp, e2: Exp) extends Exp {
  // override def toString: String = "( OP "+ e1.toString + " " + e2.toString + " )"
  def getOp: OP = op;

  def getE1: Exp = e1;

  def getE2: Exp = e2;
}

case class IfExp(op: IF, e1: Exp, e2: Exp, e3: Exp) extends Exp {
  //override def toString: String = "( if "+ e1.toString + " " + e2.toString + " "+e3.toString + " )"
  def getE1: Exp = e1;

  def getE2: Exp = e2;

  def getE3: Exp = e3;

}

case class LinkedExp(ex1: Exp, ex2: Exp) extends Exp {
  def getE1: Exp = ex1;

  def getE2: Exp = ex2;
}

case class VarDef(op: ASSIGN, id: ID, e2: Exp) extends Exp {
  def getID: ID = id;

  def getEx: Exp = e2;
}

case class Body(ex: Exp) extends Exp {
  def getEx: Exp = ex;
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