package test

trait Test {
  private var count = 0
  private var success = 0
  private var verbose = false

  def report(): Unit = println(s"$success successful tests out of $count")

  def test(verbose: Boolean, filename: String, mess1: String, expected: Option[Int]): Unit = {
    count += 1
    val args0 = new Array[String](1)
    args0(0) = filename
    if (verbose) println(s"====: ${filename}\ncontent: ${mess1}\nexpected: ${expected}")
    try {
      val found = None; // interpret(new FileInputStream(filename))
      expected match {
        case None => // error expected
          System.err.println(s"FAILURE on $filename")
          System.err.println(s"error expected, found $found")
        case Some(result) => // integer expected
          if (verbose) println(s"result: $found")
          if (found != result) {
            System.err.println(s"FAILURE on $filename")
            System.err.println(s"expecting $result, found $found")
          } else {
            success += 1
            println(s"SUCCESS on $filename")
          }
      }
    } catch {
      case e: Exception =>
        expected match {
          case None =>
            if (e.isInstanceOf[NullPointerException]) {
              System.err.println(s"FAILURE on $filename") // for debugging purposes
            } else {
              success += 1
              println(s"SUCCESS on $filename with error : ${e.getMessage}") // is it indeed a meaningful error?
            }
            if (verbose) e.printStackTrace()
          case _ => // unexpected error
            System.err.println(s"FAILURE on $filename")
            if (verbose) e.printStackTrace()
        }
    }
  }

  def main(args: Array[String]): Unit =
    if (args.length > 0 && args(0) == "-v") verbose = true
    else verbose = false
}
