package calc
import java.io.FileInputStream
import java.io.InputStream

import lexer.{Lexer, Token}
import parser.Parser

object Launcher {

	// args - arg[0] is the filename of the file to analyze
	// (if it exists, otherwise the standard input stream is used).

	def main(args: Array[String]) {
		var is: InputStream = System.in;
		println(System.getProperty("user.dir"))
		var nameFile = "src/test/in01.test"
		is = new FileInputStream(nameFile)
		try {
			val lexer = new Lexer(is);
			val tokens:List[Token] = lexer.lex()
			// for(t <- tokens) println(t)
			// println(tokens)
	 		// println(tokens.size + " token(s) found")
			var parser = new Parser(tokens);
			parser.getAST();
		} catch {
			case e: Exception => e.printStackTrace()
		}
	}
}
