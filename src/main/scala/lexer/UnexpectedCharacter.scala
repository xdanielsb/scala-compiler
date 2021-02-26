package lexer

class UnexpectedCharacter(buf : String) extends Exception(s"unexpected character in " + buf)
