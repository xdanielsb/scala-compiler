package evaluator

import parser._

object ExpUtils {

  implicit class ExpImprovements(val s: Exp) {
    def isVar: Boolean = s.isInstanceOf[Var]

    def asVar(): Var = s.asInstanceOf[Var]

    def isIntLit: Boolean = s.isInstanceOf[IntLit]

    def asIntLit:IntLit = s.asInstanceOf[IntLit]

    def isMinExp: Boolean = s.isInstanceOf[MinExp]

    def asMinExp() : MinExp= s.asInstanceOf[MinExp]

    def isBinExp: Boolean = s.isInstanceOf[BinExp]

    def asBinExp() : BinExp = s.asInstanceOf[BinExp]

    def isIfExp: Boolean = s.isInstanceOf[IfExp]

    def asIfExp(): IfExp = s.asInstanceOf[IfExp]

    def isLinkedExp: Boolean = s.isInstanceOf[LinkedExp]

    def asLinkedExp(): LinkedExp = s.asInstanceOf[LinkedExp]

    def isVarDef: Boolean = s.isInstanceOf[VarDef]

    def asVarDef(): VarDef = s.asInstanceOf[VarDef]

    def isFunDef: Boolean = s.isInstanceOf[FunDef]

    def asFunDef(): FunDef = s.asInstanceOf[FunDef]

    def isCall: Boolean = s.isInstanceOf[Call]

    def asCall(): Call = s.asInstanceOf[Call]

  }

}
