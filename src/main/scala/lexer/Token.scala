package lexer

sealed trait Token
case object EOF extends Token
case object LPAR extends Token // Left parenthesis (
case object RPAR extends Token // Right parenthesis )
case object OBLOCK extends  Token // Open Block {
case object CBLOCK extends Token // Close Block }
case object ID extends Token // Identifier i.e := nameUser, x, y , foo12
case object FOO extends Token // Token not classified
case object SP extends Token // Space ' '
case object INT extends Token // Integer
case object PLUS extends Token // +
case object MINUS extends Token // -
case object DIV extends Token // /
case object MUL extends Token // *
case object IF extends Token // *
