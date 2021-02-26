package test

object TestBlue extends Test {
  override def main(args: Array[String]): Unit = {
    super.main(args)
    var verbose = true
    test(verbose, "test/blueDef.calc", "(= a 11) a", Some(11))
    test(verbose, "test/blueDefError.calc", "(= A 11) A", None)
    test(verbose, "test/blueDef1.calc", "(= if0 11) if0", Some(11))
    test(verbose, "test/blueDef2.calc", "(= a 1) (= b 2) (+ a b)", Some(3))
    test(verbose, "test/blueDef3.calc", "(= a 1) (= b (- a 1)) (+ a b)", Some(1))
    test(verbose, "test/blueDef4.calc", "(= a 11) (+ 0a)", Some(11))
    test(verbose, "test/blueRedef.calc", "(= a 11) (= a 12)", None)
    test(verbose, "test/blueUndef.calc", "a", None)
    test(verbose, "test/blueUseDef.calc", "(= b a) (= a 1) b", None)
    report()
  }
}
