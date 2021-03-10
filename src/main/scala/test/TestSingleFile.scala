package test

import evaluator.Evaluator
import lexer.Lexer
import parser.Parser

import java.io.{BufferedInputStream, FileInputStream}

object TestSingleFile {

  def interpret(nameFile: String, verbose: Boolean = false): Int = {
    val file = scala.io.Source.fromFile(nameFile).mkString
    println(nameFile)
    println(file)

    //1 - we get the tokens
    val lexer = new Lexer(new BufferedInputStream(new FileInputStream(nameFile)))
    val tokens = lexer.getTokens
    if( verbose){
      println("Tokens: "+ tokens)
    }

    //2 - we build the syntax tree
    val parser = new Parser(tokens)

    val ast = parser.build()
   // val ast = parser.buildTree()

    if(verbose){
      println("Syntax tree", ast)
    }

    //3 - we evaluate the tree
    val inspect = new Evaluator(parser.functions)
    val res = inspect.evaluate(ast)

    if(verbose){
      println("Evaluation of the tree: " + res)
    }

    res
  }

  def main(args: Array[String]) {
    // am I asleep?
    val nameFile = "src/test/redFib3.calc"
    try {
      interpret(nameFile, verbose = true)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}

