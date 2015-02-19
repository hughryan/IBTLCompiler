/*
 * Hugh McDonald
 * 2/16/15
 */
package lexer;

import java.io.*;
import java.util.*;

public class Lexer {
	
	public int line = 1;
	char peek = ' ';
	Hashtable<String, Token> tokens = new Hashtable<String, Token>();
	File file = null;
	InputStream filestream = null;
	Reader filereader = null;
	
	public Lexer(String f) throws IOException {
		// Open Source File
		file = new File(f);
		
		try {
			filestream = new FileInputStream(file);
			filereader = new InputStreamReader(filestream, "UTF-8");
			
		} catch (IOException e) {
			throw e;
		}
		
		// Type
		tokens.put("bool", new Type("bool"));
		tokens.put("int", new Type("int"));
		tokens.put("real", new Type("real"));
		tokens.put("string", new Type("string"));
		
		// Boolconst
		tokens.put("true", new Boolconst("true"));
		tokens.put("false", new Boolconst("false"));
		
		// Boolop
		tokens.put("and", new Boolop("and"));
		tokens.put("or", new Boolop("or"));
		tokens.put("not", new Boolop("not"));
		
		// Mathop
		tokens.put("-", new Mathop("-"));
		tokens.put("*", new Mathop("*"));
		tokens.put("/", new Mathop("/"));
		tokens.put("%", new Mathop("%"));
		tokens.put("^", new Mathop("^"));
		
		// Relop
		tokens.put("=", new Relop("="));
		tokens.put("<", new Relop("<"));
		tokens.put(">", new Relop(">"));
		tokens.put("<=", new Relop("<="));
		tokens.put(">=", new Relop(">="));
		tokens.put("!=", new Relop("!="));
		
		// Realmathop
		tokens.put("sin", new Realmathop("sin"));
		tokens.put("cos", new Realmathop("cos"));
		tokens.put("tan", new Realmathop("tan"));
		
		// Genop
		tokens.put("+", new Genop("+"));
		
		// Keywords
		tokens.put("let", new Keyword("let"));
		tokens.put("(", new Keyword("("));
		tokens.put(")", new Keyword(")"));
		tokens.put(":=", new Keyword(":="));
		tokens.put("stdout", new Keyword("stdout"));
		tokens.put("if", new Keyword("if"));
		tokens.put("while", new Keyword("while"));
	}
	
	void readch() throws IOException {
			int i = filereader.read();
			if (i == -1) {
				throw new EOFException();
			} else {
				peek = (char)i;
			}
	}
	
	boolean readch(char c) throws IOException {
		readch();
		if (peek != c) return false;
		peek = ' ';
		return true;
	}
	
	public Token nextToken() throws IOException {
		
		// Skip over whitespace, tabs, carriage returns and newlines, increment line if newline
		for( ; ; readch() ) {
			if (peek == ' ' || peek == '\t' || peek == '\r') {
				continue;
			} else if (peek == '\n') {
				line = line + 1;
				continue;
			}
			else break;
		}

			
		// Check for String with "
		if (peek == '"') {
			StringBuffer b = new StringBuffer();
			readch();
			while (peek != '"') {
				b.append(peek);
				readch();
			}
			
			String s = b.toString();
			peek = ' ';
			return new Stringvalue(s);
		}
			
			
		// Check for String with '
		if (peek == '\'') {
			StringBuffer b = new StringBuffer();
			readch();
			while (peek != '\'') {
				b.append(peek);
				readch();
			}
			
			String s = b.toString();
			peek = ' ';
			return new Stringvalue(s);
		}
		
		// Check for Non-Alphanumerics that may be longer than one character
		switch(peek) {
		case '<':
			if (readch('=')) return tokens.get("<="); else return tokens.get("<");
		case '>':
			if (readch('=')) return tokens.get(">="); else return tokens.get(">");
		case '!':
			if (readch('=')) return tokens.get("!=");
		case ':':
			if (readch('=')) return tokens.get(":=");
		}
		
		// Check for Digits
		if (Character.isDigit(peek)) {
			int i = 0;
			do {
				i = 10 * i + Character.digit(peek, 10);
				readch();
			} while (Character.isDigit(peek));
			// The number was an integer
			if (peek != '.' && peek != 'e' && peek != 'E') {
				return new Intconst(i);
			} else if (peek == 'e' || peek == 'E') {
			// It's a float
				float f = i;
				readch();
				if (peek == '-') {
					f = f * -1;
					readch();
				}
				if (Character.isDigit(peek)) {
					int j = 0;
					do {
						j = 10 * j + Character.digit(peek, 10);
						readch();
					} while (Character.isDigit(peek));
					f = f * (float)Math.pow(10, j);
				}
				return new Realconst(f);
			} else if (peek == '.') {
				float f = i;
				float d = 10;
				while (true) {
					readch();
					if (!Character.isDigit(peek)) break;
					f = f + Character.digit(peek, 10) / d;
					d = d * 10;
				}
				if (peek != 'e' && peek != 'E') {
					return new Realconst(f);
				} else {
					readch();
					if (peek == '-') {
						f = f * -1;
						readch();
					}					
					if (Character.isDigit(peek)) {
						int j = 0;
						do {
							j = 10 * j + Character.digit(peek, 10);
							readch();
						} while (Character.isDigit(peek));
						f = f * (float)Math.pow(10, j);
					}
					return new Realconst(f);
				}
			}
		}
		
		// Check for Float starting with a period
		if (peek == '.') {
			float f = 0;
			float d = 10;
			readch();
			while (true) {
				readch();
				if (!Character.isDigit(peek)) break;
				f = f + Character.digit(peek, 10) / d;
				d = d * 10;
			}
			if (peek != 'e' && peek != 'E') {
				return new Realconst(f);
			}
			else if (peek == 'e' || peek == 'E') {
				readch();
				if (peek == '-') {
					f = f * -1;
					readch();
				}					
				if (Character.isDigit(peek)) {
					int j = 0;
					do {
						j = 10 * j + Character.digit(peek, 10);
						readch();
					} while (Character.isDigit(peek));
					f = f * (float)Math.pow(10, j);
				}
				return new Realconst(f);
			}
		}
		
		// Check for Letters
		if (Character.isLetter(peek)) {
			StringBuffer b = new StringBuffer();
			
			do {
				b.append(peek);
				readch();				
			} while (Character.isLetterOrDigit(peek));
			
			// Check for existing Token
			String s = b.toString();
			Token t = (Token)tokens.get(s);
			if (t != null) return t;
			
			// No existing Token, must be an Identifier
			tokens.put(s, new Identifier(s));
			return tokens.get(s);
			
		}
	
		// Fell through, check for single Non-Alphanumeric Tokens
		Token t = (Token)tokens.get(Character.toString(peek));
		if (t != null) {
			peek = ' ';
			return t;
		}
		
		// Fell through again, return a new Token
		t = new Token(Character.toString(peek));
		peek = ' ';
		return t;
	}
}
