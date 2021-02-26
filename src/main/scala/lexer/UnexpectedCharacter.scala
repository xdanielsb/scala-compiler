package lexer

class UnexpectedCharacter(i: Int) extends Exception(s"unexpected character : ascii $i - char ${i.toChar}")
