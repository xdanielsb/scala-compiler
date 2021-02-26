package test

object TestErrors extends Test {
  override def main(args: Array[String]): Unit = {
    super.main(args)
    var verbose = true
    // various errors
    test(verbose, "test/error.calc", "no file", None)
    test(verbose, "test/errorEmpty.calc", "", None)
    test(verbose, "test/errorGarbage.calc", "FOO!", None)
    test(verbose, "test/errorGarbage2.calc", "01", None)
    test(verbose, "test/errorUnaryExp.calc", "(+ 2)", None)
    test(verbose, "test/errorParens.calc", "(2)", None)
    test(verbose, "test/errorRPar.calc", "(+ 1 2", None)
    test(verbose, "test/errorIf.calc", "(if1 1 0)", None)
    test(verbose, "test/errorIf2.calc", "(if 1 1)", None)
    test(verbose, "test/errorDivide.calc", "(/ 1 0)", None)
    report()
  }
}
