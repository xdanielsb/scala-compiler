package parser

import lexer.{INT, LPAR, Token, UnexpectedCharacter}
import parser.TokenUtils.TokenImprovements
class Parser(var tokens: List[Token]) {

  def parse(): Exp ={
    val y = pop()
    var ans:Exp = null
    if (y.isInt)  return IntLit(y.getValue());
    if (y.isLPAR) {
      val z = pop()
      if( z.isMINUS ) {
        ans = MinExp(z.asMINUS(), parse());
      }else if(z.isOP ){
        ans = BinExp(z.asOP(), parse(), parse());
      }else if(z.isIF){
        ans = ifExp(z.asIF(), parse(), parse(), parse());
      }
      if(tokens.length == 0 || !pop().isRPAR) throw new SyntaxError("E01: Missing closing parenthesis in expression.")
      return ans;
    }
    return IntLit("a");
  }

  def pop(): Token={
    // pop front
    val y = tokens.head
    tokens = tokens.tail
    return y
  }

  def toString(x: Exp): String={
    return x.toString
  }


}
