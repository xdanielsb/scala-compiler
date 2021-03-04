package parser

import lexer.{FUNDEF, ID, INT, LPAR, Token, UnexpectedCharacter}
import parser.TokenUtils.TokenImprovements
import parser.ExpUtils.ExpImprovements

import scala.util.Try


class Parser(var tokens: List[Token]) {

  var functor: Map[ID, FunDef] = Map()

  def eval(s: Exp, ctx: Map[ID ,Exp] ): Int ={
    if (s.isIntLit()) return s.asIntLit().toInt
    if(s.isCall()){
      val v = s.asCall().getID
      var args= s.asCall().getArgs.map(x => IntLit(eval(x, ctx)))

      if(functor.contains(v)) {
        val fu = functor(v)
        val params = fu.getHead.getParams
        val ctx2 = ctx ++ (params zip args) toMap;
        return eval(fu.getBody.getEx, ctx2) // Todo change to ctx2
      }
    }
    if (s.isVar()){
      val v = s.asVar().getID
      if(ctx.contains(v)){
        // TODO: may this happen?. recursion i.e ( = a 1 )  ( = b a ) ( = c b ) --> dfs
        var yak = eval(ctx(v), ctx)
        return yak
      }
      return eval(ctx(v), ctx)
    }
    if (s.isMinExp()) return -eval(s.asMinExp().getExp, ctx)
    if( s.isBinExp()) {
      val bin = s.asBinExp()
      var a1 = eval(bin.getE1, ctx);
      var a2 = eval( bin.getE2, ctx);
      return bin.getOp.eval(a1, a2);
    }
    if (s.isIfExp()){
      val ife = s.asIfExp()
      if(eval(ife.getE1, ctx ) != 0) return  eval(ife.getE2, ctx)
      return eval(ife.getE3, ctx)
    }
    if ( s.isLinkedExp()){
      val lx = s.asLinkedExp()
      if( lx.getE1.isVarDef()){
        val ex1 = lx.getE1.asVarDef()
        if( ex1.getEx.isVar()  ) {
          // info: just query the element to check if exist
          ctx(ex1.getEx.asVar().getID)
        }
        val ctx2 = ctx ++ collection.immutable.Map( ex1.getID -> ex1.getEx)
        return eval(lx.getE2, ctx2)
      }else{
       // eval(lx.getE1, ctx);
      }
      return eval(lx.getE2, ctx)
    }
    if (s.isVarDef()){
      val vd = s.asVarDef()
      if(Try{ eval(vd.getEx, ctx) }.isSuccess){
        return eval(vd.getEx, ctx)
      }else{
        throw new SyntaxError("E04: Variable "+ vd.getID + " not defined")
      }
    }
    if( s.isFunDef()){

    }
    return -10000
  }

  def buildTree(): Exp ={
    val y = pop()
    var ans:Exp = null

    if (y.isInt)  return IntLit(y.getValue().toInt)
    if(y.isID())  return Var(y.asID())

    if (y.isLPAR) {
      val z = pop()
      if (z.isID()){
        var arguments: List[Exp] = Nil
        while(tokens.nonEmpty && !tokens.head.isRPAR()) arguments ::= buildTree()
        ans = Call(z.asID(), arguments)
        // info: is it possible to have multiple calls?
      } else if( z.isMINUS  && isUnaryMinus() ) {
        // info: this case tackle: i.e (-1) ( -1 3) (-1 ( 3)) (- (- 1 0))
        ans = MinExp(z.asMINUS(), buildTree())
      }else if(z.isASSIGN() && tokens.head.isID){
        ans = getVarDef(z)
      }else if(z.isOP ){
        ans = BinExp(z.asOP(), buildTree(), buildTree())
      }else if(z.isIF){
        ans = IfExp(z.asIF(), buildTree(), buildTree(), buildTree())
      }else if(z.isFUN()){
        // info : init head
        if( !pop().isLPAR()) throw new SyntaxError("E05: Wrong syntax, missing (.")
        val funID= pop()
        if( !funID.isID()) throw new SyntaxError("E05: Wrong syntax, defun should be followed by an id.")
        var parameters: List[ID] = Nil
        while(tokens.head.isID) parameters ::= pop().asID() // info: should be fun add a type
        var head = Head(funID.asID(), parameters)
        // TODO: save function and metadata info in a symbols table
        if( !pop().isRPAR()) throw new SyntaxError("E05: Wrong syntax, missing ).") // pertinent comment: common dude is not a 
        // info : end head
        // info : init body
        var body = Body(buildTree())  // Note: if there are variables inside here have a different scope
        ans = FunDef(head, body)
        addFunction(funID.asID(), ans.asFunDef())
      }
      if(tokens.length == 0 || !pop().isRPAR)
        throw new SyntaxError("E01: Missing closing parenthesis in expression.")
    }
    if ((ans.isInstanceOf[VarDef]  || ans.isInstanceOf[FunDef]) && tokens.nonEmpty) {
      // info: you can have some variable definitions linked
      ans = LinkedExp(ans, buildTree())
    }
    return ans
  }

  def pop(): Token={
    // info: pop front
    // todo: check if stack is void and throw an error
    val y = tokens.head
    tokens = tokens.tail
    return y
  }

  def addFunction(id: ID, v : FunDef): Unit = {
    functor = functor + (id -> v)
  }

  def isUnaryMinus(): Boolean = {
    val aux = tokens(1)
    if( tokens.head.isID()  && (aux.isID() || aux.isInt())) return false
    return (tokens.head.isInt != true || tokens.lift(1) == None || tokens(1).isInt != true)
  }

  def getVarDef(z: Token): VarDef = {
    // TODO: variables with different scope?
    val vid = pop().asID()
    val exp = buildTree()
    return VarDef(z.asASSIGN(), vid, exp) // TODO: do I need to add it to the ast? maybe sort a of heap
  }

  //def toString(x: Exp): String={
  //  return x.toString
  //}

}

