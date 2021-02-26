package parser

import lexer.{INT, LPAR, Token, UnexpectedCharacter}
import parser.TokenUtils.TokenImprovements
class Parser(var tokens: List[Token]) {

  def buildTree(): Exp ={
    val y = pop()
    var ans:Exp = null
    if (y.isInt)  return IntLit(y.getValue());
    if (y.isLPAR) {
      val z = pop()
      if( z.isMINUS  && (tokens.head.isInt != true || tokens.lift(1) == None || tokens(1).isInt != true) ) {
        // this case (-1) ( -1 3) (-1 ( 3)) (- (- 1 0))
        ans = MinExp(z.asMINUS(), buildTree());
      }else if(z.isOP ){
        ans = BinExp(z.asOP(), buildTree(), buildTree());
      }else if(z.isIF){
        ans = ifExp(z.asIF(), buildTree(), buildTree(), buildTree());
      }
      if(tokens.length == 0 || !pop().isRPAR) throw new SyntaxError("E01: Missing closing parenthesis in expression.")
    }
    assert(ans != null)
    return ans;
  }

  def pop(): Token={
    // pop front
    val y = tokens.head
    tokens = tokens.tail
    return y
  }

  //def toString(x: Exp): String={
  //  return x.toString
  //}

}
