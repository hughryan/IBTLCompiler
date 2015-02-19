package lexer;

public class Type extends Token {
	
	public String value = "";
	
	public Type(String v) {
		super("type");
		value = v;
	}
	
	@Override public String toString() {
		String s = "";
		s += "<" + id.toString() + ", \"" + value.toString() + "\">";
		return s;
	}
	
}
