package lexer;

public class Token {

	public String id = new String();
	
	public Token() {
		id = null;
	}
	
	public Token(String s) {
		id = s;
	}
	
	public String toString() {
		String s = "";
		s += "<" + id.toString() + ">";
		return s;
	}
	
}
