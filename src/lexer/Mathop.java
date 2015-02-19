package lexer;

public class Mathop extends Token {

	public String value;
	
	public Mathop(String v) {
		super("mathop");
		value = v;
	}
	
	@Override public String toString() {
		String s = "";
		s += "<" + id.toString() + ", \"" + value.toString() + "\">";
		return s;
	}
}
