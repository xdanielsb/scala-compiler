package lexer
import lexer.StringUtils.StringImprovements

import java.io.{IOException, InputStream}
import scala.util.control.Breaks.{break, breakable}


class Lexer(in: InputStream) {
	private var chr = in.read(); // current ASCII character (coded as an integer)
	private var buf = ""; // buffer
	
	def lex(): List[Token] = {
		// return the list of tokens recorded in the file
		var tokens: List[Token] = Nil
		var token: Token = null // safe use of null will never be used

		while(chr != -1)  {
			// println(chr.toChar, chr);
			if( chr.toChar == ' ' || chr == 10){
				if( buf.length > 0){
					token = getToken()
					tokens ::= token
					buf = ""
				}
			}else{
					buf += chr.toChar
			}
			chr = in.read()
		}
		if( buf.length >0)
			println(buf)
		in.close();

		tokens.reverse
	}

	def getToken(): Token = {
		if( buf.isInt ) return INT
		if( buf == "(") return LPAR
		if( buf == ")") return RPAR
		else return KEY
	}
}


