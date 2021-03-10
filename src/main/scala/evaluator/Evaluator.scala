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
    if (s.isIntLit) return s.asIntLit.value
    if (s.isCall) {
      val v = s.asCall().id
      val args = s.asCall().args.map(x => IntLit(eval(x, ctx)))

      if (functor.contains(v)) {
        val fu = functor(v)
        val params = fu.head.params
        // info: here we remove ctx of the parent
        //       actual parameters take its values
        val ctx2 = (params zip args) toMap; // do not remove ;
        return eval(fu.body.exp, ctx2)
      }
    }
    if (s.isVar) {
      val v = s.asVar().id
      // we look v in immutable vars
      if (ctx.contains(ID(v.key))) return eval(ctx(ID(v.key)), ctx)
      // we look v in mutable vars
      if (ctx.contains(ID(v.key, isMut = false))) return eval(ctx(ID(v.key, isMut = false)), ctx)
      // this should rise an exception
      return eval(ctx(v), ctx) // this should rise key not found
    }
    if (s.isMinExp) return -eval(s.asMinExp().exp, ctx)
    if (s.isBinExp) {
      val bin = s.asBinExp()
      val a1 = eval(bin.exp1, ctx)
      val a2 = eval(bin.exp2, ctx)
      return bin.op.eval(a1, a2)
    }
    if (s.isIfExp) {
      val ife = s.asIfExp()
      if (eval(ife.cond, ctx) != 0) return eval(ife.expTrue, ctx)
      return eval(ife.expFalse, ctx)
    }
    if (s.isLinkedExp) {
      val lx = s.asLinkedExp()
      if (lx.exp1.isVarDef) {
        val ex1 = lx.exp1.asVarDef()
        if (ex1.exp.isVar)
          ctx(ex1.exp.asVar().id) // info: just query the element to check if exist
        if (ctx.contains(ID(ex1.id.key, isMut = false)))
          throw new SyntaxError("E09: Variable " + ex1.id.key + " immutable")
        val ctx2 = ctx ++ collection.immutable.Map(ex1.id -> ex1.exp)
        return eval(lx.exp2, ctx2)
      }
      return eval(lx.exp2, ctx)
    }
    if (s.isVarDef) {
      val vd = s.asVarDef()
      if (Try {
        eval(vd.exp, ctx)
      }.isSuccess) {
        return eval(vd.exp, ctx)
      } else {
        throw new SyntaxError("E04: Variable " + vd.id + " not defined")
      }
    }
    throw new SyntaxError("E09: Uncaught syntax")
  }
}
