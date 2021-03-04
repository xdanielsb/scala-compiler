package parser

import lexer.{ASSIGN, EQUA, FUNDEF, ID, IF, INT, LPAR, MINUS, OP, RPAR, Token}

object TokenUtils {

  implicit class TokenImprovements(val s: Token) {
    def isInt: Boolean = s.isInstanceOf[INT]

    def isLPAR: Boolean = s.isInstanceOf[LPAR]

    def isRPAR: Boolean = s.isInstanceOf[RPAR]

    def isMINUS: Boolean = s.isInstanceOf[MINUS]

    def isOP: Boolean = s.isInstanceOf[OP]

    def isIF: Boolean = s.isInstanceOf[IF]

    def isID: Boolean = s.isInstanceOf[ID]

    def isEQUAL: Boolean = s.isInstanceOf[EQUA]

    def isASSIGN: Boolean = s.isInstanceOf[ASSIGN]

    def isFUN: Boolean = s.isInstanceOf[FUNDEF]; // gc: haha is 'fun' :3

    def asInt(): INT = s.asInstanceOf[INT]

    def asLPAR(): LPAR = s.asInstanceOf[LPAR]

    def asRPAR(): RPAR = s.asInstanceOf[RPAR]

    def asMINUS(): MINUS = s.asInstanceOf[MINUS]

    def asOP(): OP = s.asInstanceOf[OP]

    def asIF(): IF = s.asInstanceOf[IF]

    def asID(): ID = s.asInstanceOf[ID]

    def asEQUAL(): EQUA = s.asInstanceOf[EQUA]

    def asASSIGN(): ASSIGN = s.asInstanceOf[ASSIGN]

    def asFUN(): FUNDEF = s.asInstanceOf[FUNDEF]; // pertinent comment: boring to 'fun'

  }

}