package lexer

class UndefinedToken(buf : String) extends Exception(s"E03: Undefined token  " + buf)