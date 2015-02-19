package lexer;

public class Realconst extends Token {

	public float value;
	
	public Realconst(float v) {
		super("realconst");
		value = v;
	}
	
	@Override public String toString() {
		String s = "";
		s += "<" + id.toString() + ", \"" + Float.toString(value) + "\">";
		return s;
	}
}
