package lexer

sealed trait Token
case object EOF extends Token
case object LPAR extends Token // Left parenthesis (
case object RPAR extends Token // Right parenthesis )
case object OBLOCK extends  Token // Open Block {
case object CBLOCK extends Token // Close Block }
case object ID extends Token // Identifier i.e := nameUser, x, y , foo12
case object KEY extends Token // Keyword
case object SP extends Token // Space ' '
case object INT extends Token // Integer
