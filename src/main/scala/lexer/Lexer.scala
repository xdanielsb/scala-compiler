/*
    Copyright (C) 2021  github.com/xdanielsb
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package lexer

import lexer.StringUtils.StringImprovements
import java.io.InputStream
import parser.TokenUtils.TokenImprovements

class Lexer(in: InputStream) {
  private var chr = in.read();
  private var buf = ""; // buffer
  private var tokens: List[Token] = Nil

  def getTokens: List[Token] = {
    while (chr != -1) {
      if (chr.toChar == ' ' || chr == 10 || chr == 13 || chr == 9) {
        // info: maybe a better option: 1 <= chr <= 32 ?
        // 13 for pre-OS X
        addToken()
      } else if (buf.isEmpty && chr.toChar == '0') {
        in.mark(32)
        val aux = in.read()
        if (aux != -1) {
          if (aux.toChar != '0' && aux.toChar.isDigit && !tokens.head.isOP)
            throw new UnexpectedCharacter(chr.toChar + "")
        }
        in.reset()
        buf += chr.toChar
        addToken()
      } else if (tokens.nonEmpty && chr.toChar == '-' && tokens.last.value == "(") {
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
