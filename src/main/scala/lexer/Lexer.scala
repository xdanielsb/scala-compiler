package lexer
import lexer.StringUtils.StringImprovements

import java.io.{IOException, InputStream}
import scala.util.control.Breaks.{break, breakable}

class Lexer(in: InputStream) {
	private var chr = in.read(); // current ASCII character (coded as an integer)
	private var buf = ""; // buffer
	private var tokens: List[Token] = Nil

	def lex(): List[Token] = {
		// return the list of tokens recorded in the file
		while(chr != -1)  {
			if( chr.toChar == ' ' || chr == 10){
				addToken();
			} else if( chr.toChar == '(' || chr.toChar ==')'){
				// this logic can be removed if the file is preprocessed
				addToken();
				buf += chr.toChar;
				addToken();
			}
			else{
					buf += chr.toChar;
			}
			chr = in.read()
		}
		addToken();
		in.close();

		tokens.reverse
	}
	def addToken(): Unit ={
		if( buf.length > 0){
			tokens ::= getToken(buf)
			buf = ""
		}
	}

	def getToken(buf: String): Token = {
		if( buf.isInt ) return INT
		if( buf == "(") return LPAR
		if( buf == ")") return RPAR
		if( buf == "+") return PLUS
		if( buf == "-") return MINUS
		if( buf == "*") return MUL
		if( buf == "/") return DIV
		if( buf == "if") return IF
		if( buf.isIdentifier() ) return ID
		if( buf.hasSpecialChars())	throw new UnexpectedCharacter(buf)
		else return FOO
	}

}


