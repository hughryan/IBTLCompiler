package lexer;

public class Intconst extends Token {

	public int value;
	
	public Intconst(int v) {
		super("intconst");
		value = v;
	}
	
	@Override public String toString() {
		String s = "";
		s += "<" + id.toString() + ", \"" + Integer.toString(value) + "\">";
		return s;
	}
}
