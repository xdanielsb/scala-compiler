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
      return s.matches("^[A-Za-z]+.*[0-9]*$");
    }

  }
}