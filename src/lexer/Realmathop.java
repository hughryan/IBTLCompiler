package lexer;

public class Realmathop extends Token {

	public String value;
	
	public Realmathop(String v) {
		super("realmathop");
		value = v;
	}
	
	@Override public String toString() {
		String s = "";
		s += "<" + id.toString() + ", \"" + value.toString() + "\">";
		return s;
	}
}
