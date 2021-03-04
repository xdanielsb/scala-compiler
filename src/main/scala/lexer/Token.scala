package lexer

sealed class Token(key: String) {
  def getValue: String = key

  def toInt: Int = key.toInt
}

///--- general
case class EOF(key: String) extends Token("EOF")

case class LPAR(key: String) extends Token(key) // Left parenthesis (
case class RPAR(key: String) extends Token(key) // Right parenthesis )
case class ID(key: String, mut: Boolean = true) extends Token(key) {
  // info: mut defines if the variable is mutable
  def getKey: String = key

  def isMut: Boolean = mut
} // Identifier i.e := nameUser, x, y , foo12
case class SP(key: String) extends Token(key) // Space ' '
case class INT(key: String) extends Token(key) {

} // Integer
case class FOO(key: String) extends Token(key) // Token not classified

///--- operations
sealed abstract class OP(key: String) extends Token(key) {
  def getKey: String = key
  def eval(a: Int, b: Int): Int
}

case class PLUS(key: String) extends OP(key) {
  override def eval(a: Int, b: Int): Int = a + b
} // +
case class MINUS(key: String) extends OP(key) {
  // if you want to negate i.e -3 then a = 0
  override def eval(a: Int, b: Int): Int = a - b
} // -
case class DIV(key: String) extends OP(key) {
  override def eval(a: Int, b: Int): Int = a / b
} // /
case class MUL(key: String) extends OP(key) {
  // problem with long ?
  override def eval(a: Int, b: Int): Int = a * b

} // *
case class EQUA(key: String) extends OP(key) {
  override def eval(a: Int, b: Int): Int = if (a == b) 1 else 0
} // ==
case class ASSIGN(key: String) extends OP(key) {
  override def eval(a: Int, b: Int): Int = a + b // TODO
} // ==
case class LESS(key: String) extends OP(key) {
  override def eval(a: Int, b: Int): Int = if (a < b) 1 else 0
} // <


///--- reserved
case class IF(key: String) extends Token(key) // *


sealed abstract class DEF(key: String) extends Token(key)

case class FUNDEF(key: String) extends Token(key)
