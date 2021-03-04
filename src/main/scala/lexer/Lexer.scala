package lexer

import lexer.StringUtils.StringImprovements
import java.io.InputStream
import parser.TokenUtils.TokenImprovements

class Lexer(in: InputStream) {
  private var chr = in.read(); // current ASCII character (coded as an integer)
  private var buf = ""; // buffer
  private var tokens: List[Token] = Nil

  def getTokens: List[Token] = {
    // return: list of tokens recorded in the file
    while (chr != -1) {
      if (chr.toChar == ' ' || chr == 10 || chr == 13 || chr == 9) {
        // info: maybe a better option: 1 <= chr <= 32 ?
        // 13 for pre-OS X
        addToken()
      } else if (buf.isEmpty && chr.toChar == '0') {
        in.mark(32)
        val aux = in.read()
        if (aux != -1) {
          if (aux.toChar != '0' && aux.toChar.isDigit && !tokens.head.isOP())
            throw new UnexpectedCharacter(chr.toChar + "")
        }
        in.reset()
        buf += chr.toChar
        addToken()
      } else if (tokens.nonEmpty && chr.toChar == '-' && tokens.last.getValue == "(") {
        // info: to tackle this: (-2), (--2)
        assert(buf.isEmpty)
        buf += chr.toChar
        addToken()
      } else if (chr.toChar == '(' || chr.toChar == ')') {
        // info: this logic can be removed if the file is preprocessed
        addToken()
        buf += chr.toChar
        addToken()
      } else {
        buf += chr.toChar
      }
      chr = in.read()
    }
    addToken()
    in.close()

    tokens.reverse
  }

  def addToken(): Unit = {
    if (buf.nonEmpty) {
      tokens ::= getToken(buf)
      buf = ""
    }
  }

  def getToken(buf: String): Token = {
    if (buf == "(") return LPAR(buf)
    if (buf == ")") return RPAR(buf)
    if (buf == "+") return PLUS(buf)
    if (buf == "-") return MINUS(buf)
    if (buf == "*") return MUL(buf)
    if (buf == "<") return LESS(buf)
    if (buf == "==") return EQUA(buf)
    if (buf == "=") return ASSIGN(buf)
    if (buf == "/") return DIV(buf)
    if (buf.isInt) return INT(buf)
    if (buf.isIf) return IF(buf)
    if (buf.isFun) return FUNDEF(buf)
    if (buf.isIdentifier) return ID(buf)
    if (buf.hasSpecialChars) throw new UnexpectedCharacter(buf)
    else throw new UndefinedToken(buf) //  :'( shouldn't
  }

}
