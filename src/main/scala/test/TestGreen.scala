package test

object TestGreen extends Test {
  override def main(args: Array[String]): Unit = {
    super.main(args)
    // Calc Green
    var verbose = true
    test(verbose, "test/greenZeroUnix.calc", "0", Some(0))
    test(verbose, "test/greenZeroDos.calc", "0", Some(0))
    test(verbose, "test/greenZeroTab.calc", "0", Some(0))
    test(verbose, "test/greenOne.calc", "1", Some(1))
    test(verbose, "test/greenLit.calc", "100", Some(100))
    test(verbose, "test/greenUnExpMinus.calc", "-1", Some(-1))
    test(verbose, "test/greenUnExpMinus2.calc", "(- (- 1 0))", Some(-1))
    test(verbose, "test/greenBinExpPlus.calc", "(+ 1 1)", Some(2))
    test(verbose, "test/greenBinExpPlus01.calc", "(+ 01)", Some(1))
    test(verbose, "test/greenBinExpMinus.calc", "(- 1 1)", Some(0))
    test(verbose, "test/greenBinExpDivide.calc", "(/ 5 2)", Some(2))
    test(verbose, "test/greenBinExpEqual.calc", "(== 1 2)", Some(0))
    test(verbose, "test/greenBinExpEqual2.calc", "(== 3 3)", Some(1))
    test(verbose, "test/greenNestedExp.calc", "(+ (- 2 3) (+ 0 1))", Some(0))
    test(verbose, "test/greenIf0.calc", "(if 0 1 0)", Some(0))
    test(verbose, "test/greenIf0Indent.calc", "(if 0 1 0)", Some(0))
    test(verbose, "test/greenIf1.calc", "(if 1 11 0)", Some(11))
    test(verbose, "test/greenIf2.calc", "(if 2 22 0)", Some(22))
    test(verbose, "test/greenIf3.calc", "(if (-2) 33 0)", Some(33))
    test(verbose, "test/greenNestedIfInTest.calc", "(if (if 1 1 0) 2 3)", Some(2))
    test(verbose, "test/greenNestedIfInBranch.calc", "(if 1 (if 1 2 0) 3)", Some(2))
    test(verbose, "test/greenNestedIfInExp.calc", "(+ (if 1 1 0) 1)", Some(2))
    test(verbose, "test/greenNestedExpAsTest.calc", "(if (- 1 1) 1 44)", Some(44))
    test(verbose, "test/greenLazyIf.calc", "(if 0 (/ 1 (- 2 2)) 122)", Some(122))
    report()
  }
}
