package lexer
import java.io.{IOException, InputStream}

class Lexer(in: InputStream) {
	private var i = in.read(); // current ASCII character (coded as an integer)
	
	def lex(): List[Token] = {
		// return the list of tokens recorded in the file
		var tokens: List[Token] = Nil
		
		try {
			var token: Token = null // safe use of null will never be used
			do {
				token = getToken()
				tokens ::= token
			} while (token != EOF)
		} catch {
			case e: IOException => {
				in.close() // close the reader
				throw e // pass the exception up the stack
			}
		}
		tokens.reverse
	}

	def getToken(): Token = i match {
		case -1 => { in.close(); EOF }
		case _ => throw new UnexpectedCharacter(i)
	}
}


