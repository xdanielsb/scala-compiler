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
package evaluator

import lexer.ID
import parser.{Exp, FunDef, IntLit, SyntaxError}

import scala.util.Try
import ExpUtils.ExpImprovements

class Evaluator(functor: Map[ID, FunDef]) {
  def eval(s: Exp, ctx: Map[ID, Exp]): Int = {
    if (s.isIntLit) return s.asIntLit.toInt
    if (s.isCall) {
      val v = s.asCall().getID
      val args = s.asCall().getArgs.map(x => IntLit(eval(x, ctx)))

      if (functor.contains(v)) {
        val fu = functor(v)
        val params = fu.getHead.getParams
        // info: here we remove ctx of the parent
        //       actual parameters take its values
        val ctx2 = (params zip args) toMap; // do not remove ;
        return eval(fu.getBody.getEx, ctx2)
      }
    }
    if (s.isVar) {
      val v = s.asVar().getID
      // we look v in immutable vars
      if (ctx.contains(ID(v.getKey))) return eval(ctx(ID(v.getKey)), ctx)
      // we look v in mutable vars
      if (ctx.contains(ID(v.getKey, mut = false))) return eval(ctx(ID(v.getKey, mut = false)), ctx)
      // this should rise an exception
      return eval(ctx(v), ctx) // this should rise key not found
    }
    if (s.isMinExp) return -eval(s.asMinExp().getExp, ctx)
    if (s.isBinExp) {
      val bin = s.asBinExp()
      val a1 = eval(bin.getE1, ctx)
      val a2 = eval(bin.getE2, ctx)
      return bin.getOp.eval(a1, a2)
    }
    if (s.isIfExp) {
      val ife = s.asIfExp()
      if (eval(ife.getE1, ctx) != 0) return eval(ife.getE2, ctx)
      return eval(ife.getE3, ctx)
    }
    if (s.isLinkedExp) {
      val lx = s.asLinkedExp()
      if (lx.getE1.isVarDef) {
        val ex1 = lx.getE1.asVarDef()
        if (ex1.getEx.isVar)
          ctx(ex1.getEx.asVar().getID) // info: just query the element to check if exist
        if (ctx.contains(ID(ex1.getID.getKey, mut = false)))
          throw new SyntaxError("E09: Variable " + ex1.getID.getKey + " immutable")
        val ctx2 = ctx ++ collection.immutable.Map(ex1.getID -> ex1.getEx)
        return eval(lx.getE2, ctx2)
      }
      return eval(lx.getE2, ctx)
    }
    if (s.isVarDef) {
      val vd = s.asVarDef()
      if (Try {
        eval(vd.getEx, ctx)
      }.isSuccess) {
        return eval(vd.getEx, ctx)
      } else {
        throw new SyntaxError("E04: Variable " + vd.getID + " not defined")
      }
    }
    throw new SyntaxError("E09: Uncaught syntax")
  }
}
