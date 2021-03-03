package parser

import lexer.{ASSIGN, EQUA, FUNDEF, ID, IF, INT, LPAR, MINUS, OP, RPAR, Token}

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
    def isFUN() = s.isInstanceOf[FUNDEF]; // gc: haha is 'fun' :3

    def asInt() = s.asInstanceOf[INT];
    def asLPAR() = s.asInstanceOf[LPAR];
    def asRPAR() = s.asInstanceOf[RPAR];
    def asMINUS() = s.asInstanceOf[MINUS];
    def asOP() = s.asInstanceOf[OP];
    def asIF() = s.asInstanceOf[IF];
    def asID() = s.asInstanceOf[ID];
    def asEQUAL() = s.asInstanceOf[EQUA]
    def asASSIGN() = s.asInstanceOf[ASSIGN];
    def asFUN() = s.asInstanceOf[FUNDEF]; // pertinent comment: boring to 'fun'

  }
}