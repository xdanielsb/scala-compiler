package lexer

import scala.util.Try

object StringUtils {

  implicit class StringImprovements(val s: String) {

    def isInt: Boolean = {
      Try {
        s.toInt
      }.isSuccess
    }

    def hasSpecialChars: Boolean = {
      !s.forall(_.isLetterOrDigit)
    }

    def isIdentifier: Boolean = {
      s.matches("^[a-z]+.*[0-9]*$")
    }

    def isFun: Boolean = {
      s.equals("defun")
    }

    def isIf: Boolean = {
      s.equals("if")
    }

  }

}