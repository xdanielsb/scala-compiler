package parser

import lexer.{ASSIGN, EQUA, ID, IF, INT, LPAR, MINUS, OP, RPAR, Token}

object TokenUtils {
  implicit class TokenImprovements(val s: Token) {
    def isInt() = s.isInstanceOf[INT];
    def isLPAR() = s.isInstanceOf[LPAR];
    def isRPAR() = s.isInstanceOf[RPAR];
    def isMINUS() = s.isInstanceOf[MINUS];
    def isOP() = s.isInstanceOf[OP];
    def isIF() = s.isInstanceOf[IF];
    def isID() = s.isInstanceOf[ID];
    def isEQUAL() = s.isInstanceOf[EQUA];
    def isASSIGN() = s.isInstanceOf[ASSIGN];

    def asInt() = s.asInstanceOf[INT];
    def asLPAR() = s.asInstanceOf[LPAR];
    def asRPAR() = s.asInstanceOf[RPAR];
    def asMINUS() = s.asInstanceOf[MINUS];
    def asOP() = s.asInstanceOf[OP];
    def asIF() = s.asInstanceOf[IF];
    def asID() = s.asInstanceOf[ID];
    def asEQUAL() = s.asInstanceOf[EQUA]
    def asASSIGN() = s.asInstanceOf[ASSIGN];

  }
}