package lexer
import lexer.StringUtils.StringImprovements

import java.io.{IOException, InputStream}
import scala.util.control.Breaks.{break, breakable}

class Lexer(in: InputStream) {
	private var chr = in.read(); // current ASCII character (coded as an integer)
	private var buf = ""; // buffer
	private var tokens: List[Token] = Nil

	def getTokens(): List[Token] = {
		// return the list of tokens recorded in the file
		while(chr != -1)  {
			if( chr.toChar == ' ' || chr == 10 || chr == 13 || chr == 9) {
				// 13 for pre-OS X
				addToken();
			} else if(buf.length == 0 && chr.toChar == '0'){
				// to tackle 00 -> 0 0
				buf += chr.toChar;
				addToken();
			}
			else if(tokens.length > 0 && chr.toChar == '-' && tokens.last.getValue()=="("){
				// to tackle this: (-2), (--2)
				assert(buf.length == 0)
				buf += chr.toChar;
				addToken();
			} else if( chr.toChar == '(' || chr.toChar ==')'){
				// this logic can be removed if the file is preprocessed
				addToken();
				buf += chr.toChar;
				addToken();
			} else{
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
		if( buf == "(") return LPAR(buf)
		if( buf == ")") return RPAR(buf)
		if( buf == "+") return PLUS(buf)
		if( buf == "-") return MINUS(buf)
		if( buf == "*") return MUL(buf)
		if( buf == "<") return LESS(buf)
		if( buf == "==") return EQUA(buf)
		if( buf == "/") return DIV(buf)
		if( buf.isInt ) return INT(buf)
		if( buf == "if") return IF(buf)
		if( buf.isIdentifier() ) return ID(buf)
		if( buf.hasSpecialChars())	throw new UnexpectedCharacter(buf)
		else return FOO(buf) //  :'( shouldn't
	}

}


