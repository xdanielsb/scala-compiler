package lexer

import scala.util.Try

object StringUtils {
  implicit class StringImprovements(val s: String) {

    def isInt(): Boolean={
      return Try{ s.toInt }.isSuccess
    }

    def hasSpecialChars(): Boolean ={
      return !s.forall(_.isLetterOrDigit);
    }

    def isIdentifier(): Boolean ={
      return s.matches("^[a-z]+.*[0-9]*$");
    }

    def isFun(): Boolean={
      return s.equals("defun")
    }

    def isIf(): Boolean={
      return s.equals("if")
    }

  }
}