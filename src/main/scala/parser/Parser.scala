package parser

import lexer.{ID, INT, LPAR, Token, UnexpectedCharacter}
import parser.TokenUtils.TokenImprovements

import scala.util.Try
class Parser(var tokens: List[Token]) {

  var symbols: Map[ID ,String] = Map();

  def buildTree(): Exp ={
    val y = pop()
    var ans:Exp = null
    if (y.isInt)  return IntLit(y.getValue());
    else if(y.isID()){
      if(symbols.contains(y.asID())){
        return IntLit(symbols(y.asID()));
      }else{
        // TODO: maybe this could happen?. recursion i.e ( = a 1 )  ( = b a ) ( = c b ) --> dfs
        throw new SyntaxError("E05:Variable not defined " +y.asID())
      }
    }
    if (y.isLPAR) {
      val z = pop()
      if( z.isMINUS  && isUnaryMinus() ) {
        // info: this case tackle: i.e (-1) ( -1 3) (-1 ( 3)) (- (- 1 0))
        ans = MinExp(z.asMINUS(), buildTree());
      }else if(z.isASSIGN() && tokens.head.isID){
          // TODO: variables with different scope?
          val vid = pop().asID()
          val exp = buildTree()
          //varDef(z.asASSIGN(), vid, exp); // TODO: do I need to add it to the ast? maybe symbols table
          if(Try{ exp.eval }.isSuccess){
            addSymbol(vid, exp.eval.toString);
          }else{
            throw new SyntaxError("E04: Variable "+ vid + " not produce a value")
          }
      }else if(z.isOP ){
        ans = BinExp(z.asOP(), buildTree(), buildTree());
      }else if(z.isIF){
        ans = ifExp(z.asIF(), buildTree(), buildTree(), buildTree());
      }
      if(tokens.length == 0 || !pop().isRPAR)
        throw new SyntaxError("E01: Missing closing parenthesis in expression.")
    }
    if (ans == null && tokens.length > 0)
      ans = buildTree()
   // assert(ans != null)

    return ans;
  }

  def pop(): Token={
    // info: pop front
    val y = tokens.head
    tokens = tokens.tail
    return y
  }

  def addSymbol(id: ID, v : String): Unit = {
    // println(id +" -> " + v)
    symbols = symbols + (id -> v)
  }

  def isUnaryMinus(): Boolean = {
    val aux = tokens(1)
    if( tokens.head.isID()  && (aux.isID() || aux.isInt())) return false;
    return (tokens.head.isInt != true || tokens.lift(1) == None || tokens(1).isInt != true)
  }

  //def toString(x: Exp): String={
  //  return x.toString
  //}

}
