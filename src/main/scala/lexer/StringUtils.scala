package lexer

object StringUtils {
  implicit class StringImprovements(val s: String) {

    def isInt(): Boolean={
      return s.forall(_.isDigit)
    }

    def hasSpecialChars(): Boolean ={
      return !s.forall(_.isLetterOrDigit);
    }

    def isIdentifier(): Boolean ={
      return s.matches("^[A-Za-z]+.*[0-9]*$");
    }

  }
}