package parser

trait AST

import lexer._

sealed class Exp extends AST
case class Var(id: ID) extends Exp
case class IntLit(value: Int) extends Exp
case class MinExp(op: MINUS, exp: Exp) extends Exp
case class BinExp(op: OP, exp1: Exp, exp2: Exp) extends Exp
case class IfExp(op: IF, cond: Exp, expTrue: Exp, expFalse: Exp) extends Exp
case class LinkedExp(exp1: Exp, exp2: Exp) extends Exp
case class VarDef(op: ASSIGN, id: ID, exp: Exp) extends Exp
case class Body(exp: Exp) extends Exp
case class Head(id: ID, params: List[ID])
case class FunDef(head: Head, body: Body) extends Exp
case class Call(id: ID, args: List[Exp]) extends Exp