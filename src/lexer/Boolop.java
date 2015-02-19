package lexer;

public class Boolop extends Token {
	
	public String value = "";
	
	public Boolop(String v) {
		super("boolop");
		value = v;
	}
	
	@Override public String toString() {
		String s = "";
		s += "<" + id.toString() + ", \"" + value.toString() + "\">";
		return s;
	}
	
}
