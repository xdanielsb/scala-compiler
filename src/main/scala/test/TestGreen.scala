package test

object TestGreen extends Test {
   def main(args: Array[String]): Unit = {

    // Calc Green
    var verbose = false
    test(verbose, "src/test/greenZeroUnix.calc", "0", Some(0))
    test(verbose, "src/test/greenZeroDos.calc", "0", Some(0))
    test(verbose, "src/test/greenZeroTab.calc", "0", Some(0))
    test(verbose, "src/test/greenOne.calc", "1", Some(1))
    test(verbose, "src/test/greenLit.calc", "100", Some(100))
    test(verbose, "src/test/greenUnExpMinus.calc", "-1", Some(-1))
    test(verbose, "src/test/greenUnExpMinus2.calc", "(- (- 1 0))", Some(-1))
    test(verbose, "src/test/greenBinExpPlus.calc", "(+ 1 1)", Some(2))
    test(verbose, "src/test/greenBinExpPlus01.calc", "(+ 01)", Some(1))
    test(verbose, "src/test/greenBinExpMinus.calc", "(- 1 1)", Some(0))
    test(verbose, "src/test/greenBinExpDivide.calc", "(/ 5 2)", Some(2))
    test(verbose, "src/test/greenBinExpEqual.calc", "(== 1 2)", Some(0))
    test(verbose, "src/test/greenBinExpEqual2.calc", "(== 3 3)", Some(1))
    test(verbose, "src/test/greenNestedExp.calc", "(+ (- 2 3) (+ 0 1))", Some(0))
    test(verbose, "src/test/greenIf0.calc", "(if 0 1 0)", Some(0))
    test(verbose, "src/test/greenIf0Indent.calc", "(if 0 1 0)", Some(0))
    test(verbose, "src/test/greenIf1.calc", "(if 1 11 0)", Some(11))
    test(verbose, "src/test/greenIf2.calc", "(if 2 22 0)", Some(22))
    test(verbose, "src/test/greenIf3.calc", "(if (-2) 33 0)", Some(33))
    test(verbose, "src/test/greenNestedIfInTest.calc", "(if (if 1 1 0) 2 3)", Some(2))
    test(verbose, "src/test/greenNestedIfInBranch.calc", "(if 1 (if 1 2 0) 3)", Some(2))
    test(verbose, "src/test/greenNestedIfInExp.calc", "(+ (if 1 1 0) 1)", Some(2))
    test(verbose, "src/test/greenNestedExpAsTest.calc", "(if (- 1 1) 1 44)", Some(44))
    test(verbose, "src/test/greenLazyIf.calc", "(if 0 (/ 1 (- 2 2)) 122)", Some(122))
    report()
  }
}
