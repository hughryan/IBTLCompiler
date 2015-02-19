package lexer;

public class Boolconst extends Token {
	
	public String value = "";
	
	public Boolconst(String v) {
		super("boolconst");
		value = v;
	}
	
	@Override public String toString() {
		String s = "";
		s += "<" + id.toString() + ", \"" + value.toString() + "\">";
		return s;
	}
	
}
