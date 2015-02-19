package lexer;

public class Relop extends Token {

	public String value;
	
	public Relop(String v) {
		super("relop");
		value = v;
	}
	
	@Override public String toString() {
		String s = "";
		s += "<" + id.toString() + ", \"" + value.toString() + "\">";
		return s;
	}
}
