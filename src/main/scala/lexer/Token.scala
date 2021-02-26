package lexer

sealed class Token(key: String){
  def getValue(): String ={
    return key
  }
}

///--- general
case class EOF(key:String) extends Token("EOF")
case class LPAR(key: String) extends Token(key) // Left parenthesis (
case class RPAR(key: String) extends Token(key) // Right parenthesis )
case class ID(key: String) extends Token(key) // Identifier i.e := nameUser, x, y , foo12
case class SP(key: String) extends Token(key) // Space ' '
case class INT(key: String) extends Token(key) // Integer
case class FOO(key: String) extends Token(key) // Token not classified

///--- operations
sealed class OP(key: String) extends Token(key)
case class PLUS(key: String) extends OP(key) // +
case class MINUS(key: String) extends OP(key) // -
case class DIV(key: String) extends OP(key) // /
case class MUL(key: String) extends OP(key) // *
case class EQUA(key: String) extends OP(key) // ==
case class LESS(key: String) extends OP(key) // <


///--- reserved
case class IF(key: String) extends Token(key) // *
case class DEF(key: String) extends Token(key)