package lexer
import lexer.StringUtils.StringImprovements
import parser.SyntaxError

import java.io.{IOException, InputStream}
import scala.util.control.Breaks.{break, breakable}

class Lexer(in: InputStream) {
	private var chr = in.read(); // current ASCII character (coded as an integer)
	private var buf = ""; // buffer
	private var tokens: List[Token] = Nil

	def getTokens(): List[Token] = {
		// return: list of tokens recorded in the file
		while(chr != -1)  {
			if( chr.toChar == ' ' || chr == 10 || chr == 13 || chr == 9) {
				// info: maybe a better option: 1 <= chr <= 32 ?
				// 13 for pre-OS X
				addToken();
			} else if(buf.length == 0 && chr.toChar == '0'){
				// info: to tackle: 00 -> 0 0
				buf += chr.toChar;
				addToken();
			} else if(tokens.length > 0 && chr.toChar == '-' && tokens.last.getValue()=="("){
				// info: to tackle this: (-2), (--2)
				assert(buf.length == 0)
				buf += chr.toChar;
				addToken();
			} else if( chr.toChar == '(' || chr.toChar ==')'){
				// info: this logic can be removed if the file is preprocessed
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
			val token = getToken(buf)
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
		if( buf == "=") return ASSIGN(buf)
		if( buf == "/") return DIV(buf)
		if( buf.isInt ) return INT(buf)
		if( buf.isIf) return IF(buf)
		if( buf.isFun) return FUNDEF(buf);
		if( buf.isIdentifier) return ID(buf)
		if( buf.hasSpecialChars)	throw new UnexpectedCharacter(buf)
		else throw new UndefinedToken(buf) //  :'( shouldn't
	}

}
