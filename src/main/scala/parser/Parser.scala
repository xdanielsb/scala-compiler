package parser

import lexer.Token

class Parser(var tokens: List[Token]) {

  def getAST(): Unit ={
    println(tokens)
  }
}
