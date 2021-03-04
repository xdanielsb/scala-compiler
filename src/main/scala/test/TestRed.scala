package test

object TestRed extends Test {
   def main(args: Array[String]): Unit = {

    var verbose = false
    // no recursion
    test(verbose, "src/test/redFunc0.calc", "(define (zero) 0)(zero)", Some(0))
    test(verbose, "src/test/redId.calc", "(define (id x) x) (id 11)", Some(11))
    test(verbose, "src/test/redFuncs.calc", "(define (id x) x)(define (id2 x) x) 22", Some(22))
    test(verbose, "src/test/redFunc1.calc", "(define (inc x) (+ x 1))(inc 0)", Some(1))
    test(verbose, "src/test/redFunc2.calc", "(define (id x) x)(id (+ 0 1))", Some(1))
    test(verbose, "src/test/redFunc3.calc", "(define (id x) x)(= a 3)a", Some(3))
    test(verbose, "src/test/redFunc4.calc", "(define (inc x) (+ x 1))(inc (+ 3 1))", Some(5))
    test(verbose, "src/test/redFunc5.calc", "(define (inc x) (+ x 1))(= a 2)(inc (+ 1 a))", Some(4))
    test(verbose, "src/test/redOverride.calc", "(inc x) (inc x y)", None)
    test(verbose, "src/test/redParam.calc", "(inc x) (= x 0)", None)
    test(verbose, "src/test/redParams.calc", "(defun (id x x) x) (id 0 1)", None)
    test(verbose, "src/test/redZarbi.calc", "(define (f) a)(= a 1)(f)", None)
    test(verbose, "src/test/redNoLoop.calc", "lazy conditional", Some(2))
    test(verbose, "src/test/redPlusScoping.calc", "handling scopes", Some(99))
    test(verbose, "src/test/redPlusScoping2.calc", "handling scopes", None)
    // simple recursion
    test(verbose, "src/test/redFuncRedef.calc", "(define (f x)(= x 1) x)(f x)", None)
    test(verbose, "src/test/redFib0.calc", "(define (fib n)... (fib 4)", Some(3))
    test(verbose, "src/test/redFib.calc", "(define (fib n)... (fib 4)", Some(3))
    test(verbose, "src/test/redFib2.calc", "(define (fib n)... (fib 4))", Some(3))
    test(verbose, "src/test/redFib3.calc", "(define (fib n)... (fib 4))", Some(3))
    // mutual recursion
    test(verbose, "src/test/redMutRec.calc", "odd & even", Some(2))
    report()
  }
}
