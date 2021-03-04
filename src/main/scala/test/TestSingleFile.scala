package test

import lexer.{ID, Lexer}
import parser.{FunDef, Parser}
import evaluator.Evaluator

import java.io.{BufferedInputStream, FileInputStream, InputStream}

object TestSingleFile {

  def interpret(nameFile: String): Int = {
    val file = scala.io.Source.fromFile(nameFile).mkString
    println(nameFile)
    println(file)

    //1 - we get the tokens
    val lexer = new Lexer(new BufferedInputStream(new FileInputStream((nameFile))));
    val tokens = lexer.getTokens
    println(tokens)

    //2 - we build the syntax tree
    val parser = new Parser(tokens);
    val ast = parser.buildTree();
    println(ast)

    //3 - we evaluate the tree
    val inspect = new Evaluator(parser.functor)
    val res = inspect.eval(ast, Map());
    return res
  }

  def main(args: Array[String]) {
    var is: InputStream = System.in;
    // am I asleep?
    var nameFile = "src/test/errorGarbage2.calc"
    try {
      val res = interpret(nameFile);
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}

