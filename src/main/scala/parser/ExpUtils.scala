package parser

object ExpUtils {
  implicit class ExpImprovements(val s: Exp) {
    def isVar() = s.isInstanceOf[Var];
    def asVar() = s.asInstanceOf[Var];

    def isIntLit() = s.isInstanceOf[IntLit];
    def asIntLit() = s.asInstanceOf[IntLit];

    def isMinExp() = s.isInstanceOf[MinExp];
    def asMinExp() = s.asInstanceOf[MinExp];

    def isBinExp() = s.isInstanceOf[BinExp];
    def asBinExp() = s.asInstanceOf[BinExp];

    def isIfExp() = s.isInstanceOf[IfExp];
    def asIfExp() = s.asInstanceOf[IfExp];

    def isLinkedExp() = s.isInstanceOf[LinkedExp];
    def asLinkedExp() = s.asInstanceOf[LinkedExp];

    def isVarDef() = s.isInstanceOf[VarDef];
    def asVarDef() = s.asInstanceOf[VarDef];

    def isFunDef() = s.isInstanceOf[FunDef];
    def asFunDef() = s.asInstanceOf[FunDef];

    /*
    def is() = s.isInstanceOf[];
    def as() = s.asInstanceOf[];
    */
  }
}