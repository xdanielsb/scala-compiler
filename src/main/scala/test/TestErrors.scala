package test

object TestErrors extends Test {
  def main(args: Array[String]): Unit = {
    var verbose = false
    // various errors
    test(verbose, "test/error.calc", "no file", None)
    test(verbose, "src/test/errorEmpty.calc", "", None)
    test(verbose, "src/test/errorGarbage.calc", "FOO!", None)
    test(verbose, "src/test/errorGarbage2.calc", "01", None)
    test(verbose, "src/test/errorUnaryExp.calc", "(+ 2)", None)
    test(verbose, "src/test/errorParens.calc", "(2)", None)
    test(verbose, "src/test/errorRPar.calc", "(+ 1 2", None)
    test(verbose, "src/test/errorIf.calc", "(if1 1 0)", None)
    test(verbose, "src/test/errorIf2.calc", "(if 1 1)", None)
    test(verbose, "src/test/errorDivide.calc", "(/ 1 0)", None)
    report()
  }
}
