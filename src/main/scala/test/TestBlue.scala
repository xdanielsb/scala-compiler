package test

object TestBlue extends Test {
  def main(args: Array[String]): Unit = {
    var verbose = true
    test(verbose, "src/test/blueDef.calc", "(= a 11) a", Some(11))
    test(verbose, "src/test/blueDefError.calc", "(= A 11) A", None)
    test(verbose, "test/blueDef1.calc", "(= if0 11) if0", Some(11))
    test(verbose, "src/test/blueDef2.calc", "(= a 1) (= b 2) (+ a b)", Some(3))
    test(verbose, "src/test/blueDef3.calc", "(= a 1) (= b (- a 1)) (+ a b)", Some(1))
    test(verbose, "src/test/blueDef4.calc", "(= a 11) (+ 0a)", Some(11))
    test(verbose, "src/test/blueRedef.calc", "(= a 11) (= a 12)", None)
    test(verbose, "src/test/blueUndef.calc", "a", None)
    test(verbose, "src/test/blueUseDef.calc", "(= b a) (= a 1) b", None)
    report()
  }
}
