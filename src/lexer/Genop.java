package lexer;

public class Genop extends Token {

	public String value;
	
	public Genop(String v) {
		super("genop");
		value = v;
	}
	
	@Override public String toString() {
		String s = "";
		s += "<" + id.toString() + ", \"" + value.toString() + "\">";
		return s;
	}
}
