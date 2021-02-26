package calc
import java.io.FileInputStream
import java.io.InputStream
import lexer.{Lexer, Token}
import parser.{AST, Parser}

object Launcher {

	def interpret(nameFile: String): Int ={
		val lexer = new Lexer(new FileInputStream(nameFile));
		val tokens = lexer.getTokens();
		println(tokens)
		val ast = new Parser(tokens).buildTree();
		println(ast)
		return ast.eval;
	}
	def main(args: Array[String]) {
		var is: InputStream = System.in;
		// where am i?
		var nameFile = "src/test/greenUnExpMinus2.calc"
		try {
			val res = interpret(nameFile);
			println(res);
			println("")
		} catch {
			case e: Exception => e.printStackTrace()
		}
	}
}
