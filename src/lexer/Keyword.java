package lexer;

public class Keyword extends Token {

	public String value;
	
	public Keyword(String v) {
		super("keyword");
		value = v;
	}
	
	@Override public String toString() {
		String s = "";
		s += "<" + id.toString() + ", \"" + value.toString() + "\">";
		return s;
	}
}
