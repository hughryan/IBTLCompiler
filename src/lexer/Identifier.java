package lexer;

public class Identifier extends Token {

	public String value;
	
	public Identifier(String v) {
		super("identifier");
		value = v;
	}
	
	@Override public String toString() {
		String s = "";
		s += "<" + id.toString() + ", \"" + value.toString() + "\">";
		return s;
	}
}
