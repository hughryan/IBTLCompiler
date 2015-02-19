package lexer;

public class Stringvalue extends Token {

	public String value = "";
	
	public Stringvalue(String v) {
		super("stringvalue");
		value = v;
	}
	
	@Override public String toString() {
		String s = "";
		s += "<" + id.toString() + ", \"" + value.toString() + "\">";
		return s;
	}
}
