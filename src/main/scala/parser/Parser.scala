/*
    Copyright (C) 2021  github.com/xdanielsb
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package parser

import evaluator.ExpUtils.ExpImprovements
import lexer.{ID, Token}
import parser.TokenUtils.TokenImprovements


class Parser(var tokens: List[Token]) {

  var functions: Map[ID, FunDef] = Map()

  def buildTree(): Exp = {
    val y = pop()
    var ans: Exp = null

    if (y.isInt) return IntLit(y.toInt)
    if (y.isID)  return Var(y.asID())
    if (y.isLPAR) {
      val z = pop()
      if (z.isID) ans = createCall(z.asID()); // info: is it possible to have multiple calls?
      else if (z.isMINUS && isUnaryMinus) ans = MinExp(z.asMINUS(), buildTree()) // info: this case tackle: i.e (-1) ( -1 3) (-1 ( 3)) (- (- 1 0))
      else if (z.isASSIGN && tokens.head.isID) ans = getVarDef(z)
      else if (z.isOP) ans = BinExp(z.asOP(), buildTree(), buildTree())
      else if (z.isIF) ans = IfExp(z.asIF(), buildTree(), buildTree(), buildTree())
      else if (z.isFUN) ans = createFunction()
      if (tokens.isEmpty || !pop().isRPAR)
        throw new SyntaxError("E01: Missing closing parenthesis in expression.")
    }
    if ((ans.isInstanceOf[VarDef] || ans.isInstanceOf[FunDef]) && tokens.nonEmpty) {
      ans = LinkedExp(ans, buildTree())
    }
    ans
  }

  def pop(): Token = {
    // info: pop front
    val y = tokens.head
    tokens = tokens.tail
    y
  }

  def createCall(id: ID): Call = {
    var arguments: List[Exp] = Nil
    while (tokens.nonEmpty && !tokens.head.isRPAR) arguments ::= buildTree()
    Call(id, arguments)
  }

  def createFunction(): FunDef = {
    // info : init head
    if (!pop().isLPAR) throw new SyntaxError("E05: Wrong syntax, missing (.")
    val funID = pop()
    if (!funID.isID) throw new SyntaxError("E07: Wrong syntax, defun should be followed by an id.")
    var parameters: List[ID] = Nil
    while (tokens.head.isID) {
      val param = pop().asID()
      if (parameters.contains(param)) {
        throw new SyntaxError("E08: Redefinition of parameter " + param)
      }
      parameters ::= param
    } // info: should be fun add a type
    // info: we define that parameters of a function are immutable
    val parameters2 = parameters.map(x => ID(x.key, isMut = false))
    val head = Head(funID.asID(), parameters2)
    if (!pop().isRPAR) throw new SyntaxError("E05: Wrong syntax, missing ).")
    // info : end head
    // info : init body
    val body = Body(buildTree())
    val ans = FunDef(head, body)
    // info : end body
    addFunction(funID.asID(), ans.asFunDef())
    ans
  }

  def addFunction(id: ID, v: FunDef): Unit = {
    if (functions.contains(id)) {
      // info: it should be interesting have dynamic linking with dif func like scala, java, c++ ...
      throw new SyntaxError("E06: previous definition of " + ID) // pertinent comment: common dude is not a colon
    }
    functions = functions + (id -> v)
  }

  def isUnaryMinus: Boolean = {
    val aux = tokens(1)
    if (tokens.head.isID && (aux.isID || aux.isInt)) return false
    !tokens.head.isInt || tokens.lift(1).isEmpty || !tokens(1).isInt
  }

  def getVarDef(z: Token): VarDef = {
    val vid = pop().asID()
    val exp = buildTree()
    VarDef(z.asASSIGN(), vid, exp)
  }
}

