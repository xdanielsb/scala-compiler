package calc
import java.io.FileInputStream
import java.io.InputStream

import lexer.{Lexer, Token}

object TestLexer {

	// args - arg[0] is the filename of the file to analyze
	// (if it exists, otherwise the standard input stream is used).

	def main(args: Array[String]) {
		var is: InputStream = System.in;
		println(System.getProperty("user.dir"))
		var nameFile = "src/test/in01.test"
		// if ( args.length>0 ) is = new FileInputStream(args(0))
		is = new FileInputStream(nameFile)
		try {
			val lexer = new Lexer(is);
			val tokens:List[Token] = lexer.lex()
			// output of the result	
			println(tokens)
	 		println(tokens.size + " token(s) found")
		} catch {
			case e: Exception => e.printStackTrace()
		}
	}
}
