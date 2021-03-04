package calc
import java.io.FileInputStream
import java.io.InputStream
import lexer.{Lexer, Token}
import parser.{AST, Parser}

object Launcher {

	def interpret(nameFile: String): Int ={
		val file = scala.io.Source.fromFile(nameFile).mkString
		println(nameFile)
	//	println(file)
		val lexer = new Lexer(new FileInputStream(nameFile));
		val tokens = lexer.getTokens();
		println(tokens)
		val parser = new Parser(tokens);
		val ast = parser.buildTree();
		println(ast)

		return parser.eval(ast, Map());
	}
	def main(args: Array[String]) {
		// (= a 1) (= b 2) (+ a b)
		var is: InputStream = System.in;
		// where am i?
		var nameFile = "src/test/redParam.calc"
		try {
			val res = interpret(nameFile);
			println(res);
			println("")
		} catch {
			case e: Exception => e.printStackTrace()
		}
	}
}
