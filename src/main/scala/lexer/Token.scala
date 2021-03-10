package lexer

class Token(key: String) {
  def value: String = key
  def toInt: Int = key.toInt
}

//---------------- general -----------------//
case class EOF(key: String) extends Token("EOF")
case class LPAR(key: String) extends Token(key) // Left parenthesis (
case class RPAR(key: String) extends Token(key) // Right parenthesis )
case class ID(key: String, isMut: Boolean = true) extends Token(key)  // Identifier i.e := nameUser, x, y , foo12
case class SP(key: String) extends Token(key) // Space ' '
case class INT(key: String) extends Token(key)
case class FOO(key: String) extends Token(key) // Token not classified

//--------------- operations ----------------//
sealed abstract class OP(key: String) extends Token(key) {
  def eval(a: Int, b: Int): Int
}
case class PLUS(key: String) extends OP(key) {
  override def eval(a: Int, b: Int): Int = a + b
}
case class MINUS(key: String) extends OP(key) {
  override def eval(a: Int, b: Int): Int = a - b
}
case class DIV(key: String) extends OP(key) {
  override def eval(a: Int, b: Int): Int = a / b
}
case class MUL(key: String) extends OP(key) {
  // problem with long ?
  override def eval(a: Int, b: Int): Int = a * b
}
case class EQUA(key: String) extends OP(key) {
  override def eval(a: Int, b: Int): Int = if (a == b) 1 else 0
}
case class ASSIGN(key: String) extends OP(key) {
  override def eval(a: Int, b: Int): Int = ???
}
case class LESS(key: String) extends OP(key) {
  override def eval(a: Int, b: Int): Int = if (a < b) 1 else 0
}

//---------------- control -----------------//
case class IF(key: String) extends Token(key)

sealed abstract class DEF(key: String) extends Token(key)
case class FUNDEF(key: String) extends Token(key)
