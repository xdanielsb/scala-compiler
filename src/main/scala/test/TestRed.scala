package test

object TestRed extends Test {
  override def main(args: Array[String]): Unit = {
    super.main(args)
    var verbose = true
    // no recursion
    test(verbose, "test/redFunc0.calc", "(define (zero) 0)(zero)", Some(0))
    test(verbose, "test/redId.calc", "(define (id x) x) (id 11)", Some(11))
    test(verbose, "test/redFuncs.calc", "(define (id x) x)(define (id2 x) x) 22", Some(22))
    test(verbose, "test/redFunc1.calc", "(define (inc x) (+ x 1))(inc 0)", Some(1))
    test(verbose, "test/redFunc2.calc", "(define (id x) x)(id (+ 0 1))", Some(1))
    test(verbose, "test/redFunc3.calc", "(define (id x) x)(= a 3)a", Some(3))
    test(verbose, "test/redFunc4.calc", "(define (inc x) (+ x 1))(inc (+ 3 1))", Some(5))
    test(verbose, "test/redFunc5.calc", "(define (inc x) (+ x 1))(= a 2)(inc (+ 1 a))", Some(4))
    test(verbose, "test/redOverride.calc", "(inc x) (inc x y)", None)
    test(verbose, "test/redParam.calc", "(inc x) (= x 0)", None)
    test(verbose, "test/redParams.calc", "(defun (id x x) x) (id 0 1)", None)
    test(verbose, "test/redZarbi.calc", "(define (f) a)(= a 1)(f)", None)
    test(verbose, "test/redNoLoop.calc", "lazy conditional", Some(2))
    test(verbose, "test/redPlusScoping.calc", "handling scopes", Some(99))
    test(verbose, "test/redPlusScoping2.calc", "handling scopes", None)
    // simple recursion
    test(verbose, "test/redFuncRedef.calc", "(define (f x)(= x 1) x)(f x)", None)
    test(verbose, "test/redFib0.calc", "(define (fib n)... (fib 4)", Some(3))
    test(verbose, "test/redFib.calc", "(define (fib n)... (fib 4)", Some(3))
    test(verbose, "test/redFib2.calc", "(define (fib n)... (fib 4))", Some(3))
    test(verbose, "test/redFib3.calc", "(define (fib n)... (fib 4))", Some(3))
    // mutual recursion
    test(verbose, "test/redMutRec.calc", "odd & even", Some(2))
    report()
  }
}
